package tv.superawesome.sdk.publisher.components

import org.junit.Test
import tv.superawesome.sdk.publisher.models.Ad
import tv.superawesome.sdk.publisher.models.AdResponse
import tv.superawesome.sdk.publisher.models.Creative
import tv.superawesome.sdk.publisher.models.CreativeDetail
import tv.superawesome.sdk.publisher.models.CreativeFormatType
import tv.superawesome.sdk.publisher.models.CreativeReferral
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class AdResponseTest {
    @Test
    fun test_standard_shouldShowPadlock_true() {
        val ad = buildAd(showPadlock = true)
        val response = AdResponse(10, ad)

        assertTrue { response.shouldShowPadlock() }
    }

    @Test
    fun test_standard_shouldShowPadlock_false() {
        val ad = buildAd(showPadlock = false)
        val response = AdResponse(10, ad)

        assertFalse { response.shouldShowPadlock() }
    }

    private fun buildAd(showPadlock: Boolean) = Ad(
        campaignType = 123,
        showPadlock = showPadlock,
        lineItemId = 123,
        test = false,
        creative = Creative(
            id = 10,
            format = CreativeFormatType.Video,
            referral = CreativeReferral(),
            details = CreativeDetail(
                placementFormat = "",
                width = 1,
                height = 1,
                duration = 1
            ),
        ),
        random = ""
    )
}
