import java.io.File
import java.io.PrintWriter
import java.time.LocalDate


class Funcion {

    fun registroEstudiante(nombreColegio: String): Estudiante {

        println("=======================================================")
        println("Por favor, ingrese los siguientes datos del estudiante")
        println("Nombre: ")
        val nombre = readLine().toString()
        println("Nombre Colegio: ${nombreColegio}")
        println("Fecha nacimiento aaaa-mm-dd: ")
        val fecha = LocalDate.parse(readLine().toString())
        println("Cedula: ")
        val cedula = readLine().toString()
        println("Nombre curso: ")
        val curso = readLine().toString()

        return Estudiante(nombre, fecha, curso, cedula)

    }

    fun registroColegio(): Colegio {
        println("=======================================================")
        println("Por favor, ingrese los siguientes datos del colegio")
        println("Nombre: ")
        val nombre = readLine().toString()
        println("Dirección: ")
        val direccion = readLine().toString()
        println("Metros cuadrados ##.##: ")
        val metros2 = readLine().toString().toFloat()
        println("Número aulas: ")
        val numAulas = readLine().toString().toInt()
        println("¿El colegio es fiscal? true -> si ; false -> no")
        val esFiscal = readLine().toBoolean()

        println("Debe ingresar al menos un estudiante")
        var estudiante = registroEstudiante(nombre)
        return Colegio(direccion, metros2, numAulas, nombre, esFiscal, estudiante)
    }

    fun menuBienvenida(): Int {
        println("=======================================================")
        println("\t¡Bienvenid@!")
        println("\t>>Menú de Opciones<<")
        println("Seleccione una opción")
        println("1) Registrar colegio")
        println("2) Registrar estudiante")
        println("3) Imprimir colegios")
        println("4) Imprimir estudiantes colegios")
        println("5) Actualizar # aulas colegio")
        println("6) Actualizar curso estudiante")
        println("7) Borrar colegio")
        println("8) Borrar estudiante")
        println("9) Salir")

        return readLine().toString().toInt()
    }

    fun imprimeColegio(colegios: ArrayList<Colegio>): String {
        var salida = ""
        colegios.forEach {
            var colegio = it
            salida += "NombreColegio: " + colegio.nombre + "\n"
            salida += "Dirección: " + colegio.direccion + "\n"
            salida += "NúmeroAulas: " + colegio.aulas + "\n"
            salida += "Metros2: " + colegio.metros2 + "\n"
            salida += "Fiscal: " + colegio.esFiscal + "\n"
            salida += ":%%\n"
        }
        return salida
    }

    fun imprimirEstudianteXColegio(colegio: ArrayList<Colegio>): String {
        //%% delimitador de colegio
        // ## delimitador estudiante
        var salida = ""
        colegio.forEach {
            it.estudiantes.forEach {
                val estudiante = it
                salida += "Nombre: ${estudiante.nombre}\n"
                salida += "Cedula: ${estudiante.cedula}\n"
                salida += "FechaNacimiento: ${estudiante.fechaNacimiento}\n"
                salida += "Curso: ${estudiante.curso}\n"
                salida += ":##\n"
            }
            salida += ":%%\n"
        }
        return salida
    }

    fun imprimirNombreColegioReturnIdx(listaColegio: ArrayList<Colegio>): Int {
        println("Seleccione el colegio")
        listaColegio.forEachIndexed { index, actual ->
            val colegio = actual
            println("${index}) ${colegio.nombre}")
        }
        return readLine().toString().toInt()
    }

    fun imprimirEstudiante1ColegioYReturnIdx(listaColegio: ArrayList<Colegio>, indiceColegio: Int): Int {
        listaColegio.get(indiceColegio).estudiantes.forEachIndexed { index, estudiante ->
            val est = estudiante
            println("Indice: " + index + " Estudiante: " + est.nombre)
        }
        return readLine().toString().toInt()
    }

