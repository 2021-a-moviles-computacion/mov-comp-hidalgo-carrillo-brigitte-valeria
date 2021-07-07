package com.ferrifrancis.andorid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView

class ClaseDeber : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clase_deber)

        EBaseDeDatos.TablaUsuario = ESqliteHelperUsuario(this)

        if (EBaseDeDatos.TablaUsuario != null) {
            //EBaseDeDatos.TablaUsuario?.consultarUsuarioPorId()

            //EBaseDeDatos.TablaUsuario?.eliminarUsuarioFormulario()
            //EBaseDeDatos.TablaUsuario?.actualizarUsuarioFormulario()

            //contenido listview
            val contenidoListV = arrayListOf<String>("Valeria-Descripcion1","Brigitte-Descripción2", "Hidalgo-Descripción3")
            //adaptador pone los datos en el list view
            val adaptador = ArrayAdapter(this, android.R.layout.simple_list_item_1, contenidoListV)
            val listView = findViewById<ListView>(R.id.ltv_clase_deber)
            listView.adapter = adaptador

            //llamo al botón
            val botonCrear = findViewById<Button>(R.id.btn_crear_dc)
            //le hago que escuche
            botonCrear.setOnClickListener{
            //cuando de clic, que trabaje esta función
                val  resultadoCrear=EBaseDeDatos.TablaUsuario?.crearUsuarioFormulario("ejemplo", "descripcion ejemplo")
                Log.i("bs","crear usuario? ${resultadoCrear}")
                anadirItemsAlListView("ejemplo-descripcion",contenidoListV, adaptador)
            }

            val botonConsultar = findViewById<Button>(R.id.btn_consultar_dc)
            botonConsultar.setOnClickListener {
                val resultadoConsulta: EUsuarioBDD? = EBaseDeDatos.TablaUsuario?.consultarUsuarioPorId(1)
                Log.i("bd","${resultadoConsulta?.id} ${resultadoConsulta?.nombre} ${resultadoConsulta?.descripcion}")
            }

            val botonEliminar = findViewById<Button>(R.id.btn_eliminar_dc)
            botonEliminar.setOnClickListener {
                val resultadoEliminar = EBaseDeDatos.TablaUsuario?.eliminarUsuarioFormulario(1)
                Log.i("bd", "elimino usuario id1?${resultadoEliminar}")
                eliminarItemsAlListView("ejemplo-descripcion",contenidoListV,adaptador)
            }

            val botonActualizar = findViewById<Button>(R.id.btn_actualizar_dc)
            botonActualizar.setOnClickListener {
                val resultadoActualizar = EBaseDeDatos.TablaUsuario?.actualizarUsuarioFormulario("ejemplo2","descripcion2","1")
                actualizarItemsAlListView("ejemplo2-descripcion2", contenidoListV, adaptador)

            }
    }

    }
    //añade un valor en la lista
    fun anadirItemsAlListView(
        valor: String,
        arreglo: ArrayList<String>,
        adaptador: ArrayAdapter<String>
    ) {
        arreglo.add(valor)
        //actualiza la interfaz
        adaptador.notifyDataSetChanged()
    }

    fun eliminarItemsAlListView(
        valor: String,
        arreglo: ArrayList<String>,
        adaptador: ArrayAdapter<String>
    ) {
        arreglo.remove(valor)
        //actualiza la interfaz
        adaptador.notifyDataSetChanged()
    }

    fun actualizarItemsAlListView(
        valor: String,
        arreglo: ArrayList<String>,
        adaptador: ArrayAdapter<String>
    ) {

        Log.i("inf","------------------------>${arreglo.size}")
        if(arreglo.size > 3)
            arreglo[4] = valor

        //actualiza la interfaz
        adaptador.notifyDataSetChanged()
    }
}





