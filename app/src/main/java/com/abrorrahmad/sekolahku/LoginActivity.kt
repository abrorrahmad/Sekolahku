package com.abrorrahmad.sekolahku

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //membuat bar enggak keliatan
        supportActionBar?.hide()



        btnLogin.setOnClickListener{
            prosesLogin()
        }
    }

    fun prosesLogin(){
        //untuk membuat username dan pasword

        if (validateAll() == true){
            return
        }

        //konstanta untuk username dan password
        val username = "admin"
        val password = "admin"

        //ambil value dari form
        var textUsername = txtUsername.text.toString()
        var textPassword = txtPassword.text.toString()

        //cocokan antara konstanta dan input data

        if (username == textUsername && password == textPassword) {
            var intent = Intent(this, ListActivity::class.java)
            startActivity(intent)
            finish()
            showToast("selamat ! Anda berhasil Login")
        } else {
            showToast("username dan paswordnya admin")
        }
    }

        fun showToast (msg: String) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

    }

    fun validateUsername() : Boolean{
        var username = txtUsername.text.toString()
        if (username.isEmpty()){
            txtUsername.setError("Username tidak Boleh Kosong")
            txtUsername.requestFocus()
            return true
        }
        return false
    }

    fun validatePassword() : Boolean{
        var password = txtUsername.text.toString()
        if (password.isEmpty()){
            txtPassword.setError("Password tidak Boleh Kosong")
            txtPassword.requestFocus()
            return true
        }
        return false
    }

    fun validateAll() : Boolean{ //start validate all
        if (validateUsername()||
                validatePassword()){
            return true
        }
        return false
    }//end validate all

}