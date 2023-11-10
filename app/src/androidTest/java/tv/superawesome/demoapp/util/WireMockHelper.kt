package tv.superawesome.demoapp.util

import com.github.tomakehurst.wiremock.client.WireMock.*
import tv.superawesome.demoapp.model.StubFile
import tv.superawesome.demoapp.model.TestData

object WireMockHelper {
    fun stubSuccess(testData: TestData) {
        var path = "/ad/${testData.placementId}"

        if (testData.isMultiData) {
            path = "/ad/${testData.placementId}/${testData.lineItemId}/${testData.creativeId}"
        }

        stubFor(
            get(urlPathMatching(path))
                .willReturn(
                    aResponse()
                        .withStatus(200)
                        .withBody(FileUtils.readFile(testData.fileName))
                )
        )

        for (stubFile in testData.additionalPaths) {
            stubPathForFile(
                route = stubFile.route,
                filePath = stubFile.filePath,
                mimeType = stubFile.mimeType,
                useReadFile = stubFile.useReadFile,
            )
        }
    }

    fun stubFailure(testData: TestData) {
        var path = "/ad/${testData.placementId}"

        if (testData.isMultiData) {
            path = "/ad/${testData.placementId}/${testData.lineItemId}/${testData.creativeId}"
        }

        stubFor(
            get(urlPathMatching(path))
                .willReturn(
                    aResponse()
                        .withStatus(400)
                        .withBody("")
                )
        )
    }

    fun stubCommonPaths() {
        stubForSuccess("/event")
        stubForSuccess("/video/event")
        stubForSuccess("/event_click")
        stubForSuccess("/impression")
        stubForSuccess("/video/impression")
        stubForSuccess("/click")
        stubForSuccess("/video/click")
        stubForSuccess("/video/tracking")
        stubForSuccess("/video/error")
        stubForSuccess("/safead")
        stubForSuccess("/sdk/performance")

        stubVASTPaths()
        stubAssets()
        stubPlacements()
        stubVPAIDImages()
        stubVPAIDJS()
        stubMockWebsite()
    }

    fun stubFailingVPAIDJavaScript() {
        stubFor(
            get(urlPathMatching("/vpaid/failing_vpaid"))
                .willReturn(
                    aResponse()
                        .withStatus(200)
                        .withBody(FileUtils.readBytes("video_vpaid_failure_javascript.js"))
                )
        )
    }

    fun stubVPAIDJavaScript() {
        stubFor(
            get(urlPathMatching("/vpaid/success_vpaid"))
                .willReturn(
                    aResponse()
                        .withStatus(200)
                        .withBody(FileUtils.readBytes("video_vpaid_javascript.js"))
                )
        )
    }

    private fun stubPlacements() {
        stubFor(
            get(urlPathMatching("/placements.json"))
                .willReturn(
                    aResponse()
                        .withStatus(200)
                        .withBody(FileUtils.readBytes("placements.json"))
                )
        )
    }

    private fun stubAssets() {
        stubFor(
            get(urlPathMatching("/video/video_yellow.mp4"))
                .willReturn(
                    aResponse()
                        .withStatus(200)
                        .withBody(FileUtils.readBytes("video_yellow.mp4"))
                )
        )
    }

    private fun stubMockWebsite() {
        stubFor(
            get(urlPathMatching("/mock_webpage"))
                .willReturn(
                    aResponse()
                        .withStatus(200)
                        .withBody(FileUtils.readBytes("mock_webpage.html"))
                )
        )
    }

    private fun stubVASTPaths() {
        stubFor(
            get(urlPathMatching("/vast/tag"))
                .willReturn(
                    aResponse()
                        .withStatus(200)
                        .withBody(FileUtils.readFile("video_vast_success_tag.xml"))
                )
        )

        stubFor(
            get(urlPathMatching("/vast/ad_tag"))
                .willReturn(
                    aResponse()
                        .withStatus(200)
                        .withBody(FileUtils.readFile("video_vast_success_ad_tag.xml"))
                )
        )

        stubFor(
            get(urlPathMatching("/vast/tag_no_clickthrough"))
                .willReturn(
                    aResponse()
                        .withStatus(200)
                        .withBody(FileUtils.readFile("video_vast_success_tag_no_clickthrough.xml"))
                )
        )

        stubFor(
            get(urlPathMatching("/vast/tag-grey-box"))
                .willReturn(
                    aResponse()
                        .withStatus(200)
                        .withBody(FileUtils.readFile("video_vpaid_grey_box_vast_tag.xml"))
                )
        )

        stubForSuccess("/vast/impression")
        stubForSuccess("/vast/click")
        stubForSuccess("/vast/clickthrough")
    }

    private fun stubVPAIDImages() {
        stubPathForFile(
            "/images/play_red.png",
            "images/play_red.png",
            "image/png",
        )
        stubPathForFile(
            "/images/sa_close.png",
            "images/sa_close.png",
            "image/png",
        )
        stubPathForFile(
            "/images/sa_cronograph.png",
            "images/sa_cronograph.png",
            "image/png",
        )
        stubPathForFile(
            "/images/sa_mark.png",
            "images/sa_mark.png",
            "image/png",
        )
        stubPathForFile(
            "/images/sa_muted.png",
            "images/sa_muted.png",
            "image/png",
        )
        stubPathForFile(
            "/images/sa_not_muted.png",
            "images/sa_not_muted.png",
            "image/png",
        )
        stubPathForFile(
            "/images/watermark_2.png",
            "images/watermark_2.png",
            "image/png",
        )
    }

    private fun stubVPAIDJS() {
        stubPathForFile(
            "/moat/moatvideo.js",
            "js/moatvideo.js",
            "text/javascript",
        )
        stubPathForFile(
            "/videojs/inlinevideo.js",
            "js/inlinevideo.js",
            "text/javascript",
        )
        stubPathForFile(
            "/videojs/es5-shim.js",
            "js/es5-shim.js",
            "text/javascript",
        )
        stubPathForFile(
            "/videojs/video-js.min.css",
            "js/video-js.min.css",
            "text/css",
        )
        stubPathForFile(
            "/videojs/video.min.js",
            "js/video.min.js",
            "text/javascript",
        )
        stubPathForFile(
            "/videojs/vpaid/videojs_5.vast.vpaid.min.js",
            "js/videojs_5.vast.vpaid.min.js",
            "text/javascript",
        )
        stubPathForFile(
            "/videojs/vpaid/videojs.vast.vpaid.min.css",
            "js/videojs.vast.vpaid.min.css",
            "text/css",
        )
        stubPathForFile(
            "/videojs/vpaid/VPAIDFlash.swf",
            "js/VPAIDFlash.swf",
            "text/x-shockwave-flash",
        )
    }

    fun verifyUrlPathCalled(urlPath: String) {
        verify(anyRequestedFor(urlPathMatching(urlPath)))
    }

    fun verifyUrlPathNotCalled(urlPath: String) {
        verify(exactly(0), anyRequestedFor(urlPathMatching(urlPath)))
    }

    fun verifyUrlPathCalledWithQueryParam(
        urlPath: String,
        queryParamKey: String,
        queryParamValueRegex: String
    ) {
        verify(
            anyRequestedFor(urlPathMatching(urlPath))
                .withQueryParam(queryParamKey, matching(queryParamValueRegex))
        )
    }

    fun verifyQueryParamContains(urlPath: String, paramKey: String, paramValue: String) {
        verify(
            anyRequestedFor(urlPathMatching(urlPath))
                .withQueryParam(paramKey, containing(paramValue))
        )
    }

    private fun stubForSuccess(path: String) {
        stubFor(
            get(urlPathMatching(path))
                .willReturn(
                    aResponse()
                        .withStatus(200)
                        .withBody("")
                )
        )
    }

    private fun stubPathForFile(
        route: String,
        filePath: String,
        mimeType: String?,
        useReadFile: Boolean = false,
    ) {

        var response = aResponse()
            .withStatus(200)
        if (useReadFile) {
            response = response.withBody(FileUtils.readFile(filePath))
        } else {
            response = response.withBody(FileUtils.readBytes(filePath))
        }

        mimeType?.let { response = response.withHeader("Content-Type", mimeType) }

        stubFor(get(urlPathMatching(route)).willReturn(response))
    }
}
