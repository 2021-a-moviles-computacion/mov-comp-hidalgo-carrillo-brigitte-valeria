package com.ferrifrancis.firebase_uno

import android.app.DownloadManager
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CRestaurante : AppCompatActivity() {

    //var ultimoDocumento: DocumentSnapshot? = null
    var query: Query? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_d_restaurante)

        val btnCrearRestaurante = findViewById<Button>(R.id.btn_crear_restaurante)

        btnCrearRestaurante.setOnClickListener {
            crearRestaurante()
        }

        val btnDatosPrueba = findViewById<Button>(R.id.btn_datos_firebasee)
        btnDatosPrueba.setOnClickListener {
            //crearDatosPrueba()
            transaccion()
        }

        val btnConsultar = findViewById<Button>(R.id.btn_consultarrear_restaurante)
        btnConsultar.setOnClickListener {
            consultar()
        }
    }

    fun consultar() {
        //para cada una hay una funcion
        /*
            < less than
            <= less than or equal to
            == equal to
            > greater than
            >= greater than or equal to
            != not equal to
            array-contains
            array-contains-any
            in
            not-in
        * */
        val db = Firebase.firestore
        var citiesRef = db.collection("cities")
            .orderBy("population")
            .limit(2) //consulta de 2 en 2

        /*
        //consultar un documento en especifico
        citiesRef
            .document("BJ") // ID
            .get()
            .addOnSuccessListener {
                Log.i("consultas doc espec", "${it.data}")
            }
            .addOnFailureListener {  }

        // Buscar por un solo campo ==
        citiesRef
            .whereEqualTo("country", "China")
            .get()
            .addOnSuccessListener {
                Log.i("consultas", "${it.documents}")
                for (ciudad in it){
                    Log.i("consultas", "${ciudad.data}")
                    Log.i("consultas", "${ciudad.id}")
                }
            }
            .addOnFailureListener {  }

             // Buscar por dos o mas elementos campo '==' 'array-contains'
        citiesRef
            .whereEqualTo("capital", false)
            .whereArrayContainsAny("regions", arrayListOf("socal", "norcal"))
            .get()
            .addOnSuccessListener {
                for (ciudad in it){
                    Log.i("consultas", "== array-contains ${ciudad.data}")
                }
            }

            // Buscar por dos o mas elementos campo '==' '>='
        citiesRef
            .whereEqualTo("capital", true)
            .whereGreaterThanOrEqualTo("population", 1000000)
            .get()
            .addOnSuccessListener {
                for (ciudad in it){
                    Log.i("consultas", "== array-contains ${ciudad.data}")
                }
            }
             citiesRef
            .whereEqualTo("capital", false)
            .whereLessThanOrEqualTo("population", 4000000)
            .orderBy("population", Query.Direction.DESCENDING) //Para ordenar
            .get()
            .addOnSuccessListener {
                for (ciudad in it){
                    Log.i("consultas", "== array-contains ${ciudad.data}")
                }
            }
*/
        // Buscar por dos o mas elementos campo '==' '>='
        var tarea: Task<QuerySnapshot>? = null
        if(query == null) //continua la bpusqueda a partir del último documento
        {
            tarea = citiesRef.get() // si no hay valores antes jala los dos primeros documentos
        }
        else { tarea = query!!.get() }

        if(tarea != null)
        {
            tarea.addOnSuccessListener { documentSnapshots ->
                guardarQuery(documentSnapshots, citiesRef)
                for(ciudad in documentSnapshots)
                {
                    Log.i("consultas","${ciudad.data}")
                }
            }
                .addOnFailureListener { Log.i("consultas","${it}") }
        }

    }

    fun transaccion()
    {
        val db = Firebase.firestore
        val referenciaCities = db.collection("cities").document("SF")
        db.runTransaction{
            transaction ->
            val documentoActual = transaction.get(referenciaCities)
            val poblacion = documentoActual.getDouble("population")
            if(poblacion != null)
            {
                val nuevaPoblacion = poblacion +1
                transaction.update(referenciaCities,"population",nuevaPoblacion)
            }
        }
            .addOnSuccessListener { Log.i("transaccion","Transaccion completada") }
            .addOnFailureListener { Log.i("transaccion","Error") }
    }

    fun guardarQuery(documentSnapshot: QuerySnapshot,citiesRef: Query)
    {
        //guardo la query del último doc
        if(documentSnapshot.size() >0)
        {
            val ultimoDocumento = documentSnapshot.documents[documentSnapshot.size() -1]
            query = citiesRef
                .startAfter(ultimoDocumento)
        }
        else
        {}
    }

    fun crearRestaurante() {
        val etNombreRestaurante = findViewById<EditText>(R.id.et_nombre_restaurante)

        val nuevoRestaurante = hashMapOf<String, Any>(
            "nombre" to etNombreRestaurante.text.toString(),
        )

        val db = Firebase.firestore
        val referencia = db.collection("restaurante")

        referencia.add(nuevoRestaurante)
            .addOnSuccessListener {
                etNombreRestaurante.text.clear()
                Log.i("firebase-firestore", "restaurante creado")
            }

    }

    fun crearDatosPrueba() {
        val db = Firebase.firestore
        val cities = db.collection("cities")

        val data1 = hashMapOf(
            "name" to "San Francisco",
            "state" to "CA",
            "country" to "USA",
            "capital" to false,
            "population" to 860000,
            "regions" to listOf("west_coast", "norcal")
        )
        cities.document("SF").set(data1)

        val data2 = hashMapOf(
            "name" to "Los Angeles",
            "state" to "CA",
            "country" to "USA",
            "capital" to false,
            "population" to 3900000,
            "regions" to listOf("west_coast", "socal")
        )
        cities.document("LA").set(data2)

        val data3 = hashMapOf(
            "name" to "Washington D.C.",
            "state" to null,
            "country" to "USA",
            "capital" to true,
            "population" to 680000,
            "regions" to listOf("east_coast")
        )
        cities.document("DC").set(data3)

        val data4 = hashMapOf(
            "name" to "Tokyo",
            "state" to null,
            "country" to "Japan",
            "capital" to true,
            "population" to 9000000,
            "regions" to listOf("kanto", "honshu")
        )
        cities.document("TOK").set(data4)

        val data5 = hashMapOf(
            "name" to "Beijing",
            "state" to null,
            "country" to "China",
            "capital" to true,
            "population" to 21500000,
            "regions" to listOf("jingjinji", "hebei")
        )
        cities.document("BJ").set(data5)
    }

}