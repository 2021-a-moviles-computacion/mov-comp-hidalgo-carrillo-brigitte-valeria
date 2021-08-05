package com.ferrifrancis.cookpad.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.ferrifrancis.cookpad.R
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.fragment_add.*


/**
 * A simple [Fragment] subclass.
 * Use the [AddFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add, container, false)
    }

    override fun onResume() {
        super.onResume()
        chip_receta.setOnClickListener {
            Toast.makeText(context, "This is chip1", Toast.LENGTH_SHORT).show()
        }
        chip_truco.setOnClickListener {
            Toast.makeText(context, "This is chip2", Toast.LENGTH_SHORT).show()
        }
    }

}