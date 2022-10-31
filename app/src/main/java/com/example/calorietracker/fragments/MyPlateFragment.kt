package com.example.calorietracker.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import com.example.calorietracker.FoodActivity
import com.example.calorietracker.MainViewModel
import com.example.calorietracker.MealSelection
import com.example.calorietracker.R
import com.example.calorietracker.databinding.FragmentMealSelectionBinding
import com.example.calorietracker.databinding.FragmentMyPlateBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MyPlateFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MyPlateFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var _binding: FragmentMyPlateBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()
    private val categories: Array<String> by lazy {
        resources.getStringArray(R.array.categories)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
        binding.myPlate.setOnClickListener {
            val selection = handleCategory()
            if (selection != "Select?") {
                toFoodList(selection)
            }
        }
    }
    private fun toFoodList(selection: String) {
        viewModel.observeFoodGroup().observeForever {
            viewModel.updateFoodGroup(selection)
        }
        val intent = Intent(this.activity, FoodActivity::class.java)
        intent.putExtra("categorySelection", selection)
        startActivity(intent)
    }
    private fun handleCategory(): String {
        val pos = binding.categorySelection.selectedItemPosition
        if (pos != 0) {
            return categories[pos]
        }
        return ""
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MyPlateFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MyPlateFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}