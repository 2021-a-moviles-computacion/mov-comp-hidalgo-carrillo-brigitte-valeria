package com.ferrifrancis.exam

import Colegio
import Estudiante
import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import java.lang.Thread.sleep
import android.widget.Adapter as Adapter1

class BVerEstudiantesColegio : AppCompatActivity() {
    var posicionEstudiante = 0
    var listaEstudiantes = ArrayList<Estudiante>()
    var adaptador: ArrayAdapter<Estudiante>?= null
    val CODIGO_RESPUESTA = 200

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_b_ver_estudiantes_colegio)

        val listViewEstudiante = findViewById<ListView>(R.id.lv_estudiante_est_cole)

        val colegio: Colegio? = intent.getParcelableExtra<Colegio>("colegio")

        if (colegio != null) {

            val tvNombreCoelgio = findViewById<TextView>(R.id.tv_ver_est_cole)
            tvNombreCoelgio.text = colegio.nombreColegio

            //Log.i("firestore", "identificador colegio ${arregloColegio[1].distrito}")

            this.listaEstudiantes=jalarDatosEstudianteFirestore(colegio)

            adaptador= ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                listaEstudiantes
            )
            listViewEstudiante.adapter= adaptador
            registerForContextMenu(listViewEstudiante)
        }
    }


   fun jalarDatosEstudianteFirestore(colegio: Colegio): ArrayList<Estudiante>
    {
        val db = Firebase.firestore
        val arregloEstudiante =ArrayList<Estudiante>()
        //var numero =0


            db.collection("estudiante")
                .whereEqualTo("idColegio", colegio.idColegio)
                .get()

                .addOnSuccessListener { documents ->

                    for (document in documents) {
                        val cedula: String? = document.get("cedula").toString()
                        val curso: String? = document.get("curso").toString()
                        val fechaNacimiento: String? = document.get("fechaNacimiento").toString()
                        val idColegio: String? = document.get("idColegio").toString()
                        val nombre: String? = document.get("nombre").toString()
                        val sexo: String? = document.get("sexo").toString()
                        val idDoc: String? = document.id.toString()

                        val estudianteCargado =
                            Estudiante(nombre, fechaNacimiento, curso, cedula, sexo, idColegio,idDoc)
                        Log.i("firestore", "estudiante cargado ${estudianteCargado.nombre}")
                        //sleep(1000)
                        arregloEstudiante.add(estudianteCargado)
                        adaptador?.notifyDataSetChanged()
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w(ContentValues.TAG, "Error getting documents: ", exception)
                }



        //Log.i("firestore", "identificador colegio ${arregloColegio[1].distrito}")

        return arregloEstudiante
    }

    fun eliminarEstudianteFirestore()
    {
        val idxEstudainte = this.listaEstudiantes[this.posicionEstudiante].idEstudiante
        if(idxEstudainte != null)
        {
            val db = Firebase.firestore
            db.collection("estudiante").document(idxEstudainte)
                .delete()
                .addOnSuccessListener {
                    Toast.makeText(this,"¡Estudiante eliminado!",Toast.LENGTH_SHORT).show()
                    Log.d("main-activity", "Documetno estudiante se borró")
                }
                .addOnFailureListener { e -> Log.w(ContentValues.TAG, "Error deleting document", e) }

        }
    }

    fun abrirActividadConParametros(
        clase: Class<*>,
        colegio: Colegio? = null,
        estudiante: Estudiante? = null,
        codEditOrCreate: Int
    ) {
        //0 --> registra, ver
        //1 --> edita
        val intentExplicito = Intent(this, clase)// con quien te vas a comunicar
        intentExplicito.putExtra("colegio", colegio)//la información que vas a pasar
        intentExplicito.putExtra("estudiante", estudiante)//la información que vas a pasar
        intentExplicito.putExtra("id", codEditOrCreate)
        startActivityForResult(intentExplicito, CODIGO_RESPUESTA)//manda este código

    }

    fun eliminarColegioLista()
    {
        this.listaEstudiantes.removeAt(this.posicionEstudiante)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {

        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_estudiante, menu)

        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        posicionEstudiante = info.position
        Log.i("list-view", "posicion seleccionada list view: ${posicionEstudiante}")
    }
    override fun onContextItemSelected(item: MenuItem): Boolean {
        //0 --> registra, ver
        //1 --> edita


        return when (item?.itemId) {
            R.id.mi_editar_est -> {
                abrirActividadConParametros(
                    BFormularioEstudiante::class.java,
                    null,
                    listaEstudiantes[posicionEstudiante],
                    1
                )
                return true
            }
            R.id.mi_eliminar_est -> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Eliminar Estudiante")
                builder.setMessage("Se eliminará el registro del estudiante ¿Eliminar?")
                builder.apply {
                    builder.setPositiveButton(
                        "Si",
                        DialogInterface.OnClickListener { dialog, which ->
                            eliminarEstudianteFirestore()
                            eliminarColegioLista()
                            adaptador?.notifyDataSetChanged()

                        })
                    builder.setNegativeButton(
                        "no",
                        DialogInterface.OnClickListener { dialog, which ->
                            Log.i("list-view", "no")

                        })
                }
                val dialogo = builder.create()
                dialogo.show()
                return true
            }
            else -> super.onContextItemSelected(item)
        }

    }


}