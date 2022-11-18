package com.example.calorietracker.fragments

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.calorietracker.MainViewModel
import com.example.calorietracker.R
import com.example.calorietracker.databinding.FragmentMealListBinding

class CalorieSummaryFragment : Fragment() {
    private var _binding: FragmentMealListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.i(javaClass.simpleName, "onCreateView")
        _binding = FragmentMealListBinding.inflate(inflater, container, false)
        val adapter = CalorieAdapter()
        viewModel.observeDate().observeForever {
            viewModel.getMealsByDate(viewModel.observeDate().value!!)
            viewModel.observeSelectedMealsByDay().observeForever {
                var calories = 0
                val list = viewModel.observeSelectedMealsByDay().value!!.sortedWith(compareBy { it.index })
                for (i in list.indices) {
                    calories += list[i].calories
                }
                Log.i(javaClass.simpleName, calories.toString())
                viewModel.setCalories(calories)
                adapter.submitList(list)
                viewModel.observeCalories().observeForever {
                    viewModel.observeUser().observeForever {
                        binding.recommendedCalories.text = "Recommended: ${viewModel.observeUser().value!!.recommendedCal}"
                        if (calories > viewModel.observeUser().value!!.recommendedCal) {
                            binding.progress.max = viewModel.observeUser().value!!.recommendedCal
                            binding.progress.progress = viewModel.observeUser().value!!.recommendedCal
                            binding.progress.progressTintList = ColorStateList.valueOf(Color.RED)
                            binding.remainingCalories.text = "0 Calories Left"
                        } else {
                            binding.progress.max = viewModel.observeUser().value!!.recommendedCal
                            binding.progress.progress = calories
                            binding.progress.progressTintList = ColorStateList.valueOf(Color.BLUE)
                            binding.remainingCalories.text = "${viewModel.observeUser().value!!.recommendedCal - calories} Calories Left"
                        }
                    }
                }
            }
        }
        binding.recyclerview.adapter = adapter

        binding.profile.setOnClickListener {
            toProfile()
        }
        binding.trend.setOnClickListener {
            toWeeklyCal()
        }
        binding.calendar.setOnClickListener {
            toDate()
        }
        binding.add.setOnClickListener {
            toMeal()
        }
        return binding.root
    }
    private fun toMeal() {
        this.requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment, MealSelectionFragment.newInstance()).addToBackStack("mealSelectionFragment").commit()
    }
    private fun toWeeklyCal() {
        this.requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment, WeeklyCalFragment.newInstance()).addToBackStack("weeklyCalFragment").commit()
    }
    private fun toProfile() {
        this.requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment, ProfileFragment.newInstance()).addToBackStack("profileFragment").commit()
    }
    private fun toDate() {
        this.requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment, DatePickerFragment.newInstance()).addToBackStack("datePickerFragment").commit()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(javaClass.simpleName, "onViewCreated")
    }
    override fun onDestroyView() {
        Log.i(javaClass.simpleName, "onDestroyView")
        super.onDestroyView()
    }
    companion object {
        @JvmStatic
        fun newInstance(): CalorieSummaryFragment {
            return CalorieSummaryFragment()
        }
    }
}