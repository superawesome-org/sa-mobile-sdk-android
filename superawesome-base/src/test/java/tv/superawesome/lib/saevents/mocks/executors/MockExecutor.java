package tv.superawesome.lib.saevents.mocks.executors;

import java.util.concurrent.Executor;

/**
 * Created by gabriel.coman on 09/05/2018.
 */
public class MockExecutor implements Executor {
    @Override
    public void execute(Runnable runnable) {
        runnable.run();
    }
}
