/*
 * @Copyright: SuperAwesome Trading Limited 2017 @Author: Gabriel Coman
 * (gabriel.coman@superawesome.tv)
 */
package tv.superawesome.lib.saadloader;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import tv.superawesome.lib.saadloader.postprocessor.SAProcessHTML;
import tv.superawesome.lib.sajsonparser.SAJsonParser;
import tv.superawesome.lib.samodelspace.saad.SAAd;
import tv.superawesome.lib.samodelspace.saad.SAResponse;
import tv.superawesome.lib.sanetwork.file.SAFileDownloader;
import tv.superawesome.lib.sanetwork.request.SANetwork;
import tv.superawesome.lib.sasession.defines.SAConfiguration;
import tv.superawesome.lib.sasession.session.ISASession;
import tv.superawesome.lib.sautils.SAUtils;
import tv.superawesome.lib.savastparser.SAVASTParser;

/**
 * This class abstracts away the loading of a SuperAwesome ad server by the server. It tries to
 * handle two major case - when the ad comes alone, in the case of image, rich media, tag, video -
 * when the ads come as an array, in the case of app wall
 *
 * <p>Additionally it will try to: - for image, rich media and tag ads, format the needed HTML to
 * display the ad in a web view - for video ads, parse the associated VAST tag and get all the
 * events and media files; also it will try to download the media resource on disk - for app wall
 * ads, download all the image resources associated with each ad in the wall
 */
public class SALoader {

  // private context
  private final Executor executor;
  private final int timeout;
  private final Context context;
  private final boolean isDebug;

  public SALoader(Context context) {
    this(context, Executors.newSingleThreadExecutor(), false, 15000);
  }

  public SALoader(Context context, Executor executor, boolean isDebug, int timeout) {
    this.context = context;
    this.executor = executor;
    this.timeout = timeout;
    this.isDebug = isDebug;
  }

  public String getAwesomeAdsEndpoint(ISASession session, int placementId) {
    try {
      String base = session.getBaseUrl();
      try {
        char last = base.charAt(base.length() - 1);
        String separator = last == '/' ? "" : "/";
        return base + separator + "ad/" + placementId;
      } catch (Exception e) {
        return base + "/ad/" + placementId;
      }
    } catch (Exception e) {
      return null;
    }
  }

  public String getAwesomeAdsEndpoint(ISASession session, int lineItemId, int creativeId) {
    try {
      String base = session.getBaseUrl();
      try {
        char last = base.charAt(base.length() - 1);
        String separator = last == '/' ? "" : "/";
        return base + separator + "ad/" + lineItemId + "/" + creativeId;
      } catch (Exception e) {
        return base + "ad/" + lineItemId + "/" + creativeId;
      }
    } catch (Exception e) {
      return null;
    }
  }

  public JSONObject getAwesomeAdsQuery(ISASession session) {
    try {
      return SAJsonParser.newObject(
          "test", session.getTestMode(),
          "sdkVersion", session.getVersion(),
          "rnd", session.getCachebuster(),
          "bundle", session.getPackageName(),
          "name", session.getAppName(),
          "dauid", session.getDauId(),
          "ct", session.getConnectionType().ordinal(),
          "lang", session.getLang(),
          "device", session.getDevice(),
          "pos", session.getPos().getValue(),
          "skip", session.getSkip().getValue(),
          "playbackmethod", session.getPlaybackMethod().getValue(),
          "startdelay", session.getStartDelay().getValue(),
          "instl", session.getInstl().getValue(),
          "w", session.getWidth(),
          "h", session.getHeight()
          // "preload", true
          );
    } catch (Exception e) {
      return new JSONObject();
    }
  }

  public JSONObject getAwesomeAdsHeader(ISASession session) {
    try {
      return SAJsonParser.newObject(
          "Content-Type", "application/json", "User-Agent", session.getUserAgent());
    } catch (Exception e) {
      return new JSONObject();
    }
  }

  /**
   * Shorthand method that only takes a placement Id and a session
   *
   * @param placementId the AwesomeAds ID to load an ad for
   * @param session the current session to load the placement Id for
   * @param listener listener copy so that the loader can return the response to the library user
   */
  public void loadAd(
      final int placementId, final ISASession session, final SALoaderInterface listener) {

    // get connection things to AwesomeAds
    String endpoint = getAwesomeAdsEndpoint(session, placementId);
    JSONObject query = getAwesomeAdsQuery(session);
    JSONObject header = getAwesomeAdsHeader(session);

    // call to the load ad method
    loadAd(endpoint, query, header, placementId, session.getConfiguration(), listener);
  }

  /**
   * Shorthand method that only takes a placement Id and a session
   *
   * @param placementId the AwesomeAds ID to load an ad for
   * @param session the current session to load the placement Id for
   * @param listener listener copy so that the loader can return the response to the library user
   */
  public void loadAd(
      int placementId,
      int lineItemId,
      int creativeId,
      final ISASession session,
      final SALoaderInterface listener) {

    // get connection things to AwesomeAds
    String endpoint = getAwesomeAdsEndpoint(session, lineItemId, creativeId);
    JSONObject query = getAwesomeAdsQuery(session);
    JSONObject header = getAwesomeAdsHeader(session);

    // call to the load ad method
    loadAd(endpoint, query, header, placementId, session.getConfiguration(), listener);
  }

  /**
   * Method that actually loads the ad
   *
   * @param endpoint endpoint from where to get an ad resource
   * @param query additional query parameters
   * @param header request header
   * @param placementId placement Id (bc it's not returned by the request)
   * @param listener listener copy so that the loader can return the response to the library user
   */
  public void loadAd(
      final String endpoint,
      final JSONObject query,
      JSONObject header,
      final int placementId,
      final SAConfiguration configuration,
      final SALoaderInterface listener) {

    // create a local listener to avoid null pointer exceptions
    final SALoaderInterface localListener =
        listener != null
            ? listener
            : response -> {};

    SANetwork network = new SANetwork(executor, timeout);
    network.sendGET(
        endpoint,
        query,
        header,
            (status, data, success) -> {

              if (!isDebug) {
                Log.d(
                    "SuperAwesome",
                    success
                        + " | "
                        + status
                        + " | "
                        + endpoint
                        + "?"
                        + SAUtils.formGetQueryFromDict(query));
              }

              processAd(placementId, data, status, configuration, localListener);
            });
  }

  /**
   * @param placementId the AwesomeAds ID to load an ad for
   * @param creativeId - the creative id [SACreatve]
   * @param lineItemId - The line Item id
   * @param session the current session to load the placement Id for
   * @param listener listener copy so that the loader can return the response to the library user
   */
  public void loadAdForLineItem(
          final int placementId,
          final int lineItemId,
          final int creativeId,
          @NonNull final ISASession session,
          @NonNull final SALoaderInterface listener) {

    // get connection things to AwesomeAds
    String endpoint = session.getBaseUrl() + "/ads/" + lineItemId + "/" + creativeId;
    JSONObject query = getAwesomeAdsQuery(session);
    JSONObject header = getAwesomeAdsHeader(session);

    // call to the load ad method
    loadAd(endpoint, query, header, placementId, session.getConfiguration(), listener);
  }

  public void processAd(
      int placementId,
      String data,
      int status,
      SAConfiguration configuration,
      SALoaderInterface listener) {

    // create a local listener to avoid null pointer exceptions
    final SALoaderInterface localListener =
        listener != null
            ? listener
            : response -> {};

    // create a new object of type SAResponse
    final SAResponse response = new SAResponse();
    response.status = status;
    response.placementId = placementId;

    // error case, just bail out with a non-null invalid response
    if (data == null) {
      localListener.saDidLoadAd(response);
    }
    // good case, continue trying to figure out what kind of ad this is
    else {

      // declare the two possible json outcomes
      JSONObject jsonObject;

      // try to get json Object
      try {
        jsonObject = new JSONObject(data);
      } catch (JSONException e) {
        jsonObject = new JSONObject();
      }

      // Normal Ad case

      // parse the final ad
      final SAAd ad = new SAAd(placementId, configuration.ordinal(), jsonObject);

      // update type in response as well
      response.format = ad.creative.format;
      response.ads.add(ad);

      switch (ad.creative.format) {
          // in this case return whatever we have at this moment
        case invalid:
          localListener.saDidLoadAd(response);
          break;
          // in this case process the HTML and return the response
        case image:
          ad.creative.details.media.html = SAProcessHTML.formatCreativeIntoImageHTML(ad);
          localListener.saDidLoadAd(response);
          break;
          // in this case process the HTML and return the response
        case rich:
          ad.creative.details.media.html =
              SAProcessHTML.formatCreativeIntoRichMediaHTML(ad, SAUtils.getCacheBuster());
          localListener.saDidLoadAd(response);
          break;
          // in this case process the HTML and return the response
        case tag:
          {
            ad.creative.details.media.html = SAProcessHTML.formatCreativeIntoTagHTML(ad);
            localListener.saDidLoadAd(response);
            break;
          }
          // in this case process the VAST response, download the files and return
        case video:
          {
            Context con = isDebug ? null : context;
            SAVASTParser parser = new SAVASTParser(con, executor, timeout);
            parser.parseVAST(
                ad.creative.details.vast,
                    savastAd -> {

                      // copy the vast data
                      ad.creative.details.media.vastAd = savastAd;
                      // and the exact url to download
                      ad.creative.details.media.url = savastAd.url;
                      // download file
                      SAFileDownloader downloader =
                          new SAFileDownloader(context, executor, isDebug, timeout);
                      downloader.downloadFileFrom(
                          ad.creative.details.media.url,
                              (success, key, playableDiskUrl) -> {

                                ad.creative.details.media.path = playableDiskUrl;
                                ad.creative.details.media.isDownloaded = playableDiskUrl != null;

                                // finally respond with a response
                                localListener.saDidLoadAd(response);
                              });
                    });
            break;
          }
      }
    }
  }
}
