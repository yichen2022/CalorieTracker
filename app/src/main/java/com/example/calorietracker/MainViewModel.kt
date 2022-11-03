package com.example.calorietracker

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.calorietracker.firebase.FirestoreAuthLiveData
import com.example.calorietracker.model.Food
import com.example.calorietracker.model.Meal
import com.example.calorietracker.model.User

class MainViewModel() : ViewModel() {
    private var currentUser = MutableLiveData<User>()
    private var foodGroup = MutableLiveData<String>()
    private var currentMeal = MutableLiveData<String>()
    private var allFoods = MutableLiveData<List<Food>>()
    private var selectedFoods = MutableLiveData<List<Food>>()
    private var allMeals = MutableLiveData<List<Meal>>()
    private val dbHelp = ViewModelDBHelper()
    private var firebaseAuthLiveData = FirestoreAuthLiveData()
    fun uploadFoods(foods: List<Food>) {
        for (i in foods.indices) {
            dbHelp.createFood(foods[i], allFoods)
        }
    }
    fun setMeals() {
        val breakfast = Meal()
        breakfast.type = "Breakfast"
        val lunch = Meal()
        lunch.type = "Lunch"
        val dinner = Meal()
        dinner.type = "Dinner"
        val snacks = Meal()
        snacks.type = "Snacks"
        dbHelp.createMeal(breakfast, allMeals)
        dbHelp.createMeal(lunch, allMeals)
        dbHelp.createMeal(dinner, allMeals)
        dbHelp.createMeal(snacks, allMeals)
    }
    fun setProfile(user: User) {

    }
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
    fun addFood(food: Food) {
        dbHelp.addFoodToSelection(food, selectedFoods)
    }
    fun removeFood(food: Food) {
        dbHelp.removeFoodFromSelection(food, selectedFoods)
    }
    fun updateFoodGroup(selection: String) {
        foodGroup.value = selection
    }
}
