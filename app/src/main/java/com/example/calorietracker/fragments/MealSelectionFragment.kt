package com.example.calorietracker.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commitNow
import com.example.calorietracker.MainViewModel
import com.example.calorietracker.R
import com.example.calorietracker.databinding.FragmentMealSelectionBinding

class MealSelectionFragment : Fragment() {
    private var _binding: FragmentMealSelectionBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.i(javaClass.simpleName, "onCreateView")
        // Inflate the layout for this fragment
        _binding = FragmentMealSelectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(javaClass.simpleName, "onViewCreated")
        binding.breakfast.setOnClickListener {
            viewModel.setMeal("Breakfast")
            myPlate()
        }
        binding.lunch.setOnClickListener {
            viewModel.setMeal("Lunch")
            myPlate()
        }
        binding.dinner.setOnClickListener {
            viewModel.setMeal("Dinner")
            myPlate()
        }
        binding.snacks.setOnClickListener {
            viewModel.setMeal("Snacks")
            myPlate()
        }
    }
    private fun myPlate() {
        this.requireActivity().supportFragmentManager.commitNow {
            setReorderingAllowed(true)
            replace(R.id.fragment, MyPlateFragment.newInstance())
        }
    }
    companion object {
        @JvmStatic
        fun newInstance(): MealSelectionFragment {
            return MealSelectionFragment()
        }
    }
}