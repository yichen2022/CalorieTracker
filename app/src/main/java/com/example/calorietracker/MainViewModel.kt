package com.example.calorietracker

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.calorietracker.firebase.FirestoreAuthLiveData
import com.example.calorietracker.firebase.Storage
import com.example.calorietracker.model.Food

class MainViewModel() : ViewModel() {
    private var foodGroup = MutableLiveData<String>()
    private var currentMeal = MutableLiveData<String>()
    private var foodSelections = MutableLiveData<MutableList<Food>>()
    private val storage = Storage()
    private var firebaseAuthLiveData = FirestoreAuthLiveData()
    fun updateUser() {
        firebaseAuthLiveData.updateUser()
    }
    fun observeFoodGroup(): LiveData<String> {
        return foodGroup
    }
    fun observeMeal(): LiveData<String> {
        return currentMeal
    }
    fun setMeal(meal: String) {
        currentMeal.value = meal
    }
    fun observeFoodSelections(): LiveData<MutableList<Food>> {
        return foodSelections
    }
    fun addFood(food: Food) {
        foodSelections.value!!.add(food)
    }
    fun removeFood(food: Food) {
        foodSelections.value!!.remove(food)
    }
    fun updateFoodGroup(selection: String) {
        foodGroup.value = selection
    }
}
