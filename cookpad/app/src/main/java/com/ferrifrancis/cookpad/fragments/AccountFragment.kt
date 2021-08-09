package com.ferrifrancis.cookpad.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ferrifrancis.cookpad.R
import com.ferrifrancis.cookpad.adapter.TusRecetasAdapter
import kotlinx.android.synthetic.main.fragment_account.*


/**
 * A simple [Fragment] subclass.
 * Use the [AccountFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AccountFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
        Log.i("fragment","Account creado")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        setUpTabs()
    }

    private fun setUpTabs()
    {
        val adapter = fragmentManager?.let { TusRecetasAdapter(supportFragmentManager= it) }
        adapter?.addFragment(TusTrucosFragment(),"Trucos")
        adapter?.addFragment(TusRecetasFragment(),"Recetas")
        view_pager.adapter= adapter
        tabs.setupWithViewPager(view_pager)

        
    }


}