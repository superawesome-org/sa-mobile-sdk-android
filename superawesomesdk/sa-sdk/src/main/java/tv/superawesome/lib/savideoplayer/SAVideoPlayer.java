package tv.superawesome.lib.savideoplayer;

import android.annotation.TargetApi;
import android.support.annotation.Nullable;
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

import java.io.IOException;

import tv.superawesome.sdk.SuperAwesome;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class SAVideoPlayer extends Fragment implements
        MediaController.MediaPlayerControl,
        MediaPlayer.OnPreparedListener,
        MediaPlayer.OnErrorListener,
        MediaPlayer.OnCompletionListener {

    /** View elements for the Video Player */
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

    /**
     * This function **WILL** get called only once - when the SAVideoPlayer fragment gets created
     * @param savedInstanceState the previous saved state
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        Log.d("SuperAwesome", "onCreate");
        /** create the media player here because it needs to be set just once */
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnErrorListener(this);
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnPreparedListener(this);
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
        Log.d("SuperAwesome", "onCreateView");
        FrameLayout frameLayout = new FrameLayout(getActivity());
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        frameLayout.setBackgroundColor(Color.BLACK);

        surfaceView = new SurfaceView(getActivity());
        surfaceView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        surfaceView.setZOrderOnTop(true);
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                mediaPlayer.setDisplay(holder);
                setSurfaceSize();

//                if (isStarted) mediaPlayer.start();

                /** setup media controller */
                controller = new SAMediaController(getActivity());
                controller.shouldShowCloseButton = shouldShowCloseButton;
                controller.shouldShowPadlock = shouldShowPadlock;
                controller.setAnchorView((View) surfaceView.getParent());
                controller.setMediaPlayer(SAVideoPlayer.this);
                controller.show();
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
                mediaPlayer.setDisplay(null);
            }
        });
        frameLayout.addView(surfaceView);

        return frameLayout;
    }

    @Override
    public void onDestroyView() {
        controller.shouldNotHide = false;
//        controller.hide();
//        mediaPlayer.pause();
        super.onDestroyView();
    }

    private void setSurfaceSize() {
        /** get the dimensions of the video (only valid when surfaceView is set) */
        float videoWidth = mediaPlayer.getVideoWidth();
        float videoHeight = mediaPlayer.getVideoHeight();

        /** get the dimensions of the container (the surfaceView's parent in this case) */
        View container = (View) surfaceView.getParent();
        float containerWidth = container.getWidth();
        float containerHeight = container.getHeight();

        /** set dimensions to surfaceView's layout params (maintaining aspect ratio) */
        android.view.ViewGroup.LayoutParams lp = surfaceView.getLayoutParams();
        lp.width = (int) containerWidth;
        lp.height = (int) ((videoHeight / videoWidth) * containerWidth);
        if(lp.height > containerHeight) {
            lp.width = (int) ((videoWidth / videoHeight) * containerHeight);
            lp.height = (int) containerHeight;
        }
        int left = (((int)containerWidth - lp.width) / 2);
        int top = (((int)containerHeight - lp.height) / 2);

        if (lp instanceof ViewGroup.MarginLayoutParams) {
            final ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)lp;
            marginLayoutParams.setMargins(left, top, 0, 0);
        }

        surfaceView.setLayoutParams(lp);
    }

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
            Log.d("SuperAwesome", "Start SAVideoPlayer " + videoURL);
            mediaPlayer.setDataSource(getActivity(), Uri.parse(videoURL));
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close(){
        pause();
        mediaPlayer.setDisplay(null);
    }

    /**
     * Function used by other components to update the listener
     * @param listener
     */
    public void setListener(SAVideoPlayerListener listener) {
        this.listener = listener;
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
        return mediaPlayer.getDuration();
    }

    @Override
    public int getCurrentPosition() {
        return mediaPlayer.getCurrentPosition();
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
     * <MediaPlayer.OnPreparedListener> implementation
     * ************************************************************
     */

    @Override
    public void onPrepared(MediaPlayer mp) {
        mediaPlayer.start();
        setSurfaceSize();

        if (listener != null && !isReadyHandled){
            isReadyHandled = true;
            listener.didFindPlayerReady();
            isStarted = true;
            if (controller != null) {
                controller.chronographer.setText("Ad: " + getDuration() / 1000);
            }
        }

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isPlaying()) return;

                current = getCurrentPosition();
                duration = getDuration();

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

                handler.postDelayed(this, 1000);
            }
        }, 1000);
    }

    /**
     * *********************************************************************************************
     * MediaPlayer.OnCompletedListener
     * *********************************************************************************************
     */
    @Override
    public void onCompletion(MediaPlayer mp) {
        if (listener != null && !isCompleteHandled) {
            isCompleteHandled = true;
            listener.didReachEnd();
        }
    }

    /**
     * *********************************************************************************************
     * MediaPlayer.OnErrorListener
     * *********************************************************************************************
     */
    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        if (listener != null && !isErrorHandled){
            isErrorHandled = true;
            listener.didPlayWithError();
        }
        return true;
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
