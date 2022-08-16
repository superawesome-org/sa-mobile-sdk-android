package tv.superawesome.sdk.publisher.common.components

import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.junit.Test
import tv.superawesome.sdk.publisher.common.models.*
import tv.superawesome.sdk.publisher.common.testutil.ResourceReader
import kotlin.test.assertEquals

class AdSerializerTest {

	private val json = Json {
		ignoreUnknownKeys = true
	}

	@Test
	fun `test_decoding_successfully`() = runBlocking {

		val ad = json.decodeFromString(
			Ad.serializer(),
			ResourceReader.readResource("mock_ksf_interstitial.json")
		)
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
		// showPadlock is false although it is true in the response as isKSF is true
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
			"\"<html></html>\"",
			creativeDetails.tag
		)
		assertEquals(320, creativeDetails.width)
		assertEquals(480, creativeDetails.height)
		assertEquals(0, creativeDetails.duration)
	}

	@Test
	fun `test_encoding_successfully`() = runBlocking {

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
			showPadlock = false,
			lineItemId = 176805,
			test = false,
			app = 41037,
			device = "phone",
			creative = Creative(
				id = 499595,
				name = "QA_SDK_Interstitial_KSF_Latest_Test",
				format = CreativeFormatType.Tag,
				clickUrl = "undefined",
				referral = null,
				details = CreativeDetail(
					placementFormat = "mobile_display",
					tag = "\"<html></html>\"",
					width = 320,
					height = 480,
					duration = 0
				),
				bumper = false,
				isKSF = true
			)
		)

		val expectedAd = json.decodeFromString(
			Ad.serializer(),
			ResourceReader.readResource("mock_ksf_interstitial.json")
		)

		val encodedAd = json.encodeToString(Ad.serializer(), ad)
		val decodedAd = json.decodeFromString(Ad.serializer(), encodedAd)

		assertEquals(expectedAd, decodedAd)
	}
}