package tv.superawesome.lib.saadloader.mocks.executors;

import java.util.concurrent.Executor;

/**
 * Created by gabriel.coman on 03/05/2018.
 */

public class MockExecutor implements Executor {
    @Override
    public void execute(Runnable runnable) {
        runnable.run();
    }
}
