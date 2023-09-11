package tv.superawesome.sdk.publisher.extensions

import org.junit.Test
import tv.superawesome.sdk.publisher.base.BaseTest
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class StringExtensionsKtTest : BaseTest() {

    @Test
    fun test_toMD5() {
        // Given
        val given = "TestString"

        // When
        val result = given.toMD5()

        // Then
        assertEquals("5b56f40f8828701f97fa4511ddcd25fb", result)
    }

    @Test
    fun test_cleanBaseUrl_with_path_params() {

        // given
        val url = "https://something.com/blah/web.js?something=some&blah=3"

        // when
        val result = url.baseUrl

        // then 
        assertEquals("https://something.com", result)
    }

    @Test
    fun test_cleanBaseUrl_with_noPath_noParams_trailingSlash() {

        // given
        val url = "https://something.com/"

        // when
        val result = url.baseUrl

        // then
        assertEquals("https://something.com", result)
    }

    @Test
    fun test_cleanBaseUrl_with_noPath_noParams() {

        // given
        val url = "https://something.com"

        // when
        val result = url.baseUrl

        // then
        assertEquals("https://something.com", result)
    }

    @Test
    fun test_cleanBaseUrl_with_path() {

        // given
        val url = "https://something.com/blah/web.js"

        // when
        val result = url.baseUrl

        // then
        assertEquals("https://something.com", result)
    }

    @Test
    fun test_extractURLs_when_string_contains_one_url_start_string() {

        // given
        val string = "https://website.com urls"

        // when
        val result = string.extractURLs()

        // then
        assertEquals(1, result.size)
        assertEquals("https://website.com", result.firstOrNull())
    }

    @Test
    fun test_extractURLs_when_string_contains_one_url_mid_string() {

        // given
        val string = "This is a string with https://website.com urls"

        // when
        val result = string.extractURLs()

        // then
        assertEquals(1, result.size)
        assertEquals("https://website.com", result.firstOrNull())
    }

    @Test
    fun test_extractURLs_when_string_contains_one_url_end_string() {

        // given
        val string = "This is a string with https://website.com"

        // when
        val result = string.extractURLs()

        // then
        assertEquals(1, result.size)
        assertEquals("https://website.com", result.firstOrNull())
    }

    @Test
    fun test_extractURLs_when_string_contains_two() {

        // given
        val string = "This is a string with https://website.com urls http://someurl.co.uk"

        // when
        val result = string.extractURLs()

        // then
        assertEquals(2, result.size)
        assertEquals("https://website.com", result.firstOrNull())
        assertEquals("http://someurl.co.uk", result.lastOrNull())
    }

    @Test
    fun test_extractURLs_when_string_contains_three() {

        // given
        val string = "This is a string with https://website.com urls http://someurl.co.uk " +
                "something something http://blah.com"

        // when
        val result = string.extractURLs()

        // then
        assertEquals(3, result.size)
        assertEquals("https://website.com", result.firstOrNull())
        assertEquals("http://someurl.co.uk", result[1])
        assertEquals("http://blah.com", result.lastOrNull())
    }

    @Test
    fun test_extractURLs_in_realistic_case() {

        // given
        val string = "<html>\n  " +
                "  <header>\n       " +
                " <meta name='viewport' content='width=device-width'/>\n      " +
                "  <style>html, body, div { margin: 0px; padding: 0px; } html, " +
                "body { width: 100%; height: 100%; }</style>\n    </header>\n    " +
                "<body><script type=\"text/javascript\" src=\"https://eu-west-1-ads.sup" +
                "erawesome.tv/v2/ad.js?placement=89056&lineItemId=182932&creativeId=510371&sd" +
                "kVersion=ios_8.6.0&rnd=d31df441-aca9-4018-8a6b-61f3fe31886a&bundle=tv.superawes" +
                "ome.awesomeads.sdk&dauid=287646772018416067&ct=2&lang=en_US&device=phone&pos=7&t" +
                "imestamp=_TIMESTAMP_&skip=1&playbackmethod=5&startdelay=null&instl=1&isProgra" +
                "mmatic=true&vpaid=true\"></script></body>\n    </html>"

        // when
        val result = string.extractURLs()

        // then
        assertEquals(1, result.size)
        assertEquals(
            "https://eu-west-1-ads.sup" +
                    "erawesome.tv/v2/ad.js?placement=89056&lineItemId=182932&creativeId=510371&sd" +
                    "kVersion=ios_8.6.0&rnd=d31df441-aca9-4018-8a6b-61f3fe31886a&bundle=tv.super" +
                    "awesome.awesomeads.sdk&dauid=287646772018416067&ct=2&lang=en_US&device=phone" +
                    "&pos=7&timestamp=_TIMESTAMP_&skip=1&playbackmethod=5&startdelay=null&instl=1" +
                    "&isProgrammatic=true&vpaid=true",
            result.firstOrNull())

        assertEquals("https://eu-west-1-ads.superawesome.tv", result.firstOrNull()?.baseUrl)
    }

    @Test
    fun test_extractURLs_when_string_contains_partial_url_host_only() {

        // given
        val string = "This is a string with https:// urls"

        // when
        val result = string.extractURLs()

        // then
        assertTrue(result.isEmpty())
    }

    @Test
    fun test_extractURLs_when_string_contains_partial_url_incomplete_host_only() {

        // given
        val string = "This is a string with https:/ urls"

        // when
        val result = string.extractURLs()

        // then
        assertTrue(result.isEmpty())
    }

    @Test
    fun test_extractURLs_when_string_contains_more_partial_url() {

        // given
        val string = "This is a string with https://something urls"

        // when
        val result = string.extractURLs()

        // then
        assertTrue(result.isEmpty())
    }

    @Test
    fun test_extractURLs_when_string_contains_url_no_host() {

        // given
        val string = "This is a string with www.something.co.uk urls"

        // when
        val result = string.extractURLs()

        // then
        assertEquals(1, result.size)
    }

    @Test
    fun test_extractURLs_when_string_does_not_contain_them() {

        // given
        val string = "This is a string with no urls"

        // when
        val result = string.extractURLs()

        // then
        assertTrue(result.isEmpty())
    }
}
