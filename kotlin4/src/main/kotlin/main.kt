import javax.swing.plaf.basic.BasicSplitPaneUI

fun main() {

    val palabra: String = "valeria";

    println("Hola mundo")
    imprimirNombre("ValeriaHidalgo")

    //calcularSueldo() //esta mal
    calcularSueldo(100.00) // esta bien, usa 12
    calcularSueldo(100.00, 14.00) // esta bien se sobreescribe y ya no es 12

    //-----------------------------------------------
    //Duck Tyiping
    var edadProfesor: Int = 32
    var sueldoProfesor = 1.32
    // no se puede hacer esto
    //edadProfesor = sueldoProfesor +5
    //-----------------------------------------------
    //Variables Mutables / Inmutables
    //1. mutables (RE asignar "=")
    var edadCachorro: Int = 0
    edadCachorro = 1
    edadCachorro = 2
    edadCachorro = 3

    //2. inmutables (NO RE asignar "=")
    val numeroCedula = 1234567890
    //-----------------------------------------------
    //Tipos de variables (JAVA)
    //1. Primitivas:
    val nombreProfesor: String = "Valeria Hidalgo"
    val sueldo: Double = 1.2
    val estadoCivil: Char = 'S'

    //2. no primitivas
    //-----------------------------------------------
    //CONDICIONALES
    //en lugar del switch when
    val estadoCivilWhen: String = "S"

    //buena práctica  poner funciones después de cada sentencia
    when (estadoCivilWhen) {
        ("S") -> {
            println("Acercarse")

        }
        "C" -> {
            println("Alejarse")
        }
        "UN" -> println("Hablar")

        else -> println("No reconocido")
    }

    val coqueteo: String = if (estadoCivilWhen == "S") "SI" else "NO"
    //-----------------------------------------------
    //NAMED PARAMETERS/ Parámetros nombrados
    //cuando puedo enviar los parametros sin que importe el orden
    //Sirven cuando se tiene muchos parámetros y quiero setear parametros en especifico
    calcularSueldo(tasa = 12.40, sueldo = 14.00)
    //------------------------------------------
    //TIPOS DE ARREGLOS
    //1. Arreglos estáticos
    //No puedo modificar los elementos del arreglo
    //arregloEstatico.add(12) --> no se puede
    val arregloEstatico: Array<Int> = arrayOf(1, 2, 3)

    //2. Arreglos dinámicos
    val arregloDinamico: ArrayList<Int> = arrayListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    println(arregloDinamico)
    arregloDinamico.add(11)
    arregloDinamico.add(12)
    //------------------------------------------
    //OPERADORES --> Sirve para los arreglos estaticos y dinamicos
    //NO utilizar ni for ni while. Sino USAR EL OPERADOR
    //Todo operador devuelve

    //------------------------------------------

    //FOR EACH --> UNIT
    //itera un arreglo
    val respuestaForEach: Unit = arregloDinamico.forEach { valorAcutal: Int ->
        println("Valor actual: ${valorAcutal}")
    }
    println(respuestaForEach)

    //It -> valor o los valores que van a llegar a esta funcion
    // Si solamente se recibe 1 parametro, este se va a llamar "it"
    arregloDinamico.forEach {
        println("Valor actual: ${it}")
    }
    println(respuestaForEach)
    arregloDinamico.forEachIndexed { index, i ->
        println("valor ${i} indice: ${index}")
    }
    //------------------------------------------

    //MAP --> muta el arreglo, cambia el arreglo
    //1 enviar el nuevo valor de la iteracion
    //" nos devuelve el nuevo arreglo con los valores modificados
    //devuelve un arreglo mutado
    val respuestaMap: List<Double> = arregloDinamico.map { valorActual: Int ->
        return@map valorActual.toDouble() + 100.00

    }

    arregloDinamico.map { it + 15 }
    //.map{valorActual: Int ->
// return@map valorActual.toDouble() +100.00}


    //---------------------------------------
    //Filter --> FILTRAR EL ARREGLO
    //1) Devolver una expresion (TRUE o False)
    //2) nuevo arreglo filtrado
    //devulve os items del arreglo que cumplen con la condicion
    val respuestaFilter: List<Int> = arregloDinamico.filter { valorActual: Int ->
        val mayoresACinco: Boolean = valorActual > 5
        return@filter mayoresACinco
    }
    //para hacerlo mas facil
    val respuestaFilterDos = arregloDinamico.filter { it <= 5 }
    println(respuestaFilter)
    println(respuestaFilterDos)

//------------------------------------------------------------------------------
    //12/06/2021
    //OR AND: verificar cierta condicion dentro de todos los elementos de la regla
    // or --> any (alguno cumple?)
    // and --> all (todos cumplen?)
    val respuestaAny: Boolean = arregloDinamico.any { valorActual: Int ->
        return@any (valorActual > 5)
    }
    val respuestaAll: Boolean = arregloDinamico.all { valorActual: Int ->
        return@all (valorActual > 5)
    }
    println("all ${respuestaAll} -- any ${respuestaAny}")

    //---------------------------------------
    //REDUCE --> valor acumulado
    //1) devuelve el acumulado => 0
    //2) en que valor empieza => 0
    // No importa si los valores son objetos
    val respuestaReduce: Int = arregloDinamico.reduce { acc, i ->
        // el reduce siempre empieza en 0
        //siempre devuelve el valor acumulado
        return@reduce acc + i
    }
    println("Respuesta reduce${respuestaReduce}")
    //ejemplo 2 Fold, para que acumulado no empieze en 0
    // vida = 100
    //ataques = [12,5,8,10]
    val arregloDanio = arrayListOf<Int>(12, 15, 8, 10)
    val arregloDinam = arrayListOf<Int>(12, 15, 8, 10)
    val respuestaReduceFold = arregloDanio.fold(100, { acumulado, valorActualIteracion ->
        return@fold acumulado - valorActualIteracion
    })
    println("respuestaReduceFold ${respuestaReduceFold}")

    //Se puede poner todo ahi mismo

    //val vidaActuaal: Double = arregloDinam
    //  .map { it * 2.3 } //devuelve arreglo
    //  .filter { it > 20 } //devuelve arreglo
    //.also {println( it) } //ejectuar codigo extra
    val ejemploUno = Suma(1, 2)
    val ejemploDos = Suma(null, 2)
    val ejemplotres = Suma(1, null)
    println(ejemploUno.sumar())
    println(ejemploDos.sumar())
    println(ejemplotres.sumar())
}//FIN MAIN

