package tv.superawesome.sdk.publisher.ui.common

import io.mockk.impl.annotations.InjectMockKs
import org.junit.Test
import tv.superawesome.sdk.publisher.common.models.AdRequest

class AdControllerTest {
    @InjectMockKs
    lateinit var controller: AdController

    @Test
    fun test() {
        val placementId = 5
        val request = AdRequest(false, 10, 20, 30, 40, 50, 60, 70)
        controller.load(placementId, request)
    }
}