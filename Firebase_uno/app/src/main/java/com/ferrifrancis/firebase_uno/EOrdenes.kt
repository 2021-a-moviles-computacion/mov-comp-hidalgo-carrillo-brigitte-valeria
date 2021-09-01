package com.ferrifrancis.firebase_uno

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.ferrifrancis.firebase_uno.dto.FirebaseProductoDto
import com.ferrifrancis.firebase_uno.dto.FirebaseRestauranteDto
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class EOrdenes : AppCompatActivity() {

    var arregloProducto: ArrayList<FirebaseProductoDto> = arrayListOf<FirebaseProductoDto>()
    val arregloRestaurante = arrayListOf<FirebaseRestauranteDto>()
    val lista = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_e_ordenes)

        setearSpinnerProducto()
        //Log.i("firebase-firestore","arreglo producto${DataProducto.arregloProducto}")
        //arregloProducto=setearArregloProducto()
        setearSpinnerRestaurante()

        //spRestaurante.adapter= ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,lista)
       //Log.i("firestore-firebase","${setearArregloProducto()[0]}")


    }

    fun setearSpinnerProducto(): ArrayList<FirebaseProductoDto>
    {
        val db = Firebase.firestore
        val arregloProductos = arrayListOf<FirebaseProductoDto>()
        val spProducto = findViewById<Spinner>(R.id.sp_producto)


        db.collection("producto")
            .get()
            .addOnSuccessListener { documents ->

                for (document in documents) {
                    val productoCargado: FirebaseProductoDto = document.toObject(FirebaseProductoDto::class.java)
                    arregloProductos.add(productoCargado)

                }
                spProducto.adapter= ArrayAdapter<FirebaseProductoDto>(this, android.R.layout.simple_list_item_1,arregloProductos)
                Log.i("firestore-firebase","${arregloProductos[1]}")
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
        DataProducto.arregloProducto = arregloProductos
        return  arregloProductos
    }

    fun setearSpinnerRestaurante()
    {
        val db = Firebase.firestore
        val arregloRestaurante = arrayListOf<FirebaseRestauranteDto>()
        val spRestaurante = findViewById<Spinner>(R.id.sp_restaurantes)

        db.collection("restaurante")
            .get()
            .addOnSuccessListener { documents ->

                for (document in documents) {
                    val restauranteCargado: FirebaseRestauranteDto = document.toObject(FirebaseRestauranteDto::class.java)

                    if(restauranteCargado != null)
                    {
                        arregloRestaurante.add(restauranteCargado)

                    }
                }
                spRestaurante.adapter= ArrayAdapter<FirebaseRestauranteDto>(this, android.R.layout.simple_list_item_1,arregloRestaurante)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }

    }

}