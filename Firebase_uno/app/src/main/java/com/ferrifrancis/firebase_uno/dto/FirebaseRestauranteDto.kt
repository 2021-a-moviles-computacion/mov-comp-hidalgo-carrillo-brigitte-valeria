package com.ferrifrancis.firebase_uno.dto

class FirebaseRestauranteDto(
    val nombre: String? = null
) {
    override fun toString(): String {
        return "${nombre}"
    }
}