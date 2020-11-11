package tv.superawesome.demoapp

import android.view.View
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.CoreMatchers.startsWith
import org.hamcrest.Matcher
import org.hamcrest.Matchers.hasToString
import org.hamcrest.StringDescription
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import tv.superawesome.demoapp.adapter.AdapterItem
import tv.superawesome.demoapp.util.waitUntil

@RunWith(AndroidJUnit4::class)
class AwesomeAdsActivityTest {

    private val bannerId = "44258"
    private val interstitialId = "44259"
    private val videoId = "44261"

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(AwesomeAdsActivity::class.java)

    private fun clickAdapterItem(position: Int) {
        onData(instanceOf(AdapterItem::class.java))
                .atPosition(position)
                .perform(click())
    }

    private fun tapConfig1() {
        onView(withId(R.id.config1Button)).perform(click())
    }

    private fun tapConfig2() {
        onView(withId(R.id.config2Button)).perform(click())
    }

    private fun waitForBannerToBeDisplayed() {
        onView(withId(R.id.bannerView)).perform(waitUntil(isDisplayed()))
    }

    private fun clickBanner() {
        onView(withId(R.id.bannerView)).perform(click())
    }

    private fun findParentalGateAndTypeAnswer() {
        onView(withTagKey(998877)).perform(typeText("123"), closeSoftKeyboard())
    }

    @Test
    fun test_config1_banner() {
        //val scenario = activityScenarioRule.scenario
        tapConfig1()
        clickAdapterItem(1)
        waitForBannerToBeDisplayed()
        clickBanner()
        findParentalGateAndTypeAnswer()
    }
}