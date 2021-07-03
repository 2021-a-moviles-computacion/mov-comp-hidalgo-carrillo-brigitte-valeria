import java.io.File
import java.io.PrintWriter
import java.time.LocalDate


class Funcion {

    fun registroEstudiante(nombreColegio: String): Estudiante? {

        println("=======================================================")
        println("Por favor, ingrese los siguientes datos del estudiante")
        println("Nombre: ")
        val nombre = esString(readLine().toString())
        println("Nombre Colegio: ${nombreColegio}")
        println("Fecha nacimiento aaaa-mm-dd: ")
        val fecha = esFormatoFecha(readLine().toString())
        println("Cedula: ")
        val cedula = esLongCedula(readLine().toString())
        println("Nombre curso: ")
        val curso = esString(readLine().toString())

        if(nombre!= null && fecha != null && cedula !=null && curso!=null)
            return Estudiante(nombre, LocalDate.parse(fecha), curso, cedula)
        else
            return null
    }

    fun registroColegio(): Colegio? {
        println("=======================================================")
        println("Por favor, ingrese los siguientes datos del colegio")
        println("Nombre: ")
        val nombre = esString(readLine().toString())
        println("Dirección: ")
        val direccion = esString(readLine().toString())
        println("Metros cuadrados ##.##: ")
        val metros2 = esFloat(readLine().toString())
        println("Número aulas: ")
        val numAulas = esNumero(readLine().toString())
        println("¿El colegio es fiscal? si -> true ; no -> false")
        val esFiscal = esBoolean(readLine().toString())

        if(nombre!= null && direccion!= null && metros2!= null && numAulas != null && esFiscal!= null ) {
            println("Debe ingresar al menos un estudiante")
            val estudiante = registroEstudiante(nombre)
            if(estudiante != null)
                return Colegio(direccion, metros2, numAulas, nombre, esFiscal, estudiante)
            else return null
        }
        else return null
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

        return esNumeroYRango(readLine().toString(), 9)
    }

    fun imprimeColegio(colegios: ArrayList<Colegio>): String {
        var salida = ""
        colegios.forEach {
            val colegio = it
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
        val colegioIndInt = esNumeroYRango(readLine().toString(), listaColegio.size-1)
        return if(colegioIndInt!= -1) colegioIndInt else -1
    }

    fun imprimirEstudiante1ColegioYReturnIdx(listaColegio: ArrayList<Colegio>, indiceColegio: Int): Int {

        listaColegio.get(indiceColegio).estudiantes.forEachIndexed { index, estudiante ->
            val est = estudiante
            println("Indice: " + index + " Estudiante: " + est.nombre)

        }

        val indEst = esNumeroYRango(readLine().toString(),listaColegio.get(indiceColegio).estudiantes.size-1)
        return if(indEst != -1) indEst else -1
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
        var datos = ""
        File(nombreArchivo).forEachLine {

            datos += it.split(":")[1] + "\n"
        }

        return datos
    }

    fun actualizarAulaColegio(colegio: ArrayList<Colegio>): Boolean {

        val indxColegio = imprimirNombreColegioReturnIdx(colegio)
        if(indxColegio !=-1) {
            println("Número de aulas registrado: ${colegio.get(indxColegio).aulas}")
            println("Ingrese el número de aulas nuevo: ")

            val entrada = readLine().toString()
            val numAulasNuevo =esNumero(entrada)
             if (numAulasNuevo != null)
             {
                 colegio.get(indxColegio).aulas = numAulasNuevo
                 return true
             } else return false
        }
        else return false
    }

    fun actualizarCursoEstudiante(arregloColegio: ArrayList<Colegio>): Boolean {
        val idxColegio: Int = imprimirNombreColegioReturnIdx(arregloColegio)
        val idxEstudiante: Int = if(idxColegio!=-1) {
            println("Seleccione el índice del estudiante")
            imprimirEstudiante1ColegioYReturnIdx(arregloColegio, idxColegio)
        } else -1

        if(idxEstudiante != -1) {
            val colegio: Colegio = arregloColegio.get(idxColegio)

            println("Curso actual estudiante: " + colegio.estudiantes[idxEstudiante].curso)
            print("Curso nuevo estudiante: ")

            val nuevoCurso = esString(readLine().toString())

            if (nuevoCurso != null) {
                colegio.estudiantes[idxEstudiante].curso = nuevoCurso
                return true
            } else return false
        } else return false
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
        val estudiantesXColegio: List<String> =
            cadenaEstudiantes.split("%%") //en cada indice[] están los estudiantes de 1 colegio

        val colegioArrayList = ArrayList<Colegio>()


        colegioFromTxt.forEachIndexed { index, s ->
            if (s.isNotBlank()) {
                val quitarWhiteSpaceCol: String
                quitarWhiteSpaceCol = s.dropWhile { it.isWhitespace() } //eliminar el salto de linea

                val datosColegio = quitarWhiteSpaceCol.split("\n")
                nombreColegio = datosColegio[0]
                direccion = datosColegio[1]
                aulas = datosColegio[2].drop(1).toInt()
                metros2 = datosColegio[3].toFloat()
                esFiscal = datosColegio[4].toBoolean()

                val estudiante: List<String> = estudiantesXColegio[index].split("##")

                val estudianteArrayList = ArrayList<Estudiante>()

                estudiante.forEach {
                    val estDaWS: String
                    estDaWS = it.dropWhile { it.isWhitespace() }

                    if (estDaWS.isNotBlank()) {
                        val datosEstudiante: List<String> = estDaWS.split("\n")

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

    fun esNumero(numero: String): Int?
    {
        //entrada teclado, eres numero?
        val regex = """^\d{1,}$""".toRegex()
        return if(regex.matches(numero)) numero.toInt() else null
    }

    fun esString(cadena: String): String?
    {
        val regex = """[" "a-zA-ZÑñ\d]*""".toRegex()
        return if(regex.matches(cadena)) cadena else null
    }

    fun esFloat(cadena: String):Float?{
        val regex ="""^([0-9]+\.?[0-9]*|\.[0-9]+)$""".toRegex()
        return if (regex.matches(cadena)) cadena.toFloat() else null
    }

    fun esBoolean(cadena: String): Boolean?
    {
        val regex ="""^((true)|(false))$""".toRegex()
        return if(regex.matches(cadena)) cadena.toBoolean() else null
    }
    fun esNumeroYRango(numeroS: String, rangoMax: Int):Int
    {
        if(esNumero(numeroS) != null)
        {
            val colegioIndInt = numeroS.toInt()
            return if(colegioIndInt >= 0 && colegioIndInt <= rangoMax) colegioIndInt else -1
        }
        else
            return -1
    }

    fun esLongCedula(cadena: String): String?
    {
        val regex = """^(\d{10})$""".toRegex()
        return if(regex.matches(cadena)) cadena else null
    }

    fun esFormatoFecha(cadena: String): String?{
        val regex = """^(\d{4}-\d{2}-\d{2})$""".toRegex()
        return if(regex.matches(cadena)) cadena else null
    }
}