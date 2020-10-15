package com.abrorrahmad.sekolahku

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.os.PersistableBundle
import android.text.TextUtils
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_form.*
import kotlinx.android.synthetic.main.activity_form.cbMembaca
import kotlinx.android.synthetic.main.activity_form.cbMenggambar
import kotlinx.android.synthetic.main.activity_form.cbMenulis
import kotlinx.android.synthetic.main.chechkbox_example.*
import java.util.*
import kotlin.collections.ArrayList

class FormActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {

        var tgl = "${dayOfMonth} - ${month + 1} - ${year}"
        inputTanggalLahir.setText(tgl)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        //Tampilkan Menu back
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

//        //object untuk si budi
//
//        var budi = Manusia ()
//        var dataBudi : String = budi.biodata("Budi",17,"Pria")
//
//        var yuni = Manusia ()
//        yuni.biodata("Yuni",17,"Wanita")
//
//        var Garuda = Elang ()
//        var dataGaruda : String = Garuda.satwa("Garuda",5,"Sumatra")
//
//        inputAlamat.setText(dataGaruda)


        btnSimpan.setOnClickListener {
            simpan()
        }

        inputTanggalLahir.setOnClickListener{
            showDialogChoosedate()
        }

        //get PARAMETER

        var id = intent.getIntExtra("id",0)
        if (id > 0){
            title = "Edit Student"
            showFormUpdateStudent(id)
        }else{
            title = "Create Student"
        }



    } //tutup on create

    fun showFormUpdateStudent(id: Int){
        var studentDataSource = StudentDataSource(this)
        var student = studentDataSource.getStudent(id)

        inputNamaDepan.setText(student.namaDepan)
        inpuNamaBelakang.setText(student.namaBelakang)
        inputEmail.setText(student.email)
        inputTanggalLahir.setText(student.tanggalLahir)
        inputNomorHp.setText(student.noHp)
        inputAlamat.setText(student.alamat)

        //gender
        var gender = student.gender
        if (gender.equals("Pria")){
            rbPria.isChecked = true
        }else{
            rbWanita.isChecked = true
        }

        //jenjang
        var jenjang = student.jenjang
        var adapter = spinnerjenjang.adapter as ArrayAdapter<String>

        //ambil posisi slected
        var jenjangSelected = adapter.getPosition(jenjang)
        spinnerjenjang.setSelection(jenjangSelected)

        //hobi
        var hobi = student.hobi
        if (hobi!!.contains("Membaca")) cbMembaca.isChecked = true
        if (hobi!!.contains("Menulis")) cbMenulis.isChecked = true
        if (hobi!!.contains("Menggambar")) cbMenggambar.isChecked = true

        btnSimpan.setText("UPDATE")


    }


    fun showDialogChoosedate(){//generate date di form activity
        var calendar = Calendar.getInstance()
        var datePicker = DatePickerDialog(this,
        this,
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH))
        datePicker.show()
    }//end

    fun simpan(){ //Start Funtion Simpan

        //cek semua data validasasi all
        if (validateAll() ==true){
            return
        }


        var valueNamaDepan = inputNamaDepan.text.toString()
        var valueNamaBelakang = inpuNamaBelakang.text.toString()
        var valueNohp = inputNomorHp.text.toString()
        var valueAlamat = inputAlamat.text.toString()
        var valueEmail = inputEmail.text.toString()
        var valueTanggalLahir = inputTanggalLahir.text.toString()

        //memanggil data dari Sinner
        var valueJenjang  = spinnerjenjang.selectedItem.toString()

        //mengambil data dari gender
        var valueGender = getSelectedGender()

        //mengambil data dari hobi
        var valueHobi = getSelectedHobi()

        //validasi Hobi
        if (valueHobi.isEmpty() || valueHobi == null){
            showToast("Minimal pilih salah satu hobi")
        }else {

            var student = Student()
            student.namaDepan = valueNamaDepan
            student.namaBelakang = valueNamaBelakang
            student.noHp = valueNohp
            student.gender = valueGender
            student.jenjang = valueJenjang
            student.hobi = valueHobi
            student.alamat = valueAlamat
            student.email = valueEmail
            student.tanggalLahir = valueTanggalLahir

            var studentDataSource = StudentDataSource(this)

            var id = intent.getIntExtra("id", 0)
            if (id > 0){
                studentDataSource.updateStudent(student, id)
                showToast("Berhasil Mengubah data")
            }else{
                studentDataSource.insertStudent(student)
                showToast("Berhasil Menyimpan data")
            }


//                //tampilkan toast
//                var biodata = """
//            Nama Depan : ${valueNamaDepan}
//            Nama Belakang : ${valueNamaBelakang}
//            Nomor Hp : ${valueNohp}
//            Gander : ${valueGender}
//            jenjang : ${valueJenjang}
//            Hobi : ${valueHobi}
//            Alamat : ${valueAlamat}"""



            //Setelah berhasil menyimpan data, arahkan ke list activity
            var intent = Intent(this,ListActivity::class.java)
            startActivity(intent)

            //Buang screen form activity
            finish()


        }//end if untuk validasi Hobi

    } //end funtion Simpan

    fun showToast(message: String){//start toast

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    }//end Toast

    fun getSelectedGender() : String{ //start selected Gender

        var gender : String

        if(rbPria.isChecked){
            gender = "pria"
        }else {
            gender = "wanita"
        }
        return gender

    }//end Selected Gender

    fun getSelectedHobi() : String { //start Hobi

        var listHobi = ArrayList<String>()

        if (cbMembaca.isChecked){
            listHobi.add("Membaca")
        }
        if (cbMenulis.isChecked){
            listHobi.add("Menulis")
        }

        if (cbMenggambar.isChecked){
            listHobi.add("Menggambar")
        }
        return TextUtils.join(",",listHobi).toString()

    }//end Hobi

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {//start Menu
        //menambahkan menu_save.xml kedalam form acty

        menuInflater.inflate(R.menu.menu_save, menu)
        return super.onCreateOptionsMenu(menu)

    }//end Menu

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        //jika menu save di klik maka
        if (item.itemId == R.id.save){//start
            simpan()//menjalankan action simpan apabila di klik
        }
        return super.onOptionsItemSelected(item)
    }//end

    fun validateNamaDepan() : Boolean{//membuat reques
        var namaDepan = inputNamaDepan.text.toString()
        if (namaDepan.isEmpty()){
            inputNamaDepan.setError("Nama Depan Wajib di isi")
            inputNamaDepan.requestFocus()
            return true
        }
        return false
    }//akhir request

    fun validateNamaBelakang() : Boolean{//membuat reques
    var namaBelakang = inpuNamaBelakang.text.toString()
    if (namaBelakang.isEmpty()){
        inpuNamaBelakang.setError("Nama belakang Wajib di isi")
        inpuNamaBelakang.requestFocus()
        return true
    }
    return false
    }//akhir request

    fun validateNoHp() : Boolean{//membuat reques
        var noHp = inputNomorHp.text.toString()
        if (noHp.isEmpty()){
            inputNomorHp.setError("No Hp Wajib di isi")
            inputNomorHp.requestFocus()
            return true
        }
        return false
    }//akhir request

    fun validateAlamat() : Boolean{//membuat reques
        var alamat = inputAlamat.text.toString()
        if (alamat.isEmpty()){
            inputAlamat.setError("Alamat  Wajib di isi")
            inputAlamat.requestFocus()
            return true
        }
        return false
    }//akhir request

    fun validatEmail() : Boolean{//membuat reques
        var email = inputEmail.text.toString()
        if (email.isEmpty()){
            inputEmail.setError("Email  Wajib di isi")
            inputEmail.requestFocus()
            return true
        }
        return false
    }//akhir request


    fun validatTanggalLahir() : Boolean{//membuat reques
        var tanggallahir = inputTanggalLahir.text.toString()
        if (tanggallahir.isEmpty()){
            inputTanggalLahir.setError("Tanggal Lahir Wajib di isi")
            inputTanggalLahir.requestFocus()
            return true
        }
        return false
    }//akhir request



    fun validateAll() : Boolean { //start validate all
        if (validateNamaDepan()||
                validateNamaBelakang()||
                validateNoHp()||
                validateAlamat()||
                validatEmail()||
                validatTanggalLahir()){

            return true
        }
        return false
    }//akhir validate all


}//tutup class form ativity