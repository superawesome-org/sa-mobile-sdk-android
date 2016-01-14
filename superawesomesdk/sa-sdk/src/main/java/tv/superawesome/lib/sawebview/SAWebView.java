package tv.superawesome.lib.sawebview;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Message;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Size;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import tv.superawesome.lib.sautils.SALog;

/**
 * Created by gabriel.coman on 30/12/15.
 */
public class SAWebView extends WebView {

    /** the internal listener */
    private SAWebViewListener listener;

    /** Constructors */
    public SAWebView(Context context) {
        super(context);
    }

    public SAWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SAWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        /** any initialisation work here */

        this.getSettings().setJavaScriptEnabled(true);
        this.getSettings().setLoadWithOverviewMode(true);
        this.getSettings().setUseWideViewPort(true);

//        this.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
//        this.getSettings().setSupportMultipleWindows(true);

//        this.setWebChromeClient(new WebChromeClient() {
//            @Override
//            public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
//                SALog.Log("onCreateWindow");
//
//                return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg);
//            }
//        });

        this.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (shouldOverrideUrlLoading(view, url)){
                    view.stopLoading();
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                SALog.Log("onPageFinished");
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.contains("file:///")) {
                    return false;
                } else {
                    if (listener != null){
                        listener.saWebViewWillNavigate(url);
                    }

                    return true;
                }
            }
        });

    }

    public void loadHTML(String html, float adWidth, float adHeight, float frameWidth, float frameHeight){
        /** calc params */
        float xscale = frameWidth / adWidth;
        float yscale = frameHeight / adHeight;
        float scale = Math.min(xscale, yscale);

        this.setInitialScale((int)(scale * 100));

        String _html = html;
        _html = _html.replace("_WIDTH_","" + (int)adWidth);
        _html = _html.replace("_HEIGHT_", "" + (int)adHeight);

        /** get the context */
        Context context = this.getContext();

        SALog.Log(_html);

        /** start creating a temporary file */
        File path = context.getFilesDir();
        File file = new File(path, "tmpHTML.html");

        /** write to the tmp file */
        FileOutputStream stream = null;
        try {
            stream = new FileOutputStream(file);
            stream.write(_html.getBytes());
            stream.close();
        } catch (IOException e1){
            e1.printStackTrace();

            if (listener != null) {
                listener.saWebViewDidFail();
            }
        }

        /** load HTML data */
        this.loadUrl("file://" + file.getAbsolutePath());

        /** call success listener */
        if (listener != null){
            listener.saWebViewDidLoad();
        }
    }

    /**
     * Assign the listener
     * @param listener
     */
    public void setListener(SAWebViewListener listener) {
        this.listener = listener;
    }
}
