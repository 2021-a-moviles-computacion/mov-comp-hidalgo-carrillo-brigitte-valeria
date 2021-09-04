package com.ferrifrancis.firebase_uno


import com.ferrifrancis.firebase_uno.dto.Restaurante

class DataRestaurante {
    companion object{
        var arregloRestaurante= arrayListOf<Restaurante>()

        fun setearArregloRestaurante( nombreRestaurante: String?)
        {
            val restauranteNuevo = Restaurante(nombreRestaurante)
            arregloRestaurante.add(restauranteNuevo)
        }

        fun limpiarArregloRestaurante()
        {
            arregloRestaurante = arrayListOf()
        }
    }
}