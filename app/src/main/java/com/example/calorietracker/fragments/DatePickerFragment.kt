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
import com.example.calorietracker.databinding.FragmentDatePickerBinding
import java.text.SimpleDateFormat
import java.util.Calendar

class DatePickerFragment : Fragment() {
    private var _binding: FragmentDatePickerBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.i(javaClass.simpleName, "onCreateView")
        // Inflate the layout for this fragment
        _binding = FragmentDatePickerBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(javaClass.simpleName, "onViewCreated")
        val calendar = Calendar.getInstance()
        binding.datePicker.maxDate = calendar.timeInMillis
        //Chooses a date
        binding.datePicker.setOnDateChangedListener { _, year, monthOfYear, dayOfMonth ->
            //YYYY-MM-DD
            parseDate(year, monthOfYear + 1, dayOfMonth)
            //Set the meals according to the date
            viewModel.setMeals()
            requireActivity().supportFragmentManager.popBackStack()
        }
    }
    @SuppressLint("SimpleDateFormat")
    private fun parseDate(year: Int, month: Int, day: Int) {
        var dateString = "$year-"
        //Leading zeroes added
        dateString += if (month < 10) {
            "0$month"
        } else {
            month.toString()
        }
        dateString += "-"
        dateString += if (day < 10) {
            "0$day"
        } else {
            day.toString()
        }

        val formatter = SimpleDateFormat("yyyy-MM-dd")
        viewModel.setDate(formatter.parse(dateString)!!)
    }
    override fun onDestroyView() {
        Log.i(javaClass.simpleName, "onDestroyView")
        super.onDestroyView()
    }
    companion object {
        @JvmStatic
        fun newInstance(): DatePickerFragment {
            return DatePickerFragment()
        }
    }
}