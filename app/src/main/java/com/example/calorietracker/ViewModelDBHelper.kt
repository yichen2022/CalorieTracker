package com.example.calorietracker

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.calorietracker.model.Food
import com.example.calorietracker.model.Meal
import com.example.calorietracker.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestoreException
import java.time.ZoneId
import java.util.Date

class ViewModelDBHelper {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    fun dbFetchSelectedFoods(foodList: MutableLiveData<List<Food>>) {
        db.collection("selectedFoods").get().addOnSuccessListener { result ->
            Log.d(javaClass.simpleName, "Selected foods fetched successfully")
            val list = mutableListOf<Food>()
            result.documents.mapNotNull {
                if (it.toObject(Food::class.java)!!.authorId == FirebaseAuth.getInstance().currentUser?.uid.toString()) {
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
                if (it.toObject(User::class.java)!!.authorId == FirebaseAuth.getInstance().currentUser?.uid.toString()) {
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
                if (it.toObject(Meal::class.java)!!.authorId == FirebaseAuth.getInstance().currentUser?.uid.toString()) {
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
            Log.d(javaClass.simpleName, it.printStackTrace().toString())
        }
    }
    fun createMeal(meal: Meal, mealList: MutableLiveData<List<Meal>>) {
        meal.firestoreId = db.collection("allMeals").document().id
        db.collection("allMeals").document(meal.firestoreId).addSnapshotListener(object : EventListener<DocumentSnapshot> {
            override fun onEvent(value: DocumentSnapshot?, error: FirebaseFirestoreException?) {
                if (error != null) {
                    Log.d(javaClass.simpleName, "Error uploading meal")
                    return
                }
                Log.d(javaClass.simpleName, "Meal successfully uploaded")
                value?.reference?.set(meal)
                dbFetchMeals(mealList)
            }

        })
//        db.collection("allMeals").document(meal.firestoreId).set(meal).addOnSuccessListener {
//            Log.d(javaClass.simpleName, "Meal successfully uploaded")
//            dbFetchMeals(mealList)
//        }.addOnFailureListener {
//            Log.d(javaClass.simpleName, "Error uploading meal")
//        }
    }
    fun addFoodToSelection(food: Food, foodList: MutableLiveData<List<Food>>) {
        food.firestoreId = db.collection("selectedFoods").document().id
        db.collection("selectedFoods").document(food.firestoreId).addSnapshotListener(object : EventListener<DocumentSnapshot> {
            override fun onEvent(value: DocumentSnapshot?, error: FirebaseFirestoreException?) {
                if (error != null) {
                    Log.d(javaClass.simpleName, "Error adding food")
                    return
                }
                Log.d(javaClass.simpleName, "Food successfully added")
                value?.reference?.set(food)
                dbFetchSelectedFoods(foodList)
            }
        })
//        db.collection("selectedFoods").document(food.firestoreId).set(food).addOnSuccessListener {
//            Log.d(javaClass.simpleName, "Food successfully added")
//            dbFetchSelectedFoods(foodList)
//        }.addOnFailureListener {
//            Log.d(javaClass.simpleName, "Error adding food")
//        }
    }
    fun updateMeal(meal: Meal, mealList: MutableLiveData<List<Meal>>) {
        db.collection("allMeals").document(meal.firestoreId).addSnapshotListener(object : EventListener<DocumentSnapshot>{
            override fun onEvent(value: DocumentSnapshot?, error: FirebaseFirestoreException?) {
                if (error != null) {
                    Log.d(javaClass.simpleName, "Error updating meal")
                    return
                }
                Log.d(javaClass.simpleName, "Meal updated successfully")
                value?.reference?.set(meal)
                dbFetchMeals(mealList)
            }

        })
//        db.collection("allMeals").document(meal.firestoreId).set(meal).addOnSuccessListener {
//            Log.d(javaClass.simpleName, "Meal updated successfully")
//            dbFetchMeals(mealList)
//        }.addOnFailureListener {
//            Log.d(javaClass.simpleName, "Error updating meal")
//        }
    }
    fun removeFoodFromSelection(food: Food, foodList: MutableLiveData<List<Food>>) {
        db.collection("selectedFoods").document(food.firestoreId).addSnapshotListener(object : EventListener<DocumentSnapshot> {
            override fun onEvent(value: DocumentSnapshot?, error: FirebaseFirestoreException?) {
                if (error != null) {
                    Log.d(javaClass.simpleName, "Error removing food")
                    return
                }
                Log.d(javaClass.simpleName, "Food successfully removed")
                value?.reference?.delete()
                dbFetchSelectedFoods(foodList)
            }
        })
//        db.collection("selectedFoods").document(food.firestoreId).delete().addOnSuccessListener {
//            Log.d(javaClass.simpleName, "Food successfully removed")
//            dbFetchSelectedFoods(foodList)
//        }.addOnFailureListener {
//            Log.d(javaClass.simpleName, "Error removing food")
//        }
    }
    fun setUser(user: User, currentUser: MutableLiveData<User>) {
        if (user.firestoreId == "") {
            user.firestoreId = db.collection("user").document().id
        }
        db.collection("user").document(user.firestoreId).addSnapshotListener(object : EventListener<DocumentSnapshot> {
            override fun onEvent(value: DocumentSnapshot?, error: FirebaseFirestoreException?) {
                if (error != null) {
                    Log.d(javaClass.simpleName, "Error setting user")
                    return
                }
                Log.d(javaClass.simpleName, "User successfully set")
                value?.reference?.set(user)
                currentUser.postValue(user)
            }

        })
//        db.collection("user").document(user.firestoreId).set(user).addOnSuccessListener {
//            Log.d(javaClass.simpleName, "User successfully set")
//            currentUser.postValue(user)
//        }.addOnFailureListener {
//            Log.d(javaClass.simpleName, "Error setting user")
//        }
    }
    fun updateUser(user: User, currentUser: MutableLiveData<User>) {
        db.collection("user").document(user.firestoreId).addSnapshotListener(object : EventListener<DocumentSnapshot> {
            override fun onEvent(value: DocumentSnapshot?, error: FirebaseFirestoreException?) {
                if (error != null) {
                    Log.d(javaClass.simpleName, "Error updating user")
                    return
                }
                Log.d(javaClass.simpleName, "User successfully updated")
                value?.reference?.set(user)
                currentUser.postValue(user)
            }

        })
//        db.collection("user").document(user.firestoreId).set(user).addOnSuccessListener {
//            Log.d(javaClass.simpleName, "User successfully updated")
//            currentUser.postValue(user)
//        }.addOnFailureListener {
//            Log.d(javaClass.simpleName, "Error updating user")
//        }
    }
}