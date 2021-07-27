import android.os.Parcel
import android.os.Parcelable

class Colegio(
    var nombre: String?,
    var esFiscal: Boolean?,
    var distrito: Int?,
    var numAulas: Int?,
    var idColegio: Int?): Parcelable
{


    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int
    ) {
    }

    override fun toString(): String {
        return "${nombre}"
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nombre)
        parcel.writeValue(esFiscal)
        parcel.writeValue(distrito)
        parcel.writeValue(numAulas)
        parcel.writeValue(idColegio)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Colegio> {
        override fun createFromParcel(parcel: Parcel): Colegio {
            return Colegio(parcel)
        }

        override fun newArray(size: Int): Array<Colegio?> {
            return arrayOfNulls(size)
        }
    }
}