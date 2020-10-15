package com.abrorrahmad.sekolahku

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper (context: Context) : SQLiteOpenHelper (context,
 "sekolahku",null,1) {

    override fun onCreate(db: SQLiteDatabase?) {//start On Create

        val sql = "Create TABLE student ( "+
                "id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                "nama_depan TEXT, "+
                "nama_belakang TEXT, "+
                "no_hp TEXT, "+
                "gender TEXT, "+
                "jenjang TEXT, "+
                "hobi TEXT, "+
                "alamat TEXT, "+
                "email TEXT, "+
                "tanggal_lahir TEXT)"

                db?.execSQL(sql)

    }//end On Create

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {//stat upgread

        val sql = "DROP TABLE IF EXISTS student"
        db?.execSQL(sql)
        onCreate(db)
    }//end upread

}//end Class Database Helper