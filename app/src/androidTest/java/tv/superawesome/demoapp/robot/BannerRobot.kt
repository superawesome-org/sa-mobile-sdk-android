package tv.superawesome.demoapp.robot

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import tv.superawesome.demoapp.R

class BannerRobot {
    fun tapOnAd() {
        onView(withId(R.id.bannerView))
            .perform(click())
    }
}

fun bannerRobot(func: BannerRobot.() -> Unit) =
    BannerRobot().apply { func() }