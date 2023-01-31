package tv.superawesome.lib.saevents;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import tv.superawesome.lib.saevents.events.DwellTimeEvent;
import tv.superawesome.lib.saevents.events.SAClickEvent;
import tv.superawesome.lib.saevents.events.SAImpressionEvent;
import tv.superawesome.lib.saevents.events.SAPGCloseEvent;
import tv.superawesome.lib.saevents.events.SAPGFailEvent;
import tv.superawesome.lib.saevents.events.SAPGOpenEvent;
import tv.superawesome.lib.saevents.events.SAPGSuccessEvent;
import tv.superawesome.lib.saevents.events.SAServerEvent.Listener;
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
    dwellTimeEvent = new DwellTimeEvent(ad, session, executor, timeout, isDebug);
  }

  public void triggerClickEvent(Listener listener) {
    if (clickEvent != null) {
      clickEvent.triggerEvent(listener);
    }
  }

  public void triggerImpressionEvent(Listener listener) {
    if (impressionEvent != null) {
      impressionEvent.triggerEvent(listener);
    }
  }

  public void triggerDwellEvent(Listener listener) {
    if (dwellTimeEvent != null) {
      dwellTimeEvent.triggerEvent(listener);
    }
  }

  public void triggerViewableImpressionEvent(Listener listener) {
    if (viewableImpressionEvent != null) {
      viewableImpressionEvent.triggerEvent(listener);
    }
  }

  public void triggerPgOpenEvent(Listener listener) {
    if (sapgOpenEvent != null) {
      sapgOpenEvent.triggerEvent(listener);
    }
  }

  public void triggerPgCloseEvent(Listener listener) {
    if (sapgCloseEvent != null) {
      sapgCloseEvent.triggerEvent(listener);
    }
  }

  public void triggerPgFailEvent(Listener listener) {
    if (sapgFailEvent != null) {
      sapgFailEvent.triggerEvent(listener);
    }
  }

  public void triggerPgSuccessEvent(Listener listener) {
    if (sapgSuccessEvent != null) {
      sapgSuccessEvent.triggerEvent(listener);
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
