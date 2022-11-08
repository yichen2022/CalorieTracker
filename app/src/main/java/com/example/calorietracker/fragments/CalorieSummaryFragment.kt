package com.example.calorietracker.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.calorietracker.MainViewModel
import com.example.calorietracker.databinding.FragmentMealListBinding

class CalorieSummaryFragment : Fragment() {
    private var _binding: FragmentMealListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.i(javaClass.simpleName, "onCreateView")
        _binding = FragmentMealListBinding.inflate(inflater, container, false)
        val adapter = CalorieAdapter()
        adapter.submitList(viewModel.observeAllMeals().value)
        binding.recyclerview.adapter = adapter
        binding.progress.max = viewModel.observeUser().value!!.recommendedCal
        binding.progress.progress = viewModel.observeUser().value!!.calories
        viewModel.observeUser().observeForever {
            binding.recommendedCalories.text = "Recommended: ${viewModel.observeUser().value!!.recommendedCal}"
            binding.remainingCalories.text = "${viewModel.observeUser().value!!.recommendedCal - viewModel.observeUser().value!!.calories} Calories Left"
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(javaClass.simpleName, "onViewCreated")
    }
    override fun onDestroyView() {
        Log.i(javaClass.simpleName, "onDestroyView")
        super.onDestroyView()
        _binding = null
    }
    companion object {
        @JvmStatic
        fun newInstance(): CalorieSummaryFragment {
            return CalorieSummaryFragment()
        }
    }
}