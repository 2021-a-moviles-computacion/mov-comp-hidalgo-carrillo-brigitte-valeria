package com.ferrifrancis.exam

import Colegio
import android.content.ContentValues.TAG
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.google.firebase.firestore.ktx.firestore
import kotlin.collections.ArrayList
import com.google.firebase.ktx.Firebase
import java.lang.Thread.sleep

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    var indxItemContextMenu = 0
    var listaColegios= jalarDatosColegioFirestore()
    val CODIGO_RESPUESTA = 200 // para crear COELGIO
    val CODIGO_RTA_ACTUALIZAR: Int = 201 //para act coelgio
    val CODIGO_RPTA_CREAR_EST = 210
    val CODIGO_RPTA_VER_EST = 211

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        poneDatosEnAdaptador()


        val botonAñadir = findViewById<Button>(R.id.btn_añadirColegio)
        botonAñadir.setOnClickListener {
            //Se usa la misma actividad para editar y registrar
            //Cuando envío el código 0, la actividad se abre para registrar
            //Cuando envío el código 1, la actividad se abre para editar
            abrirActividadConParametros(BFormularioColegio::class.java, null, 0,CODIGO_RESPUESTA)

        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            CODIGO_RESPUESTA ->{
                if (resultCode == RESULT_OK)
                {
                    val colegio = data?.getParcelableExtra<Colegio>("arregloColegiosCreados")
                    if (colegio != null) {
                        this.listaColegios.add(colegio)
                    }
                    Log.i("main-activity","Colegio recibido por intent: ${colegio}")

                }
            }
            CODIGO_RTA_ACTUALIZAR ->{
                if (resultCode == RESULT_OK)
                {
                    val colegioIntent = data?.getParcelableExtra<Colegio>("arregloColegiosCreados")
                    listaColegios.forEach {
                        val colegioLista = it
                        if (colegioIntent != null) {
                            if(colegioLista.idColegio == colegioIntent.idColegio) {
                                colegioLista.numAulas = colegioIntent.numAulas
                            }
                        }
                    }
                    Log.i("main-activity","intent codigo rpta actualziar")

                }
            }
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {

        return when (item?.itemId) {

            R.id.mi_ver_est -> {

                abrirActividadConParametros(
                    BVerEstudiantesColegio::class.java,
                    listaColegios[this.indxItemContextMenu],
                    0,
                    CODIGO_RPTA_VER_EST

                )

                return true
            }
            R.id.mi_editar -> {
                abrirActividadConParametros(
                    BFormularioColegio::class.java,
                    listaColegios[this.indxItemContextMenu],
                    1,
                    CODIGO_RTA_ACTUALIZAR
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

                            eliminarColegioFirestore()
                            eliminarColegioLista()
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
            Log.i("main-activity","colegio id -->${listaColegios[this.indxItemContextMenu].idColegio}")
                abrirActividadConParametros(
                    BFormularioEstudiante::class.java,
                    listaColegios[this.indxItemContextMenu],
                    0,
                    CODIGO_RPTA_CREAR_EST
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
        //Log.i("main-activity","item selected ${this.listaColegios[this.indxItemContextMenu].nombreColegio}")
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
        codEditOrCreate: Int,
        codigoRpta: Int
    ) {
        //0 --> registra, ver
        //1 --> edita
        val intentExplicito = Intent(this, clase)// con quien te vas a comunicar
        intentExplicito.putExtra("colegio", colegio)//la información que vas a pasar
        intentExplicito.putExtra("id", codEditOrCreate)
        startActivityForResult(intentExplicito, codigoRpta)//manda este código
    }


    fun eliminarColegioFirestore(): Boolean {
        val idColegio = this.listaColegios[this.indxItemContextMenu].idColegio
        if (idColegio != null) {
            val db = Firebase.firestore
            db.collection("colegio").document(idColegio)
                .delete()
                .addOnSuccessListener { Log.d("main-activity", "Documetno colegio se borró") }
                .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }
            return true
        }
        else
        {return false}
    }

    fun eliminarColegioLista()
    {
        this.listaColegios.removeAt(this.indxItemContextMenu)
    }
    fun poneDatosEnAdaptador()
    {
        if(this.listaColegios != null) {
            Log.i("adaptador main","la lista de colegio esta vacia")
            val adaptador: ArrayAdapter<Colegio> = ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                this.listaColegios!!

            )

            val listViewColegio = findViewById<ListView>(R.id.ltv_colegio)
            listViewColegio.adapter = adaptador
            registerForContextMenu(listViewColegio)
        }
    }

    fun jalarDatosColegioFirestore(): ArrayList<Colegio>
    {
        val db = Firebase.firestore
        val arregloColegio=ArrayList<Colegio>()
        var numero = 0

        db.collection("colegio")
            .get()
            .addOnSuccessListener { documents ->

                for (document in documents) {
                    val nombreColegio: String? = document.get("nombreColegio").toString()
                    val distrito: Int? = document.get("distrito").toString().toInt()
                    val esFiscal: Boolean? = document.get("esFiscal").toString().toBoolean()
                    val numAulas: Int? = document.get("numAulas").toString().toInt()
                    val identificador: String? = document.id

                    //Log.i("firestore", "identificador colegio ${identificador}")
                    val colegioCargado= Colegio(nombreColegio,esFiscal,distrito,numAulas,identificador)

                    //sleep(1000)
                    arregloColegio.add(colegioCargado)
                    Log.i("firestore", "identificador objeo ${arregloColegio[numero].idColegio}")
                    numero = numero +1

                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
        sleep(10000)
        //Log.i("firestore", "identificador colegio ${arregloColegio[1].distrito}")

        return arregloColegio
    }

}