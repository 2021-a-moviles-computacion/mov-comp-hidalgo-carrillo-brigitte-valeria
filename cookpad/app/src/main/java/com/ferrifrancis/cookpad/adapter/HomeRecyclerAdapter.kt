package com.ferrifrancis.cookpad.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.ferrifrancis.cookpad.Home
import com.ferrifrancis.cookpad.R
import kotlinx.android.synthetic.main.layout_home_list_item.view.*


class HomeRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: List<Home> = ArrayList()
    private lateinit var  mListener: onItemClickListener

    interface onItemClickListener{

        fun onItemClick(position: Int)

    }

    fun setOnItemClickListener(listener: onItemClickListener)
    {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return HomeViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_home_list_item,
                parent,
                false
            ), mListener
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HomeViewHolder -> {
                holder.bind(items.get(position))
            }
        }
    }

    fun submitList(homeList: List<Home>) {
        items = homeList
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class HomeViewHolder constructor(
        itemView: View, listener: onItemClickListener
    ) : RecyclerView.ViewHolder(itemView) {
        val imagenReceta = itemView.img_receta
        val tituloReceta = itemView.et_titulo_receta
        val nombreAutorReceta = itemView.tv_nombre_autor_recete
        val imagenAutorReceta = itemView.img_usuario1
        val stripeMenu = itemView.stripe_menu

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)

            }
        }
        fun bind(home: Home) {
            tituloReceta.setText(home.tituloReceta)
            nombreAutorReceta.setText(home.nombreAutorReceta)
            imagenReceta.setImageResource(home.imagenReceta)
            imagenAutorReceta.setImageResource(home.imagenAutor)
            stripeMenu.setOnClickListener {
                Log.i("popup-menu","set on click listener popupmenu")
                popupMenu(it)

            }
        }

        fun popupMenu(v: View) {
            Log.i("popup-menu","popupmenu funcion")
            val popupMenu = PopupMenu(v.getContext(), v)
            popupMenu.inflate(R.menu.menu_home_receta)
            popupMenu.setOnMenuItemClickListener{
                when (it.itemId) {
                    R.id.mi_guardar_receta -> {
                        true
                    }
                    R.id.mi_reportar_receta -> {
                        true
                    }
                    else -> true
                }

            }
            popupMenu.show()
            
        }
/*
        fun anadirLike()
        {
            this.numeroLikes= this.numeroLikes+1
            likesTextView.text= this.numeroLikes.toString()
            contexto.aumentarTotalLikes()
        }
        */
    }
}