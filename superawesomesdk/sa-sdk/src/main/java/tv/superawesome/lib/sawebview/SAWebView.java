package tv.superawesome.lib.sawebview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Size;
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
        this.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                SALog.Log("ABC! goto " + url);
                if (listener != null){
                    listener.saWebViewWillNavigate(url);
                }

                return true;
            }
        });

    }

    public void loadHTML(String html, float adWidth, float adHeight, float frameWidth, float frameHeight){
        /** calc params */
        float xscale = frameWidth / adWidth;
        float yscale = frameHeight / adHeight;
        float scale = Math.min(xscale, yscale);
        SALog.Log("Scale: " + (scale * 100));
        this.setInitialScale((int)(scale * 100));

        String _html = html;
        _html = _html.replace("_WIDTH_","" + (int)adWidth);
        _html = _html.replace("_HEIGHT_", "" + (int)adHeight);

        /** get the context */
        Context context = this.getContext();

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