    fun guardarEnArchivo(contenido: String, nombreArchivo: String) {
        //Si el archivo existe, lo limpia y lo borra
        //Si no, lo crea
        try {
            val writer = PrintWriter(nombreArchivo) //Se encarga de converitr cualquier tipo de datos en texto
            writer.append(contenido)
            writer.close()
            println("¡Datos almacenados correctamente!")
        } catch (e: Exception) {
            println("ERROR No se pudo guardar los datos en el archivo ")
        }
    }

    fun getDatosFromTxt(nombreArchivo: String): String {
        //lee el colegioDatos y estudianteDatos y retira los que esté
        //antes del paréntesis
        var datos: String = ""
        File(nombreArchivo).forEachLine {

            datos += it.split(":")[1] + "\n"
        }

        return datos
    }

    fun actualizarAulaColegio(colegio: ArrayList<Colegio>) {

        val indxColegio = imprimirNombreColegioReturnIdx(colegio)
        println("Número de aulas registrado: ${colegio.get(indxColegio).aulas}")
        println("Ingrese el número de aulas nuevo: ")

        val numAulasNuevo = readLine().toString().toInt()
        colegio.get(indxColegio).aulas = numAulasNuevo
    }

    fun actualizarCursoEstudiante(arregloColegio: ArrayList<Colegio>): Boolean {
        val idxColegio: Int = imprimirNombreColegioReturnIdx(arregloColegio)
        val idxEstudiante: Int = imprimirEstudiante1ColegioYReturnIdx(arregloColegio, idxColegio)
        var colegio: Colegio = arregloColegio.get(idxColegio)

        println("Curso actual estudiante: " + colegio.estudiantes[idxEstudiante].curso)
        println("Curso nuevo estudiante: ")

        val nuevoCurso = readLine().toString()

        if (nuevoCurso.isNotEmpty() && nuevoCurso.isNotBlank()) {
            colegio.estudiantes[idxEstudiante].curso = nuevoCurso
            return true
        } else
            return false
    }

    fun arregloColegioFromTxt(cadenaColegios: String, cadenaEstudiantes: String): ArrayList<Colegio> {
        //datos colegio
        var nombreColegio: String
        var direccion: String
        var aulas: Int
        var metros2: Float
        var esFiscal: Boolean
        //datos estudiante
        var nombreEstudiante: String
        var fechaNacimiento: LocalDate
        var curso: String
        var cedula: String

        val colegioFromTxt: List<String> = cadenaColegios.split("%%")
        var estudiantesXColegio: List<String> =
            cadenaEstudiantes.split("%%") //en cada indice[] están los estudiantes de 1 colegio

        var colegioArrayList = ArrayList<Colegio>()


        colegioFromTxt.forEachIndexed { index, s ->
            if (s.isNotBlank()) {
                var quitarWhiteSpaceCol: String
                quitarWhiteSpaceCol = s.dropWhile { it.isWhitespace() } //eliminar el salto de linea

                val datosColegio = quitarWhiteSpaceCol.split("\n")
                nombreColegio = datosColegio[0]
                direccion = datosColegio[1]
                aulas = datosColegio[2].drop(1).toInt()
                metros2 = datosColegio[3].toFloat()
                esFiscal = datosColegio[4].toBoolean()

                var estudiante: List<String> = estudiantesXColegio[index].split("##")

                var estudianteArrayList = ArrayList<Estudiante>()

                estudiante.forEach {
                    var estDaWS: String
                    estDaWS = it.dropWhile { it.isWhitespace() }

                    if (estDaWS.isNotBlank()) {
                        var datosEstudiante: List<String> = estDaWS.split("\n")

                        nombreEstudiante = datosEstudiante[0]
                        cedula = datosEstudiante[1]
                        fechaNacimiento = LocalDate.parse(datosEstudiante[2].drop(1))
                        curso = datosEstudiante[3]

                        val estudianteObjeto = Estudiante(nombreEstudiante, fechaNacimiento, curso, cedula)
                        estudianteArrayList.add(estudianteObjeto)
                    }
                }
                val colegioObjeto = Colegio(direccion, metros2, aulas, nombreColegio, esFiscal, estudianteArrayList)
                colegioArrayList.add(colegioObjeto)
            }

        }
        return colegioArrayList
    }

}