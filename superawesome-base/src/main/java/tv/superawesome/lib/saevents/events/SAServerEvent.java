package tv.superawesome.lib.saevents.events;

import android.util.Log;

import org.jetbrains.annotations.NonNls;
import org.json.JSONObject;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import tv.superawesome.lib.sajsonparser.SAJsonParser;
import tv.superawesome.lib.samodelspace.saad.SAAd;
import tv.superawesome.lib.sanetwork.request.SANetwork;
import tv.superawesome.lib.sasession.session.ISASession;
import tv.superawesome.lib.sautils.SAUtils;

public class SAServerEvent {

  protected SAAd ad;
  protected ISASession session;
  private final SANetwork network;
  private final boolean isDebug;

  public SAServerEvent(SAAd ad, ISASession session) {
    this(ad, session, Executors.newSingleThreadExecutor(), 15000, false);
  }

  public SAServerEvent(
      SAAd ad, ISASession session, Executor executor, int timeout, boolean isDebug) {
    this.ad = ad;
    this.session = session;
    this.isDebug = isDebug;
    this.network = new SANetwork(executor, timeout);
  }

  public String getUrl() {
    try {
      return session.getBaseUrl();
    } catch (Exception e) {
      return null;
    }
  }

  public @NonNls String getEndpoint() {
    return "";
  }

  public JSONObject getHeader() {
    if (session != null) {
      return SAJsonParser.newObject(
          "Content-Type", "application/json", "User-Agent", session.getUserAgent());
    } else {
      return SAJsonParser.newObject("Content-Type", "application/json");
    }
  }

  public JSONObject getQuery() {
    return new JSONObject();
  }

  public void triggerEvent(final Listener listener) {

    network.sendGET(
        getUrl() + getEndpoint(),
        getQuery(),
        getHeader(),
        (status, payload, success) -> {
          if (!isDebug) {
            String url = getUrl() + getEndpoint() + "?" + SAUtils.formGetQueryFromDict(getQuery());
            Log.d("SuperAwesome", success + " | " + status + " | " + url);
          }

          if ((status == 200 || status == 302) && success) {
            if (listener != null) {
              listener.didTriggerEvent(true);
            }
          } else {
            if (listener != null) {
              listener.didTriggerEvent(false);
            }
          }
        });
  }

  public interface Listener {
    void didTriggerEvent(boolean success);
  }
}
