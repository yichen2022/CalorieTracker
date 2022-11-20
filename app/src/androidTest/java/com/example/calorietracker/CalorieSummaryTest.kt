package com.example.calorietracker

import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.google.firebase.auth.FirebaseAuth
import org.hamcrest.Matchers.*

@RunWith(AndroidJUnit4::class)
class CalorieSummaryTest {
    @Test
    fun testCalorieSummary() {
        val auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) {
            launchActivity<MainActivity>().use {
                Thread.sleep(5000)
                onView(withId(R.id.logo)).perform(click())
                onView(withId(R.id.start)).perform(click())
                onView(withId(R.id.male)).perform(click())
                onView(withId(R.id.ageInput)).perform(replaceText("22"))
                onView(withId(R.id.heightInput)).perform(replaceText("175"))
                onView(withId(R.id.weightInput)).perform(replaceText("65"))
                onView(withId(R.id.heightUnit)).perform(click())
                onData(hasToString(startsWith("cm"))).perform(click())
                onView(withId(R.id.heightUnit)).check(matches(withSpinnerText(containsString("cm"))))
                onView(withId(R.id.weightUnit)).perform(click())
                onData(hasToString(startsWith("kg"))).perform(click())
                onView(withId(R.id.weightUnit)).check(matches(withSpinnerText(containsString("kg"))))
                onView(withId(R.id.activityDropdown)).perform(click())
                onData(hasToString(startsWith("Sedentary"))).perform(click())
                onView(withId(R.id.activityDropdown)).check(matches(withSpinnerText(containsString("Sedentary"))))
                onView(withId(R.id.calculate)).perform(click())
                onView(withId(R.id.diary)).perform(click())
                Thread.sleep(120000)
                onView(withId(R.id.recommendedCalories)).check(matches(withText("Recommended: 2025")))
            }
        }
    }
}