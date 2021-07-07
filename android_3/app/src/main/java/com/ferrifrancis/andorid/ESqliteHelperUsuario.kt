package com.ferrifrancis.andorid

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.content.Intent

class ESqliteHelperUsuario(
    context: Context?
) : SQLiteOpenHelper(
    context,
    "moviles",
    null,
    1
)  {


    override fun onCreate(db: SQLiteDatabase?) {

        val scriptCrearTablaUsuario = """ 
            CREATE TABLE USUARIO(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre VARCHAR(50),
                descripcion VARCHAR(50)
            )
        """.trimIndent()
        Log.i("bbd", "Creando la tabla de usuario")
        db?.execSQL(scriptCrearTablaUsuario)
    }

    fun crearUsuarioFormulario(
        nombre: String,
        descripcion: String
    ): Boolean{
        val conexionEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("nombre", nombre)
        valoresAGuardar.put("descripcion", descripcion)

        val resultadoEscritura: Long = conexionEscritura.insert(
            "USUARIO",
            null,
            valoresAGuardar
        )
        conexionEscritura.close()
        return if (resultadoEscritura.toInt() ==-1) false else true
    }

    fun consultarUsuarioPorId(id: Int): EUsuarioBDD
    {
        val scriptCOnsultarUsuario = "SELECT * FROM USUARIO WHERE ID = ${id}"
        val baseDatosLectura = readableDatabase
        var resultaConsultaLectura = baseDatosLectura.rawQuery(
            scriptCOnsultarUsuario,
            null
        )
        val existeUsuario = resultaConsultaLectura.moveToFirst()
        val usuarioEncontrado = EUsuarioBDD(0,"","")

        if(existeUsuario) {
            do {
                val id = resultaConsultaLectura.getInt(0) //columna indice 0--> ID
                val nombre = resultaConsultaLectura.getString(1) //columna indice 0--> ID
                val descripcion = resultaConsultaLectura.getString(2) //columna indice 0--> ID

                if(id != null)
                {
                    usuarioEncontrado.id = id
                    usuarioEncontrado.nombre = nombre
                    usuarioEncontrado.descripcion = descripcion
                }

            } while (resultaConsultaLectura.moveToNext())
        }
        resultaConsultaLectura.close()
        baseDatosLectura.close()
        return usuarioEncontrado
    }

    fun eliminarUsuarioFormulario(id: Int ): Boolean{
        val conexionEscritura = writableDatabase
        val resultadoEliminacion = conexionEscritura.delete(
            "USUARIO",
            "id=?",
            arrayOf(id.toString())
        )
        conexionEscritura.close()
        return if (resultadoEliminacion.toInt() == -1) false else true
    }

    fun actualizarUsuarioFormulario(nombre: String, descripcion: String, idActualizar: String):Boolean{
        val conexionEscritura = writableDatabase
        val valoresActualizar = ContentValues()
        valoresActualizar.put("nombre", nombre)
        valoresActualizar.put("descripcion", descripcion)
        val resultadoActualizacion = conexionEscritura.update(
            "USUARIO",
            valoresActualizar,
            "id=?",
            arrayOf(idActualizar.toString())
        )
        conexionEscritura.close()
        return if (resultadoActualizacion == -1) false else true
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) { }
}