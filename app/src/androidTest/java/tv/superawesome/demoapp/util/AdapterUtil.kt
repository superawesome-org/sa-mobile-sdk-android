package tv.superawesome.demoapp.util

import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description
import tv.superawesome.demoapp.adapter.AdapterItem
import tv.superawesome.demoapp.adapter.PlacementItem


object AdapterUtil {
    fun withPlacementId(value: String): BoundedMatcher<Any, AdapterItem> {
        return object : BoundedMatcher<Any, AdapterItem>(AdapterItem::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("has value $value")
            }

            override fun matchesSafely(item: AdapterItem): Boolean =
                (item as? PlacementItem)?.placementId?.toString() == value
        }
    }
}