package tv.superawesome.sdk.publisher.state

import kotlinx.coroutines.test.runTest
import org.junit.Test
import tv.superawesome.sdk.publisher.models.CloseButtonState
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class CloseButtonStateTest {

    @Test
    fun test_closeButtonState_isVisible() = runTest {

        // Given
        val hiddenState = CloseButtonState.Hidden
        val visibleImmediatelyState = CloseButtonState.VisibleImmediately
        val visibleWithDelayState = CloseButtonState.VisibleWithDelay

        // Then
        assertFalse { hiddenState.isVisible() }
        assertTrue { visibleImmediatelyState.isVisible() }
        assertTrue { visibleWithDelayState.isVisible() }
    }

    @Test
    fun test_closeButtonState_fromInt() = runTest {
        // Given
        val hiddenState = CloseButtonState.fromInt(2, 1.0)
        val visibleWithDelayState = CloseButtonState.fromInt(0, 1.0)
        val visibleImmediatelyState = CloseButtonState.fromInt(1, 1.0)

        // Then
        assertEquals(CloseButtonState.Hidden.value, hiddenState.value)
        assertEquals(CloseButtonState.VisibleWithDelay.value, visibleWithDelayState.value)
        assertEquals(CloseButtonState.VisibleImmediately.value, visibleImmediatelyState.value)
    }
}
