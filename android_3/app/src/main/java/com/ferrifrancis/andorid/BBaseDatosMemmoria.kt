package com.ferrifrancis.andorid

class BBaseDatosMemmoria {
    companion object{
        val arregloBEntrenador = arrayListOf<BEntrenador>()
        init {
            arregloBEntrenador.add(BEntrenador("valeria", "a@a.com"))

            arregloBEntrenador.add(BEntrenador("brigitte", "b@b.com"))
            arregloBEntrenador.add(BEntrenador("hidalgo", "c@c.com"))
        }
    }

}