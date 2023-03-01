package tv.superawesome.sdk.publisher.common.state

import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class CloseButtonStateTest {

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
        val hiddenState = CloseButtonState.fromInt(2)
        val visibleWithDelayState = CloseButtonState.fromInt(0)
        val visibleImmediatelyState = CloseButtonState.fromInt(1)

        // Then
        assertEquals(0, hiddenState.value)
        assertEquals(1, visibleWithDelayState.value)
        assertEquals(2, visibleImmediatelyState.value)
    }
}
