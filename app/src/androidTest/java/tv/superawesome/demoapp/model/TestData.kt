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
        val videoDirect = TestData(
            placementId = "87969",
            fileName = "video_direct_success.json",
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

        val videoVpaid = TestData(
            placementId = "89056",
            fileName = "video_vpaid_success.json",
        )
        val videoVpaidMulti = TestData(
            placementId = "84594",
            lineItemId = "213919",
            creativeId = "947123",
            fileName = "video_vpaid_multi_success.json",
        )
        val failingVideoVpaid = TestData(
            placementId = "89056",
            fileName = "video_vpaid_failure.json",
        )
        val videoVpaidPJ = TestData(
            placementId = "90636",
            fileName = "video_vpaid_popjam_success.json",
            additionalPaths = listOf(
                StubFile(
                    route = "/ad.js",
                    filePath = "js/pj_ad.js",
                    mimeType = "text/javascript",
                    useReadFile = true,
                ),
            )
        )
        val videoVpaidGreyBox = TestData(
            placementId = "96690",
            fileName = "video_vpaid_grey_box_success.json",
        )

        val bannerSuccess = TestData(
            placementId = "88001",
            fileName = "banner_success.json",
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

        val interstitialKsf = TestData(
            placementId = "87970",
            fileName = "interstitial_ksf_success.json",
        )
        val interstitialStandard = TestData(
            placementId = "87892",
            fileName = "interstitial_standard_success.json",
        )

        val interstitialStandardMulti = TestData(
            placementId = "82089",
            lineItemId = "176803",
            creativeId = "503038",
            fileName = "interstitial_standard_success.json",
        )
    }
}
