package tv.superawesome.demoapp.model

data class TestData(val placement: String, val fileName: String) {
    companion object {
        val videoDirect = TestData("87969", "video_direct_success.json")
        val videoVast = TestData("88406", "video_vast_success.json")
        val videoVpaid = TestData("89056", "video_vpaid_success.json")
        val videoVpaidPJ = TestData("90636", "video_vpaid_popjam_success.json")
        val videoPadlock = TestData("87969", "padlock/video_direct_success_padlock_enabled.json")
        val bannerSuccess = TestData("88001", "banner_success.json")
        val bannerPadlock = TestData("88001", "padlock/banner_success_padlock_enabled.json")
        val interstitialKsf = TestData("87970", "interstitial_ksf_success.json")
        val interstitialStandard = TestData("87892", "interstitial_standard_success.json")
    }
}
