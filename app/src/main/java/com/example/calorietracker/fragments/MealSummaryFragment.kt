package com.example.calorietracker.fragments

import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.calorietracker.MainViewModel
import com.example.calorietracker.databinding.FragmentMealSummaryBinding


class MealSummaryFragment : Fragment() {
    private var _binding: FragmentMealSummaryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.i(javaClass.simpleName, "onCreateView")
        // Inflate the layout for this fragment
        _binding = FragmentMealSummaryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(javaClass.simpleName, "onViewCreated")
        viewModel.observeMeal().observeForever {
            viewModel.observeDate().observeForever {
                binding.mealDate.text = DateFormat.format("yyyy-MM-dd", viewModel.observeDate().value!!).toString()
                binding.mealTitle.text = viewModel.observeMeal().value.toString()
                viewModel.observeAllMeals().observeForever {
                    val meal = viewModel.getMeal(viewModel.observeMeal().value.toString(), viewModel.observeDate().value!!)
                    binding.grains.text = "Grains: ${(meal.grains * 100/meal.calories)}%"
                    binding.fruitVeggie.text = "Fruit/Vegetables: ${(meal.fruitVeggie * 100/meal.calories)}%"
                    binding.meat.text = "Meat: ${(meal.meat * 100/meal.calories)}%"
                    binding.dairyGroup.text = "Dairy: ${(meal.dairy * 100/meal.calories)}%"
                    binding.other.text = "Other: ${(meal.otherCategories * 100/meal.calories)}%"
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(): MealSummaryFragment {
            return MealSummaryFragment()
        }
    }
}