package com.ferrifrancis.firebase_uno.dto

data class FirebaseUsuarioDto (
    val uid: String="",
    val email: String="",
    val roles: ArrayList<String> = arrayListOf()
) {
}