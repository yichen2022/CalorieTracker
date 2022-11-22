package com.example.calorietracker.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
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
        //Sets and fetches the meals
        viewModel.getAllMeals()
        viewModel.observeAllMeals().observeForever {
            viewModel.setMeals()
        }
        super.onViewCreated(view, savedInstanceState)
        Log.i(javaClass.simpleName, "onViewCreated")
        //Set the meal based on the click
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
        //Go to weekly calorie summary
        binding.trend.setOnClickListener {
            toWeeklyCal()
        }
        //Go to profile
        binding.profile.setOnClickListener {
            toProfile()
        }
        //Go to date selection
        binding.calendar.setOnClickListener {
            toDate()
        }
        //Go to meal summary
        binding.diary.setOnClickListener {
            toMealSummary()
        }
    }
    private fun toMealSummary() {
        this.requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment, MealSummaryFragment.newInstance()).addToBackStack("mealSummaryFragment").commit()
    }
    private fun toProfile() {
        this.requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment, ProfileFragment.newInstance()).addToBackStack("profileFragment").commit()
    }
    private fun toDate() {
        this.requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment, DatePickerFragment.newInstance()).addToBackStack("datePickerFragment").commit()
    }
    private fun myPlate() {
        this.requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment, MyPlateFragment.newInstance()).addToBackStack("myPlateFragment").commit()
    }
    private fun toWeeklyCal() {
        this.requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment, WeeklyCalFragment.newInstance()).addToBackStack("weeklyCalFragment").commit()
    }
    override fun onDestroyView() {
        Log.i(javaClass.simpleName, "onDestroyView")
        super.onDestroyView()
    }
    companion object {
        @JvmStatic
        fun newInstance(): MealSelectionFragment {
            return MealSelectionFragment()
        }
    }
}