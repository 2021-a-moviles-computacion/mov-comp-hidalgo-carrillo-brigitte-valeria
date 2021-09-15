package com.ferrifrancis.exam

import Colegio
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_b_formulario_colegio.*
import kotlin.collections.ArrayList

class BFormularioColegio : AppCompatActivity() {
    val listaColegios = ArrayList<Colegio>()

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_b_formulario_colegio)


        val botonAnadirColegio =  findViewById<Button>(R.id.id_btn_anadir_cole)
        val botonEditarColegio =  findViewById<Button>(R.id.btn_editar)
        val nombreColegio: EditText = findViewById<EditText>(R.id.it_nombre_cole)
        val distrito = findViewById<EditText>(R.id.it_distrito_cole)
        val numAulas = findViewById<EditText>(R.id.it_num_aulas_cole)
        val esFiscal = findViewById<Switch>(R.id.sw_esfiscal_cole)

        val opcionAbrirComo=intent.getIntExtra("id",-1)//recibo los datos que mandó la otra clase
        val colegio=intent.getParcelableExtra<Colegio>("colegio")//recibo los datos que mandó la otra clase
        Log.i("bd","EL ID DEL COLEGIO ${colegio?.idColegio}")


        when (opcionAbrirComo){
            //0 --> registra, ver

            0->{
                preparaActividadParaRegistrar()
                botonAnadirColegio.setOnClickListener {
                    registrarColegio()

                }
            }
            1 ->{ //1 --> edita
                if (colegio != null) {
                    preparaActividadParaEditar(colegio)

                    botonEditarColegio.setOnClickListener {
                        Log.i("bd", "ID COLEGIO ${numAulas.text.toString().toInt()}")
                        val resulAct = EBaseDeDatos.TablaUsuario!!.actualizarAulasColegioPorID(
                            colegio.idColegio,
                            numAulas.text.toString().toInt()
                        )
                        if (resulAct) Toast.makeText(this, "¡Colegio editado!", Toast.LENGTH_SHORT).show()
                        Log.i("bd", "actualizo? ${resulAct}")
                        abrirActividad(MainActivity::class.java)
                    }
                }
            }
        }



    }

    fun preparaActividadParaRegistrar()
    {
        val botonEditarColegio =  findViewById<Button>(R.id.btn_editar)
        val botonAnadirColegio =  findViewById<Button>(R.id.id_btn_anadir_cole)
        botonEditarColegio.visibility= View.GONE
        botonAnadirColegio.visibility= View.VISIBLE

    }

    fun escondeEditText(objeto: EditText){
        objeto.setFocusable(false)
        objeto.setEnabled(false)
        objeto.setCursorVisible(false)
        objeto.setKeyListener(null)
    }
    fun escondeSwitch(objeto: Switch){
        objeto.setFocusable(false);
        objeto.setEnabled(false);
        objeto.setCursorVisible(false);
        objeto.setKeyListener(null);
    }
    fun preparaActividadParaEditar( colegio: Colegio)
    {
        val nombreColegio: EditText = findViewById<EditText>(R.id.it_nombre_cole)
        val distrito = findViewById<EditText>(R.id.it_distrito_cole)
        val esFiscal = findViewById<Switch>(R.id.sw_esfiscal_cole)
        val btnAnadirColegio =  findViewById<Button>(R.id.id_btn_anadir_cole)
        val botonEditarColegio =  findViewById<Button>(R.id.btn_editar)

        //Setea y edita los edit text que no puede editar
        nombreColegio.setText(colegio.nombre)
        escondeEditText(nombreColegio)

        distrito.setText(colegio.distrito.toString())
        escondeEditText(distrito)

        if (colegio.esFiscal == true)
            esFiscal.setChecked(true)
        else
            esFiscal.setChecked(false)

        esFiscal.setText(colegio.distrito.toString())
        escondeSwitch(esFiscal)

        btnAnadirColegio.visibility= View.GONE
        botonEditarColegio.visibility= View.VISIBLE


    }

    fun registrarColegio()
    {
        val nombreColegio: EditText = findViewById<EditText>(R.id.it_nombre_cole)
        val distrito = findViewById<EditText>(R.id.it_distrito_cole)
        val numAulas = findViewById<EditText>(R.id.it_num_aulas_cole)
        val esFiscal = findViewById<Switch>(R.id.sw_esfiscal_cole)

        val nuevoColegio = hashMapOf<String, Any>(
            "nombreColegio" to nombreColegio.text.toString(),
            "distrito" to distrito.text.toString().toInt(),
            "numAulas" to numAulas.text.toString().toInt(),
            "esFiscal" to esFiscal.isChecked
        )
        val db = Firebase.firestore
        val referencia = db.collection("colegio")

        referencia.add(nuevoColegio)
            .addOnSuccessListener {
                Toast.makeText(this, "¡Colegio registrado!", Toast.LENGTH_SHORT).show()
                Log.i("firebase","Se creo colegio")

            }
            .addOnFailureListener {

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