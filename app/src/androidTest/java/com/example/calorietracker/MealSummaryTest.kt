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
import androidx.test.espresso.contrib.PickerActions
import androidx.test.espresso.matcher.ViewMatchers.*
import com.google.firebase.auth.FirebaseAuth
import org.hamcrest.Matchers.*

@RunWith(AndroidJUnit4::class)
class MealSummaryTest {
    @Test
    fun testMealSummary() {
        val auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) {
            launchActivity<MainActivity>().use {
                Thread.sleep(5000)
                onView(withId(R.id.logo)).perform(click())
                onView(withId(R.id.start)).perform(click())
                Thread.sleep(5000)
                onView(withId(R.id.add)).perform(click())
                onView(withId(R.id.breakfast)).perform(click())
                onView(withId(R.id.diary)).perform(click())
                onView(withId(R.id.mealTitle)).check(matches(withText("Breakfast")))
            }
        }
    }
    @Test
    fun testMealSummary2() {
        val auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) {
            launchActivity<MainActivity>().use {
                Thread.sleep(5000)
                onView(withId(R.id.logo)).perform(click())
                onView(withId(R.id.start)).perform(click())
                Thread.sleep(5000)
                onView(withId(R.id.add)).perform(click())
                onView(withId(R.id.lunch)).perform(click())
                onView(withId(R.id.diary)).perform(click())
                onView(withId(R.id.mealTitle)).check(matches(withText("Lunch")))
            }
        }
    }
    @Test
    fun testMealSummary3() {
        val auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) {
            launchActivity<MainActivity>().use {
                Thread.sleep(5000)
                onView(withId(R.id.logo)).perform(click())
                onView(withId(R.id.start)).perform(click())
                Thread.sleep(5000)
                onView(withId(R.id.add)).perform(click())
                onView(withId(R.id.dinner)).perform(click())
                onView(withId(R.id.diary)).perform(click())
                onView(withId(R.id.mealTitle)).check(matches(withText("Dinner")))
            }
        }
    }
    @Test
    fun testMealSummary4() {
        val auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) {
            launchActivity<MainActivity>().use {
                Thread.sleep(5000)
                onView(withId(R.id.logo)).perform(click())
                onView(withId(R.id.start)).perform(click())
                Thread.sleep(5000)
                onView(withId(R.id.add)).perform(click())
                onView(withId(R.id.snacks)).perform(click())
                onView(withId(R.id.diary)).perform(click())
                onView(withId(R.id.mealTitle)).check(matches(withText("Snacks")))
            }
        }
    }
    @Test
    fun testMealSummary5() {
        val auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) {
            launchActivity<MainActivity>().use {
                Thread.sleep(5000)
                onView(withId(R.id.logo)).perform(click())
                onView(withId(R.id.calendar)).perform(click())
                onView(withId(R.id.datePicker)).perform(PickerActions.setDate(2022, 10, 28))
                onView(withId(R.id.start)).perform(click())
                Thread.sleep(5000)
                onView(withId(R.id.add)).perform(click())
                onView(withId(R.id.lunch)).perform(click())
                onView(withId(R.id.diary)).perform(click())
                onView(withId(R.id.mealDate)).check(matches(withText("2022-10-28")))
                onView(withId(R.id.mealTitle)).check(matches(withText("Lunch")))
            }
        }
    }
    @Test
    fun testMealSummary6() {
        val auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) {
            launchActivity<MainActivity>().use {
                Thread.sleep(5000)
                onView(withId(R.id.logo)).perform(click())
                onView(withId(R.id.calendar)).perform(click())
                onView(withId(R.id.datePicker)).perform(PickerActions.setDate(2022, 9, 4))
                onView(withId(R.id.start)).perform(click())
                Thread.sleep(5000)
                onView(withId(R.id.add)).perform(click())
                onView(withId(R.id.dinner)).perform(click())
                onView(withId(R.id.diary)).perform(click())
                onView(withId(R.id.mealDate)).check(matches(withText("2022-09-04")))
                onView(withId(R.id.mealTitle)).check(matches(withText("Dinner")))
            }
        }
    }
}