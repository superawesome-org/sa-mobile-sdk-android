package tv.superawesome.demoapp.util

import com.github.tomakehurst.wiremock.client.WireMock.*

object WireMockHelper {
    fun stubSuccess(placement: String, fileName: String) {
        stubFor(
            get(urlPathMatching("/ad/$placement"))
                .willReturn(
                    aResponse()
                        .withStatus(200)
                        .withBody(FileUtils.readFile(fileName))
                )
        )
    }

    fun stubFailure(placement: String) {
        stubFor(
            get(urlPathMatching("/ad/$placement"))
                .willReturn(
                    aResponse()
                        .withStatus(400)
                        .withBody("")
                )
        )
    }

    fun stubCommonPaths() {
        stubForSuccess("/moat")
        stubForSuccess("/event")
        stubForSuccess("/impression")
        stubForSuccess("/click")
        stubForSuccess("/video/tracking")

        stubVASTPaths()
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

        stubForSuccess("/vast/impression")
        stubForSuccess("/vast/click")
        stubForSuccess("/vast/clickthrough")
    }

    fun verifyUrlPathCalled(urlPath: String) {
        verify(anyRequestedFor(urlPathMatching(urlPath)))
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
}