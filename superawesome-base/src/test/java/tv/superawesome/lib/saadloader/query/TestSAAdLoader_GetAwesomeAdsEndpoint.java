package tv.superawesome.lib.saadloader.query;

import android.content.Context;

import org.junit.Test;

import tv.superawesome.lib.saadloader.SALoader;
import tv.superawesome.lib.sasession.session.SASession;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestSAAdLoader_GetAwesomeAdsEndpoint {

    @Test
    public void test_SAAdLoader_GetAwesomeAdsEndpoint_WithCorrectSession () {
        // given
        Context context = mock(Context.class);
        SASession session = mock(SASession.class);

        // when
        when(session.getBaseUrl()).thenReturn("https://ads.superawesome.tv/v2");
        SALoader loader = new SALoader(context);
        String baseUrl = loader.getAwesomeAdsEndpoint(session, 4001);

        // then
        assertNotNull(baseUrl);
        assertEquals("https://ads.superawesome.tv/v2/ad/4001", baseUrl);

    }

    @Test
    public void test_SAAdLoader_GetAwesomeAdsEndpoint_WithNullSession () {
        // given
        SASession session = null;

        // when
        SALoader loader = new SALoader(null);
        String baseUrl = loader.getAwesomeAdsEndpoint(session, 4001);

        // then
        assertNull(baseUrl);
    }
}
