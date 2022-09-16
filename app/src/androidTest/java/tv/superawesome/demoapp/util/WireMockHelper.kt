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
        stubFor(
            get(urlPathMatching("/moat"))
                .willReturn(
                    aResponse()
                        .withStatus(200)
                        .withBody("")
                )
        )
        stubFor(
            get(urlPathMatching("/event"))
                .willReturn(
                    aResponse()
                        .withStatus(200)
                        .withBody("")
                )
        )
        stubFor(
            get(urlPathMatching("/impression"))
                .willReturn(
                    aResponse()
                        .withStatus(200)
                        .withBody("")
                )
        )
        stubFor(
            get(urlPathMatching("/vast/tag"))
                .willReturn(
                    aResponse()
                        .withStatus(200)
                        .withBody(FileUtils.readFile("video_vast_success_tag.xml"))
                )
        )

        stubFor(
            get(urlPathMatching("/vast/impression"))
                .willReturn(
                    aResponse()
                        .withStatus(200)
                        .withBody("")
                )
        )

        stubFor(
            get(urlPathMatching("/vast/click"))
                .willReturn(
                    aResponse()
                        .withStatus(200)
                        .withBody("")
                )
        )
        stubFor(
            get(urlPathMatching("/click"))
                .willReturn(
                    aResponse()
                        .withStatus(200)
                        .withBody("")
                )
        )
    }

    fun verifyUrlPathCalled(urlPath: String) {
        verify(anyRequestedFor(urlPathMatching(urlPath)))
    }

    fun verifyUrlPathCalledWithQueryParam(
        urlPath: String,
        queryParamKey: String,
        queryParamValueRegex: String)
    {
        verify(anyRequestedFor(urlPathMatching(urlPath))
            .withQueryParam(queryParamKey, matching(queryParamValueRegex)))
    }
}