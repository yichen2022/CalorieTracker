package com.example.calorietracker.model

import com.google.firebase.firestore.DocumentId
import java.util.Date

class Food {
    var numCalories = 0
    var meal = ""
    var group = ""
    var amount = 0
    var name = ""
    var date: Date? = null
    @DocumentId var firestoreId: String = ""
    override fun equals(other: Any?): Boolean =
        if (other is Food) {
            name == other.name && meal == other.meal && group == other.group
        } else {
            false
        }

    override fun toString(): String {
        return "Food{" +
                "meal: " + meal +
                ", group: " + group +
                ", name: " + name +
                ", firestoreID: " + firestoreId +
                "}"
    }
}