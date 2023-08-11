package tv.superawesome.sdk.publisher.common.openmeasurement

import android.text.TextUtils
import android.webkit.WebView
import com.iab.omid.library.superawesome.adsession.AdSession
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.spyk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import tv.superawesome.sdk.publisher.common.components.SdkInfoType
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class OpenMeasurementAdSessionFactoryTests {

    private val versionName = "2.0.0"
    private val omidActivator = spyk<OmidActivatorType>()
    private val contextBuilder = spyk<OpenMeasurementContextFactoryType>()
    private val sdkInfo = mockk<SdkInfoType>().apply {
        every { versionNumber } returns versionName
    }
    private val id = "mock-session-id"
    private val webView = mockk<WebView>(relaxed = true)
    private val mockSession = mockk<AdSession>(relaxed = true).apply {
        every { adSessionId } returns id
    }

    private val sessionBuilder = OpenMeasurementAdSessionFactory(
        omidActivator = omidActivator,
        sdkInfo = sdkInfo,
        contextBuilder = contextBuilder,
    )

    @Before
    fun setup() {
        mockkStatic(TextUtils::class)
        every { TextUtils.isEmpty(any()) } answers { arg<String?>(0).isNullOrEmpty() }

        mockkStatic(AdSession::class)
        every { AdSession.createAdSession(any(), any()) } returns mockSession
    }

    @Test
    fun `getHtmlAdSession successfully returns an adSession`() {
        // given
        val customReferenceData: String? = null

        // when
        val result = sessionBuilder.getHtmlAdSession(
            webView = webView,
            customReferenceData = customReferenceData,
        )

        // then
        verify { omidActivator.activate() }
        confirmVerified(omidActivator)
        assertNotNull(result)
        assertEquals(id, mockSession.adSessionId)
    }

    @Test
    fun `getHtmlAdSession successfully returns an adSession when custom reference string is not null`() {
        // given
        val customReferenceData = "Some data"

        // when
        val result = sessionBuilder.getHtmlAdSession(
            webView = webView,
            customReferenceData = customReferenceData,
        )

        // then
        verify { omidActivator.activate() }
        confirmVerified(omidActivator)

        assertNotNull(result)
        assertEquals(id, mockSession.adSessionId)
    }
}
