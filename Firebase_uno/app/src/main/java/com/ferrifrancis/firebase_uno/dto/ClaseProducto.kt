package com.ferrifrancis.firebase_uno.dto

class ClaseProducto
    (val nombre: String? = null,
    val precio: Double? = null ) {

        override fun toString(): String {
            return "${nombre} $${precio}"
        }
}