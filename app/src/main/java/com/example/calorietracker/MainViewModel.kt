package com.example.calorietracker

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.calorietracker.firebase.FirestoreAuthLiveData
import com.example.calorietracker.firebase.Storage

class MainViewModel() : ViewModel() {
    private var foodGroup = MutableLiveData<String>()
    private val storage = Storage()
    private var firebaseAuthLiveData = FirestoreAuthLiveData()
    fun updateUser() {
        firebaseAuthLiveData.updateUser()
    }
    fun observeFoodGroup(): LiveData<String> {
        return foodGroup
    }
    fun updateFoodGroup(selection: String) {
        foodGroup.postValue(selection)
    }
}
