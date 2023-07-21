package tv.superawesome.lib.saevents.events;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SAURLEvent extends SAServerEvent {

    protected final String vastUrl;

    public SAURLEvent(String vastUrl) {
        this(vastUrl, Executors.newSingleThreadExecutor(), 15000, 1000L, false);
    }

    public SAURLEvent (String vastUrl, Executor executor, int timeout, long retryDelay, boolean isDebug) {
        super(null, null, executor, timeout, retryDelay, isDebug);
        this.vastUrl = vastUrl;
    }

    @Override
    public String getUrl() {
        return vastUrl;
    }

    @Override
    public String getEndpoint() {
        return "";
    }
}
