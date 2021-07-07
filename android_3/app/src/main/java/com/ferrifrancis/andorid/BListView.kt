package com.ferrifrancis.andorid

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AlertDialog

class BListView : AppCompatActivity() {

    var posicionItemSeleccionado = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_b_list_view)

        //creo lo que va en el list view
        val arregloNumeros =  BBaseDatosMemmoria.arregloBEntrenador

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
            anadirItemsAlListView(
                BEntrenador("prueba", "d@d.com",null)
                , arregloNumeros, adaptador)
        }

        listViewEjemplo.setOnItemLongClickListener { adapterView, view, posicion, id ->
            Log.i("list-view", "Dio clic ${posicion}")

            val builder = AlertDialog.Builder(this)

            builder.setTitle("titulo")
            //builder.setMessage("Mensaje")
            val seleccionUsuario = booleanArrayOf(
                true, false, false
            )

            val opciones = resources.getStringArray(R.array.string_array_opciones_dialogo)

            builder.setMultiChoiceItems(
                opciones,
                seleccionUsuario,
                {
                    dialog, which, isChecked ->
                    Log.i("list-view","${which} ${isChecked}")
                }
            )
            builder.setPositiveButton(
                "si", DialogInterface.OnClickListener{dialog, which ->
                    Log.i("list-view", "si")
                }
            )
            builder.setNegativeButton(
                "No",null
            )

            val dialogo = builder.create()
            dialogo.show()
            return@setOnItemLongClickListener true
        }

        //registro a listview para que se sea un context menu
        //registerForContextMenu(listViewEjemplo)

    }
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    )
    {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater //pone en la intertaz
        inflater.inflate(R.menu.menu, menu)

        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        posicionItemSeleccionado = id
        Log.i("list-view","list view ${posicionItemSeleccionado}")
        Log.i ("list-view", "Entrenador ${BBaseDatosMemmoria.arregloBEntrenador[id]}")
    }



    //añade un valor en la lista
    fun anadirItemsAlListView(valor: BEntrenador,arreglo: ArrayList<BEntrenador>, adaptador: ArrayAdapter<BEntrenador>)
    {
        arreglo.add(valor)
        //actualiza la interfaz
        adaptador.notifyDataSetChanged()
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item?.itemId)
        {
            //Editar
                R.id.mi_editar ->{
                    Log.i("list-view","Editar  ${BBaseDatosMemmoria.arregloBEntrenador[posicionItemSeleccionado]}")
                    return true
                }
            //Eliminar
                R.id.mi_eliminar ->{
                 Log.i("list-view","Editar  ${BBaseDatosMemmoria.arregloBEntrenador[posicionItemSeleccionado]}")
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }
}
