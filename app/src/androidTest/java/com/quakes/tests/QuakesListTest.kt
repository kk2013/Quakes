package com.quakes.tests

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.quakes.MainActivity
import com.quakes.QuakesApplication
import com.quakes.R
import com.quakes.di.DaggerTestApplicationComponent
import com.schibsted.spain.barista.assertion.BaristaListAssertions.assertDisplayedAtPosition
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

        activityScenarioRule.scenario
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

        //This is a temporary wait solution due to time constraints.
        // With more time a proper waiting solution would be implemented
        Thread.sleep(2000)

        assertDisplayedAtPosition(R.id.quakes_recycler_view, 0, R.id.eqid,  "Earthquake: c0001xgp");
    }
}