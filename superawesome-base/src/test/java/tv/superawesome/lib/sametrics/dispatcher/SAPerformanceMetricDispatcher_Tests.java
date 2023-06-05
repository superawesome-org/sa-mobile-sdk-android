package tv.superawesome.lib.sametrics.dispatcher;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

import android.content.Context;

import org.json.JSONObject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.Executor;

import tv.superawesome.lib.saevents.mocks.executors.MockExecutor;
import tv.superawesome.lib.saevents.mocks.session.MockSession;
import tv.superawesome.lib.sametrics.models.SAPerformanceMetricModel;
import tv.superawesome.lib.sametrics.models.SAPerformanceMetricName;
import tv.superawesome.lib.sametrics.models.SAPerformanceMetricType;
import tv.superawesome.lib.sasession.session.ISASession;

public class SAPerformanceMetricDispatcher_Tests {

  protected Context context   = null;
  protected Executor executor = null;
  protected ISASession session = null;

  @Before
  public void setUp () throws Throwable {
    executor = new MockExecutor();
    context = mock(Context.class);
    session = new MockSession("http://localhost:64000");
  }

  @After
  public void tearDown () {
    executor = null;
    context = null;
    session = null;
  }

  @Test
  public void test_SAPerformanceMetricDispatcher_Init () throws Throwable {
    // given
    SAPerformanceMetricModel model = new SAPerformanceMetricModel(
        15.0,
        SAPerformanceMetricName.CloseButtonPressTime,
        SAPerformanceMetricType.Gauge
    );

    // when
    SAPerformanceMetricDispatcher dispatcher = new SAPerformanceMetricDispatcher(
        model,
        session,
        executor,
        1000,
        true
    );

    // then - url
    Assert.assertNotNull(dispatcher);

    Assert.assertNotNull(dispatcher.getUrl());
    Assert.assertEquals("http://localhost:64000", dispatcher.getUrl());
    Assert.assertEquals(session.getBaseUrl(), dispatcher.getUrl());

    // then - endpoint
    Assert.assertNotNull(dispatcher.getEndpoint());
    Assert.assertEquals("/sdk/performance", dispatcher.getEndpoint());

    // then - header
    Assert.assertNotNull(dispatcher.getHeader());
    Assert.assertEquals("application/json", dispatcher.getHeader().getString("Content-Type"));

    // then - query
    JSONObject query = dispatcher.getQuery();

    assertNotNull(query.opt("value"));
    assertEquals(15.0, query.opt("value"));

    assertNotNull(query.opt("metricName"));
    assertEquals("sa.ad.sdk.close.button.press.time", query.opt("metricName"));

    assertNotNull(query.opt("metricType"));
    assertEquals("gauge", query.opt("metricType"));
  }
}
