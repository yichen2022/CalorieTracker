package com.example.calorietracker.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import com.example.calorietracker.MainViewModel
import com.example.calorietracker.R
import com.example.calorietracker.databinding.FragmentMyPlateBinding

class MyPlateFragment : Fragment() {
    private var _binding: FragmentMyPlateBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()
    private val categories: Array<String> by lazy {
        resources.getStringArray(R.array.categories)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.i(javaClass.simpleName, "onCreateView")
        // Inflate the layout for this fragment
        _binding = FragmentMyPlateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(javaClass.simpleName, "onViewCreated")
        val categoryAdapter = ArrayAdapter.createFromResource(this.requireActivity().applicationContext, R.array.categories, android.R.layout.simple_spinner_item)
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
        binding.categorySelection.adapter = categoryAdapter
        //Move to food fragment based on selection
        binding.myPlate.setOnClickListener {
            val selection = handleCategory()
            if (selection != "") {
                toFoodList(selection)
            }
        }
        //Go back to meal selection page
        binding.add.setOnClickListener {
            this.requireActivity().supportFragmentManager.popBackStack()
        }
        //Go to weekly calories
        binding.trend.setOnClickListener {
            toWeeklyCal()
        }
        //Go to profile page
        binding.profile.setOnClickListener {
            toProfile()
        }
        //Go to date selection page
        binding.calendar.setOnClickListener {
            toDate()
        }
        //Go to meal summary page
        binding.diary.setOnClickListener {
            toMealSummary()
        }
    }
    private fun toFoodList(selection: String) {
        this.requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment, FoodFragment.newInstance()).addToBackStack("foodFragment").commit()
        viewModel.updateFoodGroup(selection)
    }
    private fun toProfile() {
        this.requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment, ProfileFragment.newInstance()).addToBackStack("profileFragment").commit()
    }
    private fun toDate() {
        this.requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment, DatePickerFragment.newInstance()).addToBackStack("datePickerFragment").commit()
    }
    private fun handleCategory(): String {
        val pos = binding.categorySelection.selectedItemPosition
        if (pos != 0) {
            return categories[pos]
        }
        return ""
    }
    private fun toWeeklyCal() {
        this.requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment, WeeklyCalFragment.newInstance()).addToBackStack("weeklyCalFragment").commit()
    }
    private fun toMealSummary() {
        this.requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment, MealSummaryFragment.newInstance()).addToBackStack("mealSummaryFragment").commit()
    }
    override fun onDestroyView() {
        Log.i(javaClass.simpleName, "onDestroyView")
        super.onDestroyView()
    }
    companion object {
        @JvmStatic
        fun newInstance(): MyPlateFragment {
            return MyPlateFragment()
        }
    }
}