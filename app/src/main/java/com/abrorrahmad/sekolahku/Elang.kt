package com.abrorrahmad.sekolahku

class Elang {

    var nama = ""
    var usia = 0
    var daerah = ""

    fun satwa(input_nama:String, input_usia:Int,input_daerah:String): String{

        nama = input_nama
        usia = input_usia
        daerah = input_daerah

        return "Hallo nama saya adalah ${this.nama}, usia saya ${this.usia}\n" +
                "Daerah saya ${this.daerah}"


    }
}