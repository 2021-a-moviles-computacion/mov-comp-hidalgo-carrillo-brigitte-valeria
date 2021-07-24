package com.ferrifrancis.exam

import Colegio
import Estudiante
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.google.android.material.chip.ChipGroup

class BFormularioEstudiante : AppCompatActivity() {

    var listaEstudiante = ArrayList<Estudiante>()
    var idColegio =-1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_b_formulario_estudiante)

        //0 --> registra, ver
        //1 --> edita

        val colegio = intent.getParcelableExtra<Colegio>("colegio")//recibo los datos que mandó la otra clase
        val estudiante = intent.getParcelableExtra<Estudiante>("estudiante")//recibo los datos que mandó la otra clase
        val abrirFormularioComo = intent.getIntExtra("id",-1)//recibo los datos que mandó la otra clase
        EBaseDeDatos.TablaUsuario = ESQLiteHelperUsuario(this)

        val txt_nombreColegio = findViewById<TextView>(R.id.tv_pone_nombre_cole_for_est)
        txt_nombreColegio.text = colegio?.nombre
        val botonRegsitrarEstudiante = findViewById<Button>(R.id.btn_registrar_for_est)

        val nombre = findViewById<EditText>(R.id.it_nombre_est_for_est)
        val cedula = findViewById<EditText>(R.id.it_cedula_for_est)
        val curso = findViewById<EditText>(R.id.it_curso_for_est)
        val fecha = findViewById<EditText>(R.id.it_fecha_nac_for_est)
        val sexo =findViewById<RadioGroup>(R.id.rg_sexo_for_est)

        when (abrirFormularioComo)
        {
            0->{ //registrar
                botonRegsitrarEstudiante.setOnClickListener {
                    if (colegio != null) {

                        Log.i("bdd", "nombre colegio${colegio.nombre}")
                        this.idColegio = colegio.idColegio!!

                        val estudiante = getValoresFormularioDevuelveEstudiante()
                        val resultadoTabla =
                            EBaseDeDatos.TablaUsuario!!.crearEstudianteFormulario(estudiante)

                        if (resultadoTabla) {
                            Log.i("bdd", "Estudiante creado")
                        }
                        abrirActividad(MainActivity::class.java)
                    }

                }
            }
            1->{ //editar
                if (estudiante != null) {
                    preparaActividadParaEditar(estudiante ,nombre,cedula,fecha,sexo,curso)
                    botonEditarColegio.setOnClickListener {
                        Log.i("bd", "ID COLEGIO ${numAulas.text.toString().toInt()}")
                        val resulAct = EBaseDeDatos.TablaUsuario!!.actualizarColegioPorID(
                            colegio.idColegio,
                            numAulas.text.toString().toInt()
                        )
                        Log.i("bd", "actualizo? ${resulAct}")
                        abrirActividad(MainActivity::class.java)
                    }
                }
            }

        }

    }
    fun escondeEditText(objeto: EditText){
        objeto.setFocusable(false);
        objeto.setEnabled(false);
        objeto.setCursorVisible(false);
        objeto.setKeyListener(null);
    }

    fun esconderRadioButton(objeto: RadioButton){
        objeto.setFocusable(false);
        objeto.setEnabled(false);
        objeto.setCursorVisible(false);
        objeto.setKeyListener(null);
    }

    @SuppressLint("ResourceType")
    fun preparaActividadParaEditar(estudiante: Estudiante, nombre: EditText, cedula: EditText
                                   , fecha: EditText, sexo: RadioGroup, curso: EditText)
    {


        //Setea y edita los edit text que no puede editar
        nombre.setText(estudiante.nombre)
        escondeEditText(nombre)

        cedula.setText(estudiante.cedula)
        escondeEditText(cedula)

        fecha.setText(estudiante.fechaNacimiento)
        escondeEditText(fecha)

        Log.i("list-view","${estudiante.sexo}")

        val fem = findViewById<RadioButton>(R.id.rb_femenino_for_est)
        val mas = findViewById<RadioButton>(R.id.rb_masculino_for_est)
        if (estudiante.sexo == "M")
        {
            sexo.check(R.id.rb_masculino_for_est)
        }
        else
            sexo.check(R.id.rb_femenino_for_est)
        esconderRadioButton(fem)
        esconderRadioButton(mas)
        curso.setFocusable(true)
        curso.setCursorVisible(true)


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