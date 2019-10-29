package tv.superawesome.sdk.publisher.video;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.Display;
import android.widget.RelativeLayout;

import java.io.File;

public class VideoUtils {

    /**
     * @return the current scale
     */
    public float getScale(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();

        if (context instanceof Activity) {
            Display display = ((Activity)context).getWindowManager().getDefaultDisplay();
            display.getMetrics(metrics);
            return (float)metrics.densityDpi / 160.0F;
        } else {
            return 1.0F;
        }
    }

    /**
     * Method that gets the video view proper layout params so it maintains aspect ratio
     *
     * @param sourceW   the video width
     * @param sourceH   the video height
     * @param boundingW the container width
     * @param boundingH the container height
     * @return          the FrameLayout.LayoutParams needed by the video
     */
    public RelativeLayout.LayoutParams getVideoViewLayoutParams(float sourceW, float sourceH, float boundingW, float boundingH) {
        float sourceRatio = sourceW / sourceH;
        float boundingRatio = boundingW / boundingH;
        float X, Y, W, H;
        if(sourceRatio > boundingRatio) {
            W = boundingW;
            H = W / sourceRatio;
            X = 0.0F;
            Y = (boundingH - H) / 2.0F;
        } else {
            H = boundingH;
            W = sourceRatio * H;
            Y = 0.0F;
            X = (boundingW - W) / 2.0F;
        }

        RelativeLayout.LayoutParams returnParams = new RelativeLayout.LayoutParams((int)W, (int)H);
        returnParams.setMargins((int)X, (int)Y, 0, 0);

        return returnParams;
    }

    public Uri getUriFromFile(Context context, String path) throws Exception {
        if (context == null) {
            throw new Exception("Fragment not prepared yet! Await the 'Video_Prepared' event in order to play.");
        } else {
            File file = new File(context.getFilesDir(), path);
            if (file.exists()) {
                String videoURL = file.toString();
                return Uri.parse(videoURL);
            } else {
                throw new Exception("File " + path + " does not exist on disk. Will not play!");
            }
        }
    }
}