//CLASES
abstract class NumerosJava {
    protected val numeroUno: Int
    private val numeroDos: Int

    constructor(
        uno: Int, //parametros requeridos
        dos: Int //parametros requeridos
    ) {
        numeroUno = uno
        numeroDos = dos
        println("Inicializar")
    }


}

//los getter y setter ya estan por defecto
//esta es una clase con constructor primario
abstract class Numeros(
    protected var numeroUno: Int, //constructor primario. Es una propiedad
    protected var numeroDos: Int
) //constructor primario. Es una propiedad
{
    init { //bloque de inicio del constructor primario
        println("inicializar")
    }
}

class Suma(
    //constructor primario
    uno: Int, //parametro requerido
    dos: Int, //parametro requerido
) : Numeros( //Estoy heredando de otra clase. Construtor primario para super
    uno,
    dos
) {
    init {
        this.numeroUno // en esta clase este no existen
        this.numeroDos // en esta clase este no existen
    }

    constructor(//segundo constructor
        uno: Int?,
        dos: Int
    ) : this( //llamada constructor primario
        if (uno == null) 0 else uno, dos
    )

    constructor ( // tercer constructor
        uno: Int,
        dos: Int?
    ) : this( //llamada constructor primario
        uno, if (dos == null) 0 else dos
    )

    public fun sumar(): Int {
        val total: Int = numeroUno + numeroDos
        return total
    }

    //SINGLETON
    //No se instancia varias veces
    //Presente en todas la clases
    //No hay propiedades estáticas, sino singleton
    companion object {
        val historialSumas = arrayListOf<Int>()
        fun agregarHistorial(valorNuevaSuma: Int) {
            historialSumas.add(valorNuevaSuma)
            println(historialSumas)
        }

    }
}


fun imprimirNombre(nombre: String): Unit {
    println("Nombre: ${nombre}")
}


fun calcularSueldo(
    sueldo: Double, //Requerido. No puede ser nulo
    tasa: Double = 12.00, //Opcional (valor defecto). No puede ser nulo
    bonoEspecial: Double? = null //Opcional (Puede ser nulo)
    //Double? --> significa que la variable puede ser nula
): Double {
    if (bonoEspecial == null) {
        return sueldo * (100 / tasa)
    } else {
        return sueldo * (100 / tasa) + bonoEspecial
        //return sueldo * (100 / tasa) + bono especial //No se puede
    }
//cualquier dato puede ser null
    //int --> int?
    //string --> string?
    //double --> double?
}

