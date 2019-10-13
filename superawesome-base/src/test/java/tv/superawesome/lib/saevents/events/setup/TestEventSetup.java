package tv.superawesome.lib.saevents.events.setup;

import org.junit.After;
import org.junit.Before;

import tv.superawesome.lib.saevents.events.TestEvent;

public class TestEventSetup extends TestEvent {

    @Override
    @Before
    public void setUp () throws Throwable {
        super.setUp();
    }

    @After
    public void tearDown () throws Throwable {
        super.tearDown();
    }
}
