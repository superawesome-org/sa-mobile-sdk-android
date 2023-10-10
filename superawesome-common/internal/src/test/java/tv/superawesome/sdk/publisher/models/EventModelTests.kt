package tv.superawesome.sdk.publisher.models

import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.Assert
import org.junit.Test
import java.net.URLEncoder

class EventModelTests {

    @Test
    fun `EventQueryBundle merges options to query correctly`() = runTest {
        // Given
        val options = mapOf("key1" to "value1", "key2" to 2)
        val data = EventData(10, 30, 20, EventType.ViewableImpression)

        // When
        val sut = EventQueryBundle(
            parameters = EventQuery(
                placement = 0,
                bundle = "123",
                creative = 0,
                lineItem = 0,
                ct = ConnectionType.Unknown.ordinal,
                sdkVersion = "0.0",
                rnd = "123",
                type = EventType.ViewableImpression,
                noImage = false,
                data = data?.let {
                    URLEncoder.encode(
                        Json.encodeToString(EventData.serializer(), it),
                        "UTF-8"
                    )},
                adRequestId = "test-id",
                openRtbPartnerId = null,
            ),
            options = options,
        )

        // Then
        Assert.assertEquals("" +
            "{placement=0, " +
            "bundle=123, " +
            "creative=0, " +
            "line_item=0, " +
            "ct=0, " +
            "sdkVersion=0.0, " +
            "rnd=123, " +
            "type=viewable_impression, " +
            "no_image=false, " +
            "data=%7B%22placement%22%3A10%2C%22" +
            "line_item%22%3A30%2C%22" +
            "creative%22%3A20%2C%22" +
            "type%22%3A%22" +
            "viewable_impression%22%7D, " +
            "adRequestId=test-id, " +
            "key1=value1, " +
            "key2=2}",
            sut.build().toString()
        )
    }
}