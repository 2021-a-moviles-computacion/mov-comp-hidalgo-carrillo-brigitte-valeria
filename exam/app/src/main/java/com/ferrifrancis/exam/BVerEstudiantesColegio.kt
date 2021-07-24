package com.ferrifrancis.exam

import Colegio
import Estudiante
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.*

class BVerEstudiantesColegio: AppCompatActivity() {
    var posicionEstudiante =0
    var listaEstudiantes = ArrayList<Estudiante>()
    val CODIGO_RESPUESTA = 200

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_b_ver_estudiantes_colegio)
        val listaView = findViewById<ListView>(R.id.lv_estudiante_est_cole)
        val colegio = intent.getParcelableExtra<Colegio>("colegio")

        if(colegio != null) {

            val tvNombreCoelgio = findViewById<TextView>(R.id.tv_ver_est_cole)
            tvNombreCoelgio.text = colegio.nombre
            Log.i("intent-explicito", "${colegio.nombre}")

            listaEstudiantes = jalarDatosEstudianteBD(colegio.idColegio!!)

            if (listaEstudiantes.isNotEmpty()) {
               val adaptador: ArrayAdapter<Estudiante> = ArrayAdapter(
                    this,
                    android.R.layout.simple_list_item_1,
                    listaEstudiantes
                )
                listaView.adapter = adaptador
                registerForContextMenu(listaView)


            } else
            {
                 val adaptador = ArrayAdapter(
                    this,
                    android.R.layout.simple_list_item_1,
                    arrayListOf("No existen registros")
                )
                listaView.adapter = adaptador

            }
            val botonRegistrar = findViewById<Button>(R.id.btn_anadir_est_col)
            //0 --> registra, ver
            //1 --> edita
            botonRegistrar.setOnClickListener {
                abrirActividadConParametros(BFormularioEstudiante::class.java, colegio,null,0)
            }
        }
    }

    fun jalarDatosEstudianteBD(idCole: Int): ArrayList<Estudiante>
    {
        EBaseDeDatos.TablaUsuario = ESQLiteHelperUsuario(this)
        return EBaseDeDatos.TablaUsuario!!.consultaEstudiantesXIDCole(idCole)
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