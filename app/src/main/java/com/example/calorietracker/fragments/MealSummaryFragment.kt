package com.example.calorietracker.fragments

import android.annotation.SuppressLint
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
import com.example.calorietracker.databinding.FragmentMealSummaryBinding
import java.util.Date
import kotlin.math.roundToInt

class MealSummaryFragment : Fragment() {
    private var _binding: FragmentMealSummaryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.i(javaClass.simpleName, "onCreateView")
        // Inflate the layout for this fragment
        _binding = FragmentMealSummaryBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(javaClass.simpleName, "onViewCreated")
        viewModel.observeMeal().observe(viewLifecycleOwner) { temp ->
            viewModel.observeDate().observe(viewLifecycleOwner) { date ->
                viewModel.observeAllMeals().observe(viewLifecycleOwner) {
                    calculateFoodGroups(temp, date)
                }
            }
        }
        //Go to calorie summary
        binding.diary.setOnClickListener {
            toCalorieSummary()
        }
        //Go to weekly calorie summary
        binding.trend.setOnClickListener {
            toWeeklyCal()
        }
        //Go to profile page
        binding.profile.setOnClickListener {
            toProfile()
        }
        //Date selection
        binding.calendar.setOnClickListener {
            toDate()
        }
        //Add a new food
        binding.add.setOnClickListener {
            toMeal()
        }
    }
    @SuppressLint("SetTextI18n")
    private fun calculateFoodGroups(name: String, date: Date) {
        //Sets the meal and date
        binding.mealDate.text = DateFormat.format("yyyy-MM-dd", date).toString()
        binding.mealTitle.text = name
        val meal = viewModel.getMeal(name, date)
        //Sets the percentages for each food group
        if (meal.calories != 0) {
            binding.grains.text = "Grains: ${(meal.grains * 100.0/meal.calories).roundToInt()}%"
            binding.fruitVeggie.text = "Fruit/Vegetables: ${(meal.fruitVeggie * 100.0/meal.calories).roundToInt()}%"
            binding.meat.text = "Meat: ${(meal.meat * 100.0/meal.calories).roundToInt()}%"
            binding.dairyGroup.text = "Dairy: ${(meal.dairy * 100.0/meal.calories).roundToInt()}%"
            binding.other.text = "Other: ${(meal.otherCategories * 100.0/meal.calories).roundToInt()}%"
        } else {
            binding.grains.text = "Grains: 0%"
            binding.fruitVeggie.text = "Fruit/Vegetables: 0%"
            binding.meat.text = "Meat: 0%"
            binding.dairyGroup.text = "Dairy: 0%"
            binding.other.text = "Other: 0%"
        }
    }
    private fun toMeal() {
        this.requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment, MealSelectionFragment.newInstance()).addToBackStack("mealSelectionFragment").commit()
    }
    private fun toProfile() {
        this.requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment, ProfileFragment.newInstance()).addToBackStack("profileFragment").commit()
    }
    private fun toDate() {
        this.requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment, DatePickerFragment.newInstance()).addToBackStack("datePickerFragment").commit()
    }
    private fun toCalorieSummary() {
        this.requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment, CalorieSummaryFragment.newInstance()).addToBackStack("calorieSummaryFragment").commit()
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
        fun newInstance(): MealSummaryFragment {
            return MealSummaryFragment()
        }
    }
}