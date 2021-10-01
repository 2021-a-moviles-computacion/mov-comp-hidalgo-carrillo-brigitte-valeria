package com.ferrifrancis.firebase_uno

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CProducto : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_c_producto)

        val btnCrearProducto = findViewById<Button>(R.id.btn_crear_producto)
        btnCrearProducto.setOnClickListener {
            crearProducto()
            //crearRestaurante()

        }
    }

    fun crearRestaurante()
    {
        //val etNombreRestaurante = findViewById<EditText>(R.id.et_nombre_restaurante)

        val nuevoRestaurante = hashMapOf<String, Any>(
            "nombre" to "rest prueba"//etNombreRestaurante.text.toString(),
        )

        val db = Firebase.firestore
        val referencia = db.collection("restaurante")

        referencia.add(nuevoRestaurante)
            .addOnSuccessListener {
                //etNombreRestaurante.text.clear()
                Log.i("firebase-firestore","restaurante creado")
            }

    }


    fun crearProducto(){
        val editTextNombre = findViewById<EditText>(R.id.et_nombre_producto)
        val editTextPrecio = findViewById<EditText>(R.id.et_precio_producto)

        val nuevoProducto = hashMapOf<String, Any>(
            "nombre" to editTextNombre.text.toString(),
            "precio" to editTextPrecio.text.toString().toDouble()
        )

        val db = Firebase.firestore
        val referencia = db.collection("producto")

        referencia.add(nuevoProducto)
            .addOnSuccessListener {
                editTextNombre.text.clear()
                editTextPrecio.text.clear()
            }


    }
}