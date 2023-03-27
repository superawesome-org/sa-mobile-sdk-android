package tv.superawesome.sdk.publisher;

import android.view.ViewGroup;

import tv.superawesome.lib.saevents.SAEvents;
import tv.superawesome.sdk.publisher.videoPlayer.IVideoPlayer;

public class SAVideoEvents {

  public interface Listener {
    void hasBeenVisible();
  }

  private final SAEvents events;

  public Listener listener;

  private boolean isStartHandled = false;
  private boolean is2SHandled = false;
  private boolean isFirstQuartileHandled = false;
  private boolean isMidpointHandled = false;
  private boolean isThirdQuartileHandled = false;

  public SAVideoEvents(SAEvents events, Listener listener) {
    this.events = events;
    this.listener = listener;
  }

  public void prepare(IVideoPlayer videoPlayer, int time, int duration) {}

  public void complete(IVideoPlayer videoPlayer, int time, int duration) {
    events.triggerVASTCompleteEvent();
  }

  public void error(IVideoPlayer videoPlayer, int time, int duration) {
    events.triggerVASTErrorEvent();
  }

  public void time(IVideoPlayer videoPlayer, int time, int duration) {
    // Start
    if (videoPlayer instanceof ViewGroup) {
      ViewGroup player = (ViewGroup) videoPlayer;
      events.checkViewableStatusForVideo(
          player,
          isViewable -> {
            if (isViewable) {
              events.triggerDwellTime();
            }
          });
    }

    if (time >= 1 && !isStartHandled) {
      isStartHandled = true;

      // send vast events - including impression
      events.triggerVASTImpressionEvent();
      events.triggerVASTStartEvent();
      events.triggerVASTCreativeViewEvent();
    }
    // 2 second (viewability)
    if (time >= 2000 && !is2SHandled) {
      is2SHandled = true;

      if (videoPlayer instanceof ViewGroup) {
        ViewGroup player = (ViewGroup) videoPlayer;
        events.checkViewableStatusForVideo(
            player,
            isViewable -> {
              if (isViewable) {
                events.triggerViewableImpressionEvent();
                events.triggerDwellTime();
                if (listener != null) {
                  listener.hasBeenVisible();
                }
              }
            });
      }
    }
    // 1/4
    if (time >= duration / 4 && !isFirstQuartileHandled) {
      isFirstQuartileHandled = true;

      // send events
      events.triggerVASTFirstQuartileEvent();
    }
    // 1/2
    if (time >= duration / 2 && !isMidpointHandled) {
      isMidpointHandled = true;

      // send events
      events.triggerVASTMidpointEvent();
    }
    // 3/4
    if (time >= 3 * duration / 4 && !isThirdQuartileHandled) {
      isThirdQuartileHandled = true;

      // send events
      events.triggerVASTThirdQuartileEvent();
    }
  }
}
