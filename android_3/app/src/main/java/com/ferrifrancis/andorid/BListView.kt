package com.ferrifrancis.andorid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView

class BListView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_b_list_view)
        //creo lo que va en el list view
        val arregloNumeros =  arrayListOf<Int>( 1,2,3,4,5,6,7)
        //creo un adaptador, con lo que contendrá
        val adaptador = ArrayAdapter(
            this, //contexto
            android.R.layout.simple_list_item_1, //layout
            arregloNumeros// arreglo

        )
        //llamo al list view
        val listViewEjemplo = findViewById<ListView> (R.id.ltv_ejemplo)
        //pongo el adaptador en mi list view
        listViewEjemplo.adapter = adaptador

        //llamo al botón
        val botonAnadirNumero = findViewById<Button>(R.id.btn_anadir_numero)
        //le hago que escuche
        botonAnadirNumero.setOnClickListener{
            //cuando de clic, que trabaje esta función
            anadirItemsAlListView(1, arregloNumeros, adaptador)
        }
/*
        listViewEjemplo.setOnItemLongClickListener { adapterView, view, posicion, id ->
            Log.i("list-view", "Dio clic ${posicion}")
            return@setOnItemLongClickListener true
        }
        */
        registerForContextMenu(listViewEjemplo)

    }
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    )
    {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
    }



    //añade un valor en la lista
    fun anadirItemsAlListView(valor: Int,arreglo: ArrayList<Int>, adaptador: ArrayAdapter<Int>)
    {
        arreglo.add(valor)
        //actualiza la interfaz
        adaptador.notifyDataSetChanged()
    }


}