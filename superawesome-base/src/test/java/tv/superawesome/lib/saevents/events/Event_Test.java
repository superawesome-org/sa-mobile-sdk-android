package tv.superawesome.lib.saevents.events;

import static org.mockito.Mockito.mock;

import android.content.Context;

import org.junit.After;
import org.junit.Before;

import java.util.concurrent.Executor;

import tv.superawesome.lib.saevents.mocks.executors.MockExecutor;
import tv.superawesome.lib.saevents.mocks.session.MockSession;
import tv.superawesome.lib.sasession.session.ISASession;

/**
 * Created by gabriel.coman on 09/05/2018.
 */

public class Event_Test {

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
    public void tearDown () throws Throwable {
        executor = null;
        session = null;
    }
}
