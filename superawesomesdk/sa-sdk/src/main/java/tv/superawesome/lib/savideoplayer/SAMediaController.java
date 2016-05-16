package tv.superawesome.lib.savideoplayer;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.reflect.Field;

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
    public boolean shouldAllowFullscreenClick = true;

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
        int watermark_67x25Id = getResources().getIdentifier("watermark_67x25", "drawable", packageName);
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
        if (!shouldAllowFullscreenClick) {
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
        } else {
            showMore = new Button(mContext);
            showMore.setTransformationMethod(null);
            showMore.setBackgroundColor(Color.TRANSPARENT);
            RelativeLayout.LayoutParams showLay = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            parent.addView(showMore, showLay);
        }

        /** create the padlock */
        if (shouldShowPadlock) {
            padlock = new ImageView(mContext);
            padlock.setImageResource(watermark_67x25Id);
            RelativeLayout.LayoutParams padLay = new RelativeLayout.LayoutParams((int) (83 * scale), (int) (31 * scale));
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
    public void show(int timeout) {
        super.show(timeout);
        // fix pre Android 4.3 strange positioning when used in Fragments
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion < Build.VERSION_CODES.JELLY_BEAN_MR2) {
            try {
                Field field1 = MediaController.class.getDeclaredField("mAnchor");
                field1.setAccessible(true);
                View mAnchor = (View)field1.get(this);

                Field field2 = MediaController.class.getDeclaredField("mDecor");
                field2.setAccessible(true);
                View mDecor = (View)field2.get(this);

                Field field3 = MediaController.class.getDeclaredField("mDecorLayoutParams");
                field3.setAccessible(true);
                WindowManager.LayoutParams mDecorLayoutParams = (WindowManager.LayoutParams)field3.get(this);

                Field field4 = MediaController.class.getDeclaredField("mWindowManager");
                field4.setAccessible(true);
                WindowManager mWindowManager = (WindowManager)field4.get(this);

                // NOTE: this appears in its own Window so co-ordinates are screen co-ordinates
                int [] anchorPos = new int[2];
                mAnchor.getLocationOnScreen(anchorPos);

                // we need to know the size of the controller so we can properly position it
                // within its space
                mDecor.measure(MeasureSpec.makeMeasureSpec(mAnchor.getWidth(), MeasureSpec.AT_MOST),
                        MeasureSpec.makeMeasureSpec(mAnchor.getHeight(), MeasureSpec.AT_MOST));

                mDecor.setPadding(0,0,0,0);

                WindowManager.LayoutParams p = mDecorLayoutParams;
                p.verticalMargin = 0;
                p.horizontalMargin = 0;
                p.width = mAnchor.getWidth();
                p.gravity = Gravity.LEFT | Gravity.TOP;
                p.x = anchorPos[0] + (mAnchor.getWidth() - p.width) / 2;
                p.y = anchorPos[1] + mAnchor.getHeight() - mDecor.getMeasuredHeight();
                mWindowManager.updateViewLayout(mDecor, mDecorLayoutParams);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void hide() {
        if (shouldNotHide) {
            show(0);
        } else {
            super.hide();
        }
    }
}
