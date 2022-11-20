package com.example.calorietracker.fragments

import android.annotation.SuppressLint
import android.app.Activity
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
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import com.example.calorietracker.MainActivity
import com.example.calorietracker.MainViewModel
import com.example.calorietracker.R
import com.example.calorietracker.databinding.FragmentProfileBinding
import com.example.calorietracker.firebase.AuthInit
import com.example.calorietracker.model.User
import com.google.android.material.snackbar.Snackbar
import kotlin.math.roundToInt

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private var user = User()
    private var weightUnit = ""
    private val viewModel: MainViewModel by activityViewModels()
    private val signInLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            viewModel.updateUser()
        } else {
            // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            // ...
            Log.d(javaClass.simpleName, "sign in failed $result")
        }
    }
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

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(javaClass.simpleName, "onViewCreated")
        user.authorId = viewModel.getCurrentUser()
        viewModel.fetchUser()
        //Check if the user for the account already exists.
        viewModel.observeUser().observe(viewLifecycleOwner) {
            //If it does exist, the user interface updates to reflect the
            if (viewModel.observeUser().value != null) {
                user.firestoreId = viewModel.observeUser().value!!.firestoreId
                binding.BMIValue.text = "BMI: ${(viewModel.observeUser().value!!.bmi * 10).roundToInt() / 10.0}"
                //Set the status of the person based on BMI
                val status: String
                if (viewModel.observeUser().value!!.bmi > 30) {
                    status = "Obese"
                    binding.progressBar.progressTintList = ColorStateList.valueOf(Color.RED)
                }
                else if (viewModel.observeUser().value!!.bmi > 25) {
                    status = "Overweight"
                    binding.progressBar.progressTintList = ColorStateList.valueOf(Color.YELLOW)
                }
                else if (viewModel.observeUser().value!!.bmi > 18.5) {
                    status = "Ideal"
                    binding.progressBar.progressTintList = ColorStateList.valueOf(Color.GREEN)
                }
                else {
                    status = "Underweight"
                    binding.progressBar.progressTintList = ColorStateList.valueOf(Color.BLUE)
                }
                //Fills the progress bar
                if (viewModel.observeUser().value!!.bmi > 15 && viewModel.observeUser().value!!.bmi < 40) {
                    binding.progressBar.progress = it.bmi.roundToInt()
                } else if (viewModel.observeUser().value!!.bmi > 40) {
                    binding.progressBar.progress = 40
                } else {
                    binding.progressBar.progress = 15
                }
                //Updates the ideal weight and calorie recommendations
                binding.BMIStatus.text = "Status: $status"
                binding.recommendedCal.text = viewModel.observeUser().value!!.recommendedCal.toString() + " Cal"
                binding.idealWeightText.text = "${(18.5 * viewModel.observeUser().value!!.height  * viewModel.observeUser().value!!.height / 703.0).roundToInt()} - ${(25 * viewModel.observeUser().value!!.height * viewModel.observeUser().value!!.height / 703.0).roundToInt()} lb (${(18.5 * viewModel.observeUser().value!!.height  * viewModel.observeUser().value!!.height / 703.0 * 0.454).roundToInt()} - ${(25 * viewModel.observeUser().value!!.height * viewModel.observeUser().value!!.height / 703.0 * 0.454).roundToInt()} kg)"
            }
        }
        //Add a food to selection
        binding.add.setOnClickListener {
            toMeal()
        }
        //Changes visibility of checkmarks for sex selection
        if (user.sex == "Male") {
            binding.checkmarkM.isVisible = true
        }
        else if (user.sex == "Female") {
            binding.checkmarkF.isVisible = true
        }
        //Sets the gender for the user
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
        //Sets the height unit
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
        //Sets the weight unit
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
        //Sets the activity level
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
        //Calculates the BMI and calories recommended
        binding.calculate.setOnClickListener {
            //Checks for missing and invalid values
            if (user.sex == "") {
                Snackbar.make(binding.profileLayout, "Missing sex", Snackbar.LENGTH_LONG).show()
            }
            else if (binding.ageInput.text.toString() == "") {
                Snackbar.make(binding.profileLayout, "Missing age", Snackbar.LENGTH_LONG).show()
            }
            else if (binding.heightInput.text.toString() == "") {
                Snackbar.make(binding.profileLayout, "Missing height", Snackbar.LENGTH_LONG).show()
            }
            else if (binding.weightInput.text.toString() == "") {
                Snackbar.make(binding.profileLayout, "Missing weight", Snackbar.LENGTH_LONG).show()
            }
            else if (user.activityLevel == "") {
                Snackbar.make(binding.profileLayout, "Missing activity level", Snackbar.LENGTH_LONG).show()
            }
            else if (heightUnit == "") {
                Snackbar.make(binding.profileLayout, "Missing unit for height", Snackbar.LENGTH_LONG).show()
            }
            else if (weightUnit == "") {
                Snackbar.make(binding.profileLayout, "Missing unit for weight", Snackbar.LENGTH_LONG).show()
            }
            else if (!binding.ageInput.text.toString().all { char -> char.isDigit() }) {
                Snackbar.make(binding.profileLayout, "Invalid value for age", Snackbar.LENGTH_LONG).show()
            }
            else if (binding.heightInput.text.toString()[0] == '-' || binding.heightInput.text.toString().toDoubleOrNull() == null) {
                Snackbar.make(binding.profileLayout, "Invalid value for height", Snackbar.LENGTH_LONG).show()
            }
            else if (binding.weightInput.text.toString()[0] == '-' || binding.weightInput.text.toString().toDoubleOrNull() == null) {
                Snackbar.make(binding.profileLayout, "Invalid value for weight", Snackbar.LENGTH_LONG).show()
            }
            else {
                calculateBMI()
                calculateRecommendedCalories()
                viewModel.setProfile(user)
            }
        }
        //Populates all the dropdowns
        val heightUnitAdapter = ArrayAdapter.createFromResource(this.requireActivity().applicationContext, R.array.height, android.R.layout.simple_spinner_item)
        heightUnitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.heightUnit.adapter = heightUnitAdapter
        val weightUnitAdapter = ArrayAdapter.createFromResource(this.requireActivity().applicationContext, R.array.weight, android.R.layout.simple_spinner_item)
        weightUnitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.weightUnit.adapter = weightUnitAdapter
        val activityLevelAdapter = ArrayAdapter.createFromResource(this.requireActivity().applicationContext, R.array.activity, android.R.layout.simple_spinner_item)
        activityLevelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.activityDropdown.adapter = activityLevelAdapter
        //Go to weekly calorie summary
        binding.trend.setOnClickListener {
            toWeeklyCal()
        }
        //Date selection
        binding.calendar.setOnClickListener {
            toDate()
        }
        //Go to daily calorie summary
        binding.diary.setOnClickListener {
            toCalorieSummary()
        }
        //Logs out
        binding.profile.setOnClickListener {
            logOut()
        }
    }
    //Sets the height unit according to the selection
    private fun handleHeight(heightPos: Int) {
        if (heightPos > 0) {
            heightUnit = heightUnits[heightPos]
        }
    }
    //Sets the weight unit according to the selection
    private fun handleWeight(weightPos: Int) {
        if (weightPos > 0) {
            weightUnit = weightUnits[weightPos]
        }
    }
    //Calculates the BMI of that person
    @SuppressLint("SetTextI18n")
    private fun calculateBMI() {
        user.height = binding.heightInput.text.toString().toDouble()
        user.weight = binding.weightInput.text.toString().toDouble()
        //Converts units for consistency
        if (weightUnit == "kg") {
            user.weight = user.weight * 2.205
        }
        if (heightUnit == "cm") {
            user.height = user.height / 2.54
        }
        //Sets the BMI
        user.bmi = user.weight / user.height / user.height * 703.0
        binding.BMIValue.text = "BMI: ${(user.bmi * 10).roundToInt() / 10.0}"
        //Set the status
        val status: String
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
        //Sets the progress
        if (user.bmi > 15 && user.bmi < 40) {
            binding.progressBar.setProgress(user.bmi.roundToInt(), true)
        } else if (user.bmi > 40) {
            binding.progressBar.setProgress(40, true)
        } else {
            binding.progressBar.setProgress(15, true)
        }
        //Setss ideal weight
        binding.BMIStatus.text = "Status: $status"
        binding.idealWeightText.text = "${(18.5 * user.height  * user.height / 703.0).roundToInt()} - ${(25 * user.height * user.height / 703.0).roundToInt()} lb (${(18.5 * user.height  * user.height / 703.0 * 0.454).roundToInt()} - ${(25 * user.height * user.height / 703.0 * 0.454).roundToInt()} kg)"
    }
    //Sets the activity level according to the selection
    private fun handleActivityLevel(activityPos: Int) {
        if (activityPos > 0) {
            user.activityLevel = activityLevels[activityPos]
        }
    }
    //To the meal page
    private fun toMeal() {
        this.requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment, MealSelectionFragment.newInstance()).addToBackStack("mealSelectionFragment").commit()
    }
    //To the weekly calorie graph page
    private fun toWeeklyCal() {
        this.requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment, WeeklyCalFragment.newInstance()).addToBackStack("weeklyCalFragment").commit()
    }
    //To the date selection
    private fun toDate() {
        this.requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment, DatePickerFragment.newInstance()).addToBackStack("datePickerFragment").commit()
    }
    //Calculates the number of calories recommended for that person
    @SuppressLint("SetTextI18n")
    private fun calculateRecommendedCalories() {
        var bmr = 0.0
        user.age = binding.ageInput.text.toString().toInt()
        //Calculates BMR
        when (user.sex) {
            "Female" -> {
                bmr = 655.1 + 9.563 * user.weight / 2.205 + 1.85 * user.height * 2.54 - 4.676 * user.age
            }
            "Male" -> {
                bmr = 66.47 + 13.75 * user.weight / 2.205 + 5.003 * user.height * 2.54 - 6.755 * user.age
            }
        }
        //Calculates calories recommended based on activity level
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
        //Set recommendation
        binding.recommendedCal.text = user.recommendedCal.toString() + " Cal"
    }
    override fun onDestroyView() {
        Log.i(javaClass.simpleName, "onDestroyView")
        super.onDestroyView()
    }
    //To the calorie summary page
    private fun toCalorieSummary() {
        this.requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment, CalorieSummaryFragment.newInstance()).addToBackStack("calorieSummaryFragment").commit()
    }
    //Logs out of page
    private fun logOut() {
        viewModel.signOut()
        Snackbar.make(binding.profileLayout, "Successfully logged out", Snackbar.LENGTH_LONG).show()
        if (MainActivity.isConnected) {
            AuthInit(viewModel, signInLauncher)
        }
        this.requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment, BlankFragment.newInstance()).addToBackStack("blankFragment").commit()
    }
    companion object {
        @JvmStatic
        fun newInstance(): ProfileFragment {
            return ProfileFragment()
        }
    }
}