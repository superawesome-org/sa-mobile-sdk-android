package tv.superawesome.demoapp

import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import tv.superawesome.demoapp.adapter.AdapterItem
import tv.superawesome.demoapp.util.waitUntil

@RunWith(AndroidJUnit4::class)
class AwesomeAdsActivityTest {


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