package com.ferrifrancis.exam

import Colegio
import Estudiante
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

class BVerEstudiantesColegio: AppCompatActivity() {
    var posicionEstudiante =0
    var listaEstudiantes = ArrayList<Estudiante>()
    var idColegio: String? = null
    val CODIGO_RESPUESTA = 200

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_b_ver_estudiantes_colegio)
        val listaView = findViewById<ListView>(R.id.lv_estudiante_est_cole)
        val colegio = intent.getParcelableExtra<Colegio>("colegio")
        this.idColegio = colegio?.idColegio
        if(colegio != null) {

            val tvNombreCoelgio = findViewById<TextView>(R.id.tv_ver_est_cole)
            tvNombreCoelgio.text = colegio.nombreColegio
            Log.i("intent-explicito", "${colegio.nombreColegio}")

            listaEstudiantes = jalarDatosEstudianteBD()
            poneDatosEnAdaptador()

            val botonRegistrar = findViewById<Button>(R.id.btn_anadir_est_col)
            //0 --> registra, ver
            //1 --> edita
            botonRegistrar.setOnClickListener {
                abrirActividadConParametros(BFormularioEstudiante::class.java, colegio,null,0)
            }
        }
    }
    fun eliminaEstudiante(): Boolean
    {
        EBaseDeDatos.TablaUsuario = ESQLiteHelperUsuario(this)

        val resultadoEliminar: Boolean =EBaseDeDatos.TablaUsuario!!.eliminarEstudiantePorCedula(listaEstudiantes[posicionEstudiante].cedula)
        if (resultadoEliminar) Toast.makeText(this, "¡Eliminado!", Toast.LENGTH_SHORT).show()
        return resultadoEliminar

    }

    fun jalarDatosEstudianteBD(): ArrayList<Estudiante>
    {
        if(this.idColegio != null) {
            Log.i("bdd","jalar datos estudiante")
        /*EBaseDeDatos.TablaUsuario = ESQLiteHelperUsuario(this)
            return EBaseDeDatos.TablaUsuario!!.consultaEstudiantesXIDCole(idColegio!!)

             */
        }
        return ArrayList<Estudiante>()
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
        startActivityForResult(intentExplicito,CODIGO_RESPUESTA)//manda este código

    }
    fun poneDatosEnAdaptador()
    {
        val listViewEstudiante = findViewById<ListView>(R.id.lv_estudiante_est_cole)
        if(this.listaEstudiantes.isNotEmpty()) {
            val adaptadorLleno: ArrayAdapter<Estudiante> = ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                this.listaEstudiantes

            )
            listViewEstudiante.adapter = adaptadorLleno
            registerForContextMenu(listViewEstudiante)
        }
        else{
            val adaptadorVacio: ArrayAdapter<String> = ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                arrayListOf("No existen registros")
            )
            listViewEstudiante.adapter = adaptadorVacio
        }
    }
    override fun onContextItemSelected(item: MenuItem): Boolean {
        //0 --> registra, ver
        //1 --> edita


        return when(item?.itemId)
        {
            R.id.mi_editar_est ->{
                abrirActividadConParametros(BFormularioEstudiante::class.java,null,listaEstudiantes[posicionEstudiante],1)
                return true
            }
            R.id.mi_eliminar_est ->{
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Eliminar Estudiante")
                builder.setMessage("Se eliminará el registro del estudiante ¿Eliminar?")
                builder.apply {
                    builder.setPositiveButton(
                        "Si",
                        DialogInterface.OnClickListener { dialog, which ->
                            Log.i("list-view","Estudiante seleccionado para eliminar:${listaEstudiantes[posicionEstudiante].cedula}")
                            val resultadoEliminar=eliminaEstudiante()
                            Log.i("bdd","eliminó estudainte? ${resultadoEliminar}")
                            listaEstudiantes= jalarDatosEstudianteBD()
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

         super.onCreateContextMenu(menu, v, menuInfo)
         val inflater = menuInflater
         inflater.inflate(R.menu.menu_estudiante,menu)

        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        posicionEstudiante = info.position
        Log.i("list-view","posicion seleccionada list view: ${posicionEstudiante}")
    }
}