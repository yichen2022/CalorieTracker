package com.example.calorietracker.fragments

import android.text.format.DateFormat
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.calorietracker.MainViewModel
import com.example.calorietracker.databinding.FragmentFoodBinding
import com.example.calorietracker.databinding.FragmentFoodListBinding

import com.example.calorietracker.model.Food
import com.google.android.material.snackbar.Snackbar

class FoodAdapter(private val viewModel: MainViewModel, private val foodList: List<Food>, private val binding: FragmentFoodListBinding) : RecyclerView.Adapter<FoodAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FragmentFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = ViewHolder(binding)
        holder.binding.item.setOnClickListener {
            //Fetches all the foods
            viewModel.getSelectedFoods()
            //Check for duplicates
            if (viewModel.observeSelectedFoods().value != null && viewModel.observeSelectedFoods().value!!.contains(foodList[holder.adapterPosition])) {
                val index = viewModel.observeSelectedFoods().value!!.indexOf(foodList[holder.adapterPosition])
                viewModel.removeFoodFromMeal(viewModel.observeSelectedFoods().value!![index])
                viewModel.removeFood(viewModel.observeSelectedFoods().value!![index])
                Snackbar.make(this.binding.foodList, "Successfully removed ${foodList[holder.adapterPosition].name} from ${viewModel.observeMeal().value.toString()} on ${DateFormat.format("yyyy-MM-dd", viewModel.observeDate().value!!)}", Snackbar.LENGTH_LONG).show()
            }
            else {
                //Handles the user input for the amount of foods
                this.binding.amountInput.visibility = View.VISIBLE
                this.binding.amount.visibility = View.VISIBLE
                this.binding.foodAdd.visibility = View.VISIBLE
                this.binding.foodAdd.setOnClickListener {
                    //Reject invalid values for amount
                    if (this.binding.amountInput.text.toString() == "") {
                        Snackbar.make(this.binding.foodList, "Missing value for amount", Snackbar.LENGTH_LONG).show()
                    }
                    else if (!this.binding.amountInput.text.toString().all { char -> char.isDigit() }) {
                        Snackbar.make(this.binding.foodList, "Invalid value for amount", Snackbar.LENGTH_LONG).show()
                    }
                    else {
                        foodList[holder.adapterPosition].amount = this.binding.amountInput.text.toString().toInt()
                        this.binding.amountInput.text.clear()
                        this.binding.amountInput.visibility = View.INVISIBLE
                        this.binding.amount.visibility = View.INVISIBLE
                        this.binding.foodAdd.visibility = View.INVISIBLE
                        viewModel.addFood(foodList[holder.adapterPosition])
                        viewModel.addFoodToMeal(foodList[holder.adapterPosition])
                        Snackbar.make(this.binding.foodList, "Successfully added ${foodList[holder.adapterPosition].name} to ${viewModel.observeMeal().value.toString()} on ${DateFormat.format("yyyy-MM-dd", viewModel.observeDate().value!!)}", Snackbar.LENGTH_LONG).show()
                    }
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