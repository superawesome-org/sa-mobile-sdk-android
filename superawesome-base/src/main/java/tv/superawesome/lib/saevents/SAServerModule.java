package tv.superawesome.lib.saevents;

import android.content.Context;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import tv.superawesome.lib.saevents.events.SAClickEvent;
import tv.superawesome.lib.saevents.events.SAImpressionEvent;
import tv.superawesome.lib.saevents.events.SAMoatAttemptEvent;
import tv.superawesome.lib.saevents.events.SAMoatErrorEvent;
import tv.superawesome.lib.saevents.events.SAMoatSuccessEvent;
import tv.superawesome.lib.saevents.events.SAPGCloseEvent;
import tv.superawesome.lib.saevents.events.SAPGFailEvent;
import tv.superawesome.lib.saevents.events.SAPGOpenEvent;
import tv.superawesome.lib.saevents.events.SAPGSuccessEvent;
import tv.superawesome.lib.saevents.events.SAURLEvent;
import tv.superawesome.lib.saevents.events.SAViewableImpressionEvent;
import tv.superawesome.lib.samodelspace.saad.SAAd;
import tv.superawesome.lib.sasession.session.ISASession;

public class SAServerModule {

    private SAClickEvent                clickEvent = null;
    private SAImpressionEvent           impressionEvent = null;
    private SAViewableImpressionEvent   viewableImpressionEvent = null;
    private SAPGOpenEvent               sapgOpenEvent = null;
    private SAPGCloseEvent              sapgCloseEvent = null;
    private SAPGFailEvent               sapgFailEvent = null;
    private SAPGSuccessEvent            sapgSuccessEvent = null;
    private SAMoatAttemptEvent          saMoatAttemptEvent = null;
    private SAMoatSuccessEvent          saMoatSuccessEvent = null;
    private SAMoatErrorEvent            saMoatErrorEvent = null;

    public SAServerModule (SAAd ad, ISASession session) {
        this(ad, session, Executors.newSingleThreadExecutor(), 15000, false);
    }

    public SAServerModule (SAAd ad, ISASession session, Executor executor, int timeout, boolean isDebug) {
        clickEvent = new SAClickEvent(ad, session, executor, timeout, isDebug);
        impressionEvent = new SAImpressionEvent(ad, session, executor, timeout, isDebug);
        viewableImpressionEvent = new SAViewableImpressionEvent(ad, session, executor, timeout, isDebug);
        sapgOpenEvent = new SAPGOpenEvent(ad, session, executor, timeout, isDebug);
        sapgCloseEvent = new SAPGCloseEvent(ad, session, executor, timeout, isDebug);
        sapgFailEvent = new SAPGFailEvent(ad, session, executor, timeout, isDebug);
        sapgSuccessEvent = new SAPGSuccessEvent(ad, session, executor, timeout, isDebug);
        saMoatAttemptEvent = new SAMoatAttemptEvent(ad, session, executor, timeout, isDebug);
        saMoatSuccessEvent = new SAMoatSuccessEvent(ad, session, executor, timeout, isDebug);
        saMoatErrorEvent = new SAMoatErrorEvent(ad, session, executor, timeout, isDebug);
    }

    public void triggerClickEvent (SAURLEvent.Listener listener) {
        if (clickEvent != null) {
            clickEvent.triggerEvent(listener);
        }
    }

    public void triggerImpressionEvent (SAURLEvent.Listener listener) {
        if (impressionEvent != null) {
            impressionEvent.triggerEvent(listener);
        }
    }

    public void triggerViewableImpressionEvent (SAURLEvent.Listener listener) {
        if (viewableImpressionEvent != null) {
            viewableImpressionEvent.triggerEvent(listener);
        }
    }

    public void triggerPgOpenEvent (SAURLEvent.Listener listener) {
        if (sapgOpenEvent != null) {
            sapgOpenEvent.triggerEvent(listener);
        }
    }

    public void triggerPgCloseEvent (SAURLEvent.Listener listener) {
        if (sapgCloseEvent != null) {
            sapgCloseEvent.triggerEvent(listener);
        }
    }

    public void triggerPgFailEvent (SAURLEvent.Listener listener) {
        if (sapgFailEvent != null) {
            sapgFailEvent.triggerEvent(listener);
        }
    }

    public void triggerPgSuccessEvent (SAURLEvent.Listener listener) {
        if (sapgSuccessEvent != null) {
            sapgSuccessEvent.triggerEvent(listener);
        }
    }

    public void triggerMoatAttemptEvent (SAURLEvent.Listener listener) {
        if (saMoatAttemptEvent != null) {
            saMoatAttemptEvent.triggerEvent(listener);
        }
    }

    public void triggerMoatSuccessEvent (SAURLEvent.Listener listener) {
        if (saMoatSuccessEvent != null) {
            saMoatSuccessEvent.triggerEvent(listener);
        }
    }

    public void triggerMoatErrorEvent (SAURLEvent.Listener listener) {
        if (saMoatErrorEvent != null) {
            saMoatErrorEvent.triggerEvent(listener);
        }
    }

    public SAClickEvent getClickEvent() {
        return clickEvent;
    }

    public SAImpressionEvent getImpressionEvent() {
        return impressionEvent;
    }

    public SAViewableImpressionEvent getViewableImpressionEvent() {
        return viewableImpressionEvent;
    }

    public SAPGOpenEvent getSapgOpenEvent() {
        return sapgOpenEvent;
    }

    public SAPGCloseEvent getSapgCloseEvent() {
        return sapgCloseEvent;
    }

    public SAPGFailEvent getSapgFailEvent() {
        return sapgFailEvent;
    }

    public SAPGSuccessEvent getSapgSuccessEvent() {
        return sapgSuccessEvent;
    }
}
