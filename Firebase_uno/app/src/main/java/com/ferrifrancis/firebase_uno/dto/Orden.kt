package com.ferrifrancis.firebase_uno.dto

class Orden(
    val nombreRestaurante: String?,
    val nombreProducto: String?,
    val precioProducto: Double?
) {
    override fun toString(): String {
        return "Restaurante: ${nombreRestaurante}\nProducto ${nombreProducto}\nPrecio${precioProducto}"
    }
}