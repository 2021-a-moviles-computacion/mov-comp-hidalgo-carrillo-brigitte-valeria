package com.ferrifrancis.cookpad.adapter

import com.ferrifrancis.cookpad.Home
import com.ferrifrancis.cookpad.R

class HomeData {
    companion object{
        fun createDataSet():ArrayList<Home> {
            val list= ArrayList<Home>()
            list.add(Home("usuario1", R.drawable.usuario1,R.drawable.receta_fideos,
                "Fideos", mapOf(3 to "caraFeliz", 2 to "corazon", 1 to "aplauso")))
            list.add(Home("usuario2", R.drawable.usuario2,R.drawable.receta_lasagna,
                "Lasagna", mapOf(3 to "caraFeliz", 2 to "corazon", 1 to "aplauso")))
            return list
        }
    }
}