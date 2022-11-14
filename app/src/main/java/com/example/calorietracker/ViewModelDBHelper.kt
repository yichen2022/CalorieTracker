package com.example.calorietracker

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.calorietracker.model.Food
import com.example.calorietracker.model.Meal
import com.example.calorietracker.model.User
import com.example.calorietracker.model.WeeklyCal
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import java.time.ZoneId
import java.util.Date

class ViewModelDBHelper {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    fun dbFetchSelectedFoods(foodList: MutableLiveData<List<Food>>) {
        db.collection("selectedFoods").get().addOnSuccessListener { result ->
            Log.d(javaClass.simpleName, "Selected foods fetched successfully")
            val list = mutableListOf<Food>()
            result.documents.mapNotNull {
                if (it.toObject(Food::class.java)!!.authorId == FirebaseAuth.getInstance().currentUser!!.uid) {
                    list.add(it.toObject(Food::class.java)!!)
                }
            }
            foodList.postValue(list)
        }.addOnFailureListener {
            Log.d(javaClass.simpleName, "Error fetching selected foods")
        }
    }
    fun dbFetchUser(user: MutableLiveData<User>) {
        db.collection("user").limit(1).get().addOnSuccessListener { result ->
            Log.d(javaClass.simpleName, "Successfully fetched user")
            result.documents.mapNotNull {
                if (it.toObject(User::class.java)!!.authorId == FirebaseAuth.getInstance().currentUser!!.uid) {
                    user.postValue(it.toObject(User::class.java))
                }
            }
        }.addOnFailureListener {
            Log.d(javaClass.simpleName, "Error fetching user")
        }
    }
    fun dbFetchMealByDate(date: Date, mealList: MutableLiveData<List<Meal>>){
        db.collection("allMeals").whereEqualTo("date", date).get().addOnSuccessListener { result ->
            Log.d(javaClass.simpleName, "Meals in the current day fetched successfully")
            val list = mutableListOf<Meal>()
            result.documents.mapNotNull {
                if (it.toObject(Meal::class.java)!!.authorId == FirebaseAuth.getInstance().currentUser!!.uid) {
                    list.add(it.toObject(Meal::class.java)!!)
                }
            }
            mealList.postValue(list)
        }.addOnFailureListener {
            Log.d(javaClass.simpleName, "Error fetching meals")
        }
    }
    fun dbFetchMealByLast7Days(date: Date, mealList: MutableLiveData<List<Meal>>) {
        val temp = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().minusDays(7)
            .atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()
        db.collection("allMeals").whereGreaterThanOrEqualTo("date",
            Date.from(temp)).whereLessThanOrEqualTo("date", date).orderBy("date").get()
            .addOnSuccessListener { result ->
                Log.d(javaClass.simpleName, "Meals in the last 7 days from the current date fetched successfully")
                mealList.postValue(result.documents.mapNotNull {
                    it.toObject(Meal::class.java)
                })
        }.addOnFailureListener {
            Log.d(javaClass.simpleName, "Error fetching meals")
            }
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
            Log.d(javaClass.simpleName, "User successfully set")
            currentUser.postValue(user)
        }.addOnFailureListener {
            Log.d(javaClass.simpleName, "Error setting user")
        }
    }
    fun updateUser(user: User, currentUser: MutableLiveData<User>) {
        db.collection("user").document(user.firestoreId).set(user).addOnSuccessListener {
            Log.d(javaClass.simpleName, "User successfully updated")
            currentUser.postValue(user)
        }.addOnFailureListener {
            Log.d(javaClass.simpleName, "Error updating user")
        }
    }
    fun setWeeklyCal(weeklyCal: WeeklyCal, summary: MutableLiveData<WeeklyCal>) {
        if (weeklyCal.firestoreId == "") {
            weeklyCal.firestoreId = db.collection("weeklyCal").document().id
        }
        db.collection("weeklyCal").document(weeklyCal.firestoreId).set(weeklyCal).addOnSuccessListener {
            Log.d(javaClass.simpleName, "Weekly Calories successfully set")
            summary.postValue(weeklyCal)
        }.addOnFailureListener {
            Log.d(javaClass.simpleName, "Error setting weekly calories")
        }
    }
}