package com.example.calorietracker.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commitNow
import com.example.calorietracker.R
import com.example.calorietracker.databinding.FragmentStartBinding

/**
 * A simple [Fragment] subclass.
 * Use the [StartFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StartFragment : Fragment() {
    private var _binding: FragmentStartBinding? = null
    private val binding get() = _binding!!

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
        binding.start.setOnClickListener {
            toProfile()
        }
        binding.add.setOnClickListener {
            toMeal()
        }
    }

    private fun toProfile() {
        this.requireActivity().supportFragmentManager.commitNow {
            setReorderingAllowed(true)
            replace(R.id.fragment, ProfileFragment.newInstance())
        }
    }

    private fun toMeal() {
        this.requireActivity().supportFragmentManager.commitNow {
            setReorderingAllowed(true)
            replace(R.id.fragment, MealSelectionFragment.newInstance())
        }
    }

    override fun onDestroyView() {
        Log.i(javaClass.simpleName, "onDestroyView")
        super.onDestroyView()
        _binding = null
    }
    companion object {
        @JvmStatic
        fun newInstance(): StartFragment {
            return StartFragment()
        }
    }
}