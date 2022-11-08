package com.example.calorietracker.fragments

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import com.example.calorietracker.MainViewModel
import com.example.calorietracker.R
import com.example.calorietracker.databinding.FragmentProfileBinding
import com.example.calorietracker.model.User
import kotlin.math.roundToInt

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private var user = User()
    private var weightUnit = ""
    private val viewModel: MainViewModel by activityViewModels()
    private val weightUnits: Array<String> by lazy {
        resources.getStringArray(R.array.weight)
    }
    private var heightUnit = ""
    private val heightUnits: Array<String> by lazy {
        resources.getStringArray(R.array.height)
    }
    private val activityLevels: Array<String> by lazy {
        resources.getStringArray(R.array.activity)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.i(javaClass.simpleName, "onCreateView")
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(javaClass.simpleName, "onViewCreated")
        binding.add.setOnClickListener {
            toMeal()
        }
        if (user.sex == "Male") {
            binding.checkmarkM.isVisible = true
        }
        else if (user.sex == "Female") {
            binding.checkmarkF.isVisible = true
        }
        binding.female.setOnClickListener {
            user.sex = "Female"
            binding.checkmarkM.isVisible = false
            binding.checkmarkF.isVisible = true
        }
        binding.male.setOnClickListener {
            user.sex = "Male"
            binding.checkmarkF.isVisible = false
            binding.checkmarkM.isVisible = true
        }
        binding.heightUnit.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                handleHeight(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                handleHeight(0)
            }

        }
        binding.weightUnit.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                handleWeight(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                handleWeight(0)
            }

        }
        binding.activityDropdown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                handleActivityLevel(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                handleActivityLevel(0)
            }

        }
        binding.calculate.setOnClickListener {
            calculateBMI()
            calculateRecommendedCalories()
        }
        val heightUnitAdapter = ArrayAdapter.createFromResource(this.requireActivity().applicationContext, R.array.height, android.R.layout.simple_spinner_item)
        heightUnitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.heightUnit.adapter = heightUnitAdapter
        val weightUnitAdapter = ArrayAdapter.createFromResource(this.requireActivity().applicationContext, R.array.weight, android.R.layout.simple_spinner_item)
        weightUnitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.weightUnit.adapter = weightUnitAdapter
        val activityLevelAdapter = ArrayAdapter.createFromResource(this.requireActivity().applicationContext, R.array.activity, android.R.layout.simple_spinner_item)
        activityLevelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.activityDropdown.adapter = activityLevelAdapter
        viewModel.setProfile(user)
        binding.trend.setOnClickListener {
            toWeeklyCal()
        }
    }
    private fun handleHeight(heightPos: Int) {
        if (heightPos > 0) {
            heightUnit = heightUnits[heightPos]
        }
    }
    private fun handleWeight(weightPos: Int) {
        if (weightPos > 0) {
            weightUnit = weightUnits[weightPos]
        }
    }
    private fun calculateBMI() {
        user.height = binding.heightInput.text.toString().toDouble()
        user.weight = binding.weightInput.text.toString().toDouble()
        if (weightUnit == "kg") {
            user.weight = user.weight * 2.205
        }
        if (heightUnit == "cm") {
            user.height = user.height / 2.54
        }
        user.bmi = user.weight / user.height / user.height * 703.0
        binding.BMIValue.text = "BMI: ${user.bmi}"
        var status = ""
        if (user.bmi > 30) {
            status = "Obese"
            binding.progressBar.progressTintList = ColorStateList.valueOf(Color.RED)
        }
        else if (user.bmi > 25) {
            status = "Overweight"
            binding.progressBar.progressTintList = ColorStateList.valueOf(Color.YELLOW)
        }
        else if (user.bmi > 18.5) {
            status = "Ideal"
            binding.progressBar.progressTintList = ColorStateList.valueOf(Color.GREEN)
        }
        else {
            status = "Underweight"
            binding.progressBar.progressTintList = ColorStateList.valueOf(Color.BLUE)
        }
        if (user.bmi > 15 && user.bmi < 40) {
            binding.progressBar.setProgress(user.bmi.roundToInt(), true)
        } else if (user.bmi > 40) {
            binding.progressBar.setProgress(40, true)
        } else {
            binding.progressBar.setProgress(15, true)
        }
        binding.BMIStatus.text = "Status: $status"
        binding.idealWeightText.text = "${18.5 * user.height  * user.height / 703.0} - ${25 * user.height * user.height / 703.0} lb"
    }
    private fun handleActivityLevel(activityPos: Int) {
        if (activityPos > 0) {
            user.activityLevel = activityLevels[activityPos]
        }
    }
    private fun toMeal() {
        this.requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment, MealSelectionFragment.newInstance()).addToBackStack("mealSelectionFragment").commit()
    }
    private fun toWeeklyCal() {
        this.requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment, WeeklyCalFragment.newInstance()).addToBackStack("weeklyCalFragment").commit()
    }
    private fun calculateRecommendedCalories() {
        var bmr = 0.0
        user.age = binding.ageInput.text.toString().toInt()
        when (user.sex) {
            "Female" -> {
                bmr = 655.1 + 9.563 * user.weight + 1.85 * user.height - 4.676 * user.age
            }
            "Male" -> {
                bmr = 66.47 + 13.75 * user.weight + 5.003 * user.height - 6.755 * user.age
            }
        }
        when (user.activityLevel) {
            "Sedentary" -> {
                user.recommendedCal = (1.2 * bmr).roundToInt()
            }
            "Lightly Active" -> {
                user.recommendedCal = (1.375 * bmr).roundToInt()
            }
            "Moderately Active" -> {
                user.recommendedCal = (1.55 * bmr).roundToInt()
            }
            "Active" -> {
                user.recommendedCal = (1.725 * bmr).roundToInt()
            }
            "Very Active" -> {
                user.recommendedCal = (1.9 * bmr).roundToInt()
            }
        }
        binding.recommendedCal.text = user.recommendedCal.toString() + " Cal"
        binding.diary.setOnClickListener {
            toCalorieSummary()
        }
    }
    override fun onDestroyView() {
        Log.i(javaClass.simpleName, "onDestroyView")
        super.onDestroyView()
        _binding = null
    }
    private fun toCalorieSummary() {
        this.requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment, CalorieSummaryFragment.newInstance()).addToBackStack("calorieSummaryFragment").commit()
    }
    companion object {
        @JvmStatic
        fun newInstance(): ProfileFragment {
            return ProfileFragment()
        }
    }
}