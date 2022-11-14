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
import com.google.firebase.auth.FirebaseAuth
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
        viewModel.observeSelectedMealsByWeek().observe(viewLifecycleOwner) {
            day = viewModel.observeDate().value!!
            val meals = it.toMutableList()
            val weeklyCal = WeeklyCal()
            viewModel.observeWeeklyCalSummary().observeForever {
                if (viewModel.observeWeeklyCalSummary().value != null) {
                    weeklyCal.firestoreId = viewModel.observeWeeklyCalSummary().value!!.firestoreId
                }
            }
            weeklyCal.authorId = FirebaseAuth.getInstance().currentUser!!.uid
            weeklyCal.userId = viewModel.observeUser().value!!.firestoreId
            weeklyCal.target = viewModel.observeUser().value!!.recommendedCal
            var days = 0
            for (i in 7 downTo 1) {
                Log.i(javaClass.simpleName, day.toString())
                when (i) {
                    7 -> {
                        if (meals.isEmpty()) {
                            binding.bar7.visibility = View.INVISIBLE
                            binding.day7.visibility = View.INVISIBLE
                        } else {
                            days++
                            var breakfastCal = 0
                            var lunchCal = 0
                            var dinnerCal = 0
                            var otherCal = 0
                            var totalCal = 0
                            for (j in 1..4) {
                                val meal = meals.removeLast()
                                totalCal += meal.calories
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
                            weeklyCal.numCal += totalCal
                            if (totalCal == 0) {
                                binding.breakfast7.layoutParams.height = 0
                                binding.lunch7.layoutParams.height = 0
                                binding.dinner7.layoutParams.height = 0
                                binding.other7.layoutParams.height = 0
                            }
                            else {
                                binding.breakfast7.layoutParams.height = breakfastCal * 200 / totalCal
                                binding.lunch7.layoutParams.height = lunchCal * 200 / totalCal
                                binding.dinner7.layoutParams.height = dinnerCal * 200 / totalCal
                                binding.other7.layoutParams.height = otherCal * 200 / totalCal
                            }
                            binding.breakfast7.requestLayout()
                            binding.lunch7.requestLayout()
                            binding.dinner7.requestLayout()
                            binding.other7.requestLayout()
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
                                0 -> {
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
                            var breakfastCal = 0
                            var lunchCal = 0
                            var dinnerCal = 0
                            var otherCal = 0
                            var totalCal = 0
                            for (j in 1..4) {
                                val meal = meals.removeLast()
                                totalCal += meal.calories
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
                            weeklyCal.numCal += totalCal
                            if (totalCal == 0) {
                                binding.breakfast6.layoutParams.height = 0
                                binding.lunch6.layoutParams.height = 0
                                binding.dinner6.layoutParams.height = 0
                                binding.other6.layoutParams.height = 0
                            }
                            else {
                                binding.breakfast6.layoutParams.height = breakfastCal * 200 / totalCal
                                binding.lunch6.layoutParams.height = lunchCal * 200 / totalCal
                                binding.dinner6.layoutParams.height = dinnerCal * 200 / totalCal
                                binding.other6.layoutParams.height = otherCal * 200 / totalCal
                            }
                            binding.breakfast6.requestLayout()
                            binding.lunch6.requestLayout()
                            binding.dinner6.requestLayout()
                            binding.other6.requestLayout()
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
                                0 -> {
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
                            var breakfastCal = 0
                            var lunchCal = 0
                            var dinnerCal = 0
                            var otherCal = 0
                            var totalCal = 0
                            for (j in 1..4) {
                                val meal = meals.removeLast()
                                totalCal += meal.calories
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
                            weeklyCal.numCal += totalCal
                            if (totalCal == 0) {
                                binding.breakfast5.layoutParams.height = 0
                                binding.lunch5.layoutParams.height = 0
                                binding.dinner5.layoutParams.height = 0
                                binding.other5.layoutParams.height = 0
                            }
                            else {
                                binding.breakfast5.layoutParams.height = breakfastCal * 200 / totalCal
                                binding.lunch5.layoutParams.height = lunchCal * 200 / totalCal
                                binding.dinner5.layoutParams.height = dinnerCal * 200 / totalCal
                                binding.other5.layoutParams.height = otherCal * 200 / totalCal
                            }
                            binding.breakfast5.requestLayout()
                            binding.lunch5.requestLayout()
                            binding.dinner5.requestLayout()
                            binding.other5.requestLayout()
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
                                0 -> {
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
                            var breakfastCal = 0
                            var lunchCal = 0
                            var dinnerCal = 0
                            var otherCal = 0
                            var totalCal = 0
                            for (j in 1..4) {
                                val meal = meals.removeLast()
                                totalCal += meal.calories
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
                            weeklyCal.numCal += totalCal
                            if (totalCal == 0) {
                                binding.breakfast4.layoutParams.height = 0
                                binding.lunch4.layoutParams.height = 0
                                binding.dinner4.layoutParams.height = 0
                                binding.other4.layoutParams.height = 0
                            }
                            else {
                                binding.breakfast4.layoutParams.height = breakfastCal * 200 / totalCal
                                binding.lunch4.layoutParams.height = lunchCal * 200 / totalCal
                                binding.dinner4.layoutParams.height = dinnerCal * 200 / totalCal
                                binding.other4.layoutParams.height = otherCal * 200 / totalCal
                            }
                            binding.breakfast4.requestLayout()
                            binding.lunch4.requestLayout()
                            binding.dinner4.requestLayout()
                            binding.other4.requestLayout()
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
                                0 -> {
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
                            var breakfastCal = 0
                            var lunchCal = 0
                            var dinnerCal = 0
                            var otherCal = 0
                            var totalCal = 0
                            for (j in 1..4) {
                                val meal = meals.removeLast()
                                totalCal += meal.calories
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
                            weeklyCal.numCal += totalCal
                            if (totalCal == 0) {
                                binding.breakfast3.layoutParams.height = 0
                                binding.lunch3.layoutParams.height = 0
                                binding.dinner3.layoutParams.height = 0
                                binding.other3.layoutParams.height = 0
                            }
                            else {
                                binding.breakfast3.layoutParams.height = breakfastCal * 200 / totalCal
                                binding.lunch3.layoutParams.height = lunchCal * 200 / totalCal
                                binding.dinner3.layoutParams.height = dinnerCal * 200 / totalCal
                                binding.other3.layoutParams.height = otherCal * 200 / totalCal
                            }
                            binding.breakfast3.requestLayout()
                            binding.lunch3.requestLayout()
                            binding.dinner3.requestLayout()
                            binding.other3.requestLayout()
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
                                0 -> {
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
                            var breakfastCal = 0
                            var lunchCal = 0
                            var dinnerCal = 0
                            var otherCal = 0
                            var totalCal = 0
                            for (j in 1..4) {
                                val meal = meals.removeLast()
                                totalCal += meal.calories
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
                            weeklyCal.numCal += totalCal
                            if (totalCal == 0) {
                                binding.breakfast2.layoutParams.height = 0
                                binding.lunch2.layoutParams.height = 0
                                binding.dinner2.layoutParams.height = 0
                                binding.other2.layoutParams.height = 0
                            }
                            else {
                                binding.breakfast2.layoutParams.height = breakfastCal * 200 / totalCal
                                binding.lunch2.layoutParams.height = lunchCal * 200 / totalCal
                                binding.dinner2.layoutParams.height = dinnerCal * 200 / totalCal
                                binding.other2.layoutParams.height = otherCal * 200 / totalCal
                            }
                            binding.breakfast2.requestLayout()
                            binding.lunch2.requestLayout()
                            binding.dinner2.requestLayout()
                            binding.other2.requestLayout()
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
                                0 -> {
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
                            var breakfastCal = 0
                            var lunchCal = 0
                            var dinnerCal = 0
                            var otherCal = 0
                            var totalCal = 0
                            for (j in 1..4) {
                                val meal = meals.removeLast()
                                totalCal += meal.calories
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
                            weeklyCal.numCal += totalCal
                            if (totalCal == 0) {
                                binding.breakfast1.layoutParams.height = 0
                                binding.lunch1.layoutParams.height = 0
                                binding.dinner1.layoutParams.height = 0
                                binding.other1.layoutParams.height = 0
                            }
                            else {
                                binding.breakfast1.layoutParams.height = breakfastCal * 200 / totalCal
                                binding.lunch1.layoutParams.height = lunchCal * 200 / totalCal
                                binding.dinner1.layoutParams.height = dinnerCal * 200 / totalCal
                                binding.other1.layoutParams.height = otherCal * 200 / totalCal
                            }
                            binding.breakfast1.requestLayout()
                            binding.lunch1.requestLayout()
                            binding.dinner1.requestLayout()
                            binding.other1.requestLayout()
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
                                0 -> {
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
            viewModel.setWeeklyCalSummary(weeklyCal)
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
    }

    companion object {
        @JvmStatic
        fun newInstance(): WeeklyCalFragment {
            return WeeklyCalFragment()
        }
    }
}