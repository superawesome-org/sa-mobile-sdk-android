package tv.superawesome.lib.savideoplayer;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Environment;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.graphics.Rect;
import android.app.Fragment;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;

import java.io.File;
import java.io.IOException;

import tv.superawesome.lib.sautils.SAUtils;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class SAVideoPlayer extends Fragment implements MediaController.MediaPlayerControl  {

    /** View elements for the Video Player */
    private FrameLayout containerView = null;
    private SurfaceView surfaceView = null;
    private MediaPlayer mediaPlayer = null;
    private SAMediaController controller = null;

    /** bool vars for each step */
    private boolean isReadyHandled = false;
    private boolean isStartHandled = false;
    private boolean isFirstQuartileHandled = false;
    private boolean isMidpointHandled = false;
    private boolean isThirdQuartileHandled = false;
    private boolean isErrorHandled = false;
    private boolean isCompleteHandled = false;

    /** listeners & other configuration variables */
    public SAVideoPlayerListener listener = null;
    public boolean shouldShowPadlock = true;
    public boolean shouldShowCloseButton = false;
    public String videoURL = null;

    /** current time */
    private int current = 0;
    private int duration = 0;
    private boolean isStarted = false;
    private boolean isReady = false;

    /**
     * This function **WILL** get called only once - when the SAVideoPlayer fragment gets created
     * @param savedInstanceState the previous saved state
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        /** create the media player here because it needs to be set just once */
        mediaPlayer = new MediaPlayer();

        /** add prepared listener */
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(final MediaPlayer mp) {
                setSurfaceSize();
                isReady = true;
                mp.start();

                if (listener != null && !isReadyHandled) {
                    isReadyHandled = true;
                    listener.didFindPlayerReady();
                    isStarted = true;
                    if (controller != null) {
                        controller.chronographer.setText("Ad: " + mp.getDuration() / 1000);
                    }
                }

                final Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        /** don't continue if completed */
                        if (isCompleteHandled) return;

                        current = mp.getCurrentPosition();
                        duration = mp.getDuration();

                        if (current >= 1 && !isStartHandled && listener != null) {
                            isStartHandled = true;
                            listener.didStartPlayer();
                        }
                        if (current >= duration / 4 && !isFirstQuartileHandled && listener != null) {
                            isFirstQuartileHandled = true;
                            listener.didReachFirstQuartile();
                        }
                        if (current >= duration / 2 && !isMidpointHandled && listener != null) {
                            isMidpointHandled = true;
                            listener.didReachMidpoint();
                        }
                        if (current >= 3 * duration / 4 && !isThirdQuartileHandled && listener != null) {
                            isThirdQuartileHandled = true;
                            listener.didReachThirdQuartile();
                        }

                        if (controller != null) {
                            int remaining = (duration - current) / 1000;
                            controller.chronographer.setText("Ad: " + remaining);
                        }

                        handler.postDelayed(this, 500);
                    }
                }, 500);
            }
        });

        /** add error listener */
        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                if (listener != null && !isErrorHandled) {
                    isErrorHandled = true;
                    listener.didPlayWithError();
                }
                return true;
            }
        });

        /** add completion listener */
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (listener != null && !isCompleteHandled) {
                    isCompleteHandled = true;
                    listener.didReachEnd();
                    close();
                }
            }
        });
    }

    /**
     * This function **WILL** get called each time the screen is drawn (e.g. orientation change)
     * @param inflater standard inflater
     * @param container view group
     * @param savedInstanceState the saved state
     * @return the current VideoPlayer view that needs to be drawn
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        /** create container */
        containerView = new FrameLayout(getActivity());
        containerView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        containerView.setBackgroundColor(Color.BLACK);

        /** create surface */
        surfaceView = new SurfaceView(getActivity());
        surfaceView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        surfaceView.setZOrderOnTop(true);
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                /** don't go here when video is completed */
                if (isCompleteHandled) return;

                /** final media player display setup */
                mediaPlayer.setDisplay(holder);
                setSurfaceSize();

                /** setup media controller */
                controller = new SAMediaController(getActivity());
                controller.shouldNotHide = true;
                controller.shouldShowCloseButton = shouldShowCloseButton;
                controller.shouldShowPadlock = shouldShowPadlock;
                controller.setMediaPlayer(SAVideoPlayer.this);
                controller.setAnchorView(containerView);
                controller.show(0);
                controller.showMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            listener.didGoToURL();
                        }
                    }
                });
                if (controller.close != null) {
                    controller.close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (listener != null) {
                                listener.didClickOnClose();
                            }
                        }
                    });
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                if (isCompleteHandled) return;
                controller.removeAllViews();
                mediaPlayer.setDisplay(null);
            }
        });


        containerView.addView(surfaceView);

        return containerView;
    }

    @Override
    public void onStop() {
        super.onStop();
        controller.shouldNotHide = false;
        controller.hide();
        if (isCompleteHandled || !isReady) return;
        mediaPlayer.pause();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (isCompleteHandled || !isReady) return;
        mediaPlayer.start();
    }

    /**
     * Main "play" function; if this function is not called, the player **WILL NEVER** display video content
     * @param url - the URL to play
     */
    public void playWithMediaURL(String url) {
        videoURL = url;

        if (videoURL == null) {
            if (listener != null && !isErrorHandled){
                isErrorHandled = true;
                listener.didPlayWithError();
            }
            return;
        }

        try {
            mediaPlayer.setDataSource(getActivity(), Uri.parse(videoURL));
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void playWithDiskURL(String path) {
        File sdCard = Environment.getExternalStorageDirectory();
        File dir = new File (sdCard.getAbsolutePath() + "/satmofolder");
        File file = new File(dir, path);
        videoURL = file.toString();

        try {
            mediaPlayer.setDataSource(getActivity(), Uri.parse(videoURL));
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * The "close" function; opposite of the play function
     */
    public void close(){
        isCompleteHandled = true;
        mediaPlayer.stop();
        mediaPlayer.setDisplay(null);
    }

    /**
     * Aux surface size function that calculates the video surface size needed in order to
     * maintain correct aspect ratio
     */
    private void setSurfaceSize() {
        /** get dimensions */
        float videoWidth = mediaPlayer.getVideoWidth();
        float videoHeight = mediaPlayer.getVideoHeight();
        float containerWidth = containerView.getWidth();
        float containerHeight = containerView.getHeight();

        /** set frame */
        Rect newFrame = SAUtils.mapOldSizeIntoNewSize(containerWidth, containerHeight, videoWidth, videoHeight);
        android.view.ViewGroup.LayoutParams lp = surfaceView.getLayoutParams();
        lp.width = newFrame.right;
        lp.height = newFrame.bottom;
        if (lp instanceof ViewGroup.MarginLayoutParams) {
            final ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)lp;
            marginLayoutParams.setMargins(newFrame.left, newFrame.top, 0, 0);
        }

        surfaceView.setLayoutParams(lp);
    }

    /**
     * *********************************************************************************************
     * MediaController.MediaPlayerControl
     * *********************************************************************************************
     */
    @Override
    public void start() {
        mediaPlayer.start();
    }

    @Override
    public void pause() {
        mediaPlayer.pause();
    }

    @Override
    public int getDuration() {
        return 0;
    }

    @Override
    public int getCurrentPosition() {
        return 0;
    }

    @Override
    public void seekTo(int pos) {
        mediaPlayer.seekTo(pos);
    }

    @Override
    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public boolean canPause() {
        return false;
    }

    @Override
    public boolean canSeekBackward() {
        return false;
    }

    @Override
    public boolean canSeekForward() {
        return false;
    }

    @Override
    public int getAudioSessionId() {
        return 0;
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
        void didClickOnClose();
    }
}
