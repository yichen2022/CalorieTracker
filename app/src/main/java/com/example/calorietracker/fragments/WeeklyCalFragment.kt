package com.example.calorietracker.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.calorietracker.MainViewModel
import com.example.calorietracker.R
import com.example.calorietracker.databinding.FragmentWeeklyCalListBinding
import com.example.calorietracker.model.WeeklyCal
import java.time.ZoneId
import java.util.*

class WeeklyCalFragment : Fragment() {

    private var _binding: FragmentWeeklyCalListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()
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
        var day = viewModel.observeDate().value!!
        viewModel.getMealsByLast7Days(day)
        viewModel.observeSelectedMeals().observeForever {
            val meals = viewModel.observeSelectedMeals().value!!.toMutableList()
            val weeklyCal = WeeklyCal()
            weeklyCal.target = viewModel.observeUser().value!!.recommendedCal
            var days = 0
            for (i in 7 downTo 1) {
                when (i) {
                    7 -> {
                        if (meals.isEmpty()) {
                            binding.bar7.visibility = View.INVISIBLE
                            binding.day7.visibility = View.INVISIBLE
                        } else {
                            days++
                            val snacks = meals.removeLast()
                            val dinner = meals.removeLast()
                            val lunch = meals.removeLast()
                            val breakfast = meals.removeLast()
                            val totalCal = snacks.calories + dinner.calories + lunch.calories + breakfast.calories
                            weeklyCal.otherCal += snacks.calories
                            weeklyCal.dinnerCal += dinner.calories
                            weeklyCal.lunchCal += lunch.calories
                            weeklyCal.breakfastCal += breakfast.calories
                            weeklyCal.numCal += totalCal
                            binding.breakfast7.layoutParams.height = breakfast.calories * 100 / totalCal
                            binding.lunch7.layoutParams.height = lunch.calories * 100 / totalCal
                            binding.dinner7.layoutParams.height = dinner.calories * 100 / totalCal
                            binding.other7.layoutParams.height = snacks.calories * 100 / totalCal
                            when (day.day) {
                                1 -> {
                                    if (day.date < 10) {
                                        binding.day7.text = "Mon 0${day.date}"
                                    }
                                    else {
                                        binding.day7.text = "Mon ${day.date}"
                                    }
                                }
                                2 -> {
                                    if (day.date < 10) {
                                        binding.day7.text = "Tue 0${day.date}"
                                    }
                                    else {
                                        binding.day7.text = "Tue ${day.date}"
                                    }
                                }
                                3 -> {
                                    if (day.date < 10) {
                                        binding.day7.text = "Wed 0${day.date}"
                                    }
                                    else {
                                        binding.day7.text = "Wed ${day.date}"
                                    }
                                }
                                4 -> {
                                    if (day.date < 10) {
                                        binding.day7.text = "Thu 0${day.date}"
                                    }
                                    else {
                                        binding.day7.text = "Thu ${day.date}"
                                    }
                                }
                                5 -> {
                                    if (day.date < 10) {
                                        binding.day7.text = "Fri 0${day.date}"
                                    }
                                    else {
                                        binding.day7.text = "Fri ${day.date}"
                                    }
                                }
                                6 -> {
                                    if (day.date < 10) {
                                        binding.day7.text = "Sat 0${day.date}"
                                    }
                                    else {
                                        binding.day7.text = "Sat ${day.date}"
                                    }
                                }
                                7 -> {
                                    if (day.date < 10) {
                                        binding.day7.text = "Sun 0${day.date}"
                                    }
                                    else {
                                        binding.day7.text = "Sun ${day.date}"
                                    }
                                }
                            }
                        }
                    }
                    6 -> {
                        if (meals.isEmpty()) {
                            binding.bar6.visibility = View.INVISIBLE
                            binding.day6.visibility = View.INVISIBLE
                        } else {
                            days++
                            val snacks = meals.removeLast()
                            val dinner = meals.removeLast()
                            val lunch = meals.removeLast()
                            val breakfast = meals.removeLast()
                            val totalCal = snacks.calories + dinner.calories + lunch.calories + breakfast.calories
                            weeklyCal.otherCal += snacks.calories
                            weeklyCal.dinnerCal += dinner.calories
                            weeklyCal.lunchCal += lunch.calories
                            weeklyCal.breakfastCal += breakfast.calories
                            weeklyCal.numCal += totalCal
                            binding.breakfast6.layoutParams.height = breakfast.calories * 100 / totalCal
                            binding.lunch6.layoutParams.height = lunch.calories * 100 / totalCal
                            binding.dinner6.layoutParams.height = dinner.calories * 100 / totalCal
                            binding.other6.layoutParams.height = snacks.calories * 100 / totalCal
                            when (day.day) {
                                1 -> {
                                    if (day.date < 10) {
                                        binding.day6.text = "Mon 0${day.date}"
                                    }
                                    else {
                                        binding.day6.text = "Mon ${day.date}"
                                    }
                                }
                                2 -> {
                                    if (day.date < 10) {
                                        binding.day6.text = "Tue 0${day.date}"
                                    }
                                    else {
                                        binding.day6.text = "Tue ${day.date}"
                                    }
                                }
                                3 -> {
                                    if (day.date < 10) {
                                        binding.day6.text = "Wed 0${day.date}"
                                    }
                                    else {
                                        binding.day6.text = "Wed ${day.date}"
                                    }
                                }
                                4 -> {
                                    if (day.date < 10) {
                                        binding.day6.text = "Thu 0${day.date}"
                                    }
                                    else {
                                        binding.day6.text = "Thu ${day.date}"
                                    }
                                }
                                5 -> {
                                    if (day.date < 10) {
                                        binding.day6.text = "Fri 0${day.date}"
                                    }
                                    else {
                                        binding.day6.text = "Fri ${day.date}"
                                    }
                                }
                                6 -> {
                                    if (day.date < 10) {
                                        binding.day6.text = "Sat 0${day.date}"
                                    }
                                    else {
                                        binding.day6.text = "Sat ${day.date}"
                                    }
                                }
                                7 -> {
                                    if (day.date < 10) {
                                        binding.day6.text = "Sun 0${day.date}"
                                    }
                                    else {
                                        binding.day6.text = "Sun ${day.date}"
                                    }
                                }
                            }
                        }
                    }
                    5 -> {
                        if (meals.isEmpty()) {
                            binding.bar5.visibility = View.INVISIBLE
                            binding.day5.visibility = View.INVISIBLE
                        } else {
                            days++
                            val snacks = meals.removeLast()
                            val dinner = meals.removeLast()
                            val lunch = meals.removeLast()
                            val breakfast = meals.removeLast()
                            val totalCal = snacks.calories + dinner.calories + lunch.calories + breakfast.calories
                            weeklyCal.otherCal += snacks.calories
                            weeklyCal.dinnerCal += dinner.calories
                            weeklyCal.lunchCal += lunch.calories
                            weeklyCal.breakfastCal += breakfast.calories
                            weeklyCal.numCal += totalCal
                            binding.breakfast5.layoutParams.height = breakfast.calories * 100 / totalCal
                            binding.lunch5.layoutParams.height = lunch.calories * 100 / totalCal
                            binding.dinner5.layoutParams.height = dinner.calories * 100 / totalCal
                            binding.other5.layoutParams.height = snacks.calories * 100 / totalCal
                            when (day.day) {
                                1 -> {
                                    if (day.date < 10) {
                                        binding.day5.text = "Mon 0${day.date}"
                                    }
                                    else {
                                        binding.day5.text = "Mon ${day.date}"
                                    }
                                }
                                2 -> {
                                    if (day.date < 10) {
                                        binding.day5.text = "Tue 0${day.date}"
                                    }
                                    else {
                                        binding.day5.text = "Tue ${day.date}"
                                    }
                                }
                                3 -> {
                                    if (day.date < 10) {
                                        binding.day5.text = "Wed 0${day.date}"
                                    }
                                    else {
                                        binding.day5.text = "Wed ${day.date}"
                                    }
                                }
                                4 -> {
                                    if (day.date < 10) {
                                        binding.day5.text = "Thu 0${day.date}"
                                    }
                                    else {
                                        binding.day5.text = "Thu ${day.date}"
                                    }
                                }
                                5 -> {
                                    if (day.date < 10) {
                                        binding.day5.text = "Fri 0${day.date}"
                                    }
                                    else {
                                        binding.day5.text = "Fri ${day.date}"
                                    }
                                }
                                6 -> {
                                    if (day.date < 10) {
                                        binding.day5.text = "Sat 0${day.date}"
                                    }
                                    else {
                                        binding.day5.text = "Sat ${day.date}"
                                    }
                                }
                                7 -> {
                                    if (day.date < 10) {
                                        binding.day5.text = "Sun 0${day.date}"
                                    }
                                    else {
                                        binding.day5.text = "Sun ${day.date}"
                                    }
                                }
                            }
                        }
                    }
                    4 -> {
                        if (meals.isEmpty()) {
                            binding.bar4.visibility = View.INVISIBLE
                            binding.day4.visibility = View.INVISIBLE
                        } else {
                            days++
                            val snacks = meals.removeLast()
                            val dinner = meals.removeLast()
                            val lunch = meals.removeLast()
                            val breakfast = meals.removeLast()
                            val totalCal = snacks.calories + dinner.calories + lunch.calories + breakfast.calories
                            weeklyCal.otherCal += snacks.calories
                            weeklyCal.dinnerCal += dinner.calories
                            weeklyCal.lunchCal += lunch.calories
                            weeklyCal.breakfastCal += breakfast.calories
                            weeklyCal.numCal += totalCal
                            binding.breakfast4.layoutParams.height = breakfast.calories * 100 / totalCal
                            binding.lunch4.layoutParams.height = lunch.calories * 100 / totalCal
                            binding.dinner4.layoutParams.height = dinner.calories * 100 / totalCal
                            binding.other4.layoutParams.height = snacks.calories * 100 / totalCal
                            when (day.day) {
                                1 -> {
                                    if (day.date < 10) {
                                        binding.day4.text = "Mon 0${day.date}"
                                    }
                                    else {
                                        binding.day4.text = "Mon ${day.date}"
                                    }
                                }
                                2 -> {
                                    if (day.date < 10) {
                                        binding.day4.text = "Tue 0${day.date}"
                                    }
                                    else {
                                        binding.day4.text = "Tue ${day.date}"
                                    }
                                }
                                3 -> {
                                    if (day.date < 10) {
                                        binding.day4.text = "Wed 0${day.date}"
                                    }
                                    else {
                                        binding.day4.text = "Wed ${day.date}"
                                    }
                                }
                                4 -> {
                                    if (day.date < 10) {
                                        binding.day4.text = "Thu 0${day.date}"
                                    }
                                    else {
                                        binding.day4.text = "Thu ${day.date}"
                                    }
                                }
                                5 -> {
                                    if (day.date < 10) {
                                        binding.day4.text = "Fri 0${day.date}"
                                    }
                                    else {
                                        binding.day4.text = "Fri ${day.date}"
                                    }
                                }
                                6 -> {
                                    if (day.date < 10) {
                                        binding.day4.text = "Sat 0${day.date}"
                                    }
                                    else {
                                        binding.day4.text = "Sat ${day.date}"
                                    }
                                }
                                7 -> {
                                    if (day.date < 10) {
                                        binding.day4.text = "Sun 0${day.date}"
                                    }
                                    else {
                                        binding.day4.text = "Sun ${day.date}"
                                    }
                                }
                            }
                        }
                    }
                    3 -> {
                        if (meals.isEmpty()) {
                            binding.bar3.visibility = View.INVISIBLE
                            binding.day3.visibility = View.INVISIBLE
                        } else {
                            days++
                            val snacks = meals.removeLast()
                            val dinner = meals.removeLast()
                            val lunch = meals.removeLast()
                            val breakfast = meals.removeLast()
                            val totalCal = snacks.calories + dinner.calories + lunch.calories + breakfast.calories
                            weeklyCal.otherCal += snacks.calories
                            weeklyCal.dinnerCal += dinner.calories
                            weeklyCal.lunchCal += lunch.calories
                            weeklyCal.breakfastCal += breakfast.calories
                            weeklyCal.numCal += totalCal
                            binding.breakfast3.layoutParams.height = breakfast.calories * 100 / totalCal
                            binding.lunch3.layoutParams.height = lunch.calories * 100 / totalCal
                            binding.dinner3.layoutParams.height = dinner.calories * 100 / totalCal
                            binding.other3.layoutParams.height = snacks.calories * 100 / totalCal
                            when (day.day) {
                                1 -> {
                                    if (day.date < 10) {
                                        binding.day3.text = "Mon 0${day.date}"
                                    }
                                    else {
                                        binding.day3.text = "Mon ${day.date}"
                                    }
                                }
                                2 -> {
                                    if (day.date < 10) {
                                        binding.day3.text = "Tue 0${day.date}"
                                    }
                                    else {
                                        binding.day3.text = "Tue ${day.date}"
                                    }
                                }
                                3 -> {
                                    if (day.date < 10) {
                                        binding.day3.text = "Wed 0${day.date}"
                                    }
                                    else {
                                        binding.day3.text = "Wed ${day.date}"
                                    }
                                }
                                4 -> {
                                    if (day.date < 10) {
                                        binding.day3.text = "Thu 0${day.date}"
                                    }
                                    else {
                                        binding.day3.text = "Thu ${day.date}"
                                    }
                                }
                                5 -> {
                                    if (day.date < 10) {
                                        binding.day3.text = "Fri 0${day.date}"
                                    }
                                    else {
                                        binding.day3.text = "Fri ${day.date}"
                                    }
                                }
                                6 -> {
                                    if (day.date < 10) {
                                        binding.day3.text = "Sat 0${day.date}"
                                    }
                                    else {
                                        binding.day3.text = "Sat ${day.date}"
                                    }
                                }
                                7 -> {
                                    if (day.date < 10) {
                                        binding.day3.text = "Sun 0${day.date}"
                                    }
                                    else {
                                        binding.day3.text = "Sun ${day.date}"
                                    }
                                }
                            }
                        }
                    }
                    2 -> {
                        if (meals.isEmpty()) {
                            binding.bar2.visibility = View.INVISIBLE
                            binding.day2.visibility = View.INVISIBLE
                        } else {
                            days++
                            val snacks = meals.removeLast()
                            val dinner = meals.removeLast()
                            val lunch = meals.removeLast()
                            val breakfast = meals.removeLast()
                            val totalCal = snacks.calories + dinner.calories + lunch.calories + breakfast.calories
                            weeklyCal.otherCal += snacks.calories
                            weeklyCal.dinnerCal += dinner.calories
                            weeklyCal.lunchCal += lunch.calories
                            weeklyCal.breakfastCal += breakfast.calories
                            weeklyCal.numCal += totalCal
                            binding.breakfast2.layoutParams.height = breakfast.calories * 100 / totalCal
                            binding.lunch2.layoutParams.height = lunch.calories * 100 / totalCal
                            binding.dinner2.layoutParams.height = dinner.calories * 100 / totalCal
                            binding.other2.layoutParams.height = snacks.calories * 100 / totalCal
                            when (day.day) {
                                1 -> {
                                    if (day.date < 10) {
                                        binding.day2.text = "Mon 0${day.date}"
                                    }
                                    else {
                                        binding.day2.text = "Mon ${day.date}"
                                    }
                                }
                                2 -> {
                                    if (day.date < 10) {
                                        binding.day2.text = "Tue 0${day.date}"
                                    }
                                    else {
                                        binding.day2.text = "Tue ${day.date}"
                                    }
                                }
                                3 -> {
                                    if (day.date < 10) {
                                        binding.day2.text = "Wed 0${day.date}"
                                    }
                                    else {
                                        binding.day2.text = "Wed ${day.date}"
                                    }
                                }
                                4 -> {
                                    if (day.date < 10) {
                                        binding.day2.text = "Thu 0${day.date}"
                                    }
                                    else {
                                        binding.day2.text = "Thu ${day.date}"
                                    }
                                }
                                5 -> {
                                    if (day.date < 10) {
                                        binding.day2.text = "Fri 0${day.date}"
                                    }
                                    else {
                                        binding.day2.text = "Fri ${day.date}"
                                    }
                                }
                                6 -> {
                                    if (day.date < 10) {
                                        binding.day2.text = "Sat 0${day.date}"
                                    }
                                    else {
                                        binding.day2.text = "Sat ${day.date}"
                                    }
                                }
                                7 -> {
                                    if (day.date < 10) {
                                        binding.day2.text = "Sun 0${day.date}"
                                    }
                                    else {
                                        binding.day2.text = "Sun ${day.date}"
                                    }
                                }
                            }
                        }
                    }
                    1 -> {
                        if (meals.isEmpty()) {
                            binding.bar1.visibility = View.INVISIBLE
                            binding.day1.visibility = View.INVISIBLE
                        } else {
                            days++
                            val snacks = meals.removeLast()
                            val dinner = meals.removeLast()
                            val lunch = meals.removeLast()
                            val breakfast = meals.removeLast()
                            val totalCal = snacks.calories + dinner.calories + lunch.calories + breakfast.calories
                            weeklyCal.otherCal += snacks.calories
                            weeklyCal.dinnerCal += dinner.calories
                            weeklyCal.lunchCal += lunch.calories
                            weeklyCal.breakfastCal += breakfast.calories
                            weeklyCal.numCal += totalCal
                            binding.breakfast1.layoutParams.height = breakfast.calories * 100 / totalCal
                            binding.lunch1.layoutParams.height = lunch.calories * 100 / totalCal
                            binding.dinner1.layoutParams.height = dinner.calories * 100 / totalCal
                            binding.other1.layoutParams.height = snacks.calories * 100 / totalCal
                            when (day.day) {
                                1 -> {
                                    if (day.date < 10) {
                                        binding.day1.text = "Mon 0${day.date}"
                                    }
                                    else {
                                        binding.day1.text = "Mon ${day.date}"
                                    }
                                }
                                2 -> {
                                    if (day.date < 10) {
                                        binding.day1.text = "Tue 0${day.date}"
                                    }
                                    else {
                                        binding.day1.text = "Tue ${day.date}"
                                    }
                                }
                                3 -> {
                                    if (day.date < 10) {
                                        binding.day1.text = "Wed 0${day.date}"
                                    }
                                    else {
                                        binding.day1.text = "Wed ${day.date}"
                                    }
                                }
                                4 -> {
                                    if (day.date < 10) {
                                        binding.day1.text = "Thu 0${day.date}"
                                    }
                                    else {
                                        binding.day1.text = "Thu ${day.date}"
                                    }
                                }
                                5 -> {
                                    if (day.date < 10) {
                                        binding.day1.text = "Fri 0${day.date}"
                                    }
                                    else {
                                        binding.day1.text = "Fri ${day.date}"
                                    }
                                }
                                6 -> {
                                    if (day.date < 10) {
                                        binding.day1.text = "Sat 0${day.date}"
                                    }
                                    else {
                                        binding.day1.text = "Sat ${day.date}"
                                    }
                                }
                                7 -> {
                                    if (day.date < 10) {
                                        binding.day1.text = "Sun 0${day.date}"
                                    }
                                    else {
                                        binding.day1.text = "Sun ${day.date}"
                                    }
                                }
                            }
                        }
                    }
                }
                day = Date.from(day.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().minusDays(1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())
            }
            if (days != 0) {
                weeklyCal.average = weeklyCal.numCal/days
            }
            binding.avgCal.text = "Daily Average: ${weeklyCal.average}"
            binding.weeklyCalText.text = "${weeklyCal.numCal} Calories Per Week"
            binding.target.text = "Target: ${weeklyCal.target}"
            if (weeklyCal.numCal != 0) {
                weeklyCal.breakfastPercent = weeklyCal.breakfastCal * 100 / weeklyCal.numCal
                weeklyCal.lunchPercent = weeklyCal.lunchCal * 100 / weeklyCal.numCal
                weeklyCal.dinnerPercent = weeklyCal.dinnerCal * 100 / weeklyCal.numCal
                weeklyCal.otherPercent = weeklyCal.otherCal * 100 / weeklyCal.numCal
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
        binding.profile.setOnClickListener {
            toProfile()
        }
        binding.calendar.setOnClickListener {
            toDate()
        }
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
    override fun onDestroyView() {
        Log.i(javaClass.simpleName, "onDestroyView")
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(): WeeklyCalFragment {
            return WeeklyCalFragment()
        }
    }
}