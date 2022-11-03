package com.example.calorietracker

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.calorietracker.model.Food
import com.example.calorietracker.model.Meal
import com.google.firebase.firestore.FirebaseFirestore

class ViewModelDBHelper {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    fun dbFetchAllFoods(foodList: MutableLiveData<List<Food>>) {
        db.collection("allFoods").get().addOnSuccessListener { result ->
            Log.d(javaClass.simpleName, "All foods fetched successfully")
            foodList.postValue(result.documents.mapNotNull {
                it.toObject(Food::class.java)
            })
        }.addOnFailureListener {
            Log.d(javaClass.simpleName, "Error fetching foods")
        }
    }
    fun dbFetchSelectedFoods(foodList: MutableLiveData<List<Food>>) {
        db.collection("allFoods").get().addOnSuccessListener { result ->
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
    fun createFood(food: Food, foodList: MutableLiveData<List<Food>>) {
        db.collection("allFoods").document("food").set(food).addOnSuccessListener {
            Log.d(javaClass.simpleName, "Food successfully uploaded")
            dbFetchAllFoods(foodList)
        }.addOnFailureListener {
            Log.d(javaClass.simpleName, "Error uploading food")
        }
    }
    fun createMeal(meal: Meal, mealList: MutableLiveData<List<Meal>>) {
        db.collection("allMeals").document("meal").set(meal).addOnSuccessListener {
            Log.d(javaClass.simpleName, "Meal successfully uploaded")
            dbFetchMeals(mealList)
        }.addOnFailureListener {
            Log.d(javaClass.simpleName, "Error uploading meal")
        }
    }
    fun addFoodToSelection(food: Food, foodList: MutableLiveData<List<Food>>) {
        db.collection("selectedFoods").document("foodSelection").set(food).addOnSuccessListener {
            Log.d(javaClass.simpleName, "Food successfully added")
            dbFetchSelectedFoods(foodList)
        }.addOnFailureListener {
            Log.d(javaClass.simpleName, "Error adding food")
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