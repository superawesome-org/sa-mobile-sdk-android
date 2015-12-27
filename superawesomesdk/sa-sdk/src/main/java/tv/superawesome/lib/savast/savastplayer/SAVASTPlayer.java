package tv.superawesome.lib.savast.savastplayer;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;

import tv.superawesome.sdk.R;

/**
 * Created by gabriel.coman on 23/12/15.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class SAVASTPlayer extends Fragment {

    // subviews and other Fragment parameters
    private VideoView videoPlayer;
    private String videoURL;
    private SAVASTPlayerListener listener;
    private String clickURL;

    // aux views
    private TextView chronographer;
    private Button findOutMore;

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

        // retain instance
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sa_vastplayer, container, false);
    }

    /**
     * Main playing function
     * @param videoURL the URL to play
     */
    public void playWithMediaURL(String videoURL){
        // get the by now inflated video player
        videoPlayer = (VideoView)getActivity().findViewById(R.id.video_view);
        chronographer = (TextView)getActivity().findViewById(R.id.cronographer);
        findOutMore = (Button)getActivity().findViewById(R.id.find_out_more);

        // set on click listener
        findOutMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.didGoToURL(clickURL);
                }
            }
        });

        // set the video URL
        this.videoURL = videoURL;

        try {
            // Get the URL from String VideoURL
            Uri video = Uri.parse(videoURL);
            videoPlayer.setVideoURI(video);

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

                // set out text
                chronographer.setText("Ad: " + duration + "s");

                // part with seconds and stuff
                final Runnable onEverySecond = new Runnable() {
                    public void run() {
                        // get current time
                        currentTime = videoPlayer.getCurrentPosition() / 1000;

                        // update text
                        chronographer.setText("Ad: " + (duration - currentTime) + "s");

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
                // text
                chronographer.setText("Ad: 0s");

                // call listener function
                if (listener != null) {
                    listener.didReachEnd();
                }
            }
        });

        videoPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                // text
                chronographer.setText("Ad: Error");

                // call listener function
                if (listener != null){
                    listener.didPlayWithError();
                }
                return false;
            }
        });
    }

    /**
     * Function used by other components to update the listener
     * @param listener
     */
    public void setListener(SAVASTPlayerListener listener){
        this.listener = listener;
    }

    /**
     * function used by other components to update the URL
     * @param url
     */
    public void setupClickURL(String url) {
        clickURL = url;
    }
}
