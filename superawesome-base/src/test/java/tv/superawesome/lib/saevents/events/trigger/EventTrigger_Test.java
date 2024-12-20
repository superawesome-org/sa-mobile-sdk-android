package tv.superawesome.lib.saevents.events.trigger;

import org.junit.After;
import org.junit.Before;

import tv.superawesome.lib.saevents.events.Event_Test;
import tv.superawesome.lib.saevents.mocks.server.events.MockEventsServer;
import tv.superawesome.lib.saevents.mocks.session.MockSession;
import tv.superawesome.sdk.publisher.QueryAdditionalOptions;

/**
 * Created by gabriel.coman on 09/05/2018.
 */
public class EventTrigger_Test extends Event_Test {

    protected MockEventsServer server;

    @Override
    @Before
    public void setUp () throws Throwable {
        super.setUp();
        server = new MockEventsServer();
        server.start();
        super.session = new MockSession(server.url());
        QueryAdditionalOptions.Companion.setInstance(null);
    }

    @After
    public void tearDown () throws Throwable {
        server.shutdown();
        server = null;
        super.tearDown();
    }
}
