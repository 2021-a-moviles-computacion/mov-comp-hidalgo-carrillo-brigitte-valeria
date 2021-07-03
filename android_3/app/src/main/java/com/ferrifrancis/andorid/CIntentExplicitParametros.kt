package com.ferrifrancis.andorid

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

class CIntentExplicitParametros : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_c_intent_explicit_parametros)

        val nombre = intent.getStringExtra("nombre")
        val apellido = intent.getStringExtra("apellido")
        val edad = intent.getIntExtra("edad",0)
        val entrenador = intent.getParcelableExtra<BEntrenador>("entrenador")

        Log.i("intent-explicito","${nombre}")
        Log.i("intent-explicito","${apellido}")
        Log.i("intent-explicito","${edad}")
        Log.i("intent-explicito","${entrenador}")

        val botonDevolverRespuesta = findViewById<Button>(R.id.btn_devolver_respuesta)
        botonDevolverRespuesta.setOnClickListener {
            val intentDevolverParametros = Intent()

            intentDevolverParametros.putExtra("nombreModificado", "Vicente")
            intentDevolverParametros.putExtra("edadModificado", "33")
            intentDevolverParametros.putExtra("entrenadorModificado", BEntrenador("vicente","sarzosa"))
            this.setResult(Activity.RESULT_OK, intentDevolverParametros)
            //finish()
            this.finish()
        }
    }
}