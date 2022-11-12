package com.example.calorietracker

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.calorietracker.firebase.FirestoreAuthLiveData
import com.example.calorietracker.model.Food
import com.example.calorietracker.model.Meal
import com.example.calorietracker.model.User
import java.util.Date

class MainViewModel() : ViewModel() {
    private var currentUser = MutableLiveData<User>()
    private var foodGroup = MutableLiveData<String>()
    private var currentMeal = MutableLiveData<String>()
    private var selectedFoods = MutableLiveData<List<Food>>()
    private var allMeals = MutableLiveData<List<Meal>>()
    private var selectedMeals = MutableLiveData<List<Meal>>()
    private val dbHelp = ViewModelDBHelper()
    private val currentDate = MutableLiveData<Date>()
    private var firebaseAuthLiveData = FirestoreAuthLiveData()
    private val numCalories = MutableLiveData<Int>()
    fun observeCalories(): LiveData<Int> {
        return numCalories
    }
    fun setCalories(calories: Int) {
        numCalories.value = calories
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
        breakfast.index = 1
        val lunch = Meal()
        lunch.type = "Lunch"
        lunch.date = currentDate.value
        lunch.index = 2
        val dinner = Meal()
        dinner.type = "Dinner"
        dinner.date = currentDate.value
        dinner.index = 3
        val snacks = Meal()
        snacks.type = "Snacks"
        snacks.date = currentDate.value
        snacks.index = 4
        if (allMeals.value == null || !allMeals.value!!.contains(breakfast)) {
            dbHelp.createMeal(breakfast, allMeals)
        }
        if (allMeals.value == null || !allMeals.value!!.contains(lunch)) {
            dbHelp.createMeal(lunch, allMeals)
        }
        if (allMeals.value == null || !allMeals.value!!.contains(dinner)) {
            dbHelp.createMeal(dinner, allMeals)
        }
        if (allMeals.value == null || !allMeals.value!!.contains(snacks)) {
            dbHelp.createMeal(snacks, allMeals)
        }
    }
    fun getMeal(title: String, date: Date): Meal {
        val meal = Meal()
        meal.type = title
        meal.date = date
        return allMeals.value!![allMeals.value!!.indexOf(meal)]
    }
    fun getSelectedFoods() {
        dbHelp.dbFetchSelectedFoods(selectedFoods)
    }
    fun getAllMeals() {
        dbHelp.dbFetchMeals(allMeals)
    }
    fun addFoodToMeal(food: Food) {
        val meal = getMeal(currentMeal.value!!, currentDate.value!!)
        meal.foodIds.add(food.firestoreId)
        when (foodGroup.value.toString()) {
            "Grains" -> {
                meal.grains += food.numCalories * food.amount
            }
            "Veggies" -> {
                meal.fruitVeggie += food.numCalories * food.amount
            }
            "Fruits" -> {
                meal.fruitVeggie += food.numCalories * food.amount
            }
            "Protein" -> {
                meal.meat += food.numCalories * food.amount
            }
            "Dairy" -> {
                meal.dairy += food.numCalories * food.amount
            }
            else -> {
                meal.otherCategories += food.numCalories * food.amount
            }
        }
        meal.calories += food.numCalories * food.amount
        dbHelp.updateMeal(meal, allMeals)
    }
    fun removeFoodFromMeal(food: Food) {
        val meal = getMeal(currentMeal.value!!, currentDate.value!!)
        meal.foodIds.remove(food.firestoreId)
        when (foodGroup.value.toString()) {
            "Grains" -> {
                meal.grains -= food.numCalories * food.amount
            }
            "Veggies" -> {
                meal.fruitVeggie -= food.numCalories * food.amount
            }
            "Fruits" -> {
                meal.fruitVeggie -= food.numCalories * food.amount
            }
            "Protein" -> {
                meal.meat -= food.numCalories * food.amount
            }
            "Dairy" -> {
                meal.dairy -= food.numCalories * food.amount
            }
            else -> {
                meal.otherCategories -= food.numCalories * food.amount
            }
        }
        meal.calories -= food.numCalories * food.amount
        dbHelp.updateMeal(meal, allMeals)
    }
    fun observeAllMeals(): LiveData<List<Meal>> {
        return allMeals
    }
    fun observeSelectedMeals(): LiveData<List<Meal>> {
        return selectedMeals
    }
    fun getMealsByLast7Days(date: Date) {
        dbHelp.dbFetchMealByLast7Days(date, selectedMeals)
    }
    fun getMealsByDate(date: Date) {
        dbHelp.dbFetchMealByDate(date, selectedMeals)
    }
    fun observeSelectedFoods(): LiveData<List<Food>> {
        return selectedFoods
    }
    fun setProfile(user: User) {
        dbHelp.setUser(user, currentUser)
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
    fun fetchUser() {
        dbHelp.dbFetchUser(currentUser)
    }
    fun observeUser(): LiveData<User> {
        return currentUser
    }
    fun addFood(food: Food) {
        dbHelp.addFoodToSelection(food, selectedFoods)
        dbHelp.updateUser(currentUser.value!!, currentUser)
    }
    fun removeFood(food: Food) {
        dbHelp.removeFoodFromSelection(food, selectedFoods)
        dbHelp.updateUser(currentUser.value!!, currentUser)
    }
    fun updateFoodGroup(selection: String) {
        foodGroup.value = selection
    }
}
