package tv.superawesome.demoapp

import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock.*
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import tv.superawesome.demoapp.util.ViewTester
import tv.superawesome.demoapp.util.waitUntil

@RunWith(AndroidJUnit4::class)
@SmallTest
class WireMockPOCTest {

	private val server = WireMockServer(8080)

	@Before
	fun setup() {
		server.start()
		configureFor("localhost", 8080)
	}

	@Test
	fun pocWireMockTest() {

		/* With WireMock the server configuration and stubbing is done within the test files,
			this has the advantage of being more flexible at the per test level. This is just a POC that
			gets a response loading from the server but there are many things we could do to
			build a scalable and clean setup. Mocks should be loaded from local JSON files for one. WireMock is also
			compatible with iOS so we'd benefit from a familiar API. Again we need to point the app to localhost.
		*/

		stubFor(
			get(urlPathMatching("/ad/87892"))
				.willReturn(
					aResponse()
						.withStatus(200)
						.withBody(
							"{\"advertiserId\":4,\"publisherId\":4,\"creative\":{\"id\":507613,\"version\":1,\"name\":\"QA_SDK_Interstitial_Colour_Test\",\"format\":\"image_with_link\",\"formatId\":12,\"cpms\":{\"default\":\"0\"},\"impressionUrls\":[],\"freqCappingLimit\":0,\"details\":{\"url\":\"https://uploads-api-main-awesomeads.sacdn.net/images/JgXBaIa8XI1jMoYFSDewp0uVShhC6qr5.png\",\"image\":\"https://uploads-api-main-awesomeads.sacdn.net/images/JgXBaIa8XI1jMoYFSDewp0uVShhC6qr5.png\",\"video\":\"https://uploads-api-main-awesomeads.sacdn.net/images/JgXBaIa8XI1jMoYFSDewp0uVShhC6qr5.png\",\"placement_format\":\"mobile_display\",\"width\":320,\"height\":480,\"duration\":0},\"approved\":1,\"live\":true,\"osTarget\":[],\"browserTarget\":[],\"browserTargetExclude\":false,\"paused\":false,\"approvedImpressions\":[],\"approvedClickTracking\":[],\"approvedEvents\":{},\"bumper\":false,\"attributionType\":1,\"vpaid\":false,\"click_url\":\"undefined\"},\"is_fill\":false,\"is_fallback\":false,\"moat\":0.1,\"is_house\":true,\"safe_ad_approved\":false,\"show_padlock\":false,\"campaign_type\":0,\"line_item_id\":181223,\"campaign_id\":48202,\"test\":false,\"app\":41037,\"device\":\"phone\",\"aua\":\"eyJhbGciOiJIUzI1NiJ9.TW96aWxsYS81LjAgKExpbnV4OyBBbmRyb2lkIDEwKSBBcHBsZVdlYktpdC81MzcuMzYgKEtIVE1MLCBsaWtlIEdlY2tvKSBDaHJvbWUvODMuMC40MTAzLjk2IE1vYmlsZSBTYWZhcmkvNTM3LjM2.I_tn7-WYamd5_dlbbCVEZM0gj0A0s3akPbjkX2p2v6E\",\"tip\":\"eyJhbGciOiJIUzI1NiJ9.ODEuMTEwLjI0OS4w.JID_ookEWOeYqark_ZJ8RGhOQaLYYq2UodGzzmzy0oQ\"}\n"
						)
				)
		)

		// The test looks normal from here.

		launchActivity<MainActivity>()

		// When the close button is configured to be displayed with a delay
		onView(withId(R.id.config1Button))
			.perform(waitUntil(isCompletelyDisplayed()), click())

		Espresso.onData(Matchers.anything()).inAdapterView(withId(R.id.listView))
			.atPosition(4)
			.perform(click())

		ViewTester()
			.waitForView(withContentDescription("Close"))
			.perform(waitUntil(isDisplayed()))

		// Then
		onView(withContentDescription("Close"))
			.check(ViewAssertions.matches(isDisplayed()))

	}
}