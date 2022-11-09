package com.example.calorietracker

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.calorietracker.model.Food
import com.example.calorietracker.model.Meal
import com.example.calorietracker.model.User
import com.google.firebase.firestore.FirebaseFirestore
import java.time.ZoneId
import java.util.Date

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
    fun dbFetchMealByDate(date: Date): MutableList<Meal> {
        val list = mutableListOf<Meal>()
        db.collection("allMeals").whereEqualTo("date", date).get().addOnSuccessListener { result ->
            Log.d(javaClass.simpleName, "Meals in the current day fetched successfully")
            result.documents.mapNotNull {
                list.add(it.toObject(Meal::class.java)!!)
            }
        }.addOnFailureListener {
            Log.d(javaClass.simpleName, "Error fetching meals")
        }
        return list
    }
    fun dbFetchMealByLast7Days(date: Date): MutableList<Meal> {
        val list = mutableListOf<Meal>()
        db.collection("allMeals").whereGreaterThanOrEqualTo("date",
            Date.from(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().minusDays(7)
                .atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())).whereLessThanOrEqualTo("date", date).orderBy("date").get()
            .addOnSuccessListener { result ->
                Log.d(javaClass.simpleName, "Meals in the last 7 days from the current date fetched successfully")
                result.documents.mapNotNull {
                    list.add(it.toObject(Meal::class.java)!!)
                }
        }.addOnFailureListener {
            Log.d(javaClass.simpleName, "Error fetching meals")
            }
        return list
    }
    fun dbFetchMeals(mealList: MutableLiveData<List<Meal>>) {
        db.collection("allMeals").get().addOnSuccessListener { result ->
            Log.d(javaClass.simpleName, "Meals fetched successfully")
            mealList.postValue(result.documents.mapNotNull {
                it.toObject(Meal::class.java)
            })
        }.addOnFailureListener {
            Log.d(javaClass.simpleName, "Error fetching meals")
        }
    }
    fun createMeal(meal: Meal, mealList: MutableLiveData<List<Meal>>) {
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
    fun setUser(user: User, currentUser: MutableLiveData<User>) {
        user.firestoreId = db.collection("user").document().id
        db.collection("user").document(user.firestoreId).set(user).addOnSuccessListener {
            
            currentUser.postValue(user)
        }
    }
}