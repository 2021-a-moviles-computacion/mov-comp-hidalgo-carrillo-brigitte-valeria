package com.ferrifrancis.andorid

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val CODIGO_RESPUESTA_INTENT_EXPLICITO = 401
    val CODIGO_RESPUESTA_INTENT_IMPLICITO = 402

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        EBaseDeDatos.TablaUsuario = ESqliteHelperUsuario(this)
    /*
        if(EBaseDeDatos.TablaUsuario != null){
            EBaseDeDatos.TablaUsuario?.consultarUsuarioPorId()
            EBaseDeDatos.TablaUsuario?.crearUsuarioFormulario()
            EBaseDeDatos.TablaUsuario?.eliminarUsuarioFormulario()
            EBaseDeDatos.TablaUsuario?.actualizarUsuarioFormulario()
        }
    */
        setContentView(R.layout.activity_main)
        //LOG
        //TIPOS: informacion, debug, warning
        Log.i("ciclo-vida", "onCreate")

        val botonIrACicloVida = findViewById<Button>(
            R.id.btn_ciclo_vida
        //R.id : busca los recursos que he creando
        )
        botonIrACicloVida.setOnClickListener { //escucho el boton
            abrirActividad(ACicloVida::class.java)
        }
        //abre boton
        val botonIrListView = findViewById<Button>(
            R.id.btn_ir_list_view
        )
        //escucha los clicks
        btn_ir_list_view.setOnClickListener {
            abrirActividad(BListView::class.java)//abre esta clase
        }

        val botonIrIntentw = findViewById<Button>(
            R.id.btn_ir_intent
        )
        //escucha los clicks
        botonIrIntentw.setOnClickListener {
            abrirActividadConParametros(CIntentExplicitParametros::class.java)//abre esta clase
        }

        val botonAbrirIntentImplicito = findViewById<Button>(
            R.id.implicitobtn_ir_intent_implicito
            //R.id : busca los recursos que he creando
        )
        botonAbrirIntentImplicito.setOnClickListener { //escucho el boton
            val intentConRespuesta = Intent(
                Intent.ACTION_PICK,
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI
            )
            startActivityForResult(intentConRespuesta, CODIGO_RESPUESTA_INTENT_IMPLICITO)
        }

        //abre boton
        val botonIrDeberClase = findViewById<Button>(
            R.id.btn_claseDeber
        )
        //escucha los clicks
        botonIrDeberClase.setOnClickListener {
            abrirActividad(ClaseDeber::class.java)//abre esta clase
        }

    }

    fun abrirActividadConParametros(clase: Class<*>)
    {
        val intentExplicito = Intent(
            this, clase
        )
        //put extra solo me deja mandar variables primitivas
        intentExplicito.putExtra("nombre", "Adrian")
        intentExplicito.putExtra("apellido", "Eguez")
        intentExplicito.putExtra("edad", "32")
        intentExplicito.putExtra("entrenador",
            BEntrenador("Adrian","Eguez", null)
            )
        startActivityForResult(intentExplicito, CODIGO_RESPUESTA_INTENT_EXPLICITO)
        /*
        registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        {
            when(it.resultCode)
            {
                Activity.RESULT_OK ->
                {
                    it.data?.getStringExtra("nombreModificado")
                    it.data?.getIntExtra("edadModificado",0)
                    it.data?.getParcelableExtra<BEntrenador>("entrenadorModificado")

                }
            }
        }
        */

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode){
                //agarron el requestCode
            CODIGO_RESPUESTA_INTENT_EXPLICITO->{
                if (resultCode == RESULT_OK)
                {
                    Log.i("intent-explicito", "SI actualizó")
                    if (data != null)
                    {
                        val nombre = data.getStringExtra("nombreModificado")
                        val edad = data.getStringExtra("edadModificado")
                        val entrenador = data.getParcelableExtra<BEntrenador>("nombreModificado")
                        Log.i("intent-explicito","${nombre}")
                        Log.i("intent-explicito","${edad}")
                        Log.i("intent-explicito","${entrenador}")
                    }
                }
            }
            CODIGO_RESPUESTA_INTENT_IMPLICITO ->{
                if(resultCode == RESULT_OK)
                {
                    if(data != null)
                    {
                        if(data.data != null)
                        {
                            val uri: Uri = data.data!!

                            val cursor = contentResolver.query(
                                uri,
                                null,
                                null,
                                null,
                                null,
                                null
                            )
                            cursor?.moveToFirst()
                            val indiceTelefono = cursor?.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER
                            )
                            val telefono = cursor?.getString(
                                indiceTelefono!!
                            )
                            cursor?.close()
                            Log.i("resultado", "telefono${telefono}")
                        }
                    }
                }

            }
        }
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
    fun abrirActividad(clase : Class<*>)
    {
        //para que se abra la página debo hacer un intent
        val intentExplicito = Intent(
            this,
            clase
        )
        //startActivity para que aparezca la nueva actividad
        this.startActivity(intentExplicito)
    }
}