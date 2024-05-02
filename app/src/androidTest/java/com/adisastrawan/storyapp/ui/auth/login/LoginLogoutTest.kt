package com.adisastrawan.storyapp.ui.auth.login

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import com.adisastrawan.storyapp.R
import com.adisastrawan.storyapp.ui.MainActivity
import com.adisastrawan.storyapp.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Rule

@RunWith(AndroidJUnit4::class)
@MediumTest
class LoginLogoutTest{
    @get:Rule
    val activity = ActivityScenarioRule(MainActivity::class.java)


    @Before
    fun setUp(){
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)

    }

    @After
    fun tearDown(){
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }
    @Test
    fun login_Success(){
        val email = "davis1122@gmail.com"
        val password = "12345678"

        onView(withId(R.id.btn_to_login)).apply {
            check(matches(isDisplayed()))
            perform(click())
        }
        onView(withId(R.id.ed_login_email)).apply {
            check(matches(isDisplayed()))
            perform(typeText(email))
        }
        onView(withId(R.id.ed_login_password)).apply {
            check(matches(isDisplayed()))
                perform(typeText(password))
        }
        onView(withId(R.id.btn_login)).apply {
            check(matches(isDisplayed()))
            perform(click())
        }
        onView(withId(R.id.rv_story)).apply {
            check(matches(isDisplayed()))
        }
        onView(withId(R.id.action_logout)).apply {
            check(matches(isDisplayed()))
        }
    }
    @Test
    fun loginWithInvalidPassword_Error(){
        val email = "davis1122@gmail.com"
        val password = "123456789"

        onView(withId(R.id.btn_to_login)).apply {
            check(matches(isDisplayed()))
            perform(click())
        }
        onView(withId(R.id.ed_login_email)).apply {
            check(matches(isDisplayed()))
            perform(typeText(email))
        }
        onView(withId(R.id.ed_login_password)).apply {
            check(matches(isDisplayed()))
            perform(typeText(password))
        }
        onView(withId(R.id.btn_login)).apply {
            check(matches(isDisplayed()))
            perform(click())
        }
        onView(withId(R.id.tv_error)).apply {
            check(matches(isDisplayed()))
            withText("Invalid password")
        }
    }
    @Test
    fun loginWithInvalidEmail_Error(){
        val email = "davis112274682@gmail.com"
        val password = "123456789"

        onView(withId(R.id.btn_to_login)).apply {
            check(matches(isDisplayed()))
            perform(click())
        }
        onView(withId(R.id.ed_login_email)).apply {
            check(matches(isDisplayed()))
            perform(typeText(email))
        }
        onView(withId(R.id.ed_login_password)).apply {
            check(matches(isDisplayed()))
            perform(typeText(password))
        }
        onView(withId(R.id.btn_login)).apply {
            check(matches(isDisplayed()))
            perform(click())
        }
        onView(withId(R.id.tv_error)).apply {
            check(matches(isDisplayed()))
            withText("User not found")
        }
    }
    @Test
    fun logout_Success(){
        val email = "davis1122@gmail.com"
        val password = "12345678"

        onView(withId(R.id.btn_to_login)).apply {
            check(matches(isDisplayed()))
            perform(click())
        }
        onView(withId(R.id.ed_login_email)).apply {
            check(matches(isDisplayed()))
            perform(typeText(email))
        }
        onView(withId(R.id.ed_login_password)).apply {
            check(matches(isDisplayed()))
            perform(typeText(password))
        }
        onView(withId(R.id.btn_login)).apply {
            check(matches(isDisplayed()))
            perform(click())
        }
        onView(withId(R.id.rv_story)).apply {
            check(matches(isDisplayed()))
        }
        onView(withId(R.id.action_logout)).apply {
            check(matches(isDisplayed()))
            perform(click())
        }
        onView(withId(R.id.tv_welcome)).apply {
            check(matches(isDisplayed()))
        }
        onView(withId(R.id.btn_to_login)).apply {
            check(matches(isDisplayed()))
        }
        onView(withId(R.id.btn_to_register)).apply {
            check(matches(isDisplayed()))
        }
    }


}