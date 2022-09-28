package tv.superawesome.demoapp.util

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
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
            get(urlPathMatching("/click"))
                .willReturn(
                    aResponse()
                        .withStatus(200)
                        .withBody("")
                )
        )
    }

    fun stubVASTPaths() {
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
    }

    fun stubIntents() {
        Intents.intending(IntentMatchers.hasAction(Intent.ACTION_VIEW)).respondWith(
            Instrumentation.ActivityResult(
                Activity.RESULT_OK,
                Intent()
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

    fun verifyQueryParamContains(urlPath: String, paramKey: String, paramValue: String) {
        verify(anyRequestedFor(urlPathMatching(urlPath)).withQueryParam(paramKey, containing(paramValue)))
    }
}