package com.example.tinkofftest.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.tinkofftest.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ErrorFragment : Fragment() {

    private var listener: ErrorFragmentListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_error, container, false)

        root.findViewById<Button>(R.id.repeat_button).setOnClickListener{
            listener?.onRepeatClicked()
        }

        return root
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ErrorFragmentListener) {
            listener = context
        }
    }
    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}