package tv.superawesome.sdk.publisher.common.components

import org.junit.Test
import tv.superawesome.sdk.publisher.common.models.*
import kotlin.test.assertTrue
import kotlin.test.assertFalse

class AdResponseTest {

    @Test
    fun test_standard_shouldShowPadlock_true() {

        var ad = buildAd(showPadlock = true, isKSF = false)

        val response = AdResponse(10, ad)

        assertTrue { response.shouldShowPadlock() }
    }

    @Test
    fun test_standard_shouldShowPadlock_false() {

        var ad = buildAd(showPadlock = false, isKSF = false)

        val response = AdResponse(10, ad)

        assertFalse { response.shouldShowPadlock() }
    }

    @Test
    fun test_ksf_shouldShowPadlock_showPadlock_true() {

        var ad = buildAd(showPadlock = true, isKSF = true)

        val response = AdResponse(10, ad)

        assertFalse { response.shouldShowPadlock() }
    }

    @Test
    fun test_ksf_shouldShowPadlock_showPadlock_false() {

        var ad = buildAd(showPadlock = false, isKSF = true)

        val response = AdResponse(10, ad)

        assertFalse { response.shouldShowPadlock() }
    }

    private fun buildAd(showPadlock: Boolean, isKSF: Boolean = false) = Ad(
        advertiserId = null,
        publisherId = 123,
        isFill = false,
        isFallback = false,
        campaignType = 123,
        campaignId = 123,
        isHouse = false,
        safeAdApproved = false,
        showPadlock = showPadlock,
        lineItemId = 123,
        test = false,
        app = 123,
        device = "android",
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
        )
    )
}
