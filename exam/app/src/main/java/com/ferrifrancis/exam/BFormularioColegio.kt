package com.ferrifrancis.exam

import Colegio
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Switch

class BFormularioColegio : AppCompatActivity() {
    val listaColegios = ArrayList<Colegio>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_b_formulario_colegio)
        EBaseDeDatos.TablaUsuario = ESQLiteHelperUsuario(this)


        val botonAnadirColegio =  findViewById<Button>(R.id.id_btn_anadir_cole)

        botonAnadirColegio.setOnClickListener {

            val nombreColegio = findViewById<EditText>(R.id.it_nombre_cole).text.toString()
            val distrito = findViewById<EditText>(R.id.it_distrito_cole).text.toString().toInt()
            val numAulas = findViewById<EditText>(R.id.it_num_aulas_cole).text.toString().toInt()
            val esFiscal = findViewById<Switch>(R.id.sw_esfiscal_cole).isChecked
            val idColegio=EBaseDeDatos.TablaUsuario?.consultaUltimoIDColegio()

            var colegio = Colegio(null, null, null, null, null)
            if( idColegio != -1) { colegio = Colegio(nombreColegio,
                esFiscal,distrito,  numAulas, idColegio)}
            else { colegio = Colegio(nombreColegio, esFiscal,distrito,  numAulas, 0)}

            val resultadoRegistro: Boolean? =EBaseDeDatos.TablaUsuario?.creaColegioFormulario(colegio)
            Log.i("bdd","creo usuario? ${resultadoRegistro}")

            val resu=EBaseDeDatos.TablaUsuario?.consultaUltimoIDColegio()
            Log.i("bd","CANTIDAD DE CREADOS${resu}")

            abrirActividad(MainActivity::class.java)
        }

    }

    fun abrirActividad (clase: Class<*>)
    {
        val intentoExplicito = Intent(
            this,
            clase
        )
        this.startActivity(intentoExplicito)
    }
}