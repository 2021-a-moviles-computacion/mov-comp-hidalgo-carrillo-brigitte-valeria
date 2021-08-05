package com.ferrifrancis.cookpad.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.ferrifrancis.cookpad.R
import com.ferrifrancis.cookpad.adapter.Data
import com.ferrifrancis.cookpad.adapter.HomeRecyclerAdapter
import com.ferrifrancis.cookpad.adapter.SearchRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_search.*


class SearchFragment : Fragment() {

    private lateinit var searchAdapter: SearchRecyclerAdapter //es no null,pero se inicializará más luego

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        initRecyclerView()
        addDataSet()

    }

    private fun addDataSet()
    {
        val data = Data.createDataSetHome()
        searchAdapter.submitList(data)
    }

    private fun initRecyclerView()
    {
        rv_search.apply {
            rv_search.layoutManager = GridLayoutManager(activity,2)
            searchAdapter = SearchRecyclerAdapter()
            rv_search.adapter = searchAdapter
        }
    }

}