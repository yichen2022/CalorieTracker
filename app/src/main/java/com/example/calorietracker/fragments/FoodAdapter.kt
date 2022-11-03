package com.example.calorietracker.fragments

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.calorietracker.MainViewModel
import com.example.calorietracker.databinding.FragmentFoodBinding

import com.example.calorietracker.model.Food

class FoodAdapter(private val viewModel: MainViewModel, private val foodList: List<Food>) : RecyclerView.Adapter<FoodAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FragmentFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = ViewHolder(binding)
        holder.binding.item.setOnClickListener {
            viewModel.addFood(foodList[holder.adapterPosition])
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = foodList[position]
        val binding = holder.binding
        binding.title.text = item.name
        binding.numCal.text = item.numCalories.toString()
    }


    inner class ViewHolder(val binding: FragmentFoodBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int {
        return foodList.size
    }

}