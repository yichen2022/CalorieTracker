package com.example.calorietracker.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.fragment.app.activityViewModels
import com.example.calorietracker.MainViewModel
import com.example.calorietracker.databinding.FragmentDatePickerBinding
import java.text.SimpleDateFormat
import java.util.Date

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(javaClass.simpleName, "onViewCreated")
        binding.datePicker.setOnDateChangedListener(object : DatePicker.OnDateChangedListener {
            override fun onDateChanged(view: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int
            ) {
                var dateString = "$year-"
                val month = monthOfYear + 1
                if (month < 10) {
                    dateString += "0$month"
                } else {
                    dateString += month.toString()
                }
                dateString += "-"
                if (dayOfMonth < 10) {
                    dateString += "0$dayOfMonth"
                } else {
                    dateString += dayOfMonth.toString()
                }
                val formatter = SimpleDateFormat("yyyy-MM-dd")
                viewModel.setDate(formatter.parse(dateString))
                viewModel.setMeals()
                requireActivity().supportFragmentManager.popBackStack()
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance(): DatePickerFragment {
            return DatePickerFragment()
        }
    }
}