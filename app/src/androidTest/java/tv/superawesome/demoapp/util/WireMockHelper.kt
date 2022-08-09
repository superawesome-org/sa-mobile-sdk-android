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
    }

    fun verifyUrlPathCalled(urlPath: String) {
        verify(anyRequestedFor(urlPathMatching(urlPath)))
    }
}