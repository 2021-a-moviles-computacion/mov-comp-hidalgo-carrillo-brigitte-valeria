package com.ferrifrancis.exam

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

open class ESQLiteHelperUsuario(context: Context?):SQLiteOpenHelper(
    context,
    "moviles",
    null,
    1
) {
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptCrearTablaColegio = """
            CREATE TABLE colegio(
                idColegio INTEGER PRIMARY KEY AUTOINCREMENT,
                nombreColegio VARCHAR(30),
                esFiscal BOOLEAN,
                distrito INTEGER,
                numAulas INTEGER,
            )
        """.trimIndent()

        val scriptCrearTablaEstudiante ="""
            CREATE TABLE estudiante(
            cedulaEstudiante VARCHAR(10) PRIMARY KEY,
            nombreEstudiante VARCHAR(40),
            fechaNacimiento DATE,
            curso VARCHAR(40),
            sexo VARCHAR(1),
            idColegio INTEGER
            CONSTRAINT FK_estudianteColegio FOREIGN KEY (idColegio)
            REFERENCES colegio(idColegio)
            )
        """.trimIndent()

        Log.i("bdd","Se creó tabla colegio")
        db?.execSQL(scriptCrearTablaColegio)

        Log.i("bdd","Se creó tabla estudiante")
        db?.execSQL(scriptCrearTablaEstudiante)
    }

    fun crearEstudianteFormulario (nombre: String, fechaNacimiento: String,
                curso: String, cedula: String, sexo: String, idColegio: Int): Boolean{
        //existe el id?
        val conexionEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("idColegio",idColegio)
        valoresAGuardar.put("nombreEstudiante",nombre)
        valoresAGuardar.put("cedulaEstudiante",cedula)
        valoresAGuardar.put("fechaNacimiento",fechaNacimiento)
        valoresAGuardar.put("curso",curso)
        valoresAGuardar.put("idColegio",idColegio)
        valoresAGuardar.put("sexo", sexo)

        val resultadoEscritura =  conexionEscritura.insert("estudiante",null, valoresAGuardar)
        conexionEscritura.close()
        Log.i("bdd", "Creó estudiante? ${resultadoEscritura}")
        return if (resultadoEscritura.toInt() == -1) false else true
    }

    fun creaColegioFormulario(nombre: String, metros2: Float, esFiscal: Boolean,
            distrito: Int, numAulas: Int): Boolean
    {
        val conexionEscritura= writableDatabase
        val contenidosAgregar = ContentValues()
        contenidosAgregar.put("nombre", nombre)
        contenidosAgregar.put("metros2", metros2)
        contenidosAgregar.put("esFiscal", esFiscal)
        contenidosAgregar.put("distrito", distrito)
        contenidosAgregar.put("numAulas", numAulas)

        val resultadoEscritura=conexionEscritura.insert("colegio", null, contenidosAgregar)
        conexionEscritura.close()
        Log.i("bdd", "Creó colegio? ${resultadoEscritura}")

        return if(resultadoEscritura.toInt()==-1) false else true
    }
    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }

}