package com.example.calorietracker.model

import com.google.firebase.firestore.DocumentId
import java.util.Date

class Meal {
    var type = ""
    var foodIds = mutableListOf<String>()
    var calories = 0
    var grains = 0
    var fruitVeggie = 0
    var meat = 0
    var dairy = 0
    var otherCategories = 0
    var date: Date? = null
    var index = 0
    @DocumentId
    var firestoreId: String = ""

    override fun equals(other: Any?): Boolean =
        if (other is Meal) {
            type == other.type && date == other.date
        } else {
            false
        }
}