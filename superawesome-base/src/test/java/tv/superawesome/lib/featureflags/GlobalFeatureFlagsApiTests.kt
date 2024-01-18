package tv.superawesome.lib.featureflags

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import org.json.JSONException
import org.junit.After
import org.junit.Before
import org.junit.Test
import tv.superawesome.lib.saadloader.mocks.executors.MockExecutor
import tv.superawesome.lib.saadloader.mocks.servers.ads.MockAdsServer
import tv.superawesome.lib.saadloader.mocks.session.MockSession
import tv.superawesome.lib.sasession.session.ISASession
import tv.superawesome.sdk.publisher.QueryAdditionalOptions
import java.util.concurrent.Executor

class GlobalFeatureFlagsApiTests {

    lateinit var executor: Executor
    lateinit var session: ISASession
    lateinit var server: MockAdsServer

    @Before
    @Throws(Throwable::class)
    fun setUp() {
        executor = MockExecutor()
        server = MockAdsServer()
        server.start()
        session = MockSession(server.url())
        QueryAdditionalOptions.instance = null
    }

    @After
    @Throws(Throwable::class)
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun `api loads feature flags successfully`() {
        // given
        var newFeatureFlags: FeatureFlags? = null
        val api = GlobalFeatureFlagsApi(executor = executor, timeout = 2000)

        // when
        api.getGlobalFlags(object: SAFeatureFlagLoaderListener {
            override fun didLoadFeatureFlags(featureFlags: FeatureFlags) {
                newFeatureFlags = featureFlags

                // then
                assertNotNull(newFeatureFlags)
            }

            override fun didFailToLoadFeatureFlags(error: Throwable) {
                /* N/A */
            }
        })
    }

    @Test
    fun `api fails to load feature flags on JSON error`() {
        // given
        server.isErrorEnabled = true
        val api = GlobalFeatureFlagsApi(executor = executor, timeout = 2000)

        // when
        api.getGlobalFlags(object: SAFeatureFlagLoaderListener {
            override fun didLoadFeatureFlags(featureFlags: FeatureFlags) {
                /* N/A */
            }

            override fun didFailToLoadFeatureFlags(error: Throwable) {
                assertTrue(error is JSONException)
            }
        })
    }
}
