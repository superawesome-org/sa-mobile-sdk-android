package tv.superawesome.lib.savast.savastplayer;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.VideoView;

import tv.superawesome.lib.sautils.SALog;

/**
 * Created by gabriel.coman on 23/12/15.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class SAVASTPlayer extends Fragment {

    // subviews and other Fragment parameters
    private VideoView videoPlayer;
    private String videoURL;
    private SAVASTPlayerListener listener;

    // other helper private vars
    private int duration = 0;
    private int currentTime = 0;
    private int startTime = 0;
    private int firstQuartileTime;
    private int midpointTime;
    private int thirdQuartileTime;
    private int completeTime;
    private boolean isStartHandled = false;
    private boolean isFirstQuartileHandled = false;
    private boolean isMidpointHandled = false;
    private boolean isThirdQuartileHandled = false;
    private boolean isSkipHandled = false;
    private boolean isCompleteHandled = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        videoPlayer = new VideoView(getActivity().getBaseContext());
        FrameLayout.LayoutParams linearLayout = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//      FrameLayout.LayoutParams linearLayout = new FrameLayout.LayoutParams(640, 480);
        videoPlayer.setLayoutParams(linearLayout);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // return the video Player view
        return videoPlayer;
    }

    /**
     * @brief Main playing function
     * @param videoURL the URL to play
     */
    public void playWithMediaURL(String videoURL){
        this.videoURL = videoURL;

        try {
            // Get the URL from String VideoURL
            Uri video = Uri.parse(videoURL);
            videoPlayer.setVideoURI(video);
            videoPlayer.setBackgroundColor(0xff0000);

        } catch (Exception e) {
            e.printStackTrace();

            if (listener != null) {
                listener.didPlayWithError();
            }
        }

        // request focus
        videoPlayer.requestFocus();

        // on ready
        videoPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            // play the video
            public void onPrepared(MediaPlayer mp) {
                // start player
                videoPlayer.start();

                // calculate time values
                duration = videoPlayer.getDuration() / 1000;
                startTime = 0;
                firstQuartileTime = duration / 4;
                midpointTime = duration / 2;
                thirdQuartileTime = 3 * duration / 4;
                completeTime = duration;
                currentTime = 0;

                // call listeners
                if (listener != null){
                    listener.didFindPlayerReady();
                }

                // part with seconds and stuff
                final Runnable onEverySecond = new Runnable() {
                    public void run() {
                        // get current time
                        currentTime = videoPlayer.getCurrentPosition() / 1000;

                        if (currentTime >= 1 && !isStartHandled){
                            isStartHandled = true;

                            if (listener != null){
                                listener.didStartPlayer();
                            }
                        }

                        if (currentTime >= firstQuartileTime && !isFirstQuartileHandled){
                            isFirstQuartileHandled = true;

                            if (listener != null){
                                listener.didReachFirstQuartile();
                            }
                        }

                        if (currentTime >= midpointTime && !isMidpointHandled){
                            isMidpointHandled = true;

                            if (listener != null){
                                listener.didReachMidpoint();
                            }
                        }

                        if (currentTime >= thirdQuartileTime && !isThirdQuartileHandled){
                            isThirdQuartileHandled = true;

                            if (listener != null){
                                listener.didReachThirdQuartile();
                            }
                        }

                        if (videoPlayer.isPlaying()) {
                            videoPlayer.postDelayed(this, 1000);
                        }
                    }
                };
                // start monitoring
                videoPlayer.postDelayed(onEverySecond, 1000);
            }
        });

        // on complete
        videoPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (listener != null) {
                    listener.didReachEnd();
                }
            }
        });

        videoPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                if (listener != null){
                    listener.didPlayWithError();
                }
                return false;
            }
        });
    }

    public void setListener(SAVASTPlayerListener listener){
        this.listener = listener;
    }
}
