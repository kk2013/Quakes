package com.quakes.tests

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.quakes.MainActivity
import com.quakes.QuakesApplication
import com.quakes.R
import com.quakes.di.DaggerTestApplicationComponent
import com.quakes.quakeslist.QuakeViewHolder
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class QuakesListTest {

    @get:Rule
    var activityScenarioRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setUp() {
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val app = instrumentation.targetContext.applicationContext as QuakesApplication
        mockWebServer = DaggerTestApplicationComponent.factory().create(app).getMockWebserver()

        val scenario = activityScenarioRule.scenario
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun getQuakesSuccess() {
        val quakesResponse =
            InstrumentationRegistry.getInstrumentation().context.assets.open("quakes.json")
                .bufferedReader().use { it.readText() }
        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(quakesResponse))

//        onView(withId(R.id.quakes_recycler_view)).perform(
//            RecyclerViewActions.actionOnItemAtPosition<QuakeViewHolder>(
//                ITEM_BELOW_THE_FOLD,
//                click()
//            )
//        )
        onView(withId(R.id.quakes_recycler_view)).check(RecyclerViewItemCountAssertion(10));

        onView(withId(R.id.title)).check(matches(withText((QUAKE_ID))))
    }

    companion object {
        const val ITEM_BELOW_THE_FOLD = 10
        const val QUAKE_ID = "Earthquake: us10004u1y"
    }
}