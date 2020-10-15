package com.abrorrahmad.sekolahku

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView


class StudentArrayAdapter (context: Context): ArrayAdapter<Student>(
    context, R.layout.studet_array_adapter){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var view = convertView

        if (view == null){
            view = LayoutInflater.from(context).inflate(
                R.layout.studet_array_adapter, null
            )
        }

        var textNama = view!!.findViewById<TextView>(R.id.rowNama)
        var textGander = view!!.findViewById<TextView>(R.id.rowGender)
        var textjenjang = view!!.findViewById<TextView>(R.id.rowjenjang)
        var textNohp = view!!.findViewById<TextView>(R.id.rowNoHp)

        var student = getItem(position)

        textNama.setText(student?.namaDepan + " "+ student?.namaBelakang)
        textGander.setText(student?.gender)
        textjenjang.setText(student?.jenjang)
        textNohp.setText(student?.noHp)

        return view
    }


}