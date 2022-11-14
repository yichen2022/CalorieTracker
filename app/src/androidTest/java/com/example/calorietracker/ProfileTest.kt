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
import org.hamcrest.Matchers.*

@RunWith(AndroidJUnit4::class)
class ProfileTest {
    @Test
    fun testNavigationToProfile() {
        launchActivity<MainActivity>().use {
            onView(withId(R.id.logo)).perform(click())
            onView(withId(R.id.start)).perform(click())
        }
    }
    @Test
    fun testProfile() {
        launchActivity<MainActivity>().use {
            onView(withId(R.id.logo)).perform(click())
            onView(withId(R.id.start)).perform(click())
            onView(withId(R.id.male)).perform(click())
            onView(withId(R.id.checkmarkM)).check(matches(isDisplayed()))
            onView(withId(R.id.checkmarkF)).check(matches(not(isDisplayed())))
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
            onView(withId(R.id.BMIValue)).check(matches(withText("BMI: 21.2")))
            onView(withId(R.id.BMIStatus)).check(matches(withText("Status: Ideal")))
            onView(withId(R.id.idealWeightText)).check(matches(withText("125 - 169 lb (57 - 77 kg)")))
            onView(withId(R.id.recommendedCal)).check(matches(withText("2680 Cal")))
        }
    }
}