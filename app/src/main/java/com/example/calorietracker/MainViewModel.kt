package com.example.calorietracker

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.calorietracker.firebase.FirestoreAuthLiveData
import com.example.calorietracker.model.Food
import com.example.calorietracker.model.Meal
import com.example.calorietracker.model.User
import java.sql.Date

class MainViewModel() : ViewModel() {
    private var currentUser = MutableLiveData<User>()
    private var foodGroup = MutableLiveData<String>()
    private var currentMeal = MutableLiveData<String>()
    private var allFoods = MutableLiveData<List<Food>>()
    private var selectedFoods = MutableLiveData<List<Food>>(listOf())
    private var allMeals = MutableLiveData<List<Meal>>()
    private val dbHelp = ViewModelDBHelper()
    private val currentDate = MutableLiveData<Date>()
    private var firebaseAuthLiveData = FirestoreAuthLiveData()
    fun uploadFoods(foods: List<Food>) {
        for (i in foods.indices) {
            dbHelp.createFood(foods[i], allFoods)
        }
    }
    fun observeDate(): LiveData<Date> {
        return currentDate
    }
    fun setDate(date: Date) {
        currentDate.value = date
    }
    fun setMeals() {
        val breakfast = Meal()
        breakfast.type = "Breakfast"
        breakfast.date = currentDate.value
        val lunch = Meal()
        lunch.type = "Lunch"
        lunch.date = currentDate.value
        val dinner = Meal()
        dinner.type = "Dinner"
        dinner.date = currentDate.value
        val snacks = Meal()
        snacks.type = "Snacks"
        snacks.date = currentDate.value
        if (!allMeals.value!!.contains(breakfast)) {
            dbHelp.createMeal(breakfast, allMeals)
        }
        if (!allMeals.value!!.contains(lunch)) {
            dbHelp.createMeal(lunch, allMeals)
        }
        if (!allMeals.value!!.contains(dinner)) {
            dbHelp.createMeal(dinner, allMeals)
        }
        if (!allMeals.value!!.contains(snacks)) {
            dbHelp.createMeal(snacks, allMeals)
        }
    }
    fun addFoodToMeal(food: Food) {
        when(currentMeal.value.toString()) {
            "Breakfast" -> {
                allMeals.value!![0].foodIds.add(food.firestoreId)
                allMeals.value!![0].calories += food.numCalories * food.amount
                if (foodGroup.value.toString() == "Grains") {
                    allMeals.value!![0].grains += food.numCalories * food.amount
                }
                else if (foodGroup.value.toString() == "Veggies" || foodGroup.value.toString() == "Fruits") {
                    allMeals.value!![0].fruitVeggie += food.numCalories * food.amount
                }
                else if (foodGroup.value.toString() == "Protein") {
                    allMeals.value!![0].meat += food.numCalories * food.amount
                }
                else {
                    allMeals.value!![0].otherCategories += food.numCalories * food.amount
                }
            }
            "Lunch" -> {
                allMeals.value!![1].foodIds.add(food.firestoreId)
                allMeals.value!![1].calories += food.numCalories * food.amount
                if (foodGroup.value.toString() == "Grains") {
                    allMeals.value!![1].grains += food.numCalories * food.amount
                }
                else if (foodGroup.value.toString() == "Veggies" || foodGroup.value.toString() == "Fruits") {
                    allMeals.value!![1].fruitVeggie += food.numCalories * food.amount
                }
                else if (foodGroup.value.toString() == "Protein") {
                    allMeals.value!![1].meat += food.numCalories * food.amount
                }
                else {
                    allMeals.value!![1].otherCategories += food.numCalories * food.amount
                }
            }
            "Dinner" -> {
                allMeals.value!![2].foodIds.add(food.firestoreId)
                allMeals.value!![2].calories += food.numCalories * food.amount
                if (foodGroup.value.toString() == "Grains") {
                    allMeals.value!![2].grains += food.numCalories * food.amount
                }
                else if (foodGroup.value.toString() == "Veggies" || foodGroup.value.toString() == "Fruits") {
                    allMeals.value!![2].fruitVeggie += food.numCalories * food.amount
                }
                else if (foodGroup.value.toString() == "Protein") {
                    allMeals.value!![2].meat += food.numCalories * food.amount
                }
                else {
                    allMeals.value!![2].otherCategories += food.numCalories * food.amount
                }
            }
            "Snacks" -> {
                allMeals.value!![3].foodIds.add(food.firestoreId)
                allMeals.value!![3].calories += food.numCalories * food.amount
                if (foodGroup.value.toString() == "Grains") {
                    allMeals.value!![3].grains += food.numCalories * food.amount
                }
                else if (foodGroup.value.toString() == "Veggies" || foodGroup.value.toString() == "Fruits") {
                    allMeals.value!![3].fruitVeggie += food.numCalories * food.amount
                }
                else if (foodGroup.value.toString() == "Protein") {
                    allMeals.value!![3].meat += food.numCalories * food.amount
                }
                else {
                    allMeals.value!![3].otherCategories += food.numCalories * food.amount
                }
            }
        }
    }
    fun removeFoodFromMeal(food: Food) {
        when(currentMeal.value.toString()) {
            "Breakfast" -> {
                allMeals.value!![0].foodIds.remove(food.firestoreId)
                allMeals.value!![0].calories -= food.numCalories * food.amount
                if (foodGroup.value.toString() == "Grains") {
                    allMeals.value!![0].grains -= food.numCalories * food.amount
                }
                else if (foodGroup.value.toString() == "Veggies" || foodGroup.value.toString() == "Fruits") {
                    allMeals.value!![0].fruitVeggie -= food.numCalories * food.amount
                }
                else if (foodGroup.value.toString() == "Protein") {
                    allMeals.value!![0].meat -= food.numCalories * food.amount
                }
                else {
                    allMeals.value!![0].otherCategories -= food.numCalories * food.amount
                }
            }
            "Lunch" -> {
                allMeals.value!![1].foodIds.remove(food.firestoreId)
                allMeals.value!![1].calories -= food.numCalories * food.amount
                if (foodGroup.value.toString() == "Grains") {
                    allMeals.value!![1].grains -= food.numCalories * food.amount
                }
                else if (foodGroup.value.toString() == "Veggies" || foodGroup.value.toString() == "Fruits") {
                    allMeals.value!![1].fruitVeggie -= food.numCalories * food.amount
                }
                else if (foodGroup.value.toString() == "Protein") {
                    allMeals.value!![1].meat -= food.numCalories * food.amount
                }
                else {
                    allMeals.value!![1].otherCategories -= food.numCalories * food.amount
                }
            }
            "Dinner" -> {
                allMeals.value!![2].foodIds.remove(food.firestoreId)
                allMeals.value!![2].calories -= food.numCalories * food.amount
                if (foodGroup.value.toString() == "Grains") {
                    allMeals.value!![2].grains -= food.numCalories * food.amount
                }
                else if (foodGroup.value.toString() == "Veggies" || foodGroup.value.toString() == "Fruits") {
                    allMeals.value!![2].fruitVeggie -= food.numCalories * food.amount
                }
                else if (foodGroup.value.toString() == "Protein") {
                    allMeals.value!![2].meat -= food.numCalories * food.amount
                }
                else {
                    allMeals.value!![2].otherCategories -= food.numCalories * food.amount
                }
            }
            "Snacks" -> {
                allMeals.value!![3].foodIds.remove(food.firestoreId)
                allMeals.value!![3].calories -= food.numCalories * food.amount
                if (foodGroup.value.toString() == "Grains") {
                    allMeals.value!![3].grains -= food.numCalories * food.amount
                }
                else if (foodGroup.value.toString() == "Veggies" || foodGroup.value.toString() == "Fruits") {
                    allMeals.value!![3].fruitVeggie -= food.numCalories * food.amount
                }
                else if (foodGroup.value.toString() == "Protein") {
                    allMeals.value!![3].meat -= food.numCalories * food.amount
                }
                else {
                    allMeals.value!![3].otherCategories -= food.numCalories * food.amount
                }
            }
        }
    }
    fun observeAllMeals(): LiveData<List<Meal>> {
        return allMeals
    }
    fun observeSelectedFoods(): LiveData<List<Food>> {
        return selectedFoods
    }
    fun setProfile(user: User) {
        currentUser.value = user
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
