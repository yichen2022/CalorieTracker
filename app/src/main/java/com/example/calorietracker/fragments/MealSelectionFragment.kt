package com.example.calorietracker.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.calorietracker.MainViewModel
import com.example.calorietracker.MyPlate
import com.example.calorietracker.databinding.FragmentMealSelectionBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MealSelectionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MealSelectionFragment : Fragment() {
    private var _binding: FragmentMealSelectionBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i(javaClass.simpleName, "onCreateView")
        // Inflate the layout for this fragment
        _binding = FragmentMealSelectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(javaClass.simpleName, "onViewCreated")
        binding.breakfast.setOnClickListener {
            viewModel.setMeal("Breakfast")
            myPlate()
        }
        binding.lunch.setOnClickListener {
            viewModel.setMeal("Lunch")
            myPlate()
        }
        binding.dinner.setOnClickListener {
            viewModel.setMeal("Dinner")
            myPlate()
        }
        binding.snacks.setOnClickListener {
            viewModel.setMeal("Snacks")
            myPlate()
        }
    }
    private fun myPlate() {
        val intent = Intent(this.activity, MyPlate::class.java)
        startActivity(intent)
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MealSelectionFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MealSelectionFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}