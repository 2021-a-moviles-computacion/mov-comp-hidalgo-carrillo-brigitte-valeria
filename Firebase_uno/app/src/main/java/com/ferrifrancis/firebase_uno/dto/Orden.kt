package com.ferrifrancis.firebase_uno.dto

class Orden(
    val nombreRestaurante: String?,
    val nombreProducto: String?,
    val precioProducto: Double?,
    val cantidad: Int?,
    val totalOrden: Double?
) {
    override fun toString(): String {

        return "Restaurante: ${nombreRestaurante}\nProducto: ${nombreProducto}\n" +
                "Precio Unit.: $${precioProducto}\nCantidad: ${cantidad}" +
                "\nSubtotal: $${totalOrden}"
       /* return "${nombreRestaurante}\t${nombreProducto}\t" +
                "$${precioProducto}\t${cantidad}" +
                "\t$${totalOrden}"*/
    }
}