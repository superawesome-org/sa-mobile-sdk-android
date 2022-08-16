package tv.superawesome.sdk.publisher.common.components

import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.junit.Test
import tv.superawesome.sdk.publisher.common.models.*
import tv.superawesome.sdk.publisher.common.testutil.FakeFactory
import tv.superawesome.sdk.publisher.common.testutil.ResourceReader
import kotlin.test.assertEquals

class AdSerializerTest {

	private val json = Json {
		ignoreUnknownKeys = true
		useAlternativeNames = false
	}

	@Test
	fun `test_decoding_successfully`() = runBlocking {

		val jsonFile = ResourceReader.readResource("mock_ksf_interstitial.json")
		val ad = json.decodeFromString(Ad.serializer(), jsonFile)
		val creative = ad.creative
		val creativeDetails = ad.creative.details

		assertEquals(4, ad.advertiserId)
		assertEquals(4, ad.publisherId)
		assertEquals(false, ad.isFill)
		assertEquals(false, ad.isFallback)
		assertEquals(0, ad.campaignType)
		assertEquals(49137, ad.campaignId)
		assertEquals(0.1f, ad.moat)
		assertEquals(true, ad.isHouse)
		assertEquals(true, ad.safeAdApproved)
		assertEquals(false, ad.showPadlock)
		assertEquals(176805, ad.lineItemId)
		assertEquals(false, ad.test)
		assertEquals(41037, ad.app)
		assertEquals("phone", ad.device)

		// Creative
		assertEquals(499595, creative.id)
		assertEquals("QA_SDK_Interstitial_KSF_Latest_Test", creative.name)
		assertEquals(CreativeFormatType.Tag, creative.format)
		assertEquals("undefined", creative.clickUrl)
		assertEquals(false, creative.bumper)
		assertEquals(true, creative.isKSF)

		// Creative Detail
		assertEquals("mobile_display", creativeDetails.placementFormat)
		assertEquals(
			"<html>\n    <header>\n        <meta name='viewport' content='width=device-width'/>\n        <style>html, body, div { margin: 0px; padding: 0px; } html, body { width: 100%; height: 100%; }</style>\n    </header>\n    <body><script type=\"text/javascript\" src=\"https://eu-west-1-ads.superawesome.tv/v2/ad.js?placement=84799&lineItemId=176805&creativeId=499595&sdkVersion=android_8.3.6_admob&rnd=71922ddc-06aa-4f56-9496-4ae0af720b16&bundle=tv.superawesome.demoapp&dauid=580289352&ct=2&lang=en_US&device=phone&pos=7&timestamp=_TIMESTAMP_&skip=1&playbackmethod=5&startdelay=0&instl=1&isProgrammatic=true\"></script></body>\n    </html>",
			creativeDetails.tag
		)
		assertEquals(320, creativeDetails.width)
		assertEquals(480, creativeDetails.height)
		assertEquals(0, creativeDetails.duration)
	}

	@Test
	fun `test_encoding_successfully`() = runBlocking {

		val jsonFile = ResourceReader.readResource("mock_ksf_interstitial.json")

		val ad = Ad(
			advertiserId = 4,
			publisherId = 4,
			moat = 0.1f,
			isFill = false,
			isFallback = false,
			campaignType = 0,
			campaignId = 49137,
			isHouse = true,
			safeAdApproved = true,
			showPadlock = true,
			lineItemId = 176805,
			test = false,
			app = 41037,
			device = "phone",
			creative = Creative(
				id = 499595,
				format = CreativeFormatType.Tag,
				referral = CreativeReferral(),
				details = CreativeDetail(
					url = FakeFactory.exampleUrl,
					video = "",
					placementFormat = "mobile_display",
					width = 320,
					height = 480,
					duration = 0,
					image = FakeFactory.exampleUrl,
					vast = ""
				)
			)
		)

		val encodedAd = json.encodeToString(Ad.serializer(), ad)

		assertEquals(jsonFile, encodedAd)
	}
}