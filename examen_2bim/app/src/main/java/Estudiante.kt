import android.os.Parcel
import android.os.Parcelable

class Estudiante(
    val nombre: String?,
    val fechaNacimiento: String?,
    var curso: String?,
    val cedula: String?,
    val sexo: String?,
    val idColegio: String?): Parcelable
{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nombre)
        parcel.writeString(fechaNacimiento)
        parcel.writeString(curso)
        parcel.writeString(cedula)
        parcel.writeString(sexo)
        if (idColegio != null) {
            parcel.writeString(idColegio)
        }
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
       return "${this.nombre}\nCurso: ${this.curso}"
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