package tv.superawesome.lib.saevents;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import tv.superawesome.lib.saevents.events.DwellTimeEvent;
import tv.superawesome.lib.saevents.events.SAClickEvent;
import tv.superawesome.lib.saevents.events.SAImpressionEvent;
import tv.superawesome.lib.saevents.events.SAMoatAttemptEvent;
import tv.superawesome.lib.saevents.events.SAMoatAttemptNoClassEvent;
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

  private final SAClickEvent clickEvent;
  private final SAImpressionEvent impressionEvent;
  private final SAViewableImpressionEvent viewableImpressionEvent;
  private final SAPGOpenEvent sapgOpenEvent;
  private final SAPGCloseEvent sapgCloseEvent;
  private final SAPGFailEvent sapgFailEvent;
  private final SAPGSuccessEvent sapgSuccessEvent;
  private final SAMoatAttemptEvent saMoatAttemptEvent;
  private final SAMoatAttemptNoClassEvent saMoatAttemptNoClassEvent = null;
  private final SAMoatSuccessEvent saMoatSuccessEvent;
  private final SAMoatErrorEvent saMoatErrorEvent;
  private final DwellTimeEvent dwellTimeEvent;

  public SAServerModule(SAAd ad, ISASession session) {
    this(ad, session, Executors.newSingleThreadExecutor(), 15000, false);
  }

  public SAServerModule(
      SAAd ad, ISASession session, Executor executor, int timeout, boolean isDebug) {
    clickEvent = new SAClickEvent(ad, session, executor, timeout, isDebug);
    impressionEvent = new SAImpressionEvent(ad, session, executor, timeout, isDebug);
    viewableImpressionEvent =
        new SAViewableImpressionEvent(ad, session, executor, timeout, isDebug);
    sapgOpenEvent = new SAPGOpenEvent(ad, session, executor, timeout, isDebug);
    sapgCloseEvent = new SAPGCloseEvent(ad, session, executor, timeout, isDebug);
    sapgFailEvent = new SAPGFailEvent(ad, session, executor, timeout, isDebug);
    sapgSuccessEvent = new SAPGSuccessEvent(ad, session, executor, timeout, isDebug);
    saMoatAttemptEvent = new SAMoatAttemptEvent(ad, session, executor, timeout, isDebug);
    saMoatSuccessEvent = new SAMoatSuccessEvent(ad, session, executor, timeout, isDebug);
    saMoatErrorEvent = new SAMoatErrorEvent(ad, session, executor, timeout, isDebug);
    dwellTimeEvent = new DwellTimeEvent(ad, session, executor, timeout, isDebug);
  }

  public void triggerClickEvent(SAURLEvent.Listener listener) {
    if (clickEvent != null) {
      clickEvent.triggerEvent(listener);
    }
  }

  public void triggerImpressionEvent(SAURLEvent.Listener listener) {
    if (impressionEvent != null) {
      impressionEvent.triggerEvent(listener);
    }
  }

  public void triggerDwellEvent(SAURLEvent.Listener listener) {
    if (dwellTimeEvent != null) {
      dwellTimeEvent.triggerEvent(listener);
    }
  }

  public void triggerViewableImpressionEvent(SAURLEvent.Listener listener) {
    if (viewableImpressionEvent != null) {
      viewableImpressionEvent.triggerEvent(listener);
    }
  }

  public void triggerPgOpenEvent(SAURLEvent.Listener listener) {
    if (sapgOpenEvent != null) {
      sapgOpenEvent.triggerEvent(listener);
    }
  }

  public void triggerPgCloseEvent(SAURLEvent.Listener listener) {
    if (sapgCloseEvent != null) {
      sapgCloseEvent.triggerEvent(listener);
    }
  }

  public void triggerPgFailEvent(SAURLEvent.Listener listener) {
    if (sapgFailEvent != null) {
      sapgFailEvent.triggerEvent(listener);
    }
  }

  public void triggerPgSuccessEvent(SAURLEvent.Listener listener) {
    if (sapgSuccessEvent != null) {
      sapgSuccessEvent.triggerEvent(listener);
    }
  }

  public void triggerMoatAttemptEvent(SAURLEvent.Listener listener) {
    if (saMoatAttemptEvent != null) {
      saMoatAttemptEvent.triggerEvent(listener);
    }
  }

  public void triggerMoatAttemptNoClassEvent(SAURLEvent.Listener listener) {
    if (saMoatAttemptEvent != null) {
      saMoatAttemptNoClassEvent.triggerEvent(listener);
    }
  }

  public void triggerMoatSuccessEvent(SAURLEvent.Listener listener) {
    if (saMoatSuccessEvent != null) {
      saMoatSuccessEvent.triggerEvent(listener);
    }
  }

  public void triggerMoatErrorEvent(SAURLEvent.Listener listener) {
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
