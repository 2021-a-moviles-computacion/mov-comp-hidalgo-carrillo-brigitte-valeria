import kotlin.random.Random

fun main()
{
    poderes()
}

fun reversaPalabra( palabra: String): String
{
    var palabra2 = ""
    var entero=6
    var numero=0

    for (i in 0..6) {
        numero = entero - i
        palabra2 +=palabra.get(numero)

    }
    return palabra2
}

fun poderes (): Unit
{
    var nombresPoderes: Array<String> = arrayOf("strenght","dexterity"
        ,"constitution","intelligence","wisdom","charisma")
    var valores: List<Int>
    var minimo: Int?
    var suma: Int = 0
    for (i in 0..5)
    {
        valores = List(4) { Random.nextInt(1,7)}
        println("dado ${valores}")
        minimo = valores.min()

        valores.forEach { valorActual: Int ->
            if (valorActual != minimo )
            {
                suma += valorActual
            }
        }
        println("${nombresPoderes[i]} --> ${suma}")
        suma=0
    }


}