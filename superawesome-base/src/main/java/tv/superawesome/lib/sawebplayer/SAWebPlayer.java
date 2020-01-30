package tv.superawesome.lib.sawebplayer;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

public class SAWebPlayer extends RelativeLayout implements
        SAWebClient.Listener,
        ViewTreeObserver.OnGlobalLayoutListener {

    public enum Event {
        Web_Prepared,
        Web_Loaded,
        Web_Error,
        Web_Click,
        Web_Started,
        Web_Layout,
        Web_Empty
    }

    // boolean holding whether the web view has finished loading or not
    private boolean             finishedLoading = false;

    // private variables for the web player
    protected FrameLayout       holder = null;
    protected SAWebView         webView = null;

    protected int               origContentWidth = 0;
    protected int               origContentHeight = 0;
    protected int               contentWidth = 0;
    protected int               contentHeight = 0;

    // interface objects used for the web player callback mechanism
    protected Listener          eventListener;

    // mraid instance
    protected int               holderBgColor = Color.TRANSPARENT;

    public SAWebPlayer(Context context) {
        this(context, null, 0);
    }

    public SAWebPlayer(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SAWebPlayer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        eventListener = new Listener() {@Override public void saWebPlayerDidReceiveEvent(Event event, String destination) {}};

        holder = new FrameLayout(context);
        holder.setClipChildren(false);
        holder.setBackgroundColor(holderBgColor);
        holder.setClipToPadding(false);

        webView = new SAWebView(context);
        webView.setWebViewClient(new SAWebClient(this));

//        // todo: ref
//        this.getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    public void setContentSize (int width, int height) {
//        // todo: ref
//        origContentWidth = width;
//        origContentHeight = height;
//        contentWidth = width;
//        contentHeight = height;
    }

    public void setup () {
        webView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        holder.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        holder.addView(webView);
        this.addView(holder);

        eventListener.saWebPlayerDidReceiveEvent(Event.Web_Prepared, null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // SAWebClient implementation
    ////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onPageStarted(WebView view, String url) {
        if (shouldOverrideUrlLoading(view, url)) {
            view.stopLoading();
        }
    }

    @Override
    public void onPageFinished(WebView view) {
        finishedLoading = true;
        eventListener.saWebPlayerDidReceiveEvent(Event.Web_Started, null);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView webView, String url) {
        if (finishedLoading) {

            if (url.contains("sa-beta-ads-uploads-superawesome.netdna-ssl.com") && url.contains("/iframes")) {
                return false;
            }

            eventListener.saWebPlayerDidReceiveEvent(Event.Web_Click, url);
            return true;
        }
        else {
            return false;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Global Layout of the Container
    ////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onGlobalLayout() {
        // todo: ref
//        Rect newValue = mapSourceSizeIntoBoundingSize(contentWidth, contentHeight, holder.getMeasuredWidth(), holder.getMeasuredHeight());
//        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(newValue.right, newValue.bottom);
//        params.setMargins(newValue.left, newValue.top, 0, 0);
//        webView.setLayoutParams(params);
//
//        // webView.scale(contentWidth, contentHeight, holder.getMeasuredWidth(), holder.getMeasuredHeight());
//        eventListener.saWebPlayerDidReceiveEvent(Event.Web_Layout, null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Useful Web Player methods
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public void loadHTML (String base, String html) {
        if (webView != null) {
            // load data directly, not from file as before
            webView.loadHTML(base, html);

            // call success listener
            eventListener.saWebPlayerDidReceiveEvent(Event.Web_Loaded, null);
        }
    }

    public FrameLayout getHolder () {
        return holder;
    }

    public WebView getWebView () {
        return webView;
    }

    public void setEventListener(Listener l) {
        eventListener = l != null ? l : eventListener;
    }

    public interface Listener {

        void saWebPlayerDidReceiveEvent (Event event, String destination);
    }

    /**
     * Private method that maps a source rect into a bounding rect.
     *
     * @param sourceW   source width
     * @param sourceH   source height
     * @param boundingW bounding width
     * @param boundingH bounding height
     * @return          the resulting correct rect
     */
    private Rect mapSourceSizeIntoBoundingSize(float sourceW, float sourceH, float boundingW, float boundingH) {
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

        return new Rect((int)X, (int)Y, (int)W, (int)H);
    }
}
