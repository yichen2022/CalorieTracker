package com.example.calorietracker.fragments

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.calorietracker.MainViewModel
import com.example.calorietracker.databinding.FragmentFoodBinding
import com.example.calorietracker.databinding.FragmentFoodListBinding

import com.example.calorietracker.model.Food

class FoodAdapter(private val viewModel: MainViewModel, private val foodList: List<Food>, private val binding: FragmentFoodListBinding) : RecyclerView.Adapter<FoodAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FragmentFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = ViewHolder(binding)
        holder.binding.item.setOnClickListener {
            viewModel.observeSelectedFoods().observeForever {
                viewModel.getSelectedFoods()
            }
            Log.i(javaClass.simpleName, viewModel.observeSelectedFoods().value.toString())
            if (viewModel.observeSelectedFoods().value != null && viewModel.observeSelectedFoods().value!!.contains(foodList[holder.adapterPosition])) {
                viewModel.removeFoodFromMeal(foodList[holder.adapterPosition])
                viewModel.removeFood(foodList[holder.adapterPosition])
            }
            else {
                this.binding.amountInput.visibility = View.VISIBLE
                this.binding.amount.visibility = View.VISIBLE
                this.binding.foodAdd.visibility = View.VISIBLE
                this.binding.foodAdd.setOnClickListener {
                    foodList[holder.adapterPosition].amount = this.binding.amountInput.text.toString().toInt()
                    this.binding.amountInput.visibility = View.INVISIBLE
                    this.binding.amount.visibility = View.INVISIBLE
                    this.binding.foodAdd.visibility = View.INVISIBLE
                    viewModel.addFood(foodList[holder.adapterPosition])
                    viewModel.addFoodToMeal(foodList[holder.adapterPosition])
                }
            }
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