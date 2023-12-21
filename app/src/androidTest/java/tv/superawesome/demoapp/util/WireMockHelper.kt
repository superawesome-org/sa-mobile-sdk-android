package tv.superawesome.demoapp.util

import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder
import com.github.tomakehurst.wiremock.client.WireMock.aResponse
import com.github.tomakehurst.wiremock.client.WireMock.anyRequestedFor
import com.github.tomakehurst.wiremock.client.WireMock.containing
import com.github.tomakehurst.wiremock.client.WireMock.exactly
import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.client.WireMock.matching
import com.github.tomakehurst.wiremock.client.WireMock.stubFor
import com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching
import com.github.tomakehurst.wiremock.client.WireMock.verify
import com.github.tomakehurst.wiremock.http.Fault
import tv.superawesome.demoapp.model.TestData

object WireMockHelper {
    fun stubSuccess(testData: TestData) {
        var path = "/ad/${testData.placementId}"

        if (testData.isMultiData) {
            path = "/ad/${testData.placementId}/${testData.lineItemId}/${testData.creativeId}"
        }

        stubPathForFile(path, testData.fileName, null, true)

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

    fun stubNetworkFailure(testData: TestData) {
        var path = "/ad/${testData.placementId}"

        if (testData.isMultiData) {
            path = "/ad/${testData.placementId}/${testData.lineItemId}/${testData.creativeId}"
        }

        val response = buildResponse(testData.fileName, null, true)

        stubFor(
            get(urlPathMatching(path))
                .willReturn(
                    response
                        .withFault(Fault.MALFORMED_RESPONSE_CHUNK)
            )
        )
    }

    fun stubPoorNetwork(testData: TestData) {
        var path = "/ad/${testData.placementId}"

        if (testData.isMultiData) {
            path = "/ad/${testData.placementId}/${testData.lineItemId}/${testData.creativeId}"
        }

        val response = buildResponse(testData.fileName, null, true)

        stubFor(
            get(urlPathMatching(path))
                .willReturn(
                    response
                        .withStatus(200)
                        .withChunkedDribbleDelay(25, 30000)
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
        stubVPAIDJavaScript()
        stubMockWebsite()
        stubGoogleCalls()
    }

    fun stubFailingVPAIDJavaScript() {
        stubPathForFile("/vpaid/failing_vpaid", "video_vpaid_javascript.js")
    }

    private fun stubVPAIDJavaScript() {
        stubPathForFile("/vpaid/success_vpaid", "video_vpaid_javascript.js")
    }

    private fun stubPlacements() {
        stubPathForFile("/placements.json", "placements.json")
    }

    private fun stubAssets() {
        stubPathForFile("/video/video_yellow.mp4", "video_yellow.mp4")
        stubPathForFile("/interstitial_yellow.png", "images/interstitial_yellow.png")
        stubPathForFile("/banner_yellow.png", "images/banner_yellow.png")
    }

    private fun stubMockWebsite() {
        stubPathForFile("/mock_website", "mock_website.html")
    }

    private fun stubVASTPaths() {
        stubPathForFile(
            "/vast/tag",
            "video_vast_success_tag.xml",
            null,
            true,
        )
        stubPathForFile(
            "/vast/ad_tag",
            "video_vast_success_ad_tag.xml",
            null,
            true,
        )
        stubPathForFile(
            "/vast/tag_no_clickthrough",
            "video_vast_success_tag_no_clickthrough.xml",
            null,
            true,
        )
        stubPathForFile(
            "/vast/video_vast_cpi_success_tag",
            "video_vast_cpi_success_tag.xml",
            null,
            true,
        )
        stubForSuccess("/vast/impression")
        stubForSuccess("/vast/click")
        stubForSuccess("/vast/clickthrough")
    }

    private fun stubGoogleCalls() {
        stubPathForFile("/google/tracking", "generic_success_response.json")
        stubPathForFile("/google/impression", "generic_success_response.json")
        stubPathForFile("/google/error", "generic_success_response.json")
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
        mimeType: String? = null,
        useReadFile: Boolean = false,
    ) {

        val response = buildResponse(filePath, mimeType, useReadFile)

        stubFor(get(urlPathMatching(route)).willReturn(response))
    }

    private fun buildResponse(filePath: String,
                              mimeType: String? = null,
                              useReadFile: Boolean = false): ResponseDefinitionBuilder {

        return aResponse()
            .withStatus(200)
            .apply {
                if (useReadFile) {
                    withBody(FileUtils.readFile(filePath))
                } else {
                    withBody(FileUtils.readBytes(filePath))
                }
            }.apply {
                if (mimeType != null) {
                    withHeader("Content-Type", mimeType)
                }
            }.withHeader("Access-Control-Allow-Origin", "*")
    }
}
