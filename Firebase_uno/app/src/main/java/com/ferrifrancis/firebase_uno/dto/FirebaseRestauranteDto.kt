package com.ferrifrancis.firebase_uno.dto

data class FirebaseRestauranteDto(
    val nombre: String? = null
) {
    override fun toString(): String {
        return "${nombre}"
    }
}