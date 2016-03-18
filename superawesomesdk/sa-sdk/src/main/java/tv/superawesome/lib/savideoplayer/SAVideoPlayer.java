package tv.superawesome.lib.savideoplayer;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.res.Configuration;
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
import tv.superawesome.lib.sautils.SAApplication;

/**
 * Created by gabriel.coman on 23/12/15.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class SAVideoPlayer extends Fragment {

    /** subviews and other Fragment parameters */
    private VideoView videoPlayer;
    private MediaPlayer mediaPlayer;
    private String videoURL;
    private SAVideoPlayerListener listener;

    /** aux views */
    private TextView chronographer;
    private Button findOutMore;

    /** other helper private vars */
    private int duration = 0;
    private int currentTime = 0;
    private int remainingTime = 0;
    private int startTime = 0;
    private int firstQuartileTime;
    private int midpointTime;
    private int thirdQuartileTime;
    private int completeTime;
    private boolean isStartHandled = false;
    private boolean isFirstQuartileHandled = false;
    private boolean isMidpointHandled = false;
    private boolean isThirdQuartileHandled = false;
    private boolean isErrorHandled = false;
    private boolean isSkipHandled = false;
    private boolean isCompleteHandled = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        /** load resource */
        String packageName = SAApplication.getSAApplicationContext().getPackageName();
        int fragment_sa_vastplayerId = getResources().getIdentifier("fragment_sa_vastplayer", "layout", packageName);
        int video_viewId = getResources().getIdentifier("video_view", "id", packageName);
        int cronographerId = getResources().getIdentifier("cronographer", "id", packageName);
        int find_out_moreId = getResources().getIdentifier("find_out_more", "id", packageName);

        View v = inflater.inflate(fragment_sa_vastplayerId, container, false);

        videoPlayer = (VideoView)v.findViewById(video_viewId);
        chronographer = (TextView)v.findViewById(cronographerId);
        findOutMore = (Button)v.findViewById(find_out_moreId);
        findOutMore.setTransformationMethod(null);

        /** Inflate the layout for this fragment */
        return v;
    }

    /**
     * Main playing function
     * @param videoURL the URL to play
     */
    public void playWithMediaURL(final String videoURL){

        /** in case this is null */
        if (videoURL == null){
            if (listener != null){
                listener.didPlayWithError();
            }
            return;
        }

        /** set on click listener */
        findOutMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.didGoToURL();
                }
            }
        });

        /** set the video URL */
        this.videoURL = videoURL;

        try {
            /** Get the URL from String VideoURL */
            Uri video = Uri.parse(videoURL);
            videoPlayer.setVideoURI(video);

        } catch (Exception e) {
            e.printStackTrace();

            if (listener != null) {
                listener.didPlayWithError();
            }
        }

        /** request focus */
        videoPlayer.requestFocus();

        /** on ready */
        videoPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            /** play the video */
            @Override
            public void onPrepared(MediaPlayer mp) {
                /** start player only ince  */
                if (currentTime == 0) {
                    videoPlayer.start();
                }

                /** assign media player */
                mediaPlayer = mp;

                /** calculate time values */
                duration = videoPlayer.getDuration();
                startTime = 0;
                firstQuartileTime = duration / 4;
                midpointTime = duration / 2;
                thirdQuartileTime = 3 * duration / 4;
                completeTime = duration;
                remainingTime = (duration - currentTime) / 1000;

                /** call listeners */
                if (listener != null) {
                    listener.didFindPlayerReady();
                }

                /** set out text */
                chronographer.setText("Ad: " + remainingTime);

                /** part with seconds and stuff */
                final Runnable onEverySecond = new Runnable() {
                    public void run() {
                        if (!videoPlayer.isPlaying()) {
                            return;
                        }

                        /** get current time */
                        currentTime = videoPlayer.getCurrentPosition();
                        remainingTime = (duration - currentTime) / 1000;

                        /** update text */
                        chronographer.setText("Ad: " + remainingTime);

                        if (currentTime >= 1 && !isStartHandled) {
                            isStartHandled = true;

                            if (listener != null) {
                                listener.didStartPlayer();
                            }
                        }

                        if (currentTime >= firstQuartileTime && !isFirstQuartileHandled) {
                            isFirstQuartileHandled = true;

                            if (listener != null) {
                                listener.didReachFirstQuartile();
                            }
                        }

                        if (currentTime >= midpointTime && !isMidpointHandled) {
                            isMidpointHandled = true;

                            if (listener != null) {
                                listener.didReachMidpoint();
                            }
                        }

                        if (currentTime >= thirdQuartileTime && !isThirdQuartileHandled) {
                            isThirdQuartileHandled = true;

                            if (listener != null) {
                                listener.didReachThirdQuartile();
                            }
                        }

                        /** go again */
                        videoPlayer.postDelayed(this, 1000);
                    }
                };
                /** start monitoring */
                videoPlayer.postDelayed(onEverySecond, 1000);

                /** do seeking right */
                mp.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
                    @Override
                    public void onSeekComplete(MediaPlayer mp) {
                        videoPlayer.start();
                    }
                });
            }
        });

        /** on complete */
        videoPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                /** text */
                chronographer.setText("Ad: 0");

                /** call listener function */
                if (listener != null) {
                    listener.didReachEnd();
                }
            }
        });

        videoPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                /** set text */
                chronographer.setText("Ad: Error");

                /** call listener function */
                if (listener != null && !isErrorHandled) {
                    isErrorHandled = true;
                    listener.didPlayWithError();
                }
                return true;
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        if (videoPlayer != null) {
            currentTime = videoPlayer.getCurrentPosition();
            videoPlayer.pause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (videoPlayer != null) {
            videoPlayer.seekTo(currentTime);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    /**
     * Function used by other components to update the listener
     * @param listener
     */
    public void setListener(SAVideoPlayerListener listener) {
        this.listener = listener;
    }

    /**
     * Getters
     */
    public VideoView getVideoPlayer() { return this.videoPlayer; }

    public MediaPlayer getMediaPlayer () { return this.mediaPlayer; }

    /**
     * Closes and deletes the video player
     */
    public void close () {
        videoPlayer.stopPlayback();
    }

    /**
     * ************************************************************
     * Public interface
     * ************************************************************
     */
    public interface SAVideoPlayerListener {

        /**
         * called when the player is read to play
         */
        void didFindPlayerReady();

        /**
         * called when the player actually starts playing
         */
        void didStartPlayer();

        /**
         * called when the player reaches 1/4 of duration
         */
        void didReachFirstQuartile();

        /**
         * called when the player reaches 1/2 of duration
         */
        void didReachMidpoint();

        /**
         * called when the player reaches 3/4 of duration
         */
        void didReachThirdQuartile();

        /**
         * called when the player reaches end
         */
        void didReachEnd();

        /**
         * called when the player plays with error
         */
        void didPlayWithError();

        /**
         * called when the player clicks on the clicker
         */
        void didGoToURL();
    }
}
