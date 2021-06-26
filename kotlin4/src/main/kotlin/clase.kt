fun main() {
    NumerosJava1
}
//CLASES
abstract class NumerosJava1 {
    protected val numeroUno: Int
    private val numeroDos: Int
    constructor(uno: Int, dos: Int) {
        numeroUno = uno
        numeroDos = dos
        println("Inicializar")
    }
}

abstract class Numeros2( protected var numeroUno: Int, protected var numeroDos: Int) //constructor primario. Es una propiedad
{
    init { //bloque de inicio del constructor primario
        println("inicializar")
    }
}

class Suma( uno: Int, dos: Int) : Numeros2( uno, dos)
{
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
    companion object hola {
        val historialSumas = arrayListOf<Int>()
        fun agregarHistorial(valorNuevaSuma: Int) {
            historialSumas.add(valorNuevaSuma)
            println(historialSumas)
        }

    }