package tv.superawesome.sdk.publisher.video;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import tv.superawesome.lib.sautils.SAImageUtils;

import static android.widget.RelativeLayout.ALIGN_PARENT_BOTTOM;

public class VideoComponentFactory {

    public static ImageView getChronographBackground(int id, Context context) {
        return bgcreator.createComponent(id, context);
    }

    public static TextView getChronograph(int id, Context context) {
        return chronoCreator.createComponent(id, context);
    }

    public static ImageView getMask(int id, Context context) {
        return maskCreator.createComponent(id, context);
    }

    public static Button getClick(int id, Context context) {
        return clickCreator.createComponent(id, context);
    }

    public static Button getSmallClick(int id, Context context) {
        return smallClickCreator.createComponent(id, context);
    }

    public static ImageButton getPadlock(int id, Context context) {
        return padlock.createComponent(id, context);
    }

    private static ComponentCreator<ImageView> bgcreator = new ComponentCreator<ImageView>() {

        @Override
        public ImageView createComponent(int id, Context context) {
            float scale = new VideoUtils().getScale(context);

            ImageView view = new ImageView(context);
            view.setId(id);
            view.setImageBitmap(SAImageUtils.createBitmap(100, 52, 0xFFFFFFFF, 10.0f));
            view.setScaleType(ImageView.ScaleType.FIT_XY);
            view.setAlpha(0.3f);

            RelativeLayout.LayoutParams layout = new RelativeLayout.LayoutParams((int)(50*scale), (int)(26*scale));
            layout.addRule(ALIGN_PARENT_BOTTOM);
            layout.setMargins((int)(5*scale), 0, 0, (int)(5*scale));

            view.setLayoutParams(layout);

            return view;
        }
    };

    private static ComponentCreator<TextView> chronoCreator = new ComponentCreator<TextView>() {

        @Override
        public TextView createComponent(int id, Context context) {
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
        }
    };

    private static ComponentCreator<ImageView> maskCreator = new ComponentCreator<ImageView>() {
        @Override
        public ImageView createComponent(int id, Context context) {
            float scale = new VideoUtils().getScale(context);

            ImageView view = new ImageView(context);
            view.setId(id);
            view.setImageBitmap(SAImageUtils.createVideoGradientBitmap());
            view.setScaleType(ImageView.ScaleType.FIT_XY);

            RelativeLayout.LayoutParams layout = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    (int)(31 * scale));
            layout.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

            view.setLayoutParams(layout);

            return view;
        }
    };

    private static ComponentCreator<Button> clickCreator = new ComponentCreator<Button>() {
        @Override
        public Button createComponent(int id, Context context) {
            Button view = new Button(context);
            view.setId(id);
            view.setTransformationMethod(null);
            view.setBackgroundColor(Color.TRANSPARENT);

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);

            view.setLayoutParams(params);

            return view;
        }
    };

    private static ComponentCreator<Button> smallClickCreator = new ComponentCreator<Button>() {
        @Override
        public Button createComponent(int id, Context context) {
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
        }
    };

    private static ComponentCreator<ImageButton> padlock = new ComponentCreator<ImageButton>() {
        @Override
        public ImageButton createComponent(int id, Context context) {
            float scale = new VideoUtils().getScale(context);

            ImageButton view = new ImageButton(context);
            view.setId(id);
            view.setImageBitmap(SAImageUtils.createPadlockBitmap());
            view.setPadding(0, 0, 0, 0);
            view.setBackgroundColor(Color.TRANSPARENT);
            view.setScaleType(ImageView.ScaleType.FIT_XY);
            view.setLayoutParams(new ViewGroup.LayoutParams((int) (83 * scale), (int) (31 * scale)));

            return view;
        }
    };
}
