// Copyright 2014 Google Inc. All Rights Reserved.

package tv.superawesome.superawesomesdk.views.video;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import java.util.ArrayList;
import java.util.List;

/**
 * A VideoView that intercepts various methods and reports them back via a
 * PlayerCallback.
 */
public class VideoPlayer extends VideoView {

    private enum PlaybackState {
        STOPPED, PAUSED, PLAYING
    }

    private MediaController mMediaController;
    private PlaybackState mPlaybackState;
    private final List<PlayerCallback> mVideoPlayerCallbacks = new ArrayList<PlayerCallback>(1);

    public VideoPlayer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public VideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VideoPlayer(Context context) {
        super(context);
        init();
    }

    private void init() {
        mPlaybackState = PlaybackState.STOPPED;
        mMediaController = new MediaController(getContext());
        mMediaController.setAnchorView(this);

        // Set OnCompletionListener to notify our callbacks when the video is completed.
        super.setOnCompletionListener(new OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                // Reset the MediaPlayer.
                // This prevents a race condition which occasionally results in the media
                // player crashing when switching between videos.
                mediaPlayer.reset();
                mediaPlayer.setDisplay(getHolder());
                mPlaybackState = PlaybackState.STOPPED;

                for (PlayerCallback callback : mVideoPlayerCallbacks) {
                    callback.onCompleted();
                }
            }
        });

        // Set OnErrorListener to notify our callbacks if the video errors.
        super.setOnErrorListener(new OnErrorListener() {

            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                mPlaybackState = PlaybackState.STOPPED;
                for (PlayerCallback callback : mVideoPlayerCallbacks) {
                    callback.onError();
                }

                // Returning true signals to MediaPlayer that we handled the error. This will
                // prevent the completion handler from being called.
                return true;
            }
        });

        this.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    @Override
    public void setOnCompletionListener(OnCompletionListener listener) {
        // The OnCompletionListener can only be implemented by SampleVideoPlayer.
        throw new UnsupportedOperationException();
    }

    @Override
    public void setOnErrorListener(OnErrorListener listener) {
        // The OnErrorListener can only be implemented by SampleVideoPlayer.
        throw new UnsupportedOperationException();
    }

    public void play() {
        start();
    }

    @Override
    public void start() {
        super.start();
        // Fire callbacks before switching playback state.
        switch (mPlaybackState) {
            case STOPPED:
                for (PlayerCallback callback : mVideoPlayerCallbacks) {
                    callback.onPlay();
                }
                break;
            case PAUSED:
                for (PlayerCallback callback : mVideoPlayerCallbacks) {
                    callback.onResume();
                }
                break;
            default:
                // Already playing; do nothing.
        }
        mPlaybackState = PlaybackState.PLAYING;
    }

    @Override
    public void stopPlayback() {
        super.stopPlayback();
        mPlaybackState = PlaybackState.STOPPED;
    }

    @Override
    public void pause() {
        super.pause();
        mPlaybackState = PlaybackState.PAUSED;
        for (PlayerCallback callback : mVideoPlayerCallbacks) {
            callback.onPause();
        }
    }

    public void addPlayerCallback(PlayerCallback callback) {
        mVideoPlayerCallbacks.add(callback);
    }


    /**
     *  Interface for alerting caller of major video events.
     */
    public interface PlayerCallback {

        /**
         * Called when the current video starts playing from the beginning.
         */
        void onPlay();

        /**
         * Called when the current video has completed playback to the end of the video.
         */
        void onCompleted();

        /**
         * Called when an error occurs during video playback.
         */
        void onError();

        /**
         * Called when the video is paused.
         */
        void onPause();

        /**
         * Called when the video is resumed.
         */
        void onResume();
    }
}
