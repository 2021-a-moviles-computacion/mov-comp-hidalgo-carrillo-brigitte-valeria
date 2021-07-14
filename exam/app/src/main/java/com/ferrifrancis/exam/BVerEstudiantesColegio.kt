package com.ferrifrancis.exam

import Colegio
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class BVerEstudiantesColegio : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_b_ver_estudiantes_colegio)

        val colegio = intent.getParcelableExtra<Colegio>("colegio")

        if (colegio != null) {
            Log.i("intent-explicito", "${colegio.nombre}")
        }
    }

}