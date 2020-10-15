package com.abrorrahmad.sekolahku

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

class StudentDataSource (context: Context) {

    private var help =DatabaseHelper(context)
    private var sqlite : SQLiteDatabase? = null

            fun openAccess(){
                sqlite = help.writableDatabase
            }
            fun closeAccess(){
                help.close()
            }

        fun insertStudent(student: Student){
            openAccess()
            var cv = ContentValues()

            cv.put("nama_depan", student.namaDepan)
            cv.put("nama_belakang", student.namaBelakang)
            cv.put("no_hp", student.noHp)
            cv.put("gender", student.gender)
            cv.put("jenjang", student.jenjang)
            cv.put("hobi", student.hobi)
            cv.put("alamat", student.alamat)
            cv.put("email", student.email)
            cv.put("tanggal_lahir", student.tanggalLahir)

            sqlite?.insert("student",null, cv)
            closeAccess()
        }

    fun fetchRow(cursor: Cursor) : Student {//Start fetch row

        var student = Student()

        student.id = cursor.getInt(0)
        student.namaDepan = cursor.getString(1)
        student.namaBelakang = cursor.getString(2)
        student.noHp = cursor.getString(3)
        student.gender = cursor.getString(4)
        student.jenjang = cursor.getString(5)
        student.hobi = cursor.getString(6)
        student.alamat = cursor.getString(7)
        student.email = cursor.getString(8)
        student.tanggalLahir = cursor.getString(9)
        return student

    }//end fetch row

    //disini adalah perintah untuk memunculkan di list
    fun getAllsStudent() : ArrayList<Student> {
        openAccess()
        var cursor = sqlite?.rawQuery("SELECT * FROM student ORDER BY id DESC",null)
        val studentList = ArrayList<Student>()

        cursor?.moveToFirst()

        while (!cursor!!.isAfterLast){
            val student = fetchRow(cursor)
            studentList.add(student)
            cursor.moveToNext()
        }
        closeAccess()
        return studentList

    }

    fun getStudent(id: Int) : Student {

        openAccess()

        var sql = "SELECT * FROM student WHERE id = $id"
        var cursor = sqlite!!.rawQuery(sql,null)

        cursor.moveToFirst()

        var student = fetchRow(cursor)

        return student

        closeAccess()
    }

    //menambahkan fungsi pada search

    fun search(keyword: String) : ArrayList<Student>{

        openAccess()
        var query = "SELECT * FROM student WHERE nama_depan LIKE ? OR nama_belakang LIKE ? "
        var selection = arrayOf("%$keyword%","%$keyword%")
        var cursor = sqlite!!.rawQuery(query, selection)
        var listStudentBySearch = ArrayList<Student>()

        cursor?.moveToFirst()

        while (!cursor!!.isAfterLast){
            listStudentBySearch.add(fetchRow(cursor))
            cursor.moveToNext()
        }
        cursor.close()

        closeAccess()

        return listStudentBySearch
    }//akhir dari fungsi pada search

    fun deleteStudent(id: Int?){
        openAccess()
        sqlite?.delete("student", "id = $id", null)
        closeAccess()
    }

    fun updateStudent(student: Student, id: Int){//update sudent

        openAccess()
        var cv = ContentValues()

        cv.put("nama_depan", student.namaDepan)
        cv.put("nama_belakang", student.namaBelakang)
        cv.put("no_hp", student.noHp)
        cv.put("gender", student.gender)
        cv.put("jenjang", student.jenjang)
        cv.put("hobi", student.hobi)
        cv.put("alamat", student.alamat)
        cv.put("email", student.email)
        cv.put("tanggal_lahir", student.tanggalLahir)

        sqlite?.update("student",cv,"id = ?", arrayOf(id.toString()))
        closeAccess()
    }//akhir update student




}//Tutup Class