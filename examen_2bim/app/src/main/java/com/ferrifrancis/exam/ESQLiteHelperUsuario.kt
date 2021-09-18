package com.ferrifrancis.exam

import Colegio
import Estudiante
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
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

        val scriptCrearTablaEstudiante = """
            CREATE TABLE estudiante(
            cedulaEstudiante VARCHAR(10) PRIMARY KEY,
            nombreEstudiante VARCHAR(40),
            fechaNacimiento DATE,
            curso VARCHAR(40),
            sexo VARCHAR(1),
            idColegio INTEGER,
            CONSTRAINT FK_estudianteColegio FOREIGN KEY (idColegio)
            REFERENCES colegio(idColegio)
            )
        """.trimIndent()

        Log.i("bdd", "Se creó tabla colegio")
        db?.execSQL(scriptCrearTablaColegio)

        Log.i("bdd", "Se creó tabla estudiante")
        db?.execSQL(scriptCrearTablaEstudiante)
    }

    fun existeCedulaEstudiante(cedula: String?): Boolean {
        val conexion = readableDatabase
        if (cedula != null) {
            val consulta = "SELECT * FROM estudiante WHERE cedulaEstudiante =${cedula}"
            val resultadoConsulta = conexion.rawQuery(consulta, null)
            Log.i("bdd", "cuantas filas hay existeCedulaEstudiante?${resultadoConsulta.count}")
            conexion.close()
            resultadoConsulta.close()
            return if (resultadoConsulta.count != 0) true else false
        } else return false
    }

    fun consultaEstudiantesXIDCole(id: Int): ArrayList<Estudiante> {
        val arrayEstudiantes = ArrayList<Estudiante>()
        /*
        val conexionEstudiante = readableDatabase
        val consulta = "SELECT * FROM estudiante WHERE idColegio = ${id}"
        val resultadoConsulta = conexionEstudiante.rawQuery(consulta, null)
        val arrayEstudiantes = ArrayList<Estudiante>()
        Log.i("bdd", "cuantas filas hay consultaEstudiantesXID?${resultadoConsulta.count}")

        if (resultadoConsulta.moveToFirst()) {

            do {
                val cedula: String = resultadoConsulta.getString(0)
                val nombre = resultadoConsulta.getString(1)
                val fechaNacimiento = resultadoConsulta.getString(2)
                val curso = resultadoConsulta.getString(3)
                val sexo = resultadoConsulta.getString(4)
                val idColegio = resultadoConsulta.getInt(5)

                val estudiante = Estudiante(nombre, fechaNacimiento, curso, cedula, sexo, idColegio)
                arrayEstudiantes.add(estudiante)
                Log.i("db", "cedula ${cedula}")
            } while (resultadoConsulta.moveToNext())
            //Log.i("db","cedula ${resultado}")
        }
        conexionEstudiante.close()
        resultadoConsulta.close()

         */
        return arrayEstudiantes
    }

    fun crearEstudianteFormulario(estudiante: Estudiante): Boolean {
        //existe el id?
        if (!existeCedulaEstudiante(estudiante?.cedula)) {
            val conexionEscritura = writableDatabase
            val valoresAGuardar = ContentValues()

            valoresAGuardar.put("nombreEstudiante", estudiante.nombre)
            valoresAGuardar.put("cedulaEstudiante", estudiante.cedula)
            valoresAGuardar.put("fechaNacimiento", estudiante.fechaNacimiento)
            valoresAGuardar.put("curso", estudiante.curso)
            valoresAGuardar.put("idColegio", estudiante.idColegio)
            valoresAGuardar.put("sexo", estudiante.sexo)
            Log.i("bdd", "--> recibo ${estudiante.idColegio}")
            val resultadoEscritura = conexionEscritura.insert("estudiante", null, valoresAGuardar)
            conexionEscritura.close()
            Log.i("bdd", "Creó estudiante? ${resultadoEscritura}")
            conexionEscritura.close()
            return if (resultadoEscritura.toInt() == -1) false else true
        } else return false
    }

    fun creaColegioFormulario(colegio: Colegio): Boolean {
        val conexionEscritura = writableDatabase
        val contenidosAgregar = ContentValues()
        contenidosAgregar.put("nombreColegio", colegio.nombreColegio)
        //contenidosAgregar.put("metros2", colegio.metros2)
        contenidosAgregar.put("esFiscal", colegio.esFiscal)
        contenidosAgregar.put("distrito", colegio.distrito)
        contenidosAgregar.put("numAulas", colegio.numAulas)

        val resultadoEscritura = conexionEscritura.insert("colegio", null, contenidosAgregar)
        conexionEscritura.close()
        Log.i("bdd", "Creó colegio? ${resultadoEscritura}")

        return if (resultadoEscritura.toInt() == -1) false else true
    }

    fun consultaColegios(): ArrayList<Colegio> {
        val conexionConsulta = readableDatabase
        val scriptConsulta = "SELECT * FROM colegio"
        val listaColegios = ArrayList<Colegio>()

        val resultadoConsulta: Cursor = conexionConsulta.rawQuery(scriptConsulta, null)
        /*
        if (resultadoConsulta.moveToFirst()) {

            do {
                val idColegio = resultadoConsulta.getInt(0)
                val nombreColegio = resultadoConsulta.getString(1)
                val esFiscal = resultadoConsulta.getInt(2) > 0
                val distrito = resultadoConsulta.getInt(3)
                val numAulas = resultadoConsulta.getInt(4)

                val colegio = Colegio(nombreColegio, esFiscal, distrito, numAulas, idColegio)


                if (idColegio != null) {
                    listaColegios.add(colegio)
                }
            } while (resultadoConsulta.moveToNext())
        }
        conexionConsulta.close()
        resultadoConsulta.close()

         */
        return listaColegios
    }

    fun consultaColegioPorID(id: Int): ArrayList<Colegio> {
        val scriptConsulta = "SELECT * FROM colegio WHERE idColegio == ${id}"
        val baseLectura = readableDatabase
        val arregloColegio = ArrayList<Colegio>()

        val resultadoLectura = baseLectura.rawQuery(scriptConsulta, null)
        val colegio = Colegio(null, null, null, null, null)
        /*
        if (resultadoLectura.moveToFirst()) {
            do {
                val idColegio = resultadoLectura.getInt(0)

                if (idColegio != null) {
                    val nombre = resultadoLectura.getString(1)
                    val esFiscal: Boolean = resultadoLectura.getInt(2) > 0
                    val distrito = resultadoLectura.getInt(3)
                    val aulasNum = resultadoLectura.getInt(4)

                    colegio.idColegio = idColegio
                    colegio.nombreColegio = nombre
                    colegio.esFiscal = esFiscal
                    colegio.distrito = distrito
                    colegio.numAulas = aulasNum
                    arregloColegio.add(colegio)
                }
            } while (resultadoLectura.moveToNext())
        }

         */
        baseLectura.close()
        resultadoLectura.close()
        return arregloColegio
    }

    fun consultaEstudiantePorIDReturnArray(id: Int): ArrayList<Estudiante> {
        val conexionLectura = readableDatabase
        val listaEstudiante = ArrayList<Estudiante>()
        val scriptConsulta = "SELECT * FROM estudiante WHERE cedulaEstudiante =${id}"

        val resultadoConsulta = conexionLectura.rawQuery(scriptConsulta, null)

        /*
        if (resultadoConsulta.moveToFirst()) {
            do {
                val cedula = resultadoConsulta.getString(0)
                val nombre = resultadoConsulta.getString(1)
                val fechaNac = resultadoConsulta.getString(2)
                val curso = resultadoConsulta.getString(3)
                val sexo = resultadoConsulta.getString(4)
                val idColegio = resultadoConsulta.getInt(5)

                if (cedula != null) {
                    val estudiante = Estudiante(nombre, fechaNac, curso, cedula, sexo, idColegio)
                    listaEstudiante.add(estudiante)
                }
            } while (resultadoConsulta.moveToNext())
        }


         */
        conexionLectura.close()
        resultadoConsulta.close()
        Log.i("bdd", "Consulta estudiante")
        return listaEstudiante
    }

    fun eliminarColegioPorID(id: Int): Boolean {
        val conexionEliminar = writableDatabase
        val resultadoEliminar: Int = conexionEliminar.delete(
            "colegio",
            "idColegio =?",
            arrayOf(id.toString())
        )
        conexionEliminar.close()
        return if (resultadoEliminar != -1) true else false
    }

    fun eliminarEstudiantePorCedula(id: String?=null): Boolean {
        if(id != null){
            val conexionEliminar = writableDatabase
            val resultadoEliminar = conexionEliminar.delete(
                "estudiante",
                "cedulaEstudiante =?",
                arrayOf(id)
            )
            conexionEliminar.close()
            return if (resultadoEliminar == -1) false else true
        }
        return false
    }

    fun eliminarEstPorIDCole(idColegio: Int?): Boolean
    {
        if (idColegio != null) {
            val conexion = writableDatabase
            val resultadoEliminar: Int = conexion.delete(
                "estudiante",
                "idColegio=?",
                arrayOf(idColegio.toString())
            )
            conexion.close()
            Log.i("bdd", "Número estudiantes eliminado: ${resultadoEliminar}")
            return (resultadoEliminar != 0)
        }
        else return false
    }

    fun actualizarAulasColegioPorID(id: Int?, numAulas: Int): Boolean{
        val conexionActualizar = writableDatabase
        val valoresActualizar = ContentValues()
        Log.i("bd","ACTUALIZAR COLEGIO ${id}")
        Log.i("bd","ACTUALIZAR COLEGIO ${numAulas}")

        if(id != null) {
            valoresActualizar.put("numAulas", numAulas)
            val resultadoActualizar: Int = conexionActualizar.update(
                "colegio",
                valoresActualizar,
                "idColegio=?",
                arrayOf(id.toString())
            )
            conexionActualizar.close()
            return if (resultadoActualizar == -1) false else true
        }
        return false
    }

    fun actualizarCursoEstudiantePorID(cedula: String, curso: String): Boolean{
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

    fun consultaUltimoIDColegio():Int{
        val conexion = readableDatabase
        val scriptConsulta = "SELECT idColegio FROM colegio"
        
        val resultadoConsulta: Cursor =conexion.rawQuery(scriptConsulta,null)
        resultadoConsulta.moveToLast()
        val devolver=resultadoConsulta.getInt(0)
        conexion.close()
        return devolver
    }
    
    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {


    }

}











