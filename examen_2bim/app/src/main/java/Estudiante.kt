import android.os.Parcel
import android.os.Parcelable

class Estudiante(
    val nombre: String?=null,
    val fechaNacimiento: String?=null,
    var curso: String?=null,
    val cedula: String?=null,
    val sexo: String?=null,
    val idColegio: String?=null,
    val idEstudiante: String?=null,
    val coordLat: Double? = null,
    val coordLong: Double? = null
): Parcelable
{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readDouble()
    ) {
    }

    override fun toString(): String {
        return "${this.nombre}\nCurso: ${this.curso}"
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nombre)
        parcel.writeString(fechaNacimiento)
        parcel.writeString(curso)
        parcel.writeString(cedula)
        parcel.writeString(sexo)
        parcel.writeString(idColegio)
        parcel.writeString(idEstudiante)
        parcel.writeDouble(coordLat!!)
        parcel.writeDouble(coordLong!!)

    }

    override fun describeContents(): Int {
        return 0
    }



    companion object CREATOR : Parcelable.Creator<Estudiante> {
        override fun createFromParcel(parcel: Parcel): Estudiante {
            return Estudiante(parcel)
        }

        override fun newArray(size: Int): Array<Estudiante?> {
            return arrayOfNulls(size)
        }
    }

}