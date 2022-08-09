package tv.superawesome.sdk.publisher.video;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import tv.superawesome.sdk.publisher.base.R;
import tv.superawesome.sdk.publisher.videoPlayer.IVideoPlayerControllerView;


public class AdVideoPlayerControllerView extends RelativeLayout implements IVideoPlayerControllerView {

    // constants
    private final static String CRONO_DEF_TXT = "Ad: ";
    private final static String CRONO_INIT_TXT = CRONO_DEF_TXT + "0";
    private final static String FIND_OUT_MORE_TXT = "Find out more »";

    public final ImageView chronoBg;
    public final TextView chronograph;
    public final Button showMore;
    public final Button smallShowMore;
    public final ImageButton padlock;

    public static final int MASK_ID = 0x1110;
    public static final int CRONO_BG_ID = 0x1111;
    public static final int CRONO_ID = 0x1112;
    public static final int SHOW_MORE_ID = 0x1113;
    public static final int SMALL_SHOW_MORE_ID = 0x1114;
    public static final int CLOSE_ID = 0x1115;
    public static final int PADLOCK_ID = 0x1116;

    public AdVideoPlayerControllerView(Context context) {
        this(context, null, 0);
    }

    public AdVideoPlayerControllerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AdVideoPlayerControllerView(Context context, AttributeSet attrs, int defStyleAttr) {
        // call to super
        super(context, attrs, defStyleAttr);

        // create the background image
        // mask, chronograph & the "show more" button
        ImageView mask = VideoComponentFactory.getMask(MASK_ID, this.getContext());
        addView(mask);

        // create the chronograph Bg
        chronoBg = VideoComponentFactory.getChronographBackground(CRONO_BG_ID, this.getContext());
        addView(chronoBg);

        // create the timer label
        chronograph = VideoComponentFactory.getChronograph(CRONO_ID, this.getContext());
        chronograph.setText(CRONO_INIT_TXT);
        addView(chronograph);

        // create the show more button
        showMore = VideoComponentFactory.getClick(SHOW_MORE_ID, this.getContext());
        addView(showMore);

        smallShowMore = VideoComponentFactory.getSmallClick(SMALL_SHOW_MORE_ID, this.getContext());
        smallShowMore.setText(FIND_OUT_MORE_TXT);
        smallShowMore.setVisibility(GONE);
        addView(smallShowMore);

        padlock = VideoComponentFactory.getPadlock(PADLOCK_ID, this.getContext());
        padlock.setContentDescription(context.getString(R.string.superawesome__safe_ad_logo_content_description));
        addView(padlock);
    }


    public void setShouldShowSmallClickButton (boolean value) {
        if (value) {
            smallShowMore.setVisibility(VISIBLE);
            showMore.setVisibility(GONE);
        } else {
            smallShowMore.setVisibility(GONE);
            showMore.setVisibility(VISIBLE);
        }
    }

    public void shouldShowPadlock(boolean value) {
        if (value) {
            padlock.setVisibility(VISIBLE);
        } else {
            padlock.setVisibility(GONE);
        }
    }

    public void setClickListener (View.OnClickListener listener) {
        showMore.setOnClickListener(listener);
        smallShowMore.setOnClickListener(listener);
    }

    @Override
    public void setPlaying() { /* N/A */ }

    @Override
    public void setPaused() { /* N/A */ }

    @Override
    public void setCompleted() { /* N/A */ }

    @Override
    public void setError(Throwable error) { /* N/A */ }

    @Override
    public void setTime(int time, int duration) {
        int remaining = (duration - time) / 1000;
        if (chronograph != null) {
            String text = CRONO_DEF_TXT + remaining;
            chronograph.setText(text);
        }
    }

    @Override
    public boolean isPlaying() {
        return true;
    }

    @Override
    public void show() { /* N/A */ }

    @Override
    public void hide() { /* N/A */ }

    @Override
    public void setMinimised() { /* N/A */ }

    @Override
    public void setMaximised() { /* N/A */ }

    @Override
    public boolean isMaximised() {
        return false;
    }

    @Override
    public void setListener(Listener listener) { /* N/A */ }

    @Override
    public void close() { /* N/A */ }
}
