package com.ferrifrancis.exam

import Colegio
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import kotlin.collections.ArrayList

class BFormularioColegio : AppCompatActivity() {
    val listaColegios = ArrayList<Colegio>()

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_b_formulario_colegio)
        EBaseDeDatos.TablaUsuario = ESQLiteHelperUsuario(this)

        val botonAnadirColegio =  findViewById<Button>(R.id.id_btn_anadir_cole)
        val botonEditarColegio =  findViewById<Button>(R.id.btn_editar)
        val nombreColegio: EditText = findViewById<EditText>(R.id.it_nombre_cole)
        val distrito = findViewById<EditText>(R.id.it_distrito_cole)
        val numAulas = findViewById<EditText>(R.id.it_num_aulas_cole)
        val txtEditarColegio = findViewById<TextView>(R.id.tv_editar_colegio)
        val txtRegistrarColegio = findViewById<TextView>(R.id.tv_registrar_colegio)
        val esFiscal = findViewById<Switch>(R.id.sw_esfiscal_cole)

        val opcionAbrirComo=intent.getIntExtra("id",-1)//recibo los datos que mandó la otra clase
        val colegio=intent.getParcelableExtra<Colegio>("colegio")//recibo los datos que mandó la otra clase
        Log.i("bd","EL ID DEL COLEGIO ${colegio?.idColegio}")


        when (opcionAbrirComo){
            //0 --> registra, ver

            0->{
                preparaActividadParaRegistrar(botonEditarColegio,txtEditarColegio,botonAnadirColegio,txtRegistrarColegio)
                botonAnadirColegio.setOnClickListener {
                    val resulRegis=registrarColegio(nombreColegio,distrito,numAulas,esFiscal)
                    Log.i("bdd","actualizo? ${resulRegis}")
                    abrirActividad(MainActivity::class.java)

                }
            }
            1 ->{ //1 --> edita
                if (colegio != null) {
                    preparaActividadParaEditar(nombreColegio,distrito,numAulas,esFiscal,
                        colegio,botonAnadirColegio,txtRegistrarColegio
                    ,botonEditarColegio,txtEditarColegio)

                    botonEditarColegio.setOnClickListener {
                        Log.i("bd", "ID COLEGIO ${numAulas.text.toString().toInt()}")
                        val resulAct = EBaseDeDatos.TablaUsuario!!.actualizarAulasColegioPorID(
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

    fun preparaActividadParaRegistrar(botonEditarColegio: Button,txtEditarColegio: TextView,
        botonAnadirColegio: Button,txtRegistrarColegio: TextView)
    {
        botonEditarColegio.visibility= View.GONE
        txtEditarColegio.visibility= View.GONE
        botonAnadirColegio.visibility= View.VISIBLE
        txtRegistrarColegio.visibility= View.VISIBLE
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
    fun preparaActividadParaEditar(nombreColegio: EditText,distrito: EditText,
                          numAulas: EditText,esFiscal: Switch, colegio: Colegio,
                            btnAnadir: Button, txtRegistrarColegio: TextView
                                   ,botonEditarColegio: Button,txtEditarColegio: TextView)
    {
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

        btnAnadir.visibility= View.GONE
        txtRegistrarColegio.visibility= View.GONE
        botonEditarColegio.visibility= View.VISIBLE
        txtEditarColegio.visibility= View.VISIBLE


    }

    fun registrarColegio(nombreColegio: EditText,distrito: EditText,
                         numAulas: EditText,esFiscal: Switch): Boolean?
    {
        val nombreColegio = nombreColegio.text.toString()
        val distrito = distrito.text.toString().toInt()
        val numAulas = numAulas.text.toString().toInt()
        val esFiscal = esFiscal.isChecked
        val idColegio=EBaseDeDatos.TablaUsuario?.consultaUltimoIDColegio()

        var colegio = Colegio(null, null, null, null, null)
        if( idColegio != -1) { colegio = Colegio(nombreColegio,
            esFiscal,distrito,  numAulas, idColegio)}
        else { colegio = Colegio(nombreColegio, esFiscal,distrito,  numAulas, 0)}

        val resultadoRegistro: Boolean? =EBaseDeDatos.TablaUsuario?.creaColegioFormulario(colegio)
        Log.i("bdd","creo usuario? ${resultadoRegistro}")

        val resu=EBaseDeDatos.TablaUsuario?.consultaUltimoIDColegio()
        Log.i("bd","CANTIDAD DE CREADOS${resu}")

        return resultadoRegistro
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