package tv.superawesome.lib.sawebplayer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

public class SAWebPlayer extends RelativeLayout implements SAWebClient.Listener {

    public enum Event {
        Web_Prepared,
        Web_Loaded,
        Web_Error,
        Web_Click,
        Web_Started,
        Web_Layout,
        Web_Empty,
    }

    // boolean holding whether the web view has finished loading or not
    private boolean finishedLoading = false;

    // private variables for the web player
    protected final FrameLayout holder;
    protected final SAWebView webView;

    // interface objects used for the web player callback mechanism
    protected Listener eventListener;

    // mraid instance
    protected final int holderBgColor = Color.TRANSPARENT;

    public SAWebPlayer(Context context) {
        this(context, null, 0);
    }

    public SAWebPlayer(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @SuppressLint("AddJavascriptInterface")
    public SAWebPlayer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        eventListener = (event, destination) -> {
        };

        holder = new FrameLayout(context);
        holder.setClipChildren(false);
        holder.setBackgroundColor(holderBgColor);
        holder.setClipToPadding(false);

        webView = new SAWebView(context);
        webView.setWebViewClient(new SAWebClient(this));
    }

    public void setup() {
        webView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        holder.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        holder.addView(webView);
        this.addView(holder);

        if (eventListener != null) {
            eventListener.saWebPlayerDidReceiveEvent(Event.Web_Prepared, null);
        }
    }

    public void destroy() {
        if (webView != null) {
            setEventListener(null);
            webView.destroy();
        }
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
        if (eventListener != null) {
            eventListener.saWebPlayerDidReceiveEvent(Event.Web_Started, null);
        }
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView webView, String url) {
        if (finishedLoading) {

            if (url.contains("sa-beta-ads-uploads-superawesome.netdna-ssl.com") && url.contains("/iframes")) {
                return false;
            }

            if (eventListener != null) {
                eventListener.saWebPlayerDidReceiveEvent(Event.Web_Click, url);
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onError() {
        if (eventListener != null) {
            eventListener.saWebPlayerDidReceiveEvent(Event.Web_Error, null);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Useful Web Player methods
    ////////////////////////////////////////////////////////////////////////////////////////////////

    public void loadHTML(String base, String html) {
        if (webView != null) {
            // load data directly, not from file as before
            webView.loadHTML(base, html);

            // call success listener
            if (eventListener != null) {
                eventListener.saWebPlayerDidReceiveEvent(Event.Web_Loaded, null);
            }
        }
    }

    public FrameLayout getHolder() {
        return holder;
    }

    public WebView getWebView() {
        return webView;
    }

    public void setEventListener(Listener listener) {
        eventListener = listener;
    }

    public interface Listener {
        void saWebPlayerDidReceiveEvent(Event event, String destination);
    }
}
