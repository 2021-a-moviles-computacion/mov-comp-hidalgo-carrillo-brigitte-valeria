package com.ferrifrancis.exam

import Colegio
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.widget.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.collections.ArrayList

class BFormularioColegio : AppCompatActivity() {
    var colegioNuevo: Colegio = Colegio()

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_b_formulario_colegio)


        val botonAnadirColegio =  findViewById<Button>(R.id.id_btn_anadir_cole)
        val botonEditarColegio =  findViewById<Button>(R.id.btn_editar)


        val opcionAbrirComo=intent.getIntExtra("id",-1)//recibo los datos que mandó la otra clase
        val colegio=intent.getParcelableExtra<Colegio>("colegio")//recibo los datos que mandó la otra clase
        Log.i("bd","EL ID DEL COLEGIO ${colegio?.idColegio}")


        when (opcionAbrirComo){
            //0 --> registra, ver

            0->{
                preparaActividadParaRegistrar()
                botonAnadirColegio.setOnClickListener {
                    registrarColegio() //registra y envia por intent el objeto colegio nuevo
                }
            }
            1 ->{ //1 --> edita
                if (colegio != null) {
                    preparaActividadParaEditar(colegio)

                    botonEditarColegio.setOnClickListener {
                        editarColegio( colegio) //edita y envia por intent el objeto colegio nuevo
                        //abrirActividad(MainActivity::class.java)
                    }
                }
            }
        }




    }

    fun editarColegio(colegio: Colegio) {
        Log.i("formulario-colegio","entró función editar colegio")
        val numAulas = findViewById<EditText>(R.id.it_num_aulas_cole).text.toString().toInt()
        val aulasNuevo = hashMapOf<String, Any>(
            "numAulas" to numAulas
        )

        val db = Firebase.firestore
        Log.i("formulario-colegio","id colegio ${colegio.idColegio}")
        if (colegio != null) {
            colegio.idColegio?.let {
                db.collection("colegio").document(it).update(aulasNuevo)
                    .addOnFailureListener {
                        Log.i("firestore","NO se editó colegio")
                    }
                    .addOnSuccessListener {
                        Log.i("firestore","se editó colegio")
                        colegio.numAulas = numAulas
                        Toast.makeText(this, "¡Aulas colegio actualizadas!", Toast.LENGTH_SHORT).show()
                        devuelveNuevoColegioPorIntent(colegio)
                    }
            }
        }
        else
        {
            Log.i("formulario-colegio","colegio nulo")
        }
    }

    fun devuelveNuevoColegioPorIntent(colegio: Colegio)
    {
        val intentDevolverParametros = Intent()

        intentDevolverParametros.putExtra("arregloColegiosCreados",colegio)
        Log.i("main-activity","${colegio.idColegio}")
        this.setResult(Activity.RESULT_OK, intentDevolverParametros)
        finish()
        this.finish()
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

        nombreColegio.setText(colegio.nombreColegio)
        Log.i("formulario-colegio","id colegio en fun prepara.. ${colegio.idColegio}")
        escondeEditText(nombreColegio)

        distrito.setText(colegio.distrito.toString())
        escondeEditText(distrito)

        if (colegio.esFiscal == true)
            esFiscal.setChecked(true)
        else
            esFiscal.setChecked(false)

        escondeSwitch(esFiscal)

        btnAnadirColegio.visibility= View.GONE
        botonEditarColegio.visibility= View.VISIBLE


    }


    fun registrarColegio()
    {
        //registro colegio y envio por intent el objeto colegio nuevo a la main activity

        val nombreColegio= findViewById<EditText>(R.id.it_nombre_cole).text.toString()
        val distrito = findViewById<EditText>(R.id.it_distrito_cole).text.toString().toInt()
        val numAulas = findViewById<EditText>(R.id.it_num_aulas_cole).text.toString().toInt()
        val esFiscal = findViewById<Switch>(R.id.sw_esfiscal_cole).isChecked

        val nuevoColegioHashMap = hashMapOf<String, Any>(
            "nombreColegio" to nombreColegio,
            "distrito" to distrito,
            "numAulas" to numAulas,
            "esFiscal" to esFiscal
        )

        //this.listaColegiosNuevos.add(nuevoColegio)

        val db = Firebase.firestore
        val referencia = db.collection("colegio")

        referencia.add(nuevoColegioHashMap)
            .addOnSuccessListener {
                val id: String =it.id
                this.colegioNuevo= Colegio(nombreColegio,esFiscal,distrito,numAulas,id)

                devuelveNuevoColegioPorIntent(this.colegioNuevo)
                Toast.makeText(this, "¡Colegio registrado!", Toast.LENGTH_SHORT).show()
                Log.i("firebase","Se creó colegio ID -->${id}")

            }
            .addOnFailureListener {
                Log.i("firebase","NO se creó colegio")
            }


    }


}