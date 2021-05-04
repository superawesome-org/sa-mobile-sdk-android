package tv.superawesome.sdk.publisher.common.ui.video.player

import android.app.Activity
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.provider.Settings
import android.view.OrientationEventListener
import android.widget.RelativeLayout
import tv.superawesome.sdk.publisher.common.ui.video.player.FullscreenMode.Companion.fromValue

class VideoPlayerActivity : Activity() {
    private var videoPlayer: VideoPlayer? = null
    private var canDismissOnRotateToPortrait = false
    var minimisedByButton = false
    private lateinit var orientationListener: OrientationEventListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*
         * Deal with orientation
         */
        canDismissOnRotateToPortrait =
            intent.getBooleanExtra(VideoPlayer.ROTATION_DISMISS_KEY, false)
        orientationListener = object : OrientationEventListener(this) {
            override fun onOrientationChanged(orientation: Int) {
                val epsilon = 10
                val leftLandscape = 90
                val rightLandscape = 270
                if ((
                    epsilonCheck(orientation, leftLandscape, epsilon) ||
                        epsilonCheck(orientation, rightLandscape, epsilon)
                    ) &&
                    Settings.System.getInt(
                            contentResolver,
                            Settings.System.ACCELEROMETER_ROTATION,
                            0
                        ) == 1
                ) {
                    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR
                }
            }

            private fun epsilonCheck(a: Int, b: Int, epsilon: Int): Boolean {
                return a > b - epsilon && a < b + epsilon
            }
        }
        setScreenOrientation()
        if (orientationListener.canDetectOrientation()) {
            orientationListener.enable()
        }
        val size = RelativeLayout.LayoutParams.MATCH_PARENT
        val params = RelativeLayout.LayoutParams(size, size)
        val fullscreenLay = RelativeLayout(this)
        fullscreenLay.id = FULLSCREEN_LAYOUT_ID
        fullscreenLay.layoutParams = params
        setContentView(fullscreenLay)

        /*
         * try getting the video player reference
         */if (VideoPlayer.videoPlayerWeakReference != null) {
            videoPlayer = VideoPlayer.videoPlayerWeakReference!!.get()
        }
        if (videoPlayer != null) {
            /*
             * if it exists, make it the holder
             */
            fullscreenLay.addView(videoPlayer, params)
        } else {
            /*
             * if it doesn't, press back and that's that!
             */
            onBackPressed()
        }
    }

    private fun setScreenOrientation() {
        val modeInt = intent.getIntExtra(VideoPlayer.FULLSCREEN_KEY, FullscreenMode.ANY.value)
        val mode = fromValue(modeInt)
        when (mode) {
            FullscreenMode.PORTRAIT ->
                requestedOrientation =
                    ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT
            FullscreenMode.LANDSCAPE ->
                requestedOrientation =
                    ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT &&
            canDismissOnRotateToPortrait
        ) {
            onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        orientationListener.disable()
        orientationListener
    }

    override fun onBackPressed() {

        /*
         * Go back to previous
         */
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED

        /*
         * if the Android back button is pressed, toggle complex minimisation
         */if (!minimisedByButton) {
            /*
             * Video player was properly referenced in "onCreate"
             */
            if (videoPlayer != null) {
                videoPlayer!!.setMinimised()
            } else {
                super.onBackPressed()
            }
        } else {
            super.onBackPressed()
        }
    }

    companion object {
        const val FULLSCREEN_LAYOUT_ID = 0x1112
    }
}
