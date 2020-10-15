package com.abrorrahmad.sekolahku

class Manusia {

    var nama = ""//budi
    var usia = 0 //17
    var jenisKelamin = "" //Pria

    fun biodata(input_nama:String, input_usia: Int, input_jenisKelamin: String) : String {
        //disini kita akan mengisi attribut nama dengan parameter
        nama = input_nama
        usia = input_usia
        jenisKelamin = input_jenisKelamin

        //mengembalikan hasil yang akan diperoleh apabila funtion biodata di panggil

        return "Hallo nama saya adalah ${this.nama}, usia saya ${this.usia}\n" +
                "Jenis Kelamin saya ${this.jenisKelamin}"
    }
}