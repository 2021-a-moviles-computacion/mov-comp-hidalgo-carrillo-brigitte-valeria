class Colegio
{
    val direccion: String
    val metros2: Float
    var aulas: Int
    val nombre: String
    val esFiscal: Boolean
    var estudiantes: ArrayList<Estudiante>

    constructor(direccion:String, metros2:Float, aulas: Int, nombre:String, esFiscal: Boolean, estudiante: Estudiante) {
        this.direccion= direccion
        this.metros2 = metros2
        this.aulas = aulas
        this.nombre = nombre
        this. esFiscal = esFiscal
        this.estudiantes = arrayListOf(estudiante)

    }

    constructor(direccion:String, metros2:Float, aulas: Int, nombre:String, esFiscal: Boolean, estudiantes: ArrayList<Estudiante>) {
        this.direccion= direccion
        this.metros2 = metros2
        this.aulas = aulas
        this.nombre = nombre
        this. esFiscal = esFiscal
        this.estudiantes = estudiantes

    }
}