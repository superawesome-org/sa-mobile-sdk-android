package tv.superawesome.demoapp.model

data class TestData(
    val placementId: String,
    val lineItemId: String? = null,
    val creativeId: String? = null,
    val fileName: String,
    val additionalPaths: List<StubFile> = listOf(),
) {
    val isMultiData: Boolean
        get() = lineItemId != null && creativeId != null

    companion object {

        private val videoVpaidPJStubs = listOf(
            StubFile(
                route = "/webSDK.js",
                filePath = "js/webSDK.js",
                mimeType = "text/javascript",
                useReadFile = true,
            ),
            StubFile(
                route = "/vpaid_vast",
                filePath = "vpaid/pj/pj_vast.xml",
                mimeType = "text/xml",
                useReadFile = true,
            ),
            StubFile(
                route = "/pj_celtra_vast",
                filePath = "vpaid/pj/pj_celtra_vast.xml",
                mimeType = "text/xml",
                useReadFile = true,
            ),
            StubFile(
                route = "/pj_celtra_js",
                filePath = "vpaid/pj/pj_celtra.js",
                mimeType = "text/javascript",
                useReadFile = true,
            ),
            StubFile(
                route = "/pj_celtra_payload",
                filePath = "vpaid/pj/pj_celtra_payload.js",
                mimeType = "text/javascript",
                useReadFile = true,
            ),
        )

        private val videoVpaidYellowBoxStubs = listOf(
            StubFile(
                route = "/webSDK.js",
                filePath = "js/webSDK.js",
                mimeType = "text/javascript",
                useReadFile = true,
            ),
            StubFile(
                route = "/vpaid_vast",
                filePath = "vpaid/yellowBox/yellow_box_vast.xml",
                mimeType = "text/javascript",
                useReadFile = true,
            ),
            StubFile(
                route = "/yellow_box_celtra_vast",
                filePath = "vpaid/yellowBox/yellow_box_celtra_vast.xml",
                mimeType = "text/javascript",
                useReadFile = true,
            ),
            StubFile(
                route = "/yellow_box_celtra.js",
                filePath = "vpaid/yellowBox/yellow_box_celtra.js",
                mimeType = "text/javascript",
                useReadFile = true,
            ),
        )

        private val videoVpaidYellowBoxPayload = listOf(
            StubFile(
                route = "/yellow_box_celtra_payload",
                filePath = "vpaid/yellowBox/yellow_box_celtra_payload.js",
                mimeType = "text/javascript",
                useReadFile = true,
            ),
        )

        private val videoVpaidYellowBoxPayloadBrokenClickThrough = listOf(
            StubFile(
                route = "/yellow_box_celtra_payload",
                filePath = "vpaid/yellowBox/yellow_box_celtra_payload_broken_click_through.js",
                mimeType = "text/javascript",
                useReadFile = true,
            ),
        )

        val videoDirect = TestData(
            placementId = "87969",
            fileName = "video_direct_success.json",
        )
        val videoDirectNoClickthrough = TestData(
            placementId = "87969",
            fileName = "video_direct_success_no_clickthrough.json",
        )
        val videoPadlock = TestData(
            placementId = "87969",
            fileName = "padlock/video_direct_success_padlock_enabled.json",
        )
        val videoVast = TestData(
            placementId = "88406",
            fileName = "video_vast_success.json",
        )
        val videoVastMulti = TestData(
            placementId = "82090",
            lineItemId = "176803",
            creativeId = "499385",
            fileName = "video_vast_success_multi.json",
        )
        val videoVpaidMulti = TestData(
            placementId = "84594",
            lineItemId = "213919",
            creativeId = "947123",
            fileName = "video_vpaid_multi_success.json",
            additionalPaths = videoVpaidYellowBoxStubs + videoVpaidYellowBoxPayload,
        )
        val videoVastNoVastTag = TestData(
            placementId = "88406",
            fileName = "video_vast_no_tag.json",
        )
        val failingVideoVpaid = TestData(
            placementId = "89056",
            fileName = "video_vpaid_failure.json",
        )
        val videoVpaidPJ = TestData(
            placementId = "90636",
            fileName = "video_vpaid_popjam_success.json",
            additionalPaths = videoVpaidPJStubs
        )
        val videoVpaidYellowBox = TestData(
            placementId = "89056",
            fileName = "video_vpaid_yellow_box_success.json",
            additionalPaths = videoVpaidYellowBoxStubs + videoVpaidYellowBoxPayload
        )
        val videoVpaidYellowBoxNoTag = TestData(
            placementId = "89056",
            fileName = "video_vpaid_yellow_box_no_tag.json",
        )
        val videoVpaidYellowBoxNoIsVpaidFlag = TestData(
            placementId = "89056",
            fileName = "video_vpaid_yellow_box_success_no_vpaid_flag.json",
            additionalPaths = videoVpaidYellowBoxStubs + videoVpaidYellowBoxPayload
        )
        val videoVpaidYellowBoxBumper = TestData(
            placementId = "89056",
            fileName = "video_vpaid_yellow_box_success_bumper_enabled.json",
            additionalPaths = videoVpaidYellowBoxStubs + videoVpaidYellowBoxPayload
        )
        val videoVpaidYellowBoxBrokenClickThrough = TestData(
            placementId = "89056",
            fileName = "video_vpaid_yellow_box_success.json",
            additionalPaths = videoVpaidYellowBoxStubs + videoVpaidYellowBoxPayloadBrokenClickThrough
        )
        val bannerSuccess = TestData(
            placementId = "88001",
            fileName = "banner_success.json",
        )
        val bannerSuccessNoClickthrough = TestData(
            placementId = "88001",
            fileName = "banner_success_no_clickthrough.json"
        )
        val bannerMultiSuccess = TestData(
            placementId = "82088",
            lineItemId = "176803",
            creativeId = "499387",
            fileName = "banner_multi_success.json",
        )
        val bannerPadlock = TestData(
            placementId = "88001",
            fileName = "padlock/banner_success_padlock_enabled.json",
        )
        val bannerPadlockHidden = TestData(
            placementId = "88001",
            fileName = "padlock/banner_success_padlock_disabled.json",
        )
        val interstitialStandard = TestData(
            placementId = "87892",
            fileName = "interstitial_standard_success.json",
        )
        val interstitialStandardPadlock = TestData(
            placementId = "87892",
            fileName = "interstitial_standard_padlock_enabled.json",
        )
        val interstitialStandardNoClickthrough = TestData(
            placementId = "87892",
            fileName = "interstitial_standard_success_no_clickthrough.json",
        )
        val interstitialStandardClickthrough = TestData(
            placementId = "87892",
            fileName = "interstitial_standard_success_clickthrough.json",
        )
        val interstitialBrokenClickThrough = TestData(
            placementId = "87892",
            fileName = "interstitial_standard_success_broken_click_through.json",
        )
        val interstitialStandardMulti = TestData(
            placementId = "82089",
            lineItemId = "176803",
            creativeId = "503038",
            fileName = "interstitial_standard_success.json",
        )
        val vastInline = TestData(
            placementId = "88406",
            fileName = "video_vast_inline.json"
        )
        val vastInlineWrapper = TestData(
            placementId = "88406",
            fileName = "video_vast_inline_wrapper.json"
        )
    }
}
