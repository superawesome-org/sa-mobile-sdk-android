package tv.superawesome.sdk.publisher;

import android.util.Log;
import android.view.ViewGroup;

import tv.superawesome.lib.saevents.SAEvents;
import tv.superawesome.lib.saevents.SAViewableModule;
import tv.superawesome.lib.savideoplayer.VideoPlayer;

public class SAVideoEvents implements VideoPlayer.Listener {

    private SAEvents events;

    private Listener listener;

    private boolean isStartHandled = false;
    private boolean is2SHandled = false;
    private boolean isFirstQuartileHandled = false;
    private boolean isMidpointHandled = false;
    private boolean isThirdQuartileHandled = false;
    private boolean is15sHandled = false;

    void reset(SAEvents events) {

        this.events = events;

        isStartHandled = false;
        is2SHandled = false;
        isFirstQuartileHandled = false;
        isMidpointHandled = false;
        isThirdQuartileHandled = false;
        is15sHandled = false;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // VideoPlayer.Listener
    ////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onPrepared(VideoPlayer videoPlayer, int time, int duration) {
        events.startMoatTrackingForVideoPlayer(videoPlayer.getSurface(), duration);
    }

    @Override
    public void onTimeUpdated(VideoPlayer videoPlayer, int time, int duration) {
        // Start
        if (time >= 1 && !isStartHandled) {
            isStartHandled = true;

            // send callback
            if (listener != null) {
                listener.onShow();
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

            if (videoPlayer instanceof ViewGroup) {
                ViewGroup player = (ViewGroup) videoPlayer;
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
    public void onComplete(VideoPlayer videoPlayer, int time, int duration) {
        // send events
        events.sendMoatCompleteEvent(duration);
        events.triggerVASTCompleteEvent();

        // stop moat events
        events.stopMoatTrackingForVideoPlayer();

        // send an ad ended event
        if (listener != null) {
            listener.onCompleted();
        } else {
            Log.w("AwesomeAds", "Video Ad listener not implemented. Should have been adEnded");
        }
    }

    @Override
    public void onError(VideoPlayer videoPlayer, Throwable throwable, int time, int duration) {
        // send events
        events.stopMoatTrackingForVideoPlayer();
        events.triggerVASTErrorEvent();

        // ad failed to show
        if (listener != null) {
            listener.onError();
        } else {
            Log.w("AwesomeAds", "Video Ad listener not implemented. Should have been adFailedToShow");
        }
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    interface Listener {
        void onShow();
        void onCompleted();
        void onError();
    }
}
