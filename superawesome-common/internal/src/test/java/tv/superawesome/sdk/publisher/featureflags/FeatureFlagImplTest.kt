package tv.superawesome.sdk.publisher.featureflags

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import tv.superawesome.sdk.publisher.testutil.TestLogger

@OptIn(ExperimentalCoroutinesApi::class)
class FeatureFlagImplTest {

    private val fakeDatasource = FakeFeatureFlagDatasource()

    private val sut = FeatureFlagImpl(fakeDatasource, TestLogger())

    @Test
    fun `while not loaded, flags return default object`() {
        // Act
        val value = sut.flags.isAdResponseVASTEnabled

        // Assert
        assertFalse(value)
    }

    @Test
    fun `can load flags successfully`() = runTest {
        // Arrange
        sut.fetch()

        // Act
        val value = sut.flags.isAdResponseVASTEnabled

        // Assert
        assertTrue(value)
    }

    @Test
    fun `failing fetch won't replace default flags`() = runTest {
        // Arrange
        val sut = FeatureFlagImpl(FailingFeatureFlagDatasource(), TestLogger())
        sut.fetch()

        // Act
        val value = sut.flags.isAdResponseVASTEnabled

        // Assert
        assertFalse(value)
    }
}
