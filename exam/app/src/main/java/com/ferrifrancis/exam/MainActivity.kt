package com.ferrifrancis.exam

import Colegio
import android.content.DialogInterface
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
import androidx.appcompat.app.AlertDialog
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    var indxItemContextMenu = 0
    var listaColegios = ArrayList<Colegio>()
    val CODIGO_RESPUESTA = 200

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i("oncreate", "CREADA")
        //Contenido que irá en la lista
        //-------------------------------------jalar datos bd
        listaColegios = jalarDatosColegioBD()
        //--------------------------------------
        poneDatosEnAdaptador()
        /*Botones*/
        val botonAñadir = findViewById<Button>(R.id.btn_añadirColegio)
        botonAñadir.setOnClickListener {
            //abrirActividad(BFormularioColegio::class.java)
            abrirActividadConParametros(BFormularioColegio::class.java, null, 0)
        }

    }

    fun abrirActividad(clase: Class<*>) {

        val intentExplicito = Intent(
            this,
            clase
        )
        this.startActivity(intentExplicito)

    }

    fun abrirActividadConParametros(
        clase: Class<*>,
        colegio: Colegio? = null,
        codEditOrCreate: Int
    ) {
        //0 --> registra, ver
        //1 --> edita
        val intentExplicito = Intent(this, clase)// con quien te vas a comunicar
        intentExplicito.putExtra("colegio", colegio)//la información que vas a pasar
        intentExplicito.putExtra("id", codEditOrCreate)
        startActivityForResult(intentExplicito, CODIGO_RESPUESTA)//manda este código
    }


    override fun onContextItemSelected(item: MenuItem): Boolean {
        this.listaColegios
        return when (item?.itemId) {
            R.id.mi_ver_est -> {
                abrirActividadConParametros(
                    BVerEstudiantesColegio::class.java,
                    listaColegios[this.indxItemContextMenu],

                    0

                )
                Log.i("bdd", "-->envia:${listaColegios[this.indxItemContextMenu].idColegio}")
                return true
            }
            R.id.mi_editar -> {
                abrirActividadConParametros(
                    BFormularioColegio::class.java,
                    listaColegios[this.indxItemContextMenu],
                    1
                )
                //abrirActividadConParametros(BFormularioColegio::class.java, listaColegios[this.indxItemContextMenu],1)
                return true
            }
            R.id.mi_eliminar -> {
                /*builder*/

                val builder = AlertDialog.Builder(this)
                builder.setTitle("Eliminar Colegio")
                builder.setMessage("Se eliminará el colegio y el registro de todos sus estudiantes ¿Eliminar?")

                builder.apply {
                    builder.setPositiveButton(
                        "Si",
                        DialogInterface.OnClickListener { dialog, which ->
                            Log.i("list-view", "si")
                            val rptaEliminar = eliminarColegioBD()
                            Log.i("bdd", "eliminar: ${rptaEliminar}")
                            listaColegios=jalarDatosColegioBD()
                            poneDatosEnAdaptador()

                        })
                    builder.setNegativeButton(
                        "no",
                        DialogInterface.OnClickListener { dialog, which ->
                            Log.i("list-view", "no")

                        })
                }

                val dialogo = builder.create()
                dialogo.show()

                Log.i("list-view", "${indxItemContextMenu}")

                return true
            }


            R.id.mi_registrar_estudiantes -> {
                abrirActividadConParametros(
                    BFormularioEstudiante::class.java,
                    listaColegios[this.indxItemContextMenu],
                    0
                )
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
        Log.i("list-view", "TAMAÑO aulas--->${listaColegios[indxItemContextMenu].numAulas}")
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        Log.i("android", "crear")
    }

    override fun onStop() {
        super.onStop()
        Log.i("android", "on stop")
    }

    override fun onPause() {
        super.onPause()
        Log.i("android", "pausa")
    }

    override fun onResume() {
        super.onResume()
        Log.i("android", "resumen")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i("android", "on restart")

    }

    override fun onStart() {
        super.onStart()
        Log.i("android", "on start")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("android", "on destroy")
    }

    fun eliminarColegioBD(): Boolean {
        EBaseDeDatos.TablaUsuario = ESQLiteHelperUsuario(this)
        val indxCole: Int? = this.listaColegios[indxItemContextMenu].idColegio
        if (indxCole != null) {
            val rptaEliminar: Boolean = EBaseDeDatos.TablaUsuario!!.eliminarColegioPorID(indxCole)
            return rptaEliminar
        } else
            return false
    }

    fun poneDatosEnAdaptador()
    {
        val adaptador: ArrayAdapter<Colegio> = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            this.listaColegios

        )

        val listViewColegio = findViewById<ListView>(R.id.ltv_colegio)
        listViewColegio.adapter = adaptador
        registerForContextMenu(listViewColegio)
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