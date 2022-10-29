package com.example.calorietracker

import androidx.lifecycle.ViewModel
import com.example.calorietracker.firebase.FirestoreAuthLiveData
import com.example.calorietracker.firebase.Storage

class MainViewModel() : ViewModel() {
    private val storage = Storage()
    private var firebaseAuthLiveData = FirestoreAuthLiveData()
    fun updateUser() {
        firebaseAuthLiveData.updateUser()
    }
}
