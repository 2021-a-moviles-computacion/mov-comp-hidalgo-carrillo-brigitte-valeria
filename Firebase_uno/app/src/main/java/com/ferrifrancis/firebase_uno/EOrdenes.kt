package com.ferrifrancis.firebase_uno

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.ferrifrancis.firebase_uno.dto.Orden
import com.ferrifrancis.firebase_uno.dto.Producto
import com.ferrifrancis.firebase_uno.dto.Restaurante
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_e_ordenes.*

class EOrdenes : AppCompatActivity() {

    lateinit var arregloProducto: ArrayList<Producto>
    lateinit var arregloRestaurante: ArrayList<Restaurante>
    val arregloOrdenes: ArrayList<Orden> = ArrayList<Orden>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_e_ordenes)

        val spRestaurante = findViewById<Spinner>(R.id.sp_restaurantes)
        val spProducto = findViewById<Spinner>(R.id.sp_producto)
        val botonAnadir = findViewById<Button>(R.id.btn_anadir_lista_producto)
        val etCantidad = findViewById<EditText>(R.id.et_cantidad_producto)
        var posRestauranteSelec: Int? = null
        var posProductoSelec: Int? = null
        var arregloOrdenes: ArrayList<Orden>
        //val arregloOrdenes = ArrayList<Orden>()

        this.arregloProducto = jalarDocsProductosDeFirestore()
        this.arregloRestaurante = jalarDocsRestauranteDeFirestore()

        setearSpinnerProductos(spProducto)
        setearSpinnerRestaurante(spRestaurante)


        spRestaurante.onItemSelectedListener =
            object : AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    posRestauranteSelec = position
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}

                override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {}

            }

        spProducto.onItemSelectedListener =
            object : AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    posProductoSelec = position
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}

                override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {}

            }

        botonAnadir.setOnClickListener {
            val cantidad = etCantidad.text.toString().toIntOrNull()

            if (posProductoSelec != 0 && posRestauranteSelec != 0 && cantidad != null) {
                if (arregloRestaurante != null && arregloProducto != null) {
                    val precio = arregloProducto[posProductoSelec!!].precio
                    val nombreRes = arregloRestaurante[posRestauranteSelec!!].nombre
                    val nombreProd = arregloProducto[posProductoSelec!!].nombre

                    val ordenNueva = Orden(
                        nombreRes,
                        nombreProd,
                        precio,
                        cantidad,
                        precio?.times(cantidad)
                    )
                    this.arregloOrdenes.add(ordenNueva)
                    poneOrdenesEnAdaptador()
                    vaciarFormulario()
                    Toast.makeText(this, "Orden agregada", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(
                    this,
                    "Favor elejir producto, restaurante e ingresar cantidad",
                    Toast.LENGTH_LONG
                ).show()
            }
        }


    }

    fun vaciarFormulario() {
        sp_restaurantes.setSelection(0)
        sp_producto.setSelection(0)
        et_cantidad_producto.text.clear()
    }

    fun poneOrdenesEnAdaptador() {
        val adaptador: ArrayAdapter<Orden> = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            this.arregloOrdenes
        )

        lv_lista_productos.adapter = adaptador
        registerForContextMenu(lv_lista_productos)
    }

    fun setearSpinnerProductos(spProducto: Spinner) {
        spProducto.adapter =
            ArrayAdapter<Producto>(this, android.R.layout.simple_list_item_1, this.arregloProducto)
    }

    fun setearSpinnerRestaurante(spRestaurante: Spinner) {
        spRestaurante.adapter = ArrayAdapter<Restaurante>(
            this,
            android.R.layout.simple_list_item_1,
            this.arregloRestaurante
        )
    }

    fun jalarDocsProductosDeFirestore(): ArrayList<Producto> {
        val db = Firebase.firestore
        val arregloProductos = arrayListOf<Producto>()

        arregloProductos.add(
            Producto(
                null,
                null
            )
        ) //Si quito esto, no puedo seleccionar en el spinner

        db.collection("producto")
            .get()
            .addOnSuccessListener { documents ->

                for (document in documents) {
                    val productoCargado: Producto = document.toObject(Producto::class.java)
                    if (productoCargado != null) {
                        arregloProductos.add(
                            Producto(
                                productoCargado.nombre,
                                productoCargado.precio
                            )
                        )
                    }
                }
                //spProducto.adapter= ArrayAdapter<FirebaseProductoDto>(this, android.R.layout.simple_list_item_1,arregloProductos)
                //Log.i("firestore-firebase","${arregloProductos[1]}")
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }

        return arregloProductos
    }

    fun jalarDocsRestauranteDeFirestore(): ArrayList<Restaurante> {
        val db = Firebase.firestore
        val arregloRestaurante = arrayListOf<Restaurante>()

        arregloRestaurante.add(Restaurante(null))

        db.collection("restaurante")
            .get()
            .addOnSuccessListener { documents ->

                for (document in documents) {
                    val restauranteCargado: Restaurante = document.toObject(Restaurante::class.java)

                    if (restauranteCargado != null) {
                        arregloRestaurante.add(Restaurante(restauranteCargado.nombre))

                    }
                }
                //spRestaurante.adapter= ArrayAdapter<FirebaseRestauranteDto>(this, android.R.layout.simple_list_item_1,arregloRestaurante)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
        return arregloRestaurante
    }

}