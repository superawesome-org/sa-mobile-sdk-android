package tv.superawesome.lib.saevents.modules;

import static org.mockito.Mockito.mock;

import android.content.Context;

import org.junit.After;
import org.junit.Before;

import java.util.concurrent.Executor;

import tv.superawesome.lib.saevents.mocks.executors.MockExecutor;
import tv.superawesome.lib.saevents.mocks.server.events.MockEventsServer;
import tv.superawesome.lib.saevents.mocks.session.MockSession;
import tv.superawesome.lib.sasession.session.ISASession;

/**
 * Created by gabriel.coman on 09/05/2018.
 */
public class TestModule {

    protected Context context   = null;
    protected Executor executor = null;
    protected ISASession session = null;
    private MockEventsServer server;

    @Before
    public void setUp () throws Throwable {
        executor = new MockExecutor();

        context = mock(Context.class);
        server = new MockEventsServer();
        server.start();

        session = new MockSession(server.url());
    }

    @After
    public void tearDown () throws Throwable {
        server.shutdown();
        server = null;
        executor = null;
        session = null;
    }
}
