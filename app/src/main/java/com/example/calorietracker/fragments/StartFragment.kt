package com.example.calorietracker.fragments

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import com.example.calorietracker.MainViewModel
import com.example.calorietracker.R
import com.example.calorietracker.databinding.FragmentStartBinding
import com.example.calorietracker.firebase.AuthInit
import com.google.android.material.snackbar.Snackbar

class StartFragment : Fragment() {
    private var _binding: FragmentStartBinding? = null
    private val binding get() = _binding!!
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
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.i(javaClass.simpleName, "onCreateView")
        // Inflate the layout for this fragment
        _binding = FragmentStartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(javaClass.simpleName, "onViewCreated")
        viewModel.getAllMeals()
        viewModel.observeAllMeals().observe(viewLifecycleOwner) {
            viewModel.setMeals()
        }
        //Start goes to profile
        binding.start.setOnClickListener {
            toProfile()
        }
        //Date selection
        binding.calendar.setOnClickListener {
            toDate()
        }
        binding.profile.setOnClickListener {
            logOut()
        }
        viewModel.fetchUser()
        viewModel.observeUser().observe(viewLifecycleOwner) {
            binding.diary.setOnClickListener {
                if (it != null) {
                    toCalorieSummary()
                }
                else {
                    Snackbar.make(binding.startLayout, "Profile not set", Snackbar.LENGTH_LONG).show()
                }
            }
            binding.add.setOnClickListener {
                if (it != null) {
                    toMeal()
                }
                else {
                    Snackbar.make(binding.startLayout, "Profile not set", Snackbar.LENGTH_LONG).show()
                }
            }
            binding.trend.setOnClickListener {
                if (it != null) {
                    toWeeklyCal()
                }
                else {
                    Snackbar.make(binding.startLayout, "Profile not set", Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }
    //Logs out of page
    private fun logOut() {
        viewModel.signOut()
        Snackbar.make(binding.startLayout, "Successfully logged out", Snackbar.LENGTH_LONG).show()
        AuthInit(viewModel, signInLauncher)
        this.requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment, BlankFragment.newInstance()).addToBackStack("blankFragment").commit()
    }
    private fun toDate() {
        this.requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment, DatePickerFragment.newInstance()).addToBackStack("datePickerFragment").commit()
    }
    private fun toProfile() {
        this.requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment, ProfileFragment.newInstance()).addToBackStack("profileFragment").commit()
    }
    //To the meal page
    private fun toMeal() {
        this.requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment, MealSelectionFragment.newInstance()).addToBackStack("mealSelectionFragment").commit()
    }
    //To the weekly calorie graph page
    private fun toWeeklyCal() {
        this.requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment, WeeklyCalFragment.newInstance()).addToBackStack("weeklyCalFragment").commit()
    }
    //To the calorie summary page
    private fun toCalorieSummary() {
        this.requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment, CalorieSummaryFragment.newInstance()).addToBackStack("calorieSummaryFragment").commit()
    }
    override fun onDestroyView() {
        Log.i(javaClass.simpleName, "onDestroyView")
        super.onDestroyView()
    }
    companion object {
        @JvmStatic
        fun newInstance(): StartFragment {
            return StartFragment()
        }
    }
}