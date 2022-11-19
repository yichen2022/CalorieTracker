package com.example.calorietracker.fragments

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.calorietracker.databinding.FragmentMealBinding

import com.example.calorietracker.model.Meal

class CalorieAdapter() : ListAdapter<Meal, CalorieAdapter.ViewHolder>(Diff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val rowBinding = FragmentMealBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(rowBinding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.numCal.text = getItem(position).calories.toString() + " Cal"
        holder.binding.meal.text = getItem(position).type
    }

    inner class ViewHolder(val binding: FragmentMealBinding) : RecyclerView.ViewHolder(binding.root)
    class Diff : DiffUtil.ItemCallback<Meal>() {
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.type == newItem.type && oldItem.date == newItem.date
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.type == newItem.type && oldItem.date == newItem.date
        }
    }
}