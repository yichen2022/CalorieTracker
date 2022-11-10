package com.example.calorietracker.model

import com.google.firebase.firestore.DocumentId

class User {
    var recommendedCal = 0
    var activityLevel = ""
    var age = 0
    var height = 0.0
    var weight = 0.0
    var bmi = 0.0
    var sex = ""
    var calories = 0
    @DocumentId
    var firestoreId: String = ""
}