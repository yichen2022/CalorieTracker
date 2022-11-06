package com.example.calorietracker.fragments

import android.os.Bundle
import android.util.JsonReader
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.calorietracker.MainViewModel
import com.example.calorietracker.R
import com.example.calorietracker.databinding.FragmentFoodListBinding
import com.example.calorietracker.model.Food
import java.io.InputStreamReader

/**
 * A fragment representing a list of Items.
 */
class FoodFragment : Fragment() {
    private var _binding: FragmentFoodListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.i(javaClass.simpleName, "onCreateView")
        // Inflate the layout for this fragment
        _binding = FragmentFoodListBinding.inflate(inflater, container, false)
        var adapter = FoodAdapter(viewModel, listOf(), binding)
        val layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        viewModel.observeFoodGroup().observeForever {
            when(viewModel.observeFoodGroup().value!!) {
                "Grains" -> {
                    val jsonReader = JsonReader(InputStreamReader(this.requireActivity().assets.open("grains.json")))
                    val list = readFoodsArray("Grains", jsonReader)
                    adapter = FoodAdapter(viewModel, list, binding)
                }
                "Veggies" -> {
                    val jsonReader = JsonReader(InputStreamReader(this.requireActivity().assets.open("veggies.json")))
                    val list = readFoodsArray("Veggies", jsonReader)
                    adapter = FoodAdapter(viewModel, list, binding)
                }
                "Fruits" -> {
                    val jsonReader = JsonReader(InputStreamReader(this.requireActivity().assets.open("fruits.json")))
                    val list = readFoodsArray("Fruits", jsonReader)
                    adapter = FoodAdapter(viewModel, list, binding)
                }
                "Dairy" -> {
                    val jsonReader = JsonReader(InputStreamReader(this.requireActivity().assets.open("dairy.json")))
                    val list = readFoodsArray("Dairy", jsonReader)
                    adapter = FoodAdapter(viewModel, list, binding)
                }
                "Protein" -> {
                    val jsonReader = JsonReader(InputStreamReader(this.requireActivity().assets.open("protein.json")))
                    val list = readFoodsArray("Protein", jsonReader)
                    adapter = FoodAdapter(viewModel, list, binding)
                }
                "Sugars" -> {
                    val jsonReader = JsonReader(InputStreamReader(this.requireActivity().assets.open("sugars.json")))
                    val list = readFoodsArray("Sugars", jsonReader)
                    adapter = FoodAdapter(viewModel, list, binding)
                }
            }
        }
        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = layoutManager
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(javaClass.simpleName, "onViewCreated")
        binding.diary.setOnClickListener {
            toMealSummary()
        }
        binding.add.setOnClickListener {
            this.requireActivity().supportFragmentManager.popBackStack()
        }
    }
    private fun toMealSummary() {
        this.requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragment, MealSummaryFragment.newInstance()).addToBackStack("mealSummaryFragment").commit()
    }

    private fun readFoodsArray(category: String, reader: JsonReader): List<Food>  {
        val list = mutableListOf<Food>()
        reader.beginArray()
        while (reader.hasNext()) {
            list.add(readFood(category, reader))
        }
        reader.endArray()
        return list
    }
    private fun readFood(category: String, reader: JsonReader): Food {
        val food = Food()
        reader.beginObject()
        while (reader.hasNext()) {
            when (reader.nextName()) {
                "title" -> {
                    food.name = reader.nextString()
                }
                "calories" -> {
                    food.numCalories = reader.nextInt()
                }
                else -> reader.skipValue()
            }
        }
        food.group = category
        viewModel.observeMeal().observeForever {
            viewModel.observeDate().observeForever {
                food.meal = viewModel.observeMeal().value.toString()
                food.date = viewModel.observeDate().value!!
                food.mealId = viewModel.getMeal(food.meal, food.date!!).firestoreId
            }

        }
        reader.endObject()
        return food
    }

    companion object {
        @JvmStatic
        fun newInstance():FoodFragment {
            return FoodFragment()
        }
    }
}