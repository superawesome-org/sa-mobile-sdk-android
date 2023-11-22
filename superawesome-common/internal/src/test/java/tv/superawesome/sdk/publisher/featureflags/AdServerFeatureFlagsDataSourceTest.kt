package tv.superawesome.sdk.publisher.featureflags

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import tv.superawesome.sdk.publisher.components.DeviceCategory
import tv.superawesome.sdk.publisher.di.testCommonModule
import tv.superawesome.sdk.publisher.network.datasources.MockServerTest
import tv.superawesome.sdk.publisher.testutil.ResourceReader
import kotlin.test.assertFails
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class AdServerFeatureFlagsDataSourceTest : MockServerTest(), KoinTest {

    private val api: AdServerFeatureFlagsApi by inject()
    private val query = FeatureFlagsQuery("a", "b", "c", DeviceCategory.PHONE)

    private val sut by lazy { AdServerFeatureFlagsDataSource(api) }

    @Before
    override fun setup() {
        super.setup()
        startKoin { modules(testCommonModule(mockServer)) }
    }

    @After
    override fun tearDown() {
        super.tearDown()
        stopKoin()
    }

    @Test
    fun `throws if it fails to fetch the flags`() = runTest {
        // Arrange
        mockServer.enqueue(MockResponse().setResponseCode(404))

        // Assert
        assertFails {
            // Act
            sut.getFlags(query)
        }
    }

    @Test
    fun `fetches flags successfully`() = runTest {
        // Arrange
        mockServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(ResourceReader.readResource("featureFlags.json"))
        )

        // Act
        val result = sut.getFlags(query)

        // Assert
        assertTrue(result.isAdResponseVASTEnabled)
    }
}
