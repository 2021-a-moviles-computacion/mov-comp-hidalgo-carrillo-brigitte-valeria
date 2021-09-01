package com.ferrifrancis.firebase_uno


import com.ferrifrancis.firebase_uno.dto.FirebaseRestauranteDto

class DataRestaurante {
    companion object{
        var arregloRestaurante= arrayListOf<FirebaseRestauranteDto>()

        fun setearArregloRestaurante( nombreRestaurante: String?)
        {
            val restauranteNuevo = FirebaseRestauranteDto(nombreRestaurante)
            arregloRestaurante.add(restauranteNuevo)
        }

        fun limpiarArregloRestaurante()
        {
            arregloRestaurante = arrayListOf()
        }
    }
}