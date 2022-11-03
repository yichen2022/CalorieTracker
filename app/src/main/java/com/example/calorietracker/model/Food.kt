package com.example.calorietracker.model

import com.google.firebase.firestore.DocumentId

class Food {
    var numCalories = 0
    var meal = ""
    var group = ""
    var amount = 0
    var name = ""
    var mealId = 0
    @DocumentId
    var firestoreId: String = ""
}