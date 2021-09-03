package com.ferrifrancis.firebase_uno.dto

class FirebaseProductoDto(
    val nombre: String? = null,
    val precio: Double? = null ) {

    override fun toString(): String {
        return "${nombre} $${precio}"
    }


}