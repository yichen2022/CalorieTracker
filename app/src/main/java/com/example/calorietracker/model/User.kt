package com.example.calorietracker.model

import com.google.firebase.firestore.DocumentId
import kotlin.math.pow
import kotlin.math.roundToInt

class User {
    var activityLevel = ""
    var age = 0
    var height = 0.0
    var weight = 0.0
    var bmi = 0.0
    var sex = ""
    var authorId = ""
    var idealWeight = ""
    var idealCal = 0
    var bmiStatus = ""
    @DocumentId
    var firestoreId: String = ""
    fun calculateStatus() {
        bmiStatus = if (bmi > 30) {
            "Obese"
        } else if (bmi > 25) {
            "Overweight"
        } else if (bmi > 18.5) {
            "Ideal"
        } else {
            "Underweight"
        }
    }
    fun calculateBMI(age: Int, height: Double, weight: Double, heightUnit: String, weightUnit: String) {
        this.age = age
        this.height = height
        this.weight = weight
        //Conversions
        if (weightUnit == "kg") {
            this.weight *= 2.205
        }
        if (heightUnit == "cm") {
            this.height /= 2.54
        }
        bmi = this.weight / this.height.pow(2) * 703.0
    }
    fun calculateIdealWeight() {
        idealWeight = "${(18.5 * height  * height / 703.0).roundToInt()} - " +
                "${(25 * height * height / 703.0).roundToInt()} lb " +
                "(${(18.5 * height  * height / 703.0 / 2.205).roundToInt()} - " +
                "${(25 * height * height / 703.0 / 2.205).roundToInt()} kg)"
    }
    fun calculateRecommendedCal() {
        var bmr = 0.0
        //Calculates BMR
        when (sex) {
            "Female" -> {
                bmr = 655.1 + 9.563 * weight / 2.205 + 1.85 * height * 2.54 - 4.676 * age
            }
            "Male" -> {
                bmr = 66.47 + 13.75 * weight / 2.205 + 5.003 * height * 2.54 - 6.755 * age
            }
        }
        //Calculates calories recommended based on activity level
        when (activityLevel) {
            "Sedentary" -> {
                idealCal = (1.2 * bmr).roundToInt()
            }
            "Lightly Active" -> {
                idealCal = (1.375 * bmr).roundToInt()
            }
            "Moderately Active" -> {
                idealCal = (1.55 * bmr).roundToInt()
            }
            "Active" -> {
                idealCal = (1.725 * bmr).roundToInt()
            }
            "Very Active" -> {
                idealCal =(1.9 * bmr).roundToInt()
            }
        }
    }
}