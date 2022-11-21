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
import kotlin.concurrent.thread

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
        viewModel.observeDate().observe(viewLifecycleOwner) { date ->
            viewModel.getMealsByDate(date)
            viewModel.observeSelectedMealsByDay().observeForever { meal ->
                var calories = 0
                //Calculates the total number of calories consumed daily
                val list =
                meal.sortedWith(compareBy { it.index })
                for (i in list.indices) {
                    calories += list[i].calories
                }
                adapter.submitList(list)
                viewModel.observeUser().observe(viewLifecycleOwner) { user ->
                    binding.recommendedCalories.text = "Recommended: ${user.recommendedCal}"
                    binding.progress.max = user.recommendedCal
                    //If calories is over recommendation, progress bar is red and displays number of calories exceeded
                    //Otherwise, progress bar is blue and displays the remaining calories
                    if (calories > user.recommendedCal) {
                        binding.progress.progress = user.recommendedCal
                        binding.progress.progressTintList = ColorStateList.valueOf(Color.RED)
                        binding.remainingCalories.text = "${calories - user.recommendedCal} Calories Exceeded Recommendation"
                    } else {
                        binding.progress.progress = calories
                        binding.progress.progressTintList = ColorStateList.valueOf(Color.BLUE)
                        binding.remainingCalories.text = "${user.recommendedCal - calories} Calories Remaining"
                    }
                }
            }
        }
        binding.recyclerview.adapter = adapter
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