package com.ferrifrancis.exam

import Colegio
import Estudiante
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.annotation.RequiresApi

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    var indxItemContextMenu =0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        /*Lista*/
        val listaEstudiantes = arrayListOf<Estudiante>(
            Estudiante("Valeria H","1997-12-12", "matematica","1709793036", "F",0 ),
            Estudiante("Brigitte C","1997-06-14", "artistica","1701193031", "F",1 )

        )

        val listaEstudiantes2 = arrayListOf<Estudiante>(
            Estudiante("Maria H","1997-12-12", "estudios sociales","1709793036", "F",0 ),
            Estudiante("Jorge C","1997-06-14", "lenguaje","1701193031", "M",1 )
        )


        val listaColegios = arrayListOf<Colegio>(
            Colegio("manuela espejo", 300.12F, true, 4,12,0 ),
            Colegio("eugenio espejo", 300.12F, true, 4,20 ,1)
        )

        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            listaColegios

        )

        val listViewColegio = findViewById<ListView>(R.id.ltv_colegio)

        listViewColegio.adapter = adaptador

        registerForContextMenu(listViewColegio)

        /*Botones*/
        val botonAñadir = findViewById<Button>(R.id.btn_añadirColegio)
        botonAñadir.setOnClickListener {
            abrirActividad(BFormularioColegio::class.java)
        }
    }

    fun abrirActividad(clase : Class <*>)
    {
        val intentExplicito = Intent(
            this,
            clase
        )
        this.startActivity(intentExplicito)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_colegio, menu)

        
    }


}