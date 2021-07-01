package com.ferrifrancis.andorid

import android.os.Parcel
import android.os.Parcelable

class BEntrenador
    (val nombre: String?,
            val descripcion: String?): Parcelable
{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel?, p1: Int) {
        //escribe las variables en el sistema operativo
        //? elvis operator si no es null entonces haz esto ...
        //si no quiero usar el elvis operator se pone
        //if (parcel != null){}
        parcel?.writeString(nombre)
        parcel?.writeString(descripcion)

    }

    override fun toString(): String{
        return "${nombre}-${descripcion}"
    }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BEntrenador> {
        override fun createFromParcel(parcel: Parcel): BEntrenador {
            return BEntrenador(parcel)
        }

        override fun newArray(size: Int): Array<BEntrenador?> {
            return arrayOfNulls(size)
        }
    }

}