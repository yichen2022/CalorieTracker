package com.example.calorietracker.firebase

import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import com.example.calorietracker.MainViewModel
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

class AuthInit(viewModel: MainViewModel, signInLauncher: ActivityResultLauncher<Intent>) {
    companion object {
        private const val TAG = "AuthInit"
    }

    init {
        val user = FirebaseAuth.getInstance().currentUser
        if (user == null) {
            Log.d(TAG, "user null")
            val providers = arrayListOf(
                AuthUI.IdpConfig.EmailBuilder().build()
            )
            val signInIntent = AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(providers).setIsSmartLockEnabled(false).build()
            signInLauncher.launch(signInIntent)
        } else {
            Log.d(TAG, "user ${user.displayName} email ${user.email}")
            viewModel.updateUser()
            viewModel.observeSelectedFoods().observeForever {
                viewModel.getSelectedFoods()
            }
        }
    }
}