package com.curso.puzzletoroidal.ui.creditos

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.curso.puzzletoroidal.R

class Creditos : Fragment() {

    companion object {
        fun newInstance() = Creditos()
    }

    private lateinit var viewModel: CreditosViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this).get(CreditosViewModel::class.java)
        val root =  inflater.inflate(R.layout.creditos_fragment, container, false)
        return root
    }
}
