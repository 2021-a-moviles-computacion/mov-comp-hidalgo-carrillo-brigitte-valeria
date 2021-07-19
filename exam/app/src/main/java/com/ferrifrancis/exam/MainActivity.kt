package com.ferrifrancis.exam

import Colegio
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.annotation.RequiresApi
import java.util.*

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    var indxItemContextMenu =0
    var listaColegios = ArrayList<Colegio>()
    val CODIGO_RESPUESTA=200

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i("oncreate","CREADA")
        //Contenido que irá en la lista
        //-------------------------------------jalar datos bd
        listaColegios= jalarDatosColegioBD()
        //--------------------------------------

        val adaptador: ArrayAdapter<Colegio> = ArrayAdapter(
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
            //abrirActividad(BFormularioColegio::class.java)
            abrirActividadConParametros(BFormularioColegio::class.java,null,0)
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

    fun abrirActividadConParametros(clase: Class<*>, colegio: Colegio? =null, codEditOrCreate:Int)
    {
        //0 --> registra, ver
        //1 --> edita
        val intentExplicito = Intent(this,clase)// con quien te vas a comunicar
        intentExplicito.putExtra("colegio",colegio)//la información que vas a pasar
        intentExplicito.putExtra("id",codEditOrCreate)
        startActivityForResult(intentExplicito, CODIGO_RESPUESTA)//manda este código
    }


    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when(item?.itemId)
        {
            R.id.mi_ver_est ->{
                abrirActividadConParametros(BVerEstudiantesColegio::class.java, listaColegios[this.indxItemContextMenu],0)
                return true
            }
            R.id.mi_editar->{
                abrirActividadConParametros(BFormularioColegio::class.java, listaColegios[this.indxItemContextMenu],1)
                //abrirActividadConParametros(BFormularioColegio::class.java, listaColegios[this.indxItemContextMenu],1)
                return true
            }
            R.id.mi_eliminar -> {
                return true
            }
            R.id.mi_registrar_estudiantes -> {
                abrirActividadConParametros(BFormularioEstudiante::class.java, listaColegios[this.indxItemContextMenu],0)
                return true
            }
            else -> super.onContextItemSelected(item)
        }

    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        //Poniendo el menú en la lista
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_colegio, menu)

        //escuchando qué posición toca
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        this.indxItemContextMenu = info.position
        Log.i("list-view", "list view ${this.indxItemContextMenu}")
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        Log.i("android","crear")
    }

    override fun onStop() {
        super.onStop()
        Log.i("android","on stop")
    }

    override fun onPause() {
        super.onPause()
        Log.i("android","pausa")
    }

    override fun onResume() {
        super.onResume()
        Log.i("android","resumen")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i("android","on restart")
        
    }

    override fun onStart() {
        super.onStart()
        Log.i("android","on start")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.i("android","on destroy")
    }
    fun anadirItemsAlListView(/*colegio: Colegio, colegioLista: ArrayList<Colegio>,*/ adaptador: ArrayAdapter<Colegio>)
    {
        //colegioLista.add(colegio)
        adaptador.notifyDataSetChanged()
    }

    fun jalarDatosColegioBD(): ArrayList<Colegio>
    {
        EBaseDeDatos.TablaUsuario= ESQLiteHelperUsuario(this)
        val listaColegios: ArrayList<Colegio> = EBaseDeDatos.TablaUsuario!!.consultaColegios()
        Log.i("bd","TAMAÑO--->${listaColegios.size}")

        /*
        listaColegios.forEach {
           val colegio = it
           Log.i("bd", "colegio ${colegio.nombre}")
        }
       */
        return listaColegios
    }

}