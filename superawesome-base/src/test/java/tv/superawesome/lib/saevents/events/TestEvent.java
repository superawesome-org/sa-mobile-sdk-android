package tv.superawesome.lib.saevents.events;

import android.content.Context;

import org.junit.After;
import org.junit.Before;

import java.util.concurrent.Executor;

import tv.superawesome.lib.saevents.mocks.executors.MockExecutor;
import tv.superawesome.lib.saevents.mocks.models.MockCreative;
import tv.superawesome.lib.saevents.mocks.models.MockDetails;
import tv.superawesome.lib.saevents.mocks.models.MockAd;
import tv.superawesome.lib.saevents.mocks.models.MockMedia;
import tv.superawesome.lib.saevents.mocks.models.MockVastAd;
import tv.superawesome.lib.saevents.mocks.session.MockSession;
import tv.superawesome.lib.samodelspace.saad.SAAd;
import tv.superawesome.lib.samodelspace.saad.SACreative;
import tv.superawesome.lib.samodelspace.saad.SADetails;
import tv.superawesome.lib.samodelspace.saad.SAMedia;
import tv.superawesome.lib.samodelspace.vastad.SAVASTAd;
import tv.superawesome.lib.sasession.defines.SAConfiguration;
import tv.superawesome.lib.sasession.defines.SARTBInstl;
import tv.superawesome.lib.sasession.defines.SARTBPlaybackMethod;
import tv.superawesome.lib.sasession.defines.SARTBPosition;
import tv.superawesome.lib.sasession.defines.SARTBSkip;
import tv.superawesome.lib.sasession.defines.SARTBStartDelay;
import tv.superawesome.lib.sasession.session.ISASession;
import tv.superawesome.lib.sasession.session.SASession;
import tv.superawesome.lib.sautils.SAUtils;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by gabriel.coman on 09/05/2018.
 */

public class TestEvent {

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
