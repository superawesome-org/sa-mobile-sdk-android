package tv.superawesome.sdk.publisher.common.base

import io.mockk.MockKAnnotations
import org.junit.Before

open class BaseTest {

    @Before
    fun setUp() = MockKAnnotations.init(this, relaxed = true)
}
