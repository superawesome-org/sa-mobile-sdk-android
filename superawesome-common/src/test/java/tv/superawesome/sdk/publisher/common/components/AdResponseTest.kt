package tv.superawesome.sdk.publisher.common.components

import org.junit.Test
import tv.superawesome.sdk.publisher.common.models.*
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class AdResponseTest {
    @Test
    fun test_standard_shouldShowPadlock_true() {
        val ad = buildAd(showPadlock = true, isKSF = false)
        val response = AdResponse(10, ad)

        assertTrue { response.shouldShowPadlock() }
    }

    @Test
    fun test_standard_shouldShowPadlock_false() {
        val ad = buildAd(showPadlock = false, isKSF = false)
        val response = AdResponse(10, ad)

        assertFalse { response.shouldShowPadlock() }
    }

    @Test
    fun test_ksf_shouldShowPadlock_showPadlock_true() {
        val ad = buildAd(showPadlock = true, isKSF = true)
        val response = AdResponse(10, ad)

        assertFalse { response.shouldShowPadlock() }
    }

    @Test
    fun test_ksf_shouldShowPadlock_showPadlock_false() {
        val ad = buildAd(showPadlock = false, isKSF = true)
        val response = AdResponse(10, ad)

        assertFalse { response.shouldShowPadlock() }
    }

    private fun buildAd(showPadlock: Boolean, isKSF: Boolean = false) = Ad(
        publisherId = 0,
        campaignId = 0,
        moat = 0.0,
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
            isKSF = isKSF
        ),
        random = ""
    )
}
