package tv.superawesome.lib.savideoplayer;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;

import tv.superawesome.lib.sautils.SAUtils;

/**
 * Created by gabriel.coman on 01/04/16.
 */
public class SAMediaController extends MediaController {

    /** default constant-like variables */
    private final String findOutMore = "Find out more »";
    private float scale = 0.0f;

    /** UI variables */
    private RelativeLayout parent;
    private ImageView mask;
    private ImageView padlock;
    public TextView chronographer;
    public Button showMore;
    public Button close;

    /** media controller status variables */
    public boolean shouldShowPadlock = false;
    public boolean shouldShowCloseButton = false;
    public boolean shouldNotHide = true;

    public SAMediaController(Context context) {
        super(context);
        scale = SAUtils.getScaleFactor((Activity) context);
    }

    @Override
    public void setAnchorView(View view) {
        super.setAnchorView(view);
        removeAllViews();

        /** get the context */
        Context mContext = getContext();

        String packageName = mContext.getPackageName();
        int watermark_49x25Id = getResources().getIdentifier("watermark_49x25", "drawable", packageName);
        int sa_markId = getResources().getIdentifier("sa_mark", "drawable", packageName);
        int sa_crono_bgId = getResources().getIdentifier("sa_crono_bg", "drawable", packageName);
        int sa_closeId = getResources().getIdentifier("sa_close", "drawable", packageName);

        /** create the main relative layout - that spans the full W & H of the video */
        parent = new RelativeLayout(mContext);
        RelativeLayout.LayoutParams parentParams = new RelativeLayout.LayoutParams(view.getWidth(), view.getHeight());
        addView(parent, parentParams);

        /** create the background image */
        mask = new ImageView(mContext);
        mask.setImageResource(sa_markId);
        mask.setScaleType(ImageView.ScaleType.FIT_XY);
        RelativeLayout.LayoutParams maskLay = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int)(31 * scale));
        maskLay.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        parent.addView(mask, maskLay);

        /** create the timer label */
        chronographer = new TextView(mContext);
        chronographer.setText("Ad: 0");
        chronographer.setBackgroundResource(sa_crono_bgId);
        chronographer.setTextColor(Color.WHITE);
        chronographer.setTextSize(11);
        chronographer.setGravity(Gravity.CENTER);
        RelativeLayout.LayoutParams chronoLay = new RelativeLayout.LayoutParams((int)(50*scale), (int)(26*scale));
        chronoLay.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        chronoLay.setMargins((int)(5*scale), 0, 0, (int)(5*scale));
        parent.addView(chronographer, chronoLay);

        /** create the show more button */
        showMore = new Button(mContext);
        showMore.setTransformationMethod(null);
        showMore.setText(findOutMore);
        showMore.setTextColor(Color.WHITE);
        showMore.setTextSize(12);
        showMore.setBackgroundColor(Color.TRANSPARENT);
        showMore.setGravity(Gravity.CENTER_VERTICAL);
        showMore.setPadding((int) (65 * scale), 0, 0, 0);
        RelativeLayout.LayoutParams showLay = new RelativeLayout.LayoutParams((int)(200*scale), (int)(26*scale));
        showLay.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        showLay.setMargins(0, 0, 0, (int)(5*scale));
        parent.addView(showMore, showLay);

        /** create the padlock */
        if (shouldShowPadlock) {
            padlock = new ImageView(mContext);
            padlock.setImageResource(watermark_49x25Id);
            RelativeLayout.LayoutParams padLay = new RelativeLayout.LayoutParams((int) (61 * scale), (int) (31 * scale));
            padLay.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            parent.addView(padlock, padLay);
        }

        /** create the close button */
        if (shouldShowCloseButton) {
            close = new Button(mContext);
            close.setBackgroundResource(sa_closeId);
            RelativeLayout.LayoutParams closeLay = new RelativeLayout.LayoutParams((int) (30 * scale), (int) (30 * scale));
            closeLay.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            closeLay.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            parent.addView(close, closeLay);
        }
    }

    @Override
    public void hide() {
        if (shouldNotHide) {
            show();
        } else {
            super.hide();
        }
    }
}
