package tv.superawesome.demoapp.util

import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description
import tv.superawesome.demoapp.model.PlacementItem


object AdapterUtil {
    fun withPlacementId(value: String): BoundedMatcher<Any, PlacementItem> {
        return object : BoundedMatcher<Any, PlacementItem>(PlacementItem::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("has value $value")
            }

            override fun matchesSafely(item: PlacementItem): Boolean =
                (item as? PlacementItem)?.placementId?.toString() == value
        }
    }
}