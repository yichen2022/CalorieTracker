package com.example.calorietracker.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.example.calorietracker.R
import com.example.calorietracker.databinding.FragmentBlankBinding

class BlankFragment : Fragment() {
    private var _binding: FragmentBlankBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentBlankBinding.inflate(inflater, container, false)
        Log.i(javaClass.simpleName, "onCreateView")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.i(javaClass.simpleName, "onViewCreated")
        super.onViewCreated(view, savedInstanceState)
        binding.logo.setOnClickListener {
            binding.logo.isVisible = false
            this.requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment, StartFragment.newInstance()).addToBackStack("startFragment").commit()
        }
    }
}