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
import tv.superawesome.sdk.publisher.QueryAdditionalOptions;
import tv.superawesome.sdk.publisher.QueryBuilder;

public class SAServerEvent {

  protected final SAAd ad;
  protected final ISASession session;
  private final SANetwork network;
  private final boolean isDebug;
  private final QueryBuilder queryBuilder = new QueryBuilder();

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
    JSONObject query = getQuery();
    if(ad != null) {
      queryBuilder.merge(ad.getRequestOptions(), query);
    }
    network.sendGET(
        getUrl() + getEndpoint(),
            query,
        getHeader(),
        (status, payload, success) -> {
          if (!isDebug) {
            String url = getUrl() + getEndpoint() + "?" + SAUtils.formGetQueryFromDict(query);
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
