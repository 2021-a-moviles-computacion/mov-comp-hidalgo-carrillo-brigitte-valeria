package com.ferrifrancis.exam

import Colegio
import Estudiante
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import android.widget.Toast.LENGTH_SHORT
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class BFormularioEstudiante : AppCompatActivity() {

    var listaEstudiante = ArrayList<Estudiante>()
    var colegio: Colegio? = Colegio()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_b_formulario_estudiante)

        //0 --> registra, ver
        //1 --> edita

        this.colegio =
            intent.getParcelableExtra<Colegio>("colegio")//recibo los datos que mandó la otra clase
        val estudiante =
            intent.getParcelableExtra<Estudiante>("estudiante")//recibo los datos que mandó la otra clase
        val abrirFormularioComo =
            intent.getIntExtra("id", -1)//recibo los datos que mandó la otra clase


        val txt_nombreColegio = findViewById<TextView>(R.id.tv_pone_nombre_cole_for_est)
        txt_nombreColegio.text = colegio?.nombreColegio
        val botonRegsitrarEstudiante = findViewById<Button>(R.id.btn_registrar_for_est)
        val botonEditarEstudiante = findViewById<Button>(R.id.btn_editar_for_est)

         val curso = findViewById<EditText>(R.id.it_curso_for_est)

        when (abrirFormularioComo) {
            0 -> { //registrar
                prepararActividadParaRegistrar(botonEditarEstudiante)
                botonRegsitrarEstudiante.setOnClickListener {
                    registraEstudianteFirestore()
                    //abrirActividad(MainActivity::class.java)

                }
            }
            1 -> { //editar
                if (estudiante != null) {
                    preparaActividadParaEditar(
                        estudiante
                    )
                    botonEditarEstudiante.setOnClickListener {
                        val resulAct = EBaseDeDatos.TablaUsuario!!.actualizarCursoEstudiantePorID(
                            estudiante.cedula!!,
                            curso.text.toString()
                        )
                        Log.i("bd", "editó estudiante? ${resulAct}")
                        Toast.makeText(this, "¡Estudiante editado!", LENGTH_SHORT).show()
                        //abrirActividad(MainActivity::class.java)
                    }
                }
            }

        }

    }

    fun registraEstudianteFirestore() {
        Log.i("form-estudiante", "Entro registra estudiante")

        //var estudianteNuevo=Estudiante()
        val nombre = findViewById<EditText>(R.id.it_nombre_est_for_est).text.toString()
        val cedula = findViewById<EditText>(R.id.it_cedula_for_est).text.toString()
        val curso = findViewById<EditText>(R.id.it_curso_for_est).text.toString()
        val fecha = findViewById<EditText>(R.id.it_fecha_nac_for_est).text.toString()
        val sexo =
            if (findViewById<RadioGroup>(R.id.rg_sexo_for_est).checkedRadioButtonId == 0) "F" else "M"
        val idColegio = colegio?.idColegio

        Log.i("form-estudiante", "id colegio ${idColegio}")

        if (idColegio != null) {
            val nuevoEstudiante = hashMapOf<String, Any>(
                "nombre" to nombre,
                "fechaNacimiento" to fecha,
                "curso" to curso,
                "cedula" to cedula,
                "sexo" to sexo,
                "idColegio" to idColegio!!

            )

            val db = Firebase.firestore
            val referencia = db.collection("estudiante")

            referencia.add(nuevoEstudiante)
                .addOnSuccessListener {
                    Toast.makeText(this, "¡Estudiante registrado!", Toast.LENGTH_SHORT).show()
                    abreMainPorIntent()
                    Log.i("form-estudiante", "Firebase: Se creó estudiante")
                }
                .addOnFailureListener {
                    Log.i("form-estudiante", "Firebase: NO se creó estudainte")
                }
        }
    }

    private fun abreMainPorIntent() {
        val intentDevolverParametros = Intent()
        //intentDevolverParametros.putExtra("arregloColegiosCreados",this.colegioNuevo)
        this.setResult(Activity.RESULT_OK, intentDevolverParametros)
        finish()
        this.finish()
    }

    fun escondeEditText(objeto: EditText) {
        objeto.setFocusable(false);
        objeto.setEnabled(false);
        objeto.setCursorVisible(false);
        objeto.setKeyListener(null);
    }

    fun esconderRadioButton(objeto: RadioButton) {
        objeto.setFocusable(false);
        objeto.setEnabled(false);
        objeto.setCursorVisible(false);
        objeto.setKeyListener(null);
    }

    fun prepararActividadParaRegistrar(btnEditar: Button) {

        btnEditar.visibility = View.GONE

    }

    @SuppressLint("ResourceType", "WrongViewCast")
    fun preparaActividadParaEditar(
        estudiante: Estudiante

    ) {
        val nombre = findViewById<EditText>(R.id.it_nombre_est_for_est)
        val cedula = findViewById<EditText>(R.id.it_cedula_for_est)
        val curso = findViewById<EditText>(R.id.it_curso_for_est)
        val fecha = findViewById<EditText>(R.id.it_fecha_nac_for_est)
        val sexo = findViewById<RadioGroup>(R.id.rg_sexo_for_est)
        val botonRegistrar = findViewById<Button>(R.id.btn_registrar_for_est)

        //esconde lo que no debe aparecer
        findViewById<TextView>(R.id.tv_registra_est_for_est).visibility = View.GONE
        findViewById<TextView>(R.id.tv_informacion_colegio_for_est).visibility = View.GONE
        findViewById<TextView>(R.id.tv_pone_nombre_cole_for_est).visibility = View.GONE
        botonRegistrar.visibility = View.GONE
        //Setea y edita los edit text que no puede editar
        nombre.setText(estudiante.nombre)
        escondeEditText(nombre)

        cedula.setText(estudiante.cedula)
        escondeEditText(cedula)

        fecha.setText(estudiante.fechaNacimiento)
        escondeEditText(fecha)

        Log.i("list-view", "${estudiante.sexo}")

        val fem = findViewById<RadioButton>(R.id.rb_femenino_for_est)
        val mas = findViewById<RadioButton>(R.id.rb_masculino_for_est)
        if (estudiante.sexo == "M") {
            sexo.check(R.id.rb_masculino_for_est)
        } else
            sexo.check(R.id.rb_femenino_for_est)
        esconderRadioButton(fem)
        esconderRadioButton(mas)
        curso.setFocusable(true)
        curso.setCursorVisible(true)


    }

    fun getValoresFormularioDevuelveEstudiante(): Estudiante {
        val nombre = findViewById<EditText>(R.id.it_nombre_est_for_est).text.toString()
        val cedula = findViewById<EditText>(R.id.it_cedula_for_est).text.toString()
        val curso = findViewById<EditText>(R.id.it_curso_for_est).text.toString()
        val fecha = findViewById<EditText>(R.id.it_fecha_nac_for_est).text.toString()
        val sexo =
            if (findViewById<RadioGroup>(R.id.rg_sexo_for_est).checkedRadioButtonId == 0) "F" else "M"

        var estudiante = Estudiante()
        if (colegio?.idColegio != null) {
            estudiante = Estudiante(nombre, fecha, curso, cedula, sexo, colegio!!.idColegio)
        }
        return estudiante
    }

    fun abrirActividad(clase: Class<*>) {
        val intentExplicito = Intent(
            this,
            clase
        )
        this.startActivity(intentExplicito)
    }
}