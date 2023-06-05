package tv.superawesome.lib.saevents;

import android.util.Log;
import android.view.ViewGroup;

import tv.superawesome.lib.sametrics.SAPerformanceMetrics;
import tv.superawesome.lib.samodelspace.saad.SAAd;
import tv.superawesome.lib.sasession.session.ISASession;

public class SAEvents {
    private SAServerModule              serverModule;
    private SAVASTModule                vastModule;
    private SAViewableModule            viewableModule;

    private SAPerformanceMetrics        performanceMetrics;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Set & unset the ad needed for triggering events
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public void setAd (ISASession session, SAAd ad) {
        serverModule = new SAServerModule(ad, session);
        vastModule = new SAVASTModule(ad);
        viewableModule = new SAViewableModule();
        performanceMetrics = new SAPerformanceMetrics(session);
    }

    public void unsetAd () {
        serverModule = null;
        vastModule = null;
        viewableModule = null;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Normal ad events
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public void triggerClickEvent () {
        if (serverModule != null) {
            serverModule.triggerClickEvent(null);
            Log.d("Event_Tracking", "click");
        }
    }

    public void triggerImpressionEvent () {
        if (serverModule != null) {
            serverModule.triggerImpressionEvent(null);
            Log.d("Event_Tracking", "impression");
        }
    }

    public void triggerDwellTime(){
        if (serverModule != null) {
            serverModule.triggerDwellEvent(null);
            Log.d("Event_Tracking", "dwellTime");
        }
    }

    public void triggerViewableImpressionEvent () {
        if (serverModule != null) {
            serverModule.triggerViewableImpressionEvent(null);
            Log.d("Event_Tracking", "viewableImpression");
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Parental Gate Events
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public void triggerPgOpenEvent () {
        if (serverModule != null) {
            serverModule.triggerPgOpenEvent(null);
        }
    }

    public void triggerPgCloseEvent () {
        if (serverModule != null) {
            serverModule.triggerPgCloseEvent(null);
        }
    }

    public void triggerPgFailEvent () {
        if (serverModule != null) {
            serverModule.triggerPgFailEvent(null);
        }
    }

    public void triggerPgSuccessEvent () {
        if (serverModule != null) {
            serverModule.triggerPgSuccessEvent(null);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // VAST Events
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public String getVASTClickThroughEvent () {
        return vastModule != null ? vastModule.getVASTClickThroughEvent() : "";
    }

    public void triggerVASTClickThroughEvent () {
        if (vastModule != null) {
            vastModule.triggerVastClickThroughEvent(null);
            Log.d("Event_Tracking", "vast_click_through");
        }
    }

    public void triggerVASTErrorEvent () {
        if (vastModule != null) {
            vastModule.triggerVASTErrorEvent(null);
            Log.d("Event_Tracking", "vast_error");
        }
    }

    public void triggerVASTImpressionEvent () {
        if (vastModule != null) {
            vastModule.triggerVASTImpressionEvent(null);
            Log.d("Event_Tracking", "vast_impression");
        }
    }

    public void triggerVASTCreativeViewEvent () {
        if (vastModule != null) {
            vastModule.triggerVASTCreativeViewEvent(null);
            Log.d("Event_Tracking", "vast_creativeView");
        }
    }

    public void triggerVASTStartEvent () {
        if (vastModule != null) {
            vastModule.triggerVASTStartEvent(null);
            Log.d("Event_Tracking", "vast_start");
        }
    }

    public void triggerVASTFirstQuartileEvent () {
        if (vastModule != null) {
            vastModule.triggerVASTFirstQuartileEvent(null);
            Log.d("Event_Tracking", "vast_firstQuartile");
        }
    }

    public void triggerVASTMidpointEvent () {
        if (vastModule != null) {
            vastModule.triggerVASTMidpointEvent(null);
            Log.d("Event_Tracking", "vast_midpoint");
        }
    }

    public void triggerVASTThirdQuartileEvent () {
        if (vastModule != null) {
            vastModule.triggerVASTThirdQuartileEvent(null);
            Log.d("Event_Tracking", "vast_thirdQuartile");
        }
    }

    public void triggerVASTCompleteEvent () {
        if (vastModule != null) {
            vastModule.triggerVASTCompleteEvent(null);
            Log.d("Event_Tracking", "vast_complete");
        }
    }

    public void triggerVASTClickTrackingEvent () {
        if (vastModule != null) {
            vastModule.triggerVASTClickTrackingEvent(null);
            Log.d("Event_Tracking", "vast_click_tracking");
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Viewable impression
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public boolean isChildInRect (ViewGroup layout) {
        return viewableModule != null && viewableModule.isChildInRect(layout);
    }

    public void checkViewableStatusForDisplay (ViewGroup layout, SAViewableModule.Listener listener) {
        if (viewableModule != null) {
            viewableModule.checkViewableStatusForDisplay (layout, listener);
        }
    }

    public void checkViewableStatusForVideo (ViewGroup layout, SAViewableModule.Listener listener) {
        if (viewableModule != null) {
            viewableModule.checkViewableStatusForVideo (layout, listener);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Performance Metrics
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public void startTimingForCloseButtonPressed() {
        if (performanceMetrics != null) {
            performanceMetrics.startTimingForCloseButtonPressed();
        }
    }

    public void trackCloseButtonPressed () {
        if (performanceMetrics != null) {
            performanceMetrics.trackCloseButtonPressed();
        }
    }
}
