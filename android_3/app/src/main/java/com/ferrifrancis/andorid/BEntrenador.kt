package com.ferrifrancis.andorid

import android.os.Parcel
import android.os.Parcelable

class BEntrenador
    (val nombre: String,
            val descripcion: String): Parcelable
{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        TODO("Not yet implemented")
    }

    override fun toString(): String{
        return "${nombre}-${descripcion}"
    }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
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