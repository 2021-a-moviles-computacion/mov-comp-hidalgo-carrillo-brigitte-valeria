package com.ferrifrancis.exam

import Colegio
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView

class BVerEstudiantesColegio : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_b_ver_estudiantes_colegio)

        val colegio = intent.getParcelableExtra<Colegio>("colegio")

        if (colegio != null) {
            val tvNombreCoelgio = findViewById<TextView>(R.id.tv_ver_est_cole)
            tvNombreCoelgio.text = colegio.nombre
            Log.i("intent-explicito", "${colegio.nombre}")
        }
    }


}