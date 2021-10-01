package com.ferrifrancis.firebase_uno.dto

class Producto(
    val nombre: String? = null,
    val precio: Double? = null ) {

    override fun toString(): String {
        if(nombre == null && precio == null)
            return "Elija producto"
        else
            return "${nombre} $${precio}"
    }


}