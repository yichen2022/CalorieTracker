@file:Suppress("DEPRECATION")

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
import com.example.calorietracker.databinding.FragmentWeeklyCalListBinding
import com.example.calorietracker.model.Meal
import com.example.calorietracker.model.WeeklyCal
import com.google.android.material.snackbar.Snackbar
import java.time.ZoneId
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.roundToInt

class WeeklyCalFragment : Fragment() {

    private var _binding: FragmentWeeklyCalListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()
    //Placeholder for the data that is not written to the DB
    private lateinit var weeklyCal: WeeklyCal
    private var breakfastCal = 0
    private var lunchCal = 0
    private var dinnerCal = 0
    private var otherCal = 0
    private var totalCal = 0
    //The days of the week
    private val daysOfWeek = listOf("Sun ", "Mon ", "Tue ", "Wed ", "Thu ", "Fri ", "Sat ")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.i(javaClass.simpleName, "onCreateView")
        _binding = FragmentWeeklyCalListBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(javaClass.simpleName, "onViewCreated")
        weeklyCal = WeeklyCal()

        this.requireActivity().runOnUiThread {
            var day = viewModel.observeDate().value!!
            binding.mealDate.text = DateFormat.format("yyyy-MM-dd", day).toString()
            viewModel.getMealsByLast7Days(day)
            //Populates the days of the week axis
            setUpDayAxis(day)
            viewModel.observeSelectedMealsByWeek().observe(viewLifecycleOwner) { m ->
                //Reset calorie counts
                weeklyCal.breakfastCal = 0
                weeklyCal.lunchCal = 0
                weeklyCal.dinnerCal = 0
                weeklyCal.otherCal = 0
                weeklyCal.numCal = 0
                weeklyCal.breakfastPercent = 0
                weeklyCal.lunchPercent = 0
                weeklyCal.dinnerPercent = 0
                weeklyCal.otherPercent = 0
                day = viewModel.observeDate().value!!
                //Set calorie recommendation
                viewModel.observeUser().observe(viewLifecycleOwner) {
                    weeklyCal.target = it.idealCal
                }
                val meals = m.toMutableList()
                var i = 7
                //Check if meals is empty
                while (meals.isNotEmpty() && i > 0) {
                    breakfastCal = 0
                    lunchCal = 0
                    dinnerCal = 0
                    otherCal = 0
                    totalCal = 0
                    for (j in 1..4) {
                        val meal = meals.removeLast()
                        //Change bar position
                        i -= TimeUnit.MILLISECONDS.toDays(day.time - meal.date!!.time).toInt()
                        day = meal.date!!
                        calculateCalories(meal)
                    }
                    weeklyCal.numCal += totalCal

                    setBars(i)
                }
                calculateAveragesAndPercentages()
            }
        }
        //Go to profile page
        binding.profile.setOnClickListener {
            toProfile()
        }
        //Go to date selection
        binding.calendar.setOnClickListener {
            toDate()
        }
        //Go to calorie summary
        binding.diary.setOnClickListener {
            toCalorieSummary()
        }
        //Add new food
        binding.add.setOnClickListener {
            toMeal()
        }
    }

    private fun calculateCalories(meal: Meal) {
        totalCal += meal.calories
        //Checks to see which meal to add calories
        when (meal.index) {
            1 -> {
                breakfastCal = meal.calories
                weeklyCal.breakfastCal += meal.calories
            }
            2 -> {
                lunchCal = meal.calories
                weeklyCal.lunchCal += meal.calories
            }
            3 -> {
                dinnerCal = meal.calories
                weeklyCal.dinnerCal += meal.calories
            }
            4 -> {
                otherCal = meal.calories
                weeklyCal.otherCal += meal.calories
            }
        }
    }

    private fun setUpDayAxis(day: Date) {
        for (k in 0 until 7) {
            val temp = day.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().minusDays(k.toLong())
                .atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()
            val dateOfMonth = Date.from(temp).date
            val dayOfWeek = Date.from(temp).day
            var dayText: String = daysOfWeek[dayOfWeek]
            if (dateOfMonth < 10) {
                dayText += "0"
            }
            dayText += dateOfMonth.toString()
            when (k) {
                0 -> {
                    binding.day7.text = dayText
                }
                1 -> {
                    binding.day6.text = dayText
                }
                2 -> {
                    binding.day5.text = dayText
                }
                3 -> {
                    binding.day4.text = dayText
                }
                4 -> {
                    binding.day3.text = dayText
                }
                5 -> {
                    binding.day2.text = dayText
                }
                6 -> {
                    binding.day1.text = dayText
                }
            }
        }
    }

    //To calorie summary
    private fun toCalorieSummary() {
        this.requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment, CalorieSummaryFragment.newInstance()).addToBackStack("calorieSummaryFragment").commit()
    }
    //Go to profile
    private fun toProfile() {
        this.requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment, ProfileFragment.newInstance()).addToBackStack("profileFragment").commit()
    }
    //Go to date selection
    private fun toDate() {
        this.requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment, DatePickerFragment.newInstance()).addToBackStack("datePickerFragment").commit()
    }
    //To the meal page
    private fun toMeal() {
        this.requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment, MealSelectionFragment.newInstance()).addToBackStack("mealSelectionFragment").commit()
    }
    override fun onDestroyView() {
        Log.i(javaClass.simpleName, "onDestroyView")
        super.onDestroyView()
    }
    private fun setBars(i: Int) {
        if (totalCal > 3900) {
            Snackbar.make(binding.barGraph, "Too many calories intake, please check the input values.", Snackbar.LENGTH_LONG).show()
            return
        }
        //Updates the bars
        when (i) {
            7 -> {
                if (totalCal != 0) {
                    binding.breakfast7.layoutParams.height = breakfastCal / 13
                    binding.lunch7.layoutParams.height = lunchCal / 13
                    binding.dinner7.layoutParams.height = dinnerCal / 13
                    binding.other7.layoutParams.height = otherCal / 13
                }
                binding.breakfast7.requestLayout()
                binding.lunch7.requestLayout()
                binding.dinner7.requestLayout()
                binding.other7.requestLayout()
            }
            6 -> {
                if (totalCal != 0) {
                    binding.breakfast6.layoutParams.height = breakfastCal / 13
                    binding.lunch6.layoutParams.height = lunchCal / 13
                    binding.dinner6.layoutParams.height = dinnerCal / 13
                    binding.other6.layoutParams.height = otherCal / 13
                }
                binding.breakfast6.requestLayout()
                binding.lunch6.requestLayout()
                binding.dinner6.requestLayout()
                binding.other6.requestLayout()
            }
            5 -> {
                if (totalCal != 0) {
                    binding.breakfast5.layoutParams.height = breakfastCal / 13
                    binding.lunch5.layoutParams.height = lunchCal / 13
                    binding.dinner5.layoutParams.height = dinnerCal / 13
                    binding.other5.layoutParams.height = otherCal / 13
                }
                binding.breakfast5.requestLayout()
                binding.lunch5.requestLayout()
                binding.dinner5.requestLayout()
                binding.other5.requestLayout()
            }
            4 -> {
                if (totalCal != 0) {
                    binding.breakfast4.layoutParams.height = breakfastCal / 13
                    binding.lunch4.layoutParams.height = lunchCal / 13
                    binding.dinner4.layoutParams.height = dinnerCal / 13
                    binding.other4.layoutParams.height = otherCal / 13
                }
                binding.breakfast4.requestLayout()
                binding.lunch4.requestLayout()
                binding.dinner4.requestLayout()
                binding.other4.requestLayout()
            }
            3 -> {
                if (totalCal != 0) {
                    binding.breakfast3.layoutParams.height = breakfastCal / 13
                    binding.lunch3.layoutParams.height = lunchCal / 13
                    binding.dinner3.layoutParams.height = dinnerCal / 13
                    binding.other3.layoutParams.height = otherCal / 13
                }
                binding.breakfast3.requestLayout()
                binding.lunch3.requestLayout()
                binding.dinner3.requestLayout()
                binding.other3.requestLayout()
            }
            2 -> {
                if (totalCal != 0) {
                    binding.breakfast2.layoutParams.height = breakfastCal / 13
                    binding.lunch2.layoutParams.height = lunchCal / 13
                    binding.dinner2.layoutParams.height = dinnerCal / 13
                    binding.other2.layoutParams.height = otherCal / 13
                }
                binding.breakfast2.requestLayout()
                binding.lunch2.requestLayout()
                binding.dinner2.requestLayout()
                binding.other2.requestLayout()
            }
            1 -> {
                if (totalCal != 0) {
                    binding.breakfast1.layoutParams.height = breakfastCal / 13
                    binding.lunch1.layoutParams.height = lunchCal / 13
                    binding.dinner1.layoutParams.height = dinnerCal / 13
                    binding.other1.layoutParams.height = otherCal / 13
                }
                binding.breakfast1.requestLayout()
                binding.lunch1.requestLayout()
                binding.dinner1.requestLayout()
                binding.other1.requestLayout()
            }
        }
    }
    @SuppressLint("SetTextI18n")
    private fun calculateAveragesAndPercentages() {
        //Calculates average calories this week
        weeklyCal.average = weeklyCal.numCal/7
        binding.avgCal.text = "Daily Average: ${weeklyCal.average}"
        binding.weeklyCalText.text = "${weeklyCal.numCal} Calories Per Week"
        binding.target.text = "Target: ${weeklyCal.target}"
        //Calculates the meal percentages
        if (weeklyCal.numCal != 0) {
            weeklyCal.breakfastPercent = (weeklyCal.breakfastCal * 100.0 / weeklyCal.numCal).roundToInt()
            weeklyCal.lunchPercent = (weeklyCal.lunchCal * 100.0 / weeklyCal.numCal).roundToInt()
            weeklyCal.dinnerPercent = (weeklyCal.dinnerCal * 100.0 / weeklyCal.numCal).roundToInt()
            weeklyCal.otherPercent = (weeklyCal.otherCal * 100.0 / weeklyCal.numCal).roundToInt()
        }
        binding.breakfastCal.text = "${weeklyCal.breakfastCal} Cal"
        binding.breakfastPercent.text = "${weeklyCal.breakfastPercent}%"
        binding.lunchCal.text = "${weeklyCal.lunchCal} Cal"
        binding.lunchPercent.text = "${weeklyCal.lunchPercent}%"
        binding.dinnerCal.text = "${weeklyCal.dinnerCal} Cal"
        binding.dinnerPercent.text = "${weeklyCal.dinnerPercent}%"
        binding.otherCal.text = "${weeklyCal.otherCal} Cal"
        binding.otherPercent.text = "${weeklyCal.otherPercent}%"
    }
    companion object {
        @JvmStatic
        fun newInstance(): WeeklyCalFragment {
            return WeeklyCalFragment()
        }
    }
}