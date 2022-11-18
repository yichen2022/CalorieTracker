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
                onView(withId(R.id.recommendedCal)).check(matches(withText("2025 Cal")))
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
                onView(withId(R.id.recommendedCal)).check(matches(withText("2025 Cal")))
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
                onView(withId(R.id.recommendedCal)).check(matches(withText("2025 Cal")))
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
                onView(withId(R.id.recommendedCal)).check(matches(withText("2025 Cal")))
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
                onView(withId(R.id.recommendedCal)).check(matches(withText("2025 Cal")))
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
                onView(withId(R.id.recommendedCal)).check(matches(withText("2025 Cal")))
                onView(withId(R.id.add)).perform(click())
                onView(withId(R.id.dinner)).perform(click())
                onView(withId(R.id.diary)).perform(click())
                onView(withId(R.id.mealDate)).check(matches(withText("2022-09-04")))
                onView(withId(R.id.mealTitle)).check(matches(withText("Dinner")))
            }
        }
    }
}