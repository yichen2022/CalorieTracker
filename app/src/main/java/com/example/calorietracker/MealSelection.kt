package com.example.calorietracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MealSelection : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(javaClass.name, "onCreate")
        setContentView(R.layout.activity_meal_selection)
    }
    override fun onStart() {
        super.onStart()
        Log.i(javaClass.name, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.i(javaClass.name, "onResume")
    }
    override fun onPause() {
        super.onPause()
        Log.i(javaClass.name, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.i(javaClass.name, "onStop")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.i(javaClass.name, "onDestroy")
    }
}