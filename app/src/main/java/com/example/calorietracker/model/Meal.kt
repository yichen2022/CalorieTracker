package com.example.calorietracker.model

import com.google.firebase.firestore.DocumentId
import java.sql.Date

class Meal {
    var type = ""
    var foodIds = mutableListOf<String>()
    var calories = 0
    var grains = 0
    var fruitVeggie = 0
    var meat = 0
    var otherCategories = 0
    var date: Date? = null
    @DocumentId
    var firestoreId: String = ""

    override fun equals(other: Any?): Boolean =
        if (other is Meal) {
            date == other.date && type == other.type
        } else {
            false
        }
}