package com.example.calorietracker

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.example.calorietracker.databinding.ActivityMainBinding
import com.example.calorietracker.firebase.AuthInit

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private var resultLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                Log.d(javaClass.simpleName, "result ok")
            } else {
                Log.w(javaClass.simpleName, "Bad activity return code ${result.resultCode}")
            }
        }
    private val signInLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            viewModel.updateUser()
        } else {
            // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            // ...
            Log.d("MainActivity", "sign in failed $result")
        }
    }
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(javaClass.name, "onCreate")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AuthInit(viewModel, signInLauncher)
        binding.logo.setOnClickListener {
            start()
        }
    }

    private fun start() {
        val intent = Intent(this, Start::class.java)
        resultLauncher.launch(intent)
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