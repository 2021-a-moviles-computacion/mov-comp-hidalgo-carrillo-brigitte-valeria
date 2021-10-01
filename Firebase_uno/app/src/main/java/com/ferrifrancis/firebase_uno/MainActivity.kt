package com.ferrifrancis.firebase_uno

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.ferrifrancis.firebase_uno.dto.FirebaseUsuarioDto
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthAnonymousUpgradeException
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    val CODIGO_INICIO_SESION = 102

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val botonOrden = findViewById<Button>(R.id.btn_orden)
        botonOrden.setOnClickListener {
            val intent = Intent(
                this,
                EOrdenes::class.java
            )
            startActivity(intent)
        }

        val botonRestaurante = findViewById<Button>(R.id.btn_restaurante)
        botonRestaurante.setOnClickListener {
            val intent = Intent(
                this,
                CRestaurante::class.java
            )
            startActivity(intent)
        }
        val botonIrMapa = findViewById<Button>(R.id.btn_ir_mapa)
        botonIrMapa.setOnClickListener {
            val intent = Intent(
                this,
                FMapsActivity::class.java
            )
            startActivity(intent)
        }

        val botonLogin = findViewById<Button>(R.id.btn_login)
        botonLogin.setOnClickListener {
            llamarLoginUsuario()
        }

        val botonProducto = findViewById<Button>(R.id.btn_producto)
        botonProducto.setOnClickListener {
            val intent = Intent(
                this,
                CProducto::class.java
            )
            startActivity(intent)
        }
    }

    fun llamarLoginUsuario() {
        val providers = arrayListOf(
                // lista de los proveedores
                AuthUI.IdpConfig.EmailBuilder().build()
        )

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setTosAndPrivacyPolicyUrls(
                                "https://example.com/terms.html",
                                "https://example.com/privacy.html"
                        )
                        .build(),
                CODIGO_INICIO_SESION
        )
    }

    override fun onActivityResult(
            requestCode: Int, resultCode: Int, data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {

            CODIGO_INICIO_SESION -> {
                if (resultCode == Activity.RESULT_OK) {
                    val usuario: IdpResponse? = IdpResponse.fromResultIntent(data)
                    if(usuario != null) {

                        if (usuario?.isNewUser == true) {
                            Log.i("firebase-login", "Nuevo Usuario")
                            registrarUsuarioPorPrimeraVez(usuario)
                        } else {
                            setearUsuarioFirebase()
                            Log.i("firebase-login", "Usuario Antiguo")
                        }
                    }
                } else {
                    Log.i("firebase-login", "El usuario cancelo")
                }
            }
        }
    }

    fun setearUsuarioFirebase()
    {
        val instanciaAuth = FirebaseAuth.getInstance()//usuario que actualmente estalogead
        val usuarioLocal = instanciaAuth.currentUser

        if(usuarioLocal !=null)
        {
            if(usuarioLocal.email != null)
            {
                val db = Firebase.firestore
                val referencia = db. collection("usuario") //de la coleccin usuario
                    .document(usuarioLocal.email.toString()) //deme el doc con este email

                referencia.get()
                    .addOnSuccessListener {
                        val usuarioCargado = it.toObject(FirebaseUsuarioDto::class.java)

                        Log.i("firebase-firestore", "Usuario cargado")
                        if(usuarioCargado !=null){
                            BAuthUsuario.usuario = BUsuarioFirebase(
                                usuarioCargado.uid,
                                usuarioCargado.email,
                                usuarioCargado.roles

                            )
                            setearBienvenida()
                        }
                    }
                    .addOnFailureListener {
                        Log.i("firebase-firestore","Falló cargar usuario")
                    }

            }
        }
    }

    fun setearBienvenida()
    {
        val textViewBienvenida = findViewById<TextView>(R.id.tv_bienvenida)
        val botonLogin = findViewById<Button>(R.id.btn_login)
        val botonLogout = findViewById<Button>(R.id.btn_logout)
        val botonProducto = findViewById<Button>(R.id.btn_producto)
        val botonRestaurante = findViewById<Button>(R.id.btn_restaurante)
        val botonOrden = findViewById<Button>(R.id.btn_orden)

        if(BAuthUsuario.usuario != null)
        {
            textViewBienvenida.text = "Bienvenido ${BAuthUsuario.usuario?.email}"
            botonLogin.visibility = View.INVISIBLE
            botonProducto.visibility = View.VISIBLE
            botonLogout.visibility = View.VISIBLE
            botonRestaurante.visibility = View.VISIBLE
            botonOrden.visibility = View.VISIBLE
        }
        else
        {
            textViewBienvenida.text = "Ingresa al aplicativo"
            botonLogin.visibility = View.VISIBLE
            botonLogout.visibility = View.INVISIBLE
            botonProducto.visibility = View.INVISIBLE
            botonRestaurante.visibility = View.INVISIBLE
            botonOrden.visibility = View.INVISIBLE
        }
    }

    fun solicitarSalirDelAplicativo(){

        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
                BAuthUsuario.usuario = null
                setearBienvenida()
            }
    }


    fun registrarUsuarioPorPrimeraVez(usuario: IdpResponse )
    {
        val usuarioLogeado: FirebaseUser? = FirebaseAuth.getInstance().getCurrentUser() //recibo el usuario
        if(usuario.email !=null && usuarioLogeado != null)
        {
            //roles : ["usuario","admin" ]
            val db = Firebase.firestore //base dedatos
            val rolesUsuario = arrayListOf("usuario")  //le digo los roles
            val identificadorUsuario = usuario.email
            val nuevoUsuario = hashMapOf<String, Any>( //creo el nuevo usuario con:..
                "roles" to rolesUsuario,//.. su rol
            "uid" to usuarioLogeado.uid, // .. su uid
            "email" to identificadorUsuario.toString()
            )


            db.collection("usuario") //en la coleccion llamada usuario
                //forma 1
                //.add(nuevoUsuario) //añado el nuevo uusario
                //forma2
                .document(identificadorUsuario.toString())
                .set(nuevoUsuario)
                .addOnSuccessListener {
                    setearUsuarioFirebase()
                    Log.i("firebase-firestore", "Se creó") //si todo sale bien entonces--
                }
                .addOnFailureListener {
                 Log.i("firebase-firestore", "Falló") // si sale mal entonces..
                }
        }
        else {
            Log.i("firebase-login","ERROR")
        }
    }


}