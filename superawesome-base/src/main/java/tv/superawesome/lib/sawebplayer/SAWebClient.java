package tv.superawesome.lib.sawebplayer;

import android.graphics.Bitmap;
import android.os.Build;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class SAWebClient extends WebViewClient {

    private final Listener listener;
    /* Flag to indicate error is only handled once to send single event in case multiple error occurs */
    private boolean errorHandled = false;

    SAWebClient(Listener listener) {
        this.listener = listener;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        errorHandled = false;
        listener.onPageStarted(view, url);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        listener.onPageFinished(view);
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);
        handleError(errorCode);
    }

    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        super.onReceivedError(view, request, error);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            handleError(error.getErrorCode());
        }
    }

    private void handleError(int errorCode) {
        if (errorHandled) return;
        if (errorCode == WebViewClient.ERROR_HOST_LOOKUP) {
            errorHandled = true;
            listener.onError();
        }
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        return listener.shouldOverrideUrlLoading(view, url);
    }

    interface Listener {
        void onPageStarted(WebView view, String url);

        void onPageFinished(WebView view);

        boolean shouldOverrideUrlLoading(WebView webView, String url);

        void onError();
    }
}
