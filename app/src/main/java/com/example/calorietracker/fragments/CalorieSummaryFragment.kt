package com.example.calorietracker.fragments

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.calorietracker.MainViewModel
import com.example.calorietracker.R
import com.example.calorietracker.databinding.FragmentMealListBinding
import java.util.*

class CalorieSummaryFragment : Fragment() {
    private var _binding: FragmentMealListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()
    private var calories = 0
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.i(javaClass.simpleName, "onCreateView")
        _binding = FragmentMealListBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    private fun setProgressBar(date: Date, cal: Int) {
        binding.date.text = DateFormat.format("yyyy-MM-dd", date).toString()
        binding.recommendedCalories.text = "Recommended: $cal"
        binding.progress.max = cal
        //If calories is over recommendation, progress bar is red and displays number of calories exceeded
        //Otherwise, progress bar is blue and displays the remaining calories
        if (calories > cal) {
            binding.progress.progress = cal
            binding.progress.progressTintList = ColorStateList.valueOf(Color.RED)
            binding.remainingCalories.text = "${calories - cal} Calories Exceeded Recommendation"
        } else {
            binding.progress.progress = calories
            binding.progress.progressTintList = ColorStateList.valueOf(Color.BLUE)
            binding.remainingCalories.text = "${cal - calories} Calories Remaining"
        }
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
        val adapter = CalorieAdapter()
        val date = viewModel.observeDate().value!!
        viewModel.getMealsByDate(date)
        viewModel.observeSelectedMealsByDay().observe(viewLifecycleOwner) { meal ->
            adapter.submitList(meal.sortedWith(compareBy { it.index }).toMutableList())
            calories = 0
            //Calculates the total number of calories consumed daily
            for (i in meal.indices) {
                calories += meal[i].calories
            }
            binding.recyclerview.adapter = adapter
            viewModel.observeUser().observe(viewLifecycleOwner) { user ->
                setProgressBar(date, user.idealCal)
            }
        }
        //Go to profile page
        binding.profile.setOnClickListener {
            toProfile()
        }
        //Go to weekly calorie summary
        binding.trend.setOnClickListener {
            toWeeklyCal()
        }
        //Go to date selection
        binding.calendar.setOnClickListener {
            toDate()
        }
        //Go to meal selection and add food
        binding.add.setOnClickListener {
            toMeal()
        }
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