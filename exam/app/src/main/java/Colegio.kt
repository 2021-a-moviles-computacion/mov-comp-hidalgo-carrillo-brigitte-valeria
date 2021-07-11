class Colegio(
    var nombre: String?,
    val metros2: Float?,
    var esFiscal: Boolean?,
    var distrito: Int?,
    var numAulas: Int?,
    var idColegio: Int?)
{


    override fun toString(): String {
        return "${nombre}"
    }
}