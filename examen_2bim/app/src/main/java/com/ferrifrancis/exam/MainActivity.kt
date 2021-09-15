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

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    var indxItemContextMenu = 0
    var listaColegios: ArrayList<Colegio>?= null
    val CODIGO_RESPUESTA = 200

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listaColegios = jalarDatosColegioFirestore()
        poneDatosEnAdaptador()

        val botonAñadir = findViewById<Button>(R.id.btn_añadirColegio)
        botonAñadir.setOnClickListener {
            //Se usa la misma actividad para editar y registrar
            //Cuando envío el código 0, la actividad se abre para registrar
            //Cuando envío el código 1, la actividad se abre para editar
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
                /*
                abrirActividadConParametros(
                    BVerEstudiantesColegio::class.java,
                    listaColegios[this.indxItemContextMenu],

                    0

                )
                Log.i("bdd", "-->envia:${listaColegios[this.indxItemContextMenu].idColegio}")
                */

                return true
            }
            R.id.mi_editar -> {
                /*
                abrirActividadConParametros(
                    BFormularioColegio::class.java,
                    listaColegios[this.indxItemContextMenu],
                    1
                )
                //abrirActividadConParametros(BFormularioColegio::class.java, listaColegios[this.indxItemContextMenu],1)
                 */
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
                            listaColegios=jalarDatosColegioFirestore()
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
                /*
                abrirActividadConParametros(
                    BFormularioEstudiante::class.java,
                    listaColegios[this.indxItemContextMenu],
                    0
                )
                */

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

    }


    fun eliminarColegioBD(): Boolean {
        /*
        EBaseDeDatos.TablaUsuario = ESQLiteHelperUsuario(this)
        val indxCole: Int? = this.listaColegios[indxItemContextMenu].idColegio
        if (indxCole != null) {
            val rptaEliminarCole: Boolean = EBaseDeDatos.TablaUsuario!!.eliminarColegioPorID(indxCole)
            EBaseDeDatos.TablaUsuario!!.eliminarEstPorIDCole(indxCole)
            if (rptaEliminarCole) Toast.makeText(this, "¡Eliminado!", Toast.LENGTH_SHORT).show()
            return rptaEliminarCole
        } else
            return false

         */
        return true
    }

    fun poneDatosEnAdaptador()
    {
        if(this.listaColegios != null) {
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

    fun jalarDatosColegioFirestore(): ArrayList<Colegio>?
    {
        val db = Firebase.firestore
        val arregloColegio=ArrayList<Colegio>()



        db.collection("colegio")
            .get()
            .addOnSuccessListener { documents ->

                for (document in documents) {
                    val colegioCargado: Colegio = document.toObject(Colegio::class.java)

                    if (colegioCargado != null) {
                        arregloColegio.add(Colegio(colegioCargado.nombre,colegioCargado.esFiscal,colegioCargado.distrito,colegioCargado.numAulas,colegioCargado.idColegio))
                    }
                }
                //spRestaurante.adapter= ArrayAdapter<FirebaseRestauranteDto>(this, android.R.layout.simple_list_item_1,arregloRestaurante)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
        return arregloColegio
    }

}