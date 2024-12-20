package tv.superawesome.lib.saadloader.query;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import android.content.Context;

import org.json.JSONObject;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import tv.superawesome.lib.saadloader.SALoader;
import tv.superawesome.lib.sasession.defines.SARTBInstl;
import tv.superawesome.lib.sasession.defines.SARTBPlaybackMethod;
import tv.superawesome.lib.sasession.defines.SARTBPosition;
import tv.superawesome.lib.sasession.defines.SARTBSkip;
import tv.superawesome.lib.sasession.defines.SARTBStartDelay;
import tv.superawesome.lib.sasession.publisher.PublisherConfiguration;
import tv.superawesome.lib.sasession.session.SASession;
import tv.superawesome.lib.sautils.SAClock;
import tv.superawesome.lib.sautils.SAUtils;

public class SAAdLoader_GetAwesomeAdsQuery_Test {
    @Test
    public void test_SAAdLoader_GetAwesomeAdsQuery_WithValidSession () {
        // given
        Context context = mock(Context.class);
        SASession session = mock(SASession.class);
        SAClock clockMock = mock(SAClock.class);
        PublisherConfiguration publisherConfiguration = mock(PublisherConfiguration.class);

        // when
        when(session.getTestMode()).thenReturn(true);
        when(session.getVersion()).thenReturn("1.0.0");
        when(session.getCachebuster()).thenReturn(123456);
        when(session.getPackageName()).thenReturn("superawesome.tv.saadloaderdemo");
        when(session.getAppName()).thenReturn("SAAdLoaderDemo");
        when(session.getDauId()).thenReturn(654321);
        when(session.getConnectionType()).thenReturn(SAUtils.SAConnectionType.wifi);
        when(session.getLang()).thenReturn("en_GB");
        when(session.getDevice()).thenReturn("phone");
        when(session.getPos()).thenReturn(SARTBPosition.FULLSCREEN);
        when(session.getSkip()).thenReturn(SARTBSkip.NO_SKIP);
        when(session.getPlaybackMethod()).thenReturn(SARTBPlaybackMethod.WITH_SOUND_ON_SCREEN);
        when(session.getStartDelay()).thenReturn(SARTBStartDelay.PRE_ROLL);
        when(session.getInstl()).thenReturn(SARTBInstl.FULLSCREEN);
        when(session.getWidth()).thenReturn(320);
        when(session.getHeight()).thenReturn(240);
        when(clockMock.getTimestamp()).thenReturn(123L);
        when(session.getPublisherConfiguration()).thenReturn(publisherConfiguration);
        when(publisherConfiguration.toJsonString()).thenReturn("");

        SALoader loader = new SALoader(context, clockMock);

        // then
        JSONObject query = loader.getAwesomeAdsQuery(session);

        assertNotNull(query);
        assertEquals(18, query.length());

        assertNotNull(query.opt("test"));
        assertEquals(true, query.opt("test"));

        assertNotNull(query.opt("sdkVersion"));
        assertEquals("1.0.0", query.opt("sdkVersion"));

        assertNotNull(query.opt("rnd"));
        assertEquals(123456, query.opt("rnd"));

        assertNotNull(query.opt("bundle"));
        assertEquals("superawesome.tv.saadloaderdemo", query.opt("bundle"));

        assertNotNull(query.opt("name"));
        assertEquals("SAAdLoaderDemo", query.opt("name"));

        assertNotNull(query.opt("dauid"));
        assertEquals(654321, query.opt("dauid"));

        assertNotNull(query.opt("ct"));
        assertEquals(2, query.opt("ct"));

        assertNotNull(query.opt("lang"));
        assertEquals("en_GB", query.opt("lang"));

        assertNotNull(query.opt("device"));
        assertEquals("phone", query.opt("device"));

        assertNotNull(query.opt("pos"));
        assertEquals(SARTBPosition.FULLSCREEN.getValue(), query.opt("pos"));

        assertNotNull(query.opt("skip"));
        assertEquals(SARTBSkip.NO_SKIP.getValue(), query.opt("skip"));

        assertNotNull(query.opt("playbackmethod"));
        assertEquals(SARTBPlaybackMethod.WITH_SOUND_ON_SCREEN.getValue(), query.opt("playbackmethod"));

        assertNotNull(query.opt("startdelay"));
        assertEquals(SARTBStartDelay.PRE_ROLL.getValue(), query.opt("startdelay"));

        assertNotNull(query.opt("instl"));
        assertEquals(SARTBInstl.FULLSCREEN.getValue(), query.opt("instl"));

        assertNotNull(query.opt("w"));
        assertEquals(320, query.opt("w"));

        assertNotNull(query.opt("h"));
        assertEquals(240, query.opt("h"));

        assertNotNull(query.opt("timestamp"));
        assertEquals(123L, query.opt("timestamp"));
    }

    @Test
    public void test_SAAdLoader_GetAwesomeAdsQuery_WithNullSession () {
        // given
        SASession session = null;
        SAClock clockMock = mock(SAClock.class);

        // when

        when(clockMock.getTimestamp()).thenReturn(123L);
        SALoader loader = new SALoader(null, clockMock);

        // then
        JSONObject query = loader.getAwesomeAdsQuery(null);

        assertNotNull(query);
        assertEquals(0, query.length());
        assertNull(query.opt("test"));
        assertNull(query.opt("sdkVersion"));
        assertNull(query.opt("rnd"));
        assertNull(query.opt("bundle"));
        assertNull(query.opt("name"));
        assertNull(query.opt("dauid"));
        assertNull(query.opt("ct"));
        assertNull(query.opt("lang"));
        assertNull(query.opt("device"));
        assertNull(query.opt("timestamp"));
    }

    @Test
    public void test_SAAdLoader_GetAwesomeAdsQuery_With_Options() {
        // given
        Context context = mock(Context.class);
        SASession session = mock(SASession.class);
        SAClock clockMock = mock(SAClock.class);
        PublisherConfiguration publisherConfiguration = mock(PublisherConfiguration.class);

        Map<String, Object> requestOptions = new HashMap<String, Object>() {{
            put("key1", "value1");
            put("key2", 2);
            put("key3", "value3");
            put("key4", 4);
        }};

        // when
        when(session.getTestMode()).thenReturn(true);
        when(session.getVersion()).thenReturn("1.0.0");
        when(session.getCachebuster()).thenReturn(123456);
        when(session.getPackageName()).thenReturn("superawesome.tv.saadloaderdemo");
        when(session.getAppName()).thenReturn("SAAdLoaderDemo");
        when(session.getDauId()).thenReturn(654321);
        when(session.getConnectionType()).thenReturn(SAUtils.SAConnectionType.wifi);
        when(session.getLang()).thenReturn("en_GB");
        when(session.getDevice()).thenReturn("phone");
        when(session.getPos()).thenReturn(SARTBPosition.FULLSCREEN);
        when(session.getSkip()).thenReturn(SARTBSkip.NO_SKIP);
        when(session.getPlaybackMethod()).thenReturn(SARTBPlaybackMethod.WITH_SOUND_ON_SCREEN);
        when(session.getStartDelay()).thenReturn(SARTBStartDelay.PRE_ROLL);
        when(session.getInstl()).thenReturn(SARTBInstl.FULLSCREEN);
        when(session.getWidth()).thenReturn(320);
        when(session.getHeight()).thenReturn(240);
        when(clockMock.getTimestamp()).thenReturn(123L);
        when(session.getPublisherConfiguration()).thenReturn(publisherConfiguration);
        when(publisherConfiguration.toJsonString()).thenReturn("");

        SALoader loader = new SALoader(context, clockMock);

        // then
        JSONObject query = loader.getAwesomeAdsQuery(session, requestOptions);

        assertNotNull(query);
        assertEquals(22, query.length());

        assertNotNull(query.opt("test"));
        assertEquals(true, query.opt("test"));

        assertNotNull(query.opt("sdkVersion"));
        assertEquals("1.0.0", query.opt("sdkVersion"));

        assertNotNull(query.opt("rnd"));
        assertEquals(123456, query.opt("rnd"));

        assertNotNull(query.opt("bundle"));
        assertEquals("superawesome.tv.saadloaderdemo", query.opt("bundle"));

        assertNotNull(query.opt("name"));
        assertEquals("SAAdLoaderDemo", query.opt("name"));

        assertNotNull(query.opt("dauid"));
        assertEquals(654321, query.opt("dauid"));

        assertNotNull(query.opt("ct"));
        assertEquals(2, query.opt("ct"));

        assertNotNull(query.opt("lang"));
        assertEquals("en_GB", query.opt("lang"));

        assertNotNull(query.opt("device"));
        assertEquals("phone", query.opt("device"));

        assertNotNull(query.opt("pos"));
        assertEquals(SARTBPosition.FULLSCREEN.getValue(), query.opt("pos"));

        assertNotNull(query.opt("skip"));
        assertEquals(SARTBSkip.NO_SKIP.getValue(), query.opt("skip"));

        assertNotNull(query.opt("playbackmethod"));
        assertEquals(SARTBPlaybackMethod.WITH_SOUND_ON_SCREEN.getValue(), query.opt("playbackmethod"));

        assertNotNull(query.opt("startdelay"));
        assertEquals(SARTBStartDelay.PRE_ROLL.getValue(), query.opt("startdelay"));

        assertNotNull(query.opt("instl"));
        assertEquals(SARTBInstl.FULLSCREEN.getValue(), query.opt("instl"));

        assertNotNull(query.opt("w"));
        assertEquals(320, query.opt("w"));

        assertNotNull(query.opt("h"));
        assertEquals(240, query.opt("h"));

        assertNotNull(query.opt("timestamp"));
        assertEquals(123L, query.opt("timestamp"));

        assertNotNull(query.opt("key1"));
        assertEquals("value1", query.opt("key1"));

        assertNotNull(query.opt("key2"));
        assertEquals(2, query.opt("key2"));

        assertNotNull(query.opt("key3"));
        assertEquals("value3", query.opt("key3"));

        assertNotNull(query.opt("key4"));
        assertEquals(4, query.opt("key4"));
    }

    @Test
    public void test_SAAdLoader_GetAwesomeAdsQuery_WithOpenRTBPartnerId () {
        // given
        Context context = mock(Context.class);
        SASession session = mock(SASession.class);
        SAClock clockMock = mock(SAClock.class);
        PublisherConfiguration publisherConfiguration = mock(PublisherConfiguration.class);

        // when
        when(session.getTestMode()).thenReturn(true);
        when(session.getVersion()).thenReturn("1.0.0");
        when(session.getCachebuster()).thenReturn(123456);
        when(session.getPackageName()).thenReturn("superawesome.tv.saadloaderdemo");
        when(session.getAppName()).thenReturn("SAAdLoaderDemo");
        when(session.getDauId()).thenReturn(654321);
        when(session.getConnectionType()).thenReturn(SAUtils.SAConnectionType.wifi);
        when(session.getLang()).thenReturn("en_GB");
        when(session.getDevice()).thenReturn("phone");
        when(session.getPos()).thenReturn(SARTBPosition.FULLSCREEN);
        when(session.getSkip()).thenReturn(SARTBSkip.NO_SKIP);
        when(session.getPlaybackMethod()).thenReturn(SARTBPlaybackMethod.WITH_SOUND_ON_SCREEN);
        when(session.getStartDelay()).thenReturn(SARTBStartDelay.PRE_ROLL);
        when(session.getInstl()).thenReturn(SARTBInstl.FULLSCREEN);
        when(session.getWidth()).thenReturn(320);
        when(session.getHeight()).thenReturn(240);
        when(clockMock.getTimestamp()).thenReturn(123L);
        when(session.getPublisherConfiguration()).thenReturn(publisherConfiguration);
        when(publisherConfiguration.toJsonString()).thenReturn("");

        SALoader loader = new SALoader(context, clockMock);

        // then
        JSONObject query = loader.getAwesomeAdsQuery(session, Collections.emptyMap(),"123456");
        assertEquals("123456", query.opt("openRtbPartnerId"));
    }
}
