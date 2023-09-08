package tv.superawesome.sdk.publisher.video;

import static android.widget.RelativeLayout.ALIGN_PARENT_BOTTOM;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import tv.superawesome.lib.sautils.SAImageUtils;
import tv.superawesome.sdk.publisher.base.R;

class VideoComponentFactory {

    static ImageView getChronographBackground(int id, Context context) {
        return bgcreator.createComponent(id, context);
    }

    static TextView getChronograph(int id, Context context) {
        return chronoCreator.createComponent(id, context);
    }

    static Button getClick(int id, Context context) {
        return clickCreator.createComponent(id, context);
    }

    static Button getSmallClick(int id, Context context) {
        return smallClickCreator.createComponent(id, context);
    }

    static ImageButton getPadlock(int id, Context context) {
        return padlock.createComponent(id, context);
    }

    private static final ComponentCreator<ImageView> bgcreator = (id, context) -> {
        float scale = new VideoUtils().getScale(context);

        ImageView view = new ImageView(context);
        view.setId(id);
        view.setImageBitmap(SAImageUtils.createBitmap(100, 52, 0xFF4D4D4D, 10.0f));
        view.setScaleType(ImageView.ScaleType.FIT_XY);
        view.setAlpha(0.7f);

        RelativeLayout.LayoutParams layout = new RelativeLayout.LayoutParams((int)(50*scale), (int)(26*scale));
        layout.addRule(ALIGN_PARENT_BOTTOM);
        layout.setMargins((int)(5*scale), 0, 0, (int)(5*scale));

        view.setLayoutParams(layout);

        return view;
    };

    private static final ComponentCreator<TextView> chronoCreator = (id, context) -> {
        float scale = new VideoUtils().getScale(context);

        TextView view = new TextView(context);
        view.setId(id);
        view.setTextColor(Color.WHITE);
        view.setTextSize(11);
        view.setGravity(Gravity.CENTER);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((int)(50*scale), (int)(26*scale));
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        layoutParams.setMargins((int)(5*scale), 0, 0, (int)(5*scale));

        view.setLayoutParams(layoutParams);

        return view;
    };

    private static final ComponentCreator<Button> clickCreator = (id, context) -> {
        Button view = new Button(context);
        view.setId(id);
        view.setTransformationMethod(null);
        view.setBackgroundColor(Color.TRANSPARENT);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        view.setLayoutParams(params);

        return view;
    };

    private static final ComponentCreator<Button> smallClickCreator = (id, context) -> {
        float scale = new VideoUtils().getScale(context);

        Button view = new Button(context);
        view.setId(id);
        view.setTransformationMethod(null);
        view.setTextColor(Color.WHITE);
        view.setTextSize(12);
        view.setBackgroundColor(Color.TRANSPARENT);
        view.setGravity(Gravity.CENTER_VERTICAL);

        RelativeLayout.LayoutParams layout = new RelativeLayout.LayoutParams((int)(200*scale), (int)(26*scale));
        layout.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        layout.setMargins(0, 0, 0, (int)(5*scale));

        view.setLayoutParams(layout);
        view.setPadding((int) (65 * scale), 0, 0, 0);

        return view;
    };

    private static final ComponentCreator<ImageButton> padlock = (id, context) -> {
        Resources res = context.getResources();
        ImageButton view = new ImageButton(context);
        view.setId(id);
        view.setImageBitmap(SAImageUtils.createPadlockBitmap());

        view.setBackgroundColor(Color.TRANSPARENT);
        view.setScaleType(ImageView.ScaleType.FIT_XY);
        view.setPadding(
                res.getDimensionPixelOffset(R.dimen.safe_ad_logo_left_inset),
                res.getDimensionPixelOffset(R.dimen.safe_ad_logo_top_inset),
                res.getDimensionPixelOffset(R.dimen.safe_ad_logo_right_inset),
                res.getDimensionPixelOffset(R.dimen.safe_ad_logo_bottom_inset)
        );
        view.setLayoutParams(
                new ViewGroup.LayoutParams(
                        res.getDimensionPixelOffset(R.dimen.safe_ad_logo_width),
                        res.getDimensionPixelOffset(R.dimen.safe_ad_logo_height) +
                                res.getDimensionPixelOffset(R.dimen.safe_ad_logo_top_inset)
                )
        );
        return view;
    };
}
