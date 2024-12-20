package tv.superawesome.sdk.publisher.network

import org.junit.Assert.assertTrue
import org.junit.Test

internal class EnvironmentTest {
    @Test
    fun test_baseUrl() {
        checkBaseUrl(Environment.UITesting, "localhost")
        checkBaseUrl(Environment.Production, "https://ads.superawesome")
        checkBaseUrl(Environment.Staging, "https://ads.staging")
    }

    private fun checkBaseUrl(environment: Environment, expected: String) {
        assertTrue(environment.baseUrl.contains(expected))
    }
}