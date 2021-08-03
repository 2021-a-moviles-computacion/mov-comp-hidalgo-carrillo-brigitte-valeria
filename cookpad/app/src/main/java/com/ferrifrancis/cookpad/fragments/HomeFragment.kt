package com.ferrifrancis.cookpad.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.ferrifrancis.cookpad.R
import com.ferrifrancis.cookpad.adapter.HomeData
import com.ferrifrancis.cookpad.adapter.HomeRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {

    //private var layoutManager: RecyclerView.LayoutManager? = null
    //private var adapter: RecyclerView.Adapter<HomeRecyclerAdapter.HomeViewHolder>? = null
    private lateinit var homeAdapter: HomeRecyclerAdapter //es no null,pero se inicializará más luego

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        initRecyclerView()
    }

    private fun addDataSet()
    {
        val data = HomeData.createDataSet()
        homeAdapter.submitList(data)
    }

    private fun initRecyclerView()
    {
        rv_home.apply {
            rv_home.layoutManager = LinearLayoutManager(activity)
            homeAdapter = HomeRecyclerAdapter()
            rv_home.adapter = homeAdapter
        }
    }
}