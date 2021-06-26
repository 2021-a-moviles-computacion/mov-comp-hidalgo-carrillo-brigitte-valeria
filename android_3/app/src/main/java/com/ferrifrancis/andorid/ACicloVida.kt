package com.ferrifrancis.andorid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Button
import android.widget.TextView

class ACicloVida : AppCompatActivity() {
    var numero =0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_a_ciclo_vida)
        Log.i("ciclo-vida", "onCreate")

        val textViewACicloVida = findViewById<TextView>(R.id.txt_ciclo_vida)
        textViewACicloVida.text = numero.toString()
            val buttonACicloVida = findViewById<Button>(
                    R.id.btn_ciclo_vida
                    )

        buttonACicloVida.setOnClickListener{aumentarNumero() }


    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.run {
            //aqui guardamos solo primitivos
            putInt("numeroGuardado",numero)
        }
        super.onSaveInstanceState(outState)
        Log.i("ciclo-vida", "onSaveInstanceState")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val numeroRecuperado: Int? = savedInstanceState.getInt("numeroGuardado")
        if(numeroRecuperado != null)
        {
            Log.i("ciclo-vida","llego numero ${numeroRecuperado}")
            numero = numeroRecuperado
            val textViewACicloVida = findViewById<TextView>(
                R.id.txt_ciclo_vida
            )
            textViewACicloVida.text = numero.toString()
        }
    }

    fun aumentarNumero()
    {
        numero = numero +1
        val textViewACicloVida = findViewById<TextView>(
            R.id.txt_ciclo_vida
        )
        textViewACicloVida.text = numero.toString()
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