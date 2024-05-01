package com.adisastrawan.storyapp.ui.auth.login

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import com.adisastrawan.storyapp.R
import com.adisastrawan.storyapp.data.api.ApiConfig
import com.adisastrawan.storyapp.ui.MainActivity
import com.adisastrawan.storyapp.utils.EspressoIdlingResource
import com.adisastrawan.storyapp.utils.JsonConverter
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule

@RunWith(AndroidJUnit4::class)
class LoginFragmentTest{
    @get:Rule
    val activity = ActivityScenarioRule(MainActivity::class.java)

//    private val mockWebServer = MockWebServer()
    @Before
    fun setUp(){
//        mockWebServer.start(8080)

        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)

    }

    @After
    fun tearDown(){
//        mockWebServer.shutdown()
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
}