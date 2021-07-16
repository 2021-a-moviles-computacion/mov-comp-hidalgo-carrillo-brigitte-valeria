package com.ferrifrancis.exam

import Colegio
import Estudiante
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import com.google.android.material.chip.ChipGroup

class BFormularioEstudiante : AppCompatActivity() {

    var listaEstudiante = ArrayList<Estudiante>()
    var idColegio =-1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_b_formulario_estudiante)


        val colegio = intent.getParcelableExtra<Colegio>("colegio")//recibo los datos que mandó la otra clase
        EBaseDeDatos.TablaUsuario = ESQLiteHelperUsuario(this)
        val txt_nombreColegio = findViewById<TextView>(R.id.tv_pone_nombre_cole_for_est)
        val botonRegsitrarEstudiante = findViewById<Button>(R.id.btn_registrar_for_est)

        botonRegsitrarEstudiante.setOnClickListener {
            if (colegio != null) {
                txt_nombreColegio.text = colegio.nombre
                this.idColegio = colegio.idColegio!!

                val estudiante = getValoresFormularioDevuelveEstudiante()
                val resultadoTabla =
                    EBaseDeDatos.TablaUsuario!!.crearEstudianteFormulario(estudiante)

                if (resultadoTabla) {
                    Log.i("bdd", "Estudiante creado")
                }
            }
            abrirActividad(MainActivity::class.java)
        }
    }

    fun getValoresFormularioDevuelveEstudiante(): Estudiante
    {
        val nombre = findViewById<EditText>(R.id.it_nombre_est_for_est).text.toString()
        val cedula = findViewById<EditText>(R.id.it_cedula_for_est).text.toString()
        val curso = findViewById<EditText>(R.id.it_curso_for_est).text.toString()
        val fecha = findViewById<EditText>(R.id.it_fecha_nac_for_est).text.toString()
        val sexo = if (findViewById<RadioGroup>(R.id.rg_sexo_for_est).checkedRadioButtonId == 0) "F" else "M"

        val estudiante = Estudiante(nombre, fecha, curso, cedula, sexo, this.idColegio)

        return estudiante
    }

    fun abrirActividad(clase : Class <*>)
    {
        val intentExplicito = Intent(
            this,
            clase
        )
        this.startActivity(intentExplicito)
    }
}