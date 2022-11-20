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
class TestWeeklyCalSummary {
    @Test
    fun testWeeklyCal() {
        val auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) {
            launchActivity<MainActivity>().use {
                Thread.sleep(5000)
                onView(withId(R.id.logo)).perform(click())
                onView(withId(R.id.start)).perform(click())
                onView(withId(R.id.trend)).perform(click())
                Thread.sleep(30000)
                onView(withId(R.id.target)).check(matches(withText("Target: 1983")))
            }
        }
    }

    @Test
    fun testWeeklyCal2() {
        val auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) {
            launchActivity<MainActivity>().use {
                Thread.sleep(5000)
                onView(withId(R.id.logo)).perform(click())
                onView(withId(R.id.calendar)).perform(click())
                onView(withId(R.id.datePicker)).perform(PickerActions.setDate(2022, 11, 10))
                onView(withId(R.id.start)).perform(click())
                onView(withId(R.id.trend)).perform(click())
                Thread.sleep(60000)
                onView(withId(R.id.target)).check(matches(withText("Target: 1983")))
                onView(withId(R.id.day7)).check(matches(withText("Thu 10")))
            }
        }
    }
    @Test
    fun testWeeklyCal3() {
        val auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) {
            launchActivity<MainActivity>().use {
                Thread.sleep(5000)
                onView(withId(R.id.logo)).perform(click())
                onView(withId(R.id.calendar)).perform(click())
                onView(withId(R.id.datePicker)).perform(PickerActions.setDate(2022, 10, 9))
                onView(withId(R.id.start)).perform(click())
                onView(withId(R.id.trend)).perform(click())
                Thread.sleep(60000)
                onView(withId(R.id.target)).check(matches(withText("Target: 1983")))
                onView(withId(R.id.day7)).check(matches(withText("Sun 09")))
            }
        }
    }
}