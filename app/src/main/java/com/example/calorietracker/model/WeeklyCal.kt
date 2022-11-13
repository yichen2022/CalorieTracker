package com.example.calorietracker.model

import com.google.firebase.firestore.DocumentId

class WeeklyCal {
    var numCal = 0
    var breakfastPercent = 0
    var breakfastCal = 0
    var lunchCal = 0
    var lunchPercent = 0
    var dinnerCal = 0
    var dinnerPercent = 0
    var otherPercent = 0
    var otherCal = 0
    var target = 0
    var average = 0
    var userId = ""
    @DocumentId
    var firestoreId: String = ""
}