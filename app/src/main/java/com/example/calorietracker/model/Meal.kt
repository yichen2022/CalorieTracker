package com.example.calorietracker.model

import com.google.firebase.firestore.DocumentId

class Meal {
    var type = ""
    var foodIds = ""
    var calories = 0
    var grains = 0
    var fruitVeggie = 0
    var meat = 0
    var otherCategories = 0
    @DocumentId
    var firestoreId: String = ""
}