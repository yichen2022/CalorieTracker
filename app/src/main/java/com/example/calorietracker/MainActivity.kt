package com.example.calorietracker

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.calorietracker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(javaClass.name, "onCreate")
        binding = ActivityMainBinding.inflate(layoutInflater)
        val inputStream = assets.open("logo.jpg")
        val bitmap = BitmapFactory.decodeStream(inputStream)
        binding.logo.setImageBitmap(bitmap)
        setContentView(R.layout.activity_main)

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