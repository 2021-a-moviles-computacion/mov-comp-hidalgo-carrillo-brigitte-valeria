package com.ferrifrancis.firebase_uno.dto

class Restaurante(
    val nombre: String? = null
) {
    override fun toString(): String {
        if( nombre == null)
            return "Elija restaurante"
        else
            return "${nombre}"
    }
}