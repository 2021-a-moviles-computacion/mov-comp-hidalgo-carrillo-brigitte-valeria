package com.ferrifrancis.exam

import Colegio
import Estudiante
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import androidx.core.database.getStringOrNull

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

    fun consultaColegioPorID(id: Int): ArrayList<Colegio>
    {
        val scriptConsulta = "SELECT * FROM colegio WHERE idColegio === ${id}"
        val baseLectura = readableDatabase
        val arregloColegio = ArrayList<Colegio>()

        val resultadoLectura = baseLectura.rawQuery(scriptConsulta, null)
        val colegio = Colegio(null, null, null, null, null,null)

        if(resultadoLectura.moveToFirst())
        {
            do{
                val idColegio = resultadoLectura.getInt(0)
                val nombre = resultadoLectura.getString(1)
                val esFiscal: Boolean = resultadoLectura.getInt(2) >0
                val distrito = resultadoLectura.getInt(3)
                val aulasNum = resultadoLectura.getInt(4)

                if(idColegio != null)
                {
                    colegio.idColegio = idColegio
                    colegio.nombre = nombre
                    colegio.esFiscal = esFiscal
                    colegio.distrito = distrito
                    colegio.numAulas = aulasNum
                    arregloColegio.add(colegio)
                }
            }while(resultadoLectura.moveToNext())
        }
        baseLectura.close()
        resultadoLectura.close()
        return arregloColegio
    }

    fun consultaEstudiantePorID(id: Int): ArrayList<Estudiante>
    {
        val conexionLectura = readableDatabase
        val listaEstudiante =  ArrayList<Estudiante>()
        val scriptConsulta = "SELECT * FROM estudiante WHERE cedula =${id}"

        val resultadoConsulta =conexionLectura.rawQuery(scriptConsulta, null)


        if(resultadoConsulta.moveToFirst())
        {
            do{
                val cedula = resultadoConsulta.getString(0)
                val nombre = resultadoConsulta.getString(1)
                val fechaNac = resultadoConsulta.getString(2)
                val curso = resultadoConsulta.getString(3)
                val sexo = resultadoConsulta.getString(4)
                val idColegio = resultadoConsulta.getInt(5)

                if(cedula != null) {
                    val estudiante = Estudiante(nombre, fechaNac, curso, cedula, sexo, idColegio)
                    listaEstudiante.add(estudiante)
                }
            }while (resultadoConsulta.moveToNext())
        }

        conexionLectura.close()
        resultadoConsulta.close()
        Log.i("bdd", "Consulta estudiante")
        return listaEstudiante
    }

    fun eliminarColegioPorID(id: Int): Boolean
    {
        val conexionEliminar = writableDatabase
        val resultadoEliminar: Int =conexionEliminar.delete("colegio",
            "idColegio =?",
            arrayOf(id.toString())
            )
        conexionEliminar.close()
        return if(resultadoEliminar != -1) true else false
    }

    fun eliminarEstudiantePorID(id: Int): Boolean{
        val conexionEliminar = writableDatabase
        val resultadoEliminar = conexionEliminar.delete(
            "estudiante",
            "cedula =?",
            arrayOf( id.toString())
        )
        conexionEliminar.close()
        return if(resultadoEliminar ==-1) false else true
    }

    fun actualizarColegioPorID(id: Int, numAulas: Int): Boolean{
        val conexionActualizar = writableDatabase
        val valoresActualizar = ContentValues()
        valoresActualizar.put("numAulas", numAulas)
        val resultadoActualizar: Int = conexionActualizar.update(
            "colegio",
            valoresActualizar,
            "idColegio",
            arrayOf(id.toString())
        )
        conexionActualizar.close()
        return if(resultadoActualizar == -1) false else true
    }

    fun actualizarEstudianteID(cedula: String, curso: String): Boolean{
        val conexionActualizar = writableDatabase
        val valoresActualizar = ContentValues()

        valoresActualizar.put("curso", curso)

        val resultadoActualizar = conexionActualizar.update(
            "estudiante",
            valoresActualizar,
            "cedulaEstudiante=?",
            arrayOf(cedula)
        )

        conexionActualizar.close()
        return if(resultadoActualizar ==-1) false else true
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }

}











