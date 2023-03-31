package tv.superawesome.lib.saadloader.query;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import android.content.Context;

import org.json.JSONObject;
import org.junit.Test;

import tv.superawesome.lib.saadloader.SALoader;
import tv.superawesome.lib.sasession.session.SASession;

public class SAAdLoader_GetAwesomeAdsHeader_Test {

    @Test
    public void test_SAAdLoader_GetAwesomeAdsHeader_WithValidContextAndSession () {
        // given
        String userAgent = "Mozilla/5.0 (Linux; <Android Version>; <Build Tag etc.>) AppleWebKit/<WebKit Rev> (KHTML, like Gecko) Chrome/<Chrome Rev> Mobile Safari/<WebKit Rev>";
        Context context = mock(Context.class);
        SASession session = mock(SASession.class);

        // when
        when(session.getUserAgent()).thenReturn(userAgent);
        SALoader loader = new SALoader(context);

        // then
        JSONObject header = loader.getAwesomeAdsHeader(session);

        assertNotNull(header);
        assertEquals(2, header.length());

        assertNotNull(header.opt("Content-Type"));
        assertEquals("application/json", header.opt("Content-Type"));

        assertNotNull(header.opt("User-Agent"));
        assertEquals("Mozilla/5.0 (Linux; <Android Version>; <Build Tag etc.>) AppleWebKit/<WebKit Rev> (KHTML, like Gecko) Chrome/<Chrome Rev> Mobile Safari/<WebKit Rev>", header.opt("User-Agent"));
    }

    @Test
    public void test_SAAdLoader_GetAwesomeAdsHeader_WithNullContext () {
        // given
        String userAgent = "Default user agent";
        Context context = mock(Context.class);
        SASession session = mock(SASession.class);

        // when
        when(session.getUserAgent()).thenReturn(userAgent);
        SALoader loader = new SALoader(null);

        // then
        JSONObject header = loader.getAwesomeAdsHeader(session);

        assertNotNull(header);
        assertEquals(2, header.length());

        assertNotNull(header.opt("Content-Type"));
        assertEquals("application/json", header.opt("Content-Type"));

        assertNotNull(header.opt("User-Agent"));
        assertEquals("Default user agent", header.opt("User-Agent"));
    }

    @Test
    public void test_SAAdLoader_GetAwesomeAdsHeader_WithNullSession () {
        // given
        SASession session = null;

        // when
        SALoader loader = new SALoader(null);

        // then
        JSONObject header = loader.getAwesomeAdsHeader(session);

        assertNotNull(header);
        assertEquals(0, header.length());
        assertNull(header.opt("Content-Type"));
        assertNull(header.opt("User-Agent"));
    }
}
