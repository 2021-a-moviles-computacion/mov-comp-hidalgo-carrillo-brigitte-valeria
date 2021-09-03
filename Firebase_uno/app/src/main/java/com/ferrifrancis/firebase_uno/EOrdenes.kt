package com.ferrifrancis.firebase_uno

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.ferrifrancis.firebase_uno.dto.FirebaseProductoDto
import com.ferrifrancis.firebase_uno.dto.FirebaseRestauranteDto
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class EOrdenes : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_e_ordenes)

        val spRestaurante = findViewById<Spinner>(R.id.sp_restaurantes)
        val spProducto = findViewById<Spinner>(R.id.sp_producto)

        val arregloProducto: ArrayList<FirebaseProductoDto> = setearSpinnerProducto(spProducto)
        val arregloRestaurante: ArrayList<FirebaseRestauranteDto>



        setearSpinnerRestaurante(spRestaurante)

        spRestaurante.onItemSelectedListener =
            object : AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    Log.i("hola","${arregloProducto[position]}")
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                }

            }

        spProducto.onItemSelectedListener =
            object : AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {

                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                }

            }
    }

    fun setearSpinnerProducto(spProducto: Spinner): ArrayList<FirebaseProductoDto>
    {
        val db = Firebase.firestore
        val arregloProductos = arrayListOf<FirebaseProductoDto>()

        db.collection("producto")
            .get()
            .addOnSuccessListener { documents ->

                for (document in documents) {
                    val productoCargado: FirebaseProductoDto = document.toObject(FirebaseProductoDto::class.java)
                    if(productoCargado != null)
                    {
                        arregloProductos.add(FirebaseProductoDto(productoCargado.nombre,productoCargado.precio))
                    }
                }
                spProducto.adapter= ArrayAdapter<FirebaseProductoDto>(this, android.R.layout.simple_list_item_1,arregloProductos)
                //Log.i("firestore-firebase","${arregloProductos[1]}")
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }

        return  arregloProductos
    }

    fun setearSpinnerRestaurante(spRestaurante: Spinner)
    {
        val db = Firebase.firestore
        val arregloRestaurante = arrayListOf<FirebaseRestauranteDto>()

        db.collection("restaurante")
            .get()
            .addOnSuccessListener { documents ->

                for (document in documents) {
                    val restauranteCargado: FirebaseRestauranteDto = document.toObject(FirebaseRestauranteDto::class.java)

                    if(restauranteCargado != null)
                    {
                        arregloRestaurante.add(FirebaseRestauranteDto(restauranteCargado.nombre))

                    }
                }
                spRestaurante.adapter= ArrayAdapter<FirebaseRestauranteDto>(this, android.R.layout.simple_list_item_1,arregloRestaurante)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }

    }

}