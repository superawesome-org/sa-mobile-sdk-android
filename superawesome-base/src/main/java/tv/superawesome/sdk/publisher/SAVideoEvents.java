package tv.superawesome.sdk.publisher;

import android.view.ViewGroup;

import tv.superawesome.lib.saevents.SAEvents;
import tv.superawesome.lib.saevents.SAViewableModule;
import tv.superawesome.lib.savideoplayer.VideoPlayer;

public class SAVideoEvents {

    private SAEvents events;

    private boolean isStartHandled = false;
    private boolean is2SHandled = false;
    private boolean isFirstQuartileHandled = false;
    private boolean isMidpointHandled = false;
    private boolean isThirdQuartileHandled = false;

    void reset(SAEvents events) {

        this.events = events;

        isStartHandled = false;
        is2SHandled = false;
        isFirstQuartileHandled = false;
        isMidpointHandled = false;
        isThirdQuartileHandled = false;
    }

    public void prepare(VideoPlayer videoPlayer, int time, int duration) {
        events.startMoatTrackingForVideoPlayer(videoPlayer.getSurface(), duration);
    }

    public void complete(VideoPlayer videoPlayer, int time, int duration) {
        events.sendMoatCompleteEvent(duration);
        events.triggerVASTCompleteEvent();
        events.stopMoatTrackingForVideoPlayer();
    }

    public void error(VideoPlayer videoPlayer, int time, int duration) {
        events.stopMoatTrackingForVideoPlayer();
        events.triggerVASTErrorEvent();
    }

    public void time(VideoPlayer videoPlayer, int time, int duration) {
        // Start
        if (time >= 1 && !isStartHandled) {
            isStartHandled = true;

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
    }
}
