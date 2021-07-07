package com.ferrifrancis.andorid

class BBaseDatosMemmoria {
    companion object{
        val arregloBEntrenador = arrayListOf<BEntrenador>()
        init {
            arregloBEntrenador.add(BEntrenador("valeria", "a@a.com", null))
            arregloBEntrenador.add(BEntrenador("brigitte", "b@b.com", null))
            arregloBEntrenador.add(BEntrenador("hidalgo", "c@c.com", null))
        }
    }

}