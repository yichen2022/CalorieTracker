package com.example.calorietracker

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.calorietracker.model.Food
import com.example.calorietracker.model.Meal
import com.google.firebase.firestore.FirebaseFirestore

class ViewModelDBHelper {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    fun dbFetchSelectedFoods(foodList: MutableLiveData<List<Food>>) {
        db.collection("selectedFoods").get().addOnSuccessListener { result ->
            Log.d(javaClass.simpleName, "Selected foods fetched successfully")
            foodList.postValue(result.documents.mapNotNull {
                it.toObject(Food::class.java)
            })
        }.addOnFailureListener {
            Log.d(javaClass.simpleName, "Error fetching selected foods")
        }
    }
    fun dbFetchMeals(mealList: MutableLiveData<List<Meal>>) {
        db.collection("allMeals").get().addOnSuccessListener { result ->
            Log.d(javaClass.simpleName, "Meals fetched successfully")
            mealList.postValue(result.documents.mapNotNull {
                it.toObject(Meal::class.java)
            })
        }
    }
    fun createMeal(meal: Meal, mealList: MutableLiveData<List<Meal>>) {
//        meal.firestoreId = db.collection("allMeals").document().id
        db.collection("allMeals").add(meal).addOnSuccessListener {
            Log.d(javaClass.simpleName, "Meal successfully uploaded")
            dbFetchMeals(mealList)
        }.addOnFailureListener {
            Log.d(javaClass.simpleName, "Error uploading meal")
        }
    }
    fun addFoodToSelection(food: Food, foodList: MutableLiveData<List<Food>>) {
        food.firestoreId = db.collection("selectedFoods").document().id
        db.collection("selectedFoods").document(food.firestoreId).set(food).addOnSuccessListener {
            Log.d(javaClass.simpleName, "Food successfully added")
            dbFetchSelectedFoods(foodList)
        }.addOnFailureListener {
            Log.d(javaClass.simpleName, "Error adding food")
        }
    }
    fun updateMeal(meal: Meal, mealList: MutableLiveData<List<Meal>>) {
        db.collection("allMeals").document(meal.firestoreId).set(meal).addOnSuccessListener {
            Log.d(javaClass.simpleName, "Meal updated successfully")
            dbFetchMeals(mealList)
        }.addOnFailureListener {
            Log.d(javaClass.simpleName, "Error updating meal")
        }
    }
    fun removeFoodFromSelection(food: Food, foodList: MutableLiveData<List<Food>>) {
        db.collection("selectedFoods").document(food.firestoreId).delete().addOnSuccessListener {
            Log.d(javaClass.simpleName, "Food successfully removed")
            dbFetchSelectedFoods(foodList)
        }.addOnFailureListener {
            Log.d(javaClass.simpleName, "Error removing food")
        }
    }
}