package com.ferrifrancis.andorid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GRecyclerView : AppCompatActivity() {
    var totalLikes=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_g_recycler_view)

        val listaEntrenador = arrayListOf<BEntrenador>()
        val ligaPokemon = DLiga ("Kanto", "Liga Kanto")
        listaEntrenador.add(BEntrenador("Valeria","1709793036",ligaPokemon))
        listaEntrenador.add(BEntrenador("Brigitte","1725884801",ligaPokemon))

        val recyclerViewEntrenador = findViewById<RecyclerView>(R.id.rv_entrenadores)
        iniciarRecyclerView(listaEntrenador, this, recyclerViewEntrenador)
    }

    fun iniciarRecyclerView(
        lista: List<BEntrenador>,
        actividada: GRecyclerView,
        recyclerView: RecyclerView
    ){
        val adaptador = FRecyclerViewAdaptadorNombreCedula(actividada, lista, recyclerView)
        recyclerView.adapter = adaptador
        recyclerView.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(actividada)
        adaptador.notifyDataSetChanged()
    }

    fun aumentarTotalLikes()
    {
        totalLikes = totalLikes + 1
        val textView = findViewById<TextView>(R.id.tv_total_likes)
        textView.text = totalLikes.toString()
    }
}