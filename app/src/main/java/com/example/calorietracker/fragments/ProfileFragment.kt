package com.example.calorietracker.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.commitNow
import com.example.calorietracker.R
import com.example.calorietracker.databinding.FragmentProfileBinding


/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i(javaClass.simpleName, "onCreateView")
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(javaClass.simpleName, "onViewCreated")
        binding.add.setOnClickListener {
            toMeal()
        }
        val heightUnitAdapter = ArrayAdapter.createFromResource(this.requireActivity().applicationContext, R.array.height, android.R.layout.simple_spinner_item)
        heightUnitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.heightUnit.adapter = heightUnitAdapter
        val weightUnitAdapter = ArrayAdapter.createFromResource(this.requireActivity().applicationContext, R.array.weight, android.R.layout.simple_spinner_item)
        weightUnitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.weightUnit.adapter = weightUnitAdapter
        val activityLevelAdapter = ArrayAdapter.createFromResource(this.requireActivity().applicationContext, R.array.activity, android.R.layout.simple_spinner_item)
        activityLevelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.activityDropdown.adapter = activityLevelAdapter
    }
    private fun toMeal() {
        this.requireActivity().supportFragmentManager.commitNow {
            setReorderingAllowed(true)
            replace(R.id.fragment, MealSelectionFragment.newInstance())
        }
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(): ProfileFragment {
            return ProfileFragment()
        }
    }
}