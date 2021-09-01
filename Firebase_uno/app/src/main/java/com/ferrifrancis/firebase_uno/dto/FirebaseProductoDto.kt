package com.ferrifrancis.firebase_uno.dto

data class FirebaseProductoDto(
    val nombre: String? = null,
    val precio: Double? = null ) {

    override fun toString(): String {
        return "${nombre} $${precio}"
    }


}