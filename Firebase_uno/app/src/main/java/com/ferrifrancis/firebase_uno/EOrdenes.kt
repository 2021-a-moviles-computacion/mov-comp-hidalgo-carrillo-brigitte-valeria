package com.ferrifrancis.firebase_uno

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.ferrifrancis.firebase_uno.dto.FirebaseProductoDto
import com.ferrifrancis.firebase_uno.dto.FirebaseRestauranteDto
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_e_ordenes.*

class EOrdenes : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_e_ordenes)
        llenarSpinnerRestaurante()
        llenarSpinnerProducto()
    }

    fun llenarSpinnerProducto()
    {
        val spProducto = findViewById<Spinner>(R.id.sp_producto)
        val db = Firebase.firestore
        val arregloProductos: ArrayList<FirebaseProductoDto>

        db.collection("producto")
            .get()
            .addOnSuccessListener { documents ->
                DataProducto.limpiarArregloProducto()
                for (document in documents) {
                    val productoCargado = document.toObject(FirebaseProductoDto::class.java)

                    if(productoCargado != null)
                    {

                        DataProducto.setearArregloProducto(productoCargado?.nombre ,
                            productoCargado?.precio
                        )
                        spProducto.adapter= ArrayAdapter<FirebaseProductoDto>(this, android.R.layout.simple_list_item_1,DataProducto.arregloProducto)

                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }


    }

    fun llenarSpinnerRestaurante()
    {

        //spRestaurante.adapter= ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,PaisData.paisesDataSet())

        val spRestaurante = findViewById<Spinner>(R.id.sp_restaurantes)
        val db = Firebase.firestore
        val arregloRestaurante: ArrayList<FirebaseRestauranteDto>

        db.collection("restaurante")
            .get()
            .addOnSuccessListener { documents ->
                DataRestaurante.limpiarArregloRestaurante()
                for (document in documents) {
                    val restauranteCargado = document.toObject(FirebaseRestauranteDto::class.java)

                    if(restauranteCargado != null)
                    {

                        DataRestaurante.setearArregloRestaurante(restauranteCargado?.nombre)
                        spRestaurante.adapter= ArrayAdapter<FirebaseRestauranteDto>(this, android.R.layout.simple_list_item_1,DataRestaurante.arregloRestaurante)

                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
    }
}