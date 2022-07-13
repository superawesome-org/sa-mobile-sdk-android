package tv.superawesome.demoapp

import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import org.hamcrest.Matchers
import org.junit.Test
import org.junit.runner.RunWith
import tv.superawesome.demoapp.util.ViewTester
import tv.superawesome.demoapp.util.waitUntil

@RunWith(AndroidJUnit4::class)
@SmallTest
class VaporPOCTest {

	@Test
	fun pocVaporTest() {

		/* The test is setup as normal, the endpoints and responses are configured in the separate
			Vapor server project. The main change required is to point the app to Android localhost (http://10.0.2.2:8080).
			Note that WireMock works with pointing to http://localhost:8080 in SASession.
			We would also need a way to start the server from the command line (running `vapor run` from the root of the vapor server folder)
			and to consider how this would work on CI as the Vapor server would exist in a separate repo
			(it's bundled in this PR for ease of review as a POC).
			The advantages are sharing the server with iOS and having shared mock responses, the
			downsides include more tricky CI setup, lack of per test configuration, needing to raise a PR for the
			mock server changes too when adding a test with a new response and potential headaches when
			wanting to use a response in a particular way on one platform but not the other.
		 */

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