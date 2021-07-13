package com.ferrifrancis.exam

import Colegio
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.annotation.RequiresApi
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    var indxItemContextMenu =0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Contenido que irá en la lista
        //-------------------------------------jalar datos bd
        EBaseDeDatos.TablaUsuario= ESQLiteHelperUsuario(this)
        val listaColegios: ArrayList<Colegio> = EBaseDeDatos.TablaUsuario!!.consultaColegios()
        Log.i("bd","TAMAÑO--->${listaColegios.size}")
        listaColegios.forEach {
            val colegio = it
            Log.i("bd","colegio ${colegio.nombre}")
        }
        //--------------------------------------

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