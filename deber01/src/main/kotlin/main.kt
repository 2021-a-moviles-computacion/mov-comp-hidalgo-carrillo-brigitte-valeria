fun main()
{
    val funcion = Funcion()
    var listaColegioActual = ArrayList<Colegio>()
    val nombreArchivoColegio="colegioDatos.txt"
    val nombreArchivoEstudiante="estudianteDatos.txt"
    var opcion: Int


    val stringColegioFromTxt = funcion.getDatosFromTxt(nombreArchivoColegio)
    val stringEstudianteFromTxt = funcion.getDatosFromTxt(nombreArchivoEstudiante)

    listaColegioActual=funcion.arregloColegioFromTxt(stringColegioFromTxt,stringEstudianteFromTxt)

    do {
        opcion= funcion.menuBienvenida()
        when (opcion)
        {
            1 ->{ //Registrar colegio
                val colegio = funcion.registroColegio()
                listaColegioActual.add(colegio)
            }
            2 ->{ //Registrar estudiante
                val numColegio: Int=funcion.imprimirNombreColegioReturnIdx(listaColegioActual)
                if(numColegio != -1) {
                    val estudiante: Estudiante = funcion.registroEstudiante(listaColegioActual.get(numColegio).nombre)
                    listaColegioActual.get(numColegio).estudiantes.add(estudiante)
                }
                else
                    println("No se registró estudiante")
            }
            3 ->{ //Imprimir colegio
                println(funcion.imprimeColegio(listaColegioActual))
            }
            4->{//Imprimir estudiantes de un colegio
                println(funcion.imprimirEstudianteXColegio(listaColegioActual))
            }
            5 -> { //actualizar aulas colegio
                funcion.actualizarAulaColegio(listaColegioActual)
            }
            6 ->{ //actualizar curso de un estudiante
                funcion.actualizarCursoEstudiante(listaColegioActual)
            }
            7 ->{//borrar colegio
                val idxColegio = funcion.imprimirNombreColegioReturnIdx(listaColegioActual)
                listaColegioActual.removeAt(idxColegio)
            }
            8 ->{//borrar estudiante
                val idxColegio = funcion.imprimirNombreColegioReturnIdx(listaColegioActual)
                val idxEstudiante = funcion.imprimirEstudiante1ColegioYReturnIdx((listaColegioActual), idxColegio)
                listaColegioActual[idxColegio].estudiantes.removeAt(idxEstudiante)
            }
            9->{//Salir
                break
            }
            else -> {
                println("Opción inválida")
            }
        }
    }while (opcion != 9)
    funcion.guardarEnArchivo(funcion.imprimeColegio(listaColegioActual), nombreArchivoColegio)
    funcion.guardarEnArchivo(funcion.imprimirEstudianteXColegio(listaColegioActual), nombreArchivoEstudiante)
}


