package com.example.calorietracker

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.example.calorietracker.databinding.ActivityMainBinding
import com.example.calorietracker.firebase.AuthInit
import com.google.firebase.auth.FirebaseAuth
import java.net.URL
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
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
    companion object {
        var testing = false
    }
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(javaClass.name, "onCreate")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Sign out for security purposes because health data is private
        viewModel.signOut()
        //If not in testing mode, authentication starts
        //If in testing mode, the test user gets logged in
        if (!testing) {
            AuthInit(viewModel, signInLauncher)
        }
        else {
            FirebaseAuth.getInstance().signInWithEmailAndPassword("test@example.com", "1234567")
        }
        //Set the date to current date
        viewModel.setDate(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()))
        viewModel.observeSelectedFoods().observeForever {
            viewModel.getSelectedFoods()
        }
        //Sets and fetches the meals
        viewModel.getAllMeals()
        viewModel.observeAllMeals().observeForever {
            viewModel.setMeals()
        }
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
        viewModel.signOut()
        Log.i(javaClass.name, "onStop")
    }
    override fun onDestroy() {
        super.onDestroy()
        viewModel.signOut()
        Log.i(javaClass.name, "onDestroy")
    }
}