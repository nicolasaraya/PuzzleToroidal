package com.curso.puzzletoroidal.ui.Instrucciones

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.curso.puzzletoroidal.R

class Instrucciones : Fragment() {

    private lateinit var instruccionesViewModel: InstruccionesViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        instruccionesViewModel = ViewModelProviders.of(this).get(InstruccionesViewModel::class.java)
        val root = inflater.inflate(R.layout.instrucciones_fragment, container, false)
        return root
    }
}
