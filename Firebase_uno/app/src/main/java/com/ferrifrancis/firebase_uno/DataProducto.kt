package com.ferrifrancis.firebase_uno

import com.ferrifrancis.firebase_uno.dto.Producto

class DataProducto {

    companion object{
        var arregloProducto= arrayListOf<Producto>()

        fun setearArregloProducto( nombreProducto: String?, precio: Double?)
        {
            val productoNuevo = Producto(nombreProducto, precio)
            arregloProducto.add(productoNuevo)
        }

        fun limpiarArregloProducto()
        {
            arregloProducto = arrayListOf()
        }
    }
}