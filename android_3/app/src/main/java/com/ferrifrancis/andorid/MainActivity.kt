package com.ferrifrancis.andorid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //LOG
        //TIPOS: informacion, debug, warning
        Log.i("ciclo-vida", "onCreate")

        val botonIrACicloVida = findViewById<Button>(
            R.id.btn_ciclo_vida
        //R.id : busca los recursos que he creando
        )
        botonIrACicloVida.setOnClickListener { //escucho el boton
            abrirCicloVida()
        }
    }

    override  fun onStart()
    { super.onStart()
        Log.i("ciclo-vida", "onStart")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i("ciclo-vida", "onRestart")
    }

    override fun onResume() {
        super.onResume()
        Log.i("ciclo-vida", "onResume")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("ciclo-vida", "onDestroy")
    }

    override fun onPause() {
        super.onPause()
        Log.i("ciclo-vida", "onPause")
    }

    //funcion para abrir pantalla ciclo de vida
    fun abrirCicloVida()
    {
        //para que se abra la página debo hacer un intent
        val intentExplicito = Intent(
            this,
            ACicloVida:: class.java
        )
        //startActivity para que aparezca la nueva actividad
        this.startActivity(intentExplicito)
    }
}