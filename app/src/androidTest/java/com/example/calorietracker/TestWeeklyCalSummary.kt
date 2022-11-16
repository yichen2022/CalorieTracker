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
class TestWeeklyCalSummary {
    @Test
    fun testWeeklyCal() {
        val auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) {
            launchActivity<MainActivity>().use {
                onView(withId(R.id.logo)).perform(click())
                onView(withId(R.id.start)).perform(click())
                onView(withId(R.id.female)).perform(click())
                onView(withId(R.id.checkmarkM)).check(matches(not(isDisplayed())))
                onView(withId(R.id.checkmarkF)).check(matches(isDisplayed()))
                onView(withId(R.id.ageInput)).perform(replaceText("18"))
                onView(withId(R.id.heightInput)).perform(replaceText("68.4"))
                onView(withId(R.id.weightInput)).perform(replaceText("126.7"))
                onView(withId(R.id.heightUnit)).perform(click())
                onData(hasToString(startsWith("in"))).perform(click())
                onView(withId(R.id.heightUnit)).check(matches(withSpinnerText(containsString("in"))))
                onView(withId(R.id.weightUnit)).perform(click())
                onData(hasToString(startsWith("lb"))).perform(click())
                onView(withId(R.id.weightUnit)).check(matches(withSpinnerText(containsString("lb"))))
                onView(withId(R.id.activityDropdown)).perform(click())
                onData(hasToString(startsWith("Lightly Active"))).perform(click())
                onView(withId(R.id.activityDropdown)).check(matches(withSpinnerText(containsString("Lightly Active"))))
                onView(withId(R.id.calculate)).perform(click())
                onView(withId(R.id.BMIValue)).check(matches(withText("BMI: 19.0")))
                onView(withId(R.id.BMIStatus)).check(matches(withText("Status: Ideal")))
                onView(withId(R.id.idealWeightText)).check(matches(withText("123 - 166 lb (56 - 76 kg)")))
                onView(withId(R.id.recommendedCal)).check(matches(withText("1983 Cal")))
                onView(withId(R.id.trend)).perform(click())
                onView(withId(R.id.target)).check(matches(withText("Target: 1983")))
            }
        }
    }
}