package com.ferrifrancis.firebase_uno

import com.ferrifrancis.firebase_uno.dto.FirebaseProductoDto
import com.ferrifrancis.firebase_uno.dto.FirebaseRestauranteDto

class DataProducto {

    companion object{
        var arregloProducto= arrayListOf<FirebaseProductoDto>()

        fun setearArregloProducto( nombreProducto: String?, precio: Double?)
        {
            val productoNuevo = FirebaseProductoDto(nombreProducto, precio)
            arregloProducto.add(productoNuevo)
        }

        fun limpiarArregloProducto()
        {
            arregloProducto = arrayListOf()
        }
    }
}