class Colegio (val nombre: String,
               val metros2: Float,
               val esFiscal: Boolean,
               var distrito: Int,
               var numAulas: Int,
                val idColegio: Int)
{

    override fun toString(): String {
        return "${nombre}"
    }
}