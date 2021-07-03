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
                if(colegio != null) listaColegioActual.add(colegio)
                else println("Error en el ingreso de datos")
            }
            2 ->{ //Registrar estudiante
                val numColegio: Int=funcion.imprimirNombreColegioReturnIdx(listaColegioActual)
                if(numColegio != -1) {
                    val estudiante: Estudiante? = funcion.registroEstudiante(listaColegioActual.get(numColegio).nombre)
                    if(estudiante != null) listaColegioActual.get(numColegio).estudiantes.add(estudiante)
                    else println("No se registró estudiante")
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
                if(funcion.actualizarAulaColegio(listaColegioActual)) println("Actualización correcta!")
                else println("Valor ingresado incorrecto")
            }
            6 ->{ //actualizar curso de un estudiante
                if(funcion.actualizarCursoEstudiante(listaColegioActual)) println("Actualización correcta!")
                else println("Valor ingresado incorrecto")
            }
            7 ->{//borrar colegio
                val idxColegio = funcion.imprimirNombreColegioReturnIdx(listaColegioActual)
                if(idxColegio != -1)  listaColegioActual.removeAt(idxColegio)
                else println("Colegio no borrado")
            }
            8 ->{//borrar estudiante
                val idxColegio = funcion.imprimirNombreColegioReturnIdx(listaColegioActual)
                if(idxColegio != -1)
                {
                    val idxEstudiante = funcion.imprimirEstudiante1ColegioYReturnIdx((listaColegioActual), idxColegio)
                    if(idxEstudiante!=-1)
                    {
                        listaColegioActual[idxColegio].estudiantes.removeAt(idxEstudiante)
                    }
                }
                else println("Estudiante no borrado")
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


