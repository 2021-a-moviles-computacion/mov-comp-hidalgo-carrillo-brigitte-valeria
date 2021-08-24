package com.ferrifrancis.firebase_uno

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
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

        val botonLogin = findViewById<Button>(R.id.btn_login)
        botonLogin.setOnClickListener {
            llamarLoginUsuario()
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
                            Log.i("firebase-login", "Usuario Antiguo")
                        }
                    }
                } else {
                    Log.i("firebase-login", "El usuario cancelo")
                }
            }
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
            val nuevoUsuario = hashMapOf<String, Any>( //creo el nuevo usuario con:..
                "roles" to rolesUsuario,//.. su rol
            "uid" to usuarioLogeado.uid // .. su uid
            )
            val identificadorUsuario = usuario.email

            db.collection("usuario") //en la coleccion llamada usuario
                //forma 1
                //.add(nuevoUsuario) //añado el nuevo uusario
                //forma2
                .document(identificadorUsuario.toString())
                .set(nuevoUsuario)
                .addOnSuccessListener {
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