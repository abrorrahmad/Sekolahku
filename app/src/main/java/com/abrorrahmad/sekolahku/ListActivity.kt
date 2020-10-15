package com.abrorrahmad.sekolahku

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.widget.SearchView //diubah gara2 error dibawah
import kotlinx.android.synthetic.main.activity_list.*
import java.text.FieldPosition

class ListActivity : AppCompatActivity() {

    lateinit var adapter: StudentArrayAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        

    }//end OnCreate

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.add){

            //arahkan ke from activity
            var intent = Intent(this,FormActivity::class.java)

            //jalankan activity
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {

        var studentDataSource = StudentDataSource(this)
        var allStudent = studentDataSource.getAllsStudent()

//        var listNamaLengkap = ArrayList<String>()
//
//        for (index in allStudent.indices){
//            listNamaLengkap.add(allStudent.get(index).namaDepan +
//                    ""+ allStudent.get(index).namaBelakang)
//        }
//
//        var adapter = ArrayAdapter(this,
//            android.R.layout.simple_list_item_1,listNamaLengkap)
//
//        listViewStudent.adapter = adapter
//        //push Perubahan Data / on reresh
//        adapter.notifyDataSetChanged()

        adapter = StudentArrayAdapter(this)
        adapter.addAll(allStudent)
        adapter.notifyDataSetChanged()
        listViewStudent.adapter = adapter

        listViewStudent.setOnItemClickListener(
            AdapterView.OnItemClickListener{
                parent, view, position, id ->  viewDetailStudent(position)
            }
        )

        //untuk menampilkan search

        searchStudent.setOnQueryTextListener (object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                //hapus semua list student
                adapter.clear()
                //panggil student data source
                var studentDataSource = StudentDataSource(this@ListActivity)

                //panggil funtion search
                var student = studentDataSource.search(newText!!)

                //kembali adapter
                adapter.addAll(student)
                adapter.notifyDataSetChanged()
                return false
            }
        })//akhir search

        //meregister context menu

        registerForContextMenu(listViewStudent)

        super.onResume()
    }//end on resum

    fun viewDetailStudent(position: Int){

        var intent = Intent(this,DetailActivity::class.java)
        var student = adapter.getItem(position)

        //send parameter
        intent.putExtra("id",student?.id)
        startActivity(intent)
    }

    override fun onCreateContextMenu( //awal inplate
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menuInflater.inflate(R.menu.context_menu, menu)
        super.onCreateContextMenu(menu, v, menuInfo)
    }//akhir implate

    override fun onContextItemSelected(item: MenuItem): Boolean {
        var menuInfo = item?.menuInfo as AdapterView.AdapterContextMenuInfo

        var position = menuInfo.position

        var student = adapter.getItem(position)

        if (item.itemId == R.id.edit){
            var intent = Intent(this, FormActivity::class.java)
            intent.putExtra("id", student?.id)
            startActivity(intent)
        }

        if (item.itemId == R.id.delete){

            //delete
            var studentDataSource = StudentDataSource(this)
            studentDataSource.deleteStudent(student?.id)
            adapter.remove(student)
            adapter.notifyDataSetChanged()
        }


        return super.onContextItemSelected(item)
    }//akhir

}