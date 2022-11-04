package com.example.calorietracker.model

import com.google.firebase.firestore.DocumentId

class Food {
    var numCalories = 0
    var meal = ""
    var group = ""
    var amount = 0
    var name = ""
    var mealId = ""
    @DocumentId
    var firestoreId: String = ""
    override fun equals(other: Any?): Boolean =
        if (other is Food) {
            meal == other.meal && group == other.group
        } else {
            false
        }
}