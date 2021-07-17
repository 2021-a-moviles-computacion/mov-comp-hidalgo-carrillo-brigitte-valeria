package com.ferrifrancis.exam

import Colegio
import Estudiante
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView

class BVerEstudiantesColegio : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_b_ver_estudiantes_colegio)
        val listaView = findViewById<ListView>(R.id.lv_estudiante_est_cole)
        val colegio = intent.getParcelableExtra<Colegio>("colegio")

        if(colegio != null) {

            val tvNombreCoelgio = findViewById<TextView>(R.id.tv_ver_est_cole)
            tvNombreCoelgio.text = colegio.nombre
            Log.i("intent-explicito", "${colegio.nombre}")

            val listaEstudiantes = jalarDatosEstudianteBD(colegio.idColegio!!)

            if (listaEstudiantes.isNotEmpty()) {
               val adaptador: ArrayAdapter<Estudiante> = ArrayAdapter(
                    this,
                    android.R.layout.simple_list_item_1,
                    listaEstudiantes
                )
                listaView.adapter = adaptador

            } else
            {
                 val adaptador = ArrayAdapter(
                    this,
                    android.R.layout.simple_list_item_1,
                    arrayListOf("No existen registros")
                )
                listaView.adapter = adaptador

            }
            registerForContextMenu(listaView)


        }
    }

    fun jalarDatosEstudianteBD(idCole: Int): ArrayList<Estudiante>
    {
        EBaseDeDatos.TablaUsuario = ESQLiteHelperUsuario(this)
        return EBaseDeDatos.TablaUsuario!!.consultaEstudiantesXIDCole(idCole)
    }

}