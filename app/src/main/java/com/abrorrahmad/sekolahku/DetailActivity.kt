package com.abrorrahmad.sekolahku

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        //set Judul
        supportActionBar?.setTitle("Detail Student")

        //icon back
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //mengambil data intent / parameter yang dikirimkan oleh list activity

        var id = 0
        if (intent != null){
            id = intent.getIntExtra("id",0)
        }

        //get data student berdasarkan id
        var studentDataSource = StudentDataSource(this)
        var student = studentDataSource.getStudent(id)

        txt_nama.text = student.namaDepan + " " + student.namaBelakang
        txt_no_hp.text =student.noHp
        txt_email.text =student.email
        txt_gender.text =student.gender
        txt_jenjang.text =student.jenjang
        txt_hobi.text =student.hobi
        txt_tgl_lahir.text =student.tanggalLahir
        txt_alamat.text =student.alamat

    }
}