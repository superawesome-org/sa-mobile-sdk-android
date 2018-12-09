package tv.superawesome.sdk.publisher;

import android.util.Log;

import tv.superawesome.lib.saevents.SAEvents;
import tv.superawesome.lib.saevents.SAViewableModule;
import tv.superawesome.lib.samodelspace.saad.SAAd;
import tv.superawesome.lib.sasession.session.SASession;
import tv.superawesome.lib.savideoplayer.AwesomeVideoPlayer;
import tv.superawesome.lib.savideoplayer.MediaControl;

public class SAVideoEvents implements MediaControl.Listener {

    private SAEvents events;

    private int placementId;
    private SAInterface listener;

    private boolean isStartHandled = false;
    private boolean is2SHandled = false;
    private boolean isFirstQuartileHandled = false;
    private boolean isMidpointHandled = false;
    private boolean isThirdQuartileHandled = false;
    private boolean is15sHandled = false;

    void reset(int placementId,
               SAAd ad,
               SASession session,
               boolean isMoatLimitingEnabled) {

        this.placementId = placementId;

        events = new SAEvents();
        events.setAd(session, ad);
        if (!isMoatLimitingEnabled) {
            events.disableMoatLimiting();
        }
        
        isStartHandled = false;
        is2SHandled = false;
        isFirstQuartileHandled = false;
        isMidpointHandled = false;
        isThirdQuartileHandled = false;
        is15sHandled = false;
    }
    
    @Override
    public void onPrepared(MediaControl saMediaControl) {
        // start moat tracking
        AwesomeVideoPlayer player = saMediaControl.getVideoPlayerReference();
        if (player != null) {
            events.startMoatTrackingForVideoPlayer(player.getSurface(), saMediaControl.getDuration());
        }
    }

    @Override
    public void onTimeUpdated(MediaControl saMediaControl, int time, int duration) {
        // Start
        if (time >= 1 && !isStartHandled) {
            isStartHandled = true;

            // send callback
            if (listener != null) {
                listener.onEvent(placementId, SAEvent.adShown);
            } else {
                Log.w("AwesomeAds", "Video Ad listener not implemented. Should have been adShown");
            }

            // send vast events - including impression
            events.triggerVASTImpressionEvent();
            events.triggerVASTStartEvent();
            events.triggerVASTCreativeViewEvent();

            // moat
            events.sendMoatPlayingEvent(time);
            events.sendMoatStartEvent(time);
        }
        // 2 second (viewability)
        if (time >= 2000 && !is2SHandled) {
            is2SHandled = true;

            AwesomeVideoPlayer player = saMediaControl.getVideoPlayerReference();
            if (player != null) {
                events.checkViewableStatusForVideo(player, new SAViewableModule.Listener() {
                    @Override
                    public void saDidFindViewOnScreen(boolean isViewable) {
                        if (isViewable) {
                            events.triggerViewableImpressionEvent();
                        }
                    }
                });
            }
        }
        // 1/4
        if (time >= duration / 4 && !isFirstQuartileHandled) {
            isFirstQuartileHandled = true;

            // send events
            events.sendMoatFirstQuartileEvent(time);
            events.triggerVASTFirstQuartileEvent();
        }
        // 1/2
        if (time >= duration / 2 && !isMidpointHandled) {
            isMidpointHandled = true;

            // send events
            events.sendMoatMidpointEvent(time);
            events.triggerVASTMidpointEvent();
        }
        // 3/4
        if (time >= 3 * duration / 4 && !isThirdQuartileHandled) {
            isThirdQuartileHandled = true;

            // send events
            events.sendMoatThirdQuartileEvent(time);
            events.triggerVASTThirdQuartileEvent();
        }
        // end
        if (time >= 15000 && !is15sHandled) {
            is15sHandled = true;
        }
    }

    @Override
    public void onMediaComplete(MediaControl saMediaControl, int time, int duration) {

        saMediaControl.setDisplay(null);
        saMediaControl.reset();

        // send events
        events.sendMoatCompleteEvent(duration);
        events.triggerVASTCompleteEvent();

        // stop moat events
        events.stopMoatTrackingForVideoPlayer();

        // send an ad ended event
        if (listener != null) {
            listener.onEvent(placementId, SAEvent.adEnded);
        } else {
            Log.w("AwesomeAds", "Video Ad listener not implemented. Should have been adEnded");
        }
    }

    @Override
    public void onError(MediaControl saMediaControl, Throwable throwable, int time, int duration) {
        // destroy media control
        saMediaControl.setDisplay(null);
        saMediaControl.reset();

        // send events
        events.stopMoatTrackingForVideoPlayer();
        events.triggerVASTErrorEvent();

        // ad failed to show
        if (listener != null) {
            listener.onEvent(placementId, SAEvent.adFailedToShow);
        } else {
            Log.w("AwesomeAds", "Video Ad listener not implemented. Should have been adFailedToShow");
        }
    }

    @Override
    public void onSeekComplete(MediaControl mediaControl) {
        // N/A
    }

    public void setListener(SAInterface listener) {
        this.listener = listener;
    }

    public SAEvents getEvents() {
        return events;
    }

    public SAInterface getListener() {
        return listener;
    }
}
