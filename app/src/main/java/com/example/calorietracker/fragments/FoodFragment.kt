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
    ): View? {
        Log.i(javaClass.simpleName, "onCreateView")
        // Inflate the layout for this fragment
        _binding = FragmentFoodListBinding.inflate(inflater, container, false)
        var adapter = FoodAdapter(listOf())
        val layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        viewModel.observeFoodGroup().observeForever {
            when(viewModel.observeFoodGroup().value!!) {
                "Grains" -> {
                    val jsonReader = JsonReader(InputStreamReader(this.requireActivity().assets.open("grains.json")))
                    adapter = FoodAdapter((readFoodsArray(jsonReader)))
                }
                "Veggies" -> {
                    val jsonReader = JsonReader(InputStreamReader(this.requireActivity().assets.open("veggies.json")))
                    adapter = FoodAdapter((readFoodsArray(jsonReader)))
                }
                "Fruits" -> {
                    val jsonReader = JsonReader(InputStreamReader(this.requireActivity().assets.open("fruits.json")))
                    adapter = FoodAdapter((readFoodsArray(jsonReader)))
                }
                "Dairy" -> {
                    val jsonReader = JsonReader(InputStreamReader(this.requireActivity().assets.open("dairy.json")))
                    adapter = FoodAdapter((readFoodsArray(jsonReader)))
                }
                "Protein" -> {
                    val jsonReader = JsonReader(InputStreamReader(this.requireActivity().assets.open("protein.json")))
                    adapter = FoodAdapter((readFoodsArray(jsonReader)))
                }
                "Sugars" -> {
                    val jsonReader = JsonReader(InputStreamReader(this.requireActivity().assets.open("sugars.json")))
                    adapter = FoodAdapter((readFoodsArray(jsonReader)))
                }
            }
        }
        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = layoutManager
        return binding.root
    }

    private fun readFoodsArray(reader: JsonReader): List<Food>  {
        var list = mutableListOf<Food>()
        reader.beginArray()
        while (reader.hasNext()) {
            list.add(readFood(reader))
        }
        reader.endArray()
        return list
    }
    private fun readFood(reader: JsonReader): Food {
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
        reader.endObject()
        return food
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance():FoodFragment {
            return FoodFragment()
        }
    }
}