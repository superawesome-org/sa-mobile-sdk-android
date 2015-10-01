package tv.superawesome.sdk.fragments;

import android.app.Activity;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import tv.superawesome.sdk.R;
import tv.superawesome.sdk.views.SAPlacementListener;
import tv.superawesome.sdk.views.SAPlacementView;

/**
 * Created by connor.leigh-smith on 14/07/15.
 */
public abstract class SAFragment extends Fragment {

    protected final static String TAG = "SA SDK - Fragment";
    protected View rootView;
    protected int fragmentId;
    public String placementID;
    public boolean testMode;
    public boolean isParentalGateEnabled;
    public boolean showInstantly;
    protected SAPlacementView placementView;
    protected SAPlacementListener listener;

    public void setListener(SAPlacementListener listener) {
        if (this.placementView != null) {
            this.placementView.setListener(listener);
        } else {
            this.listener = listener;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate called.");
        setRetainInstance(true);
    }

    /**
     * Parse attributes during inflation from a view hierarchy into the
     * arguments we handle.
     */
    @Override
    public void onInflate(Activity activity, AttributeSet attrs, Bundle savedInstanceState) {
        super.onInflate(activity, attrs, savedInstanceState);
        Log.v(TAG, "onInflate called");

        TypedArray a = activity.obtainStyledAttributes(attrs, R.styleable.SAFragment);

        this.placementID = a.getString(R.styleable.SAFragment_placementID);
        this.testMode = a.getBoolean(R.styleable.SAFragment_testMode, false);
        this.showInstantly = a.getBoolean(R.styleable.SAFragment_showInstantly, true);
        this.isParentalGateEnabled = a.getBoolean(R.styleable.SAFragment_isParentalGateEnabled, true);

        a.recycle();
    }

}
