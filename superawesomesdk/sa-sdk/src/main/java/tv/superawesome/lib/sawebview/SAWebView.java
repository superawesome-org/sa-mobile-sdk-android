package tv.superawesome.lib.sawebview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Message;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Size;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import tv.superawesome.lib.sautils.SALog;
import tv.superawesome.lib.sautils.SAUtils;

/**
 * Created by gabriel.coman on 30/12/15.
 */
public class SAWebView extends WebView {

    /** the internal listener */
    private SAWebViewListener listener;
    private float scaleFactor = 0;

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

        scaleFactor = SAUtils.getScaleFactor((Activity)context);

        /** only for jelly bean */
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//            this.enablecrossdomain41();
//            this.getSettings().setAllowUniversalAccessFromFileURLs(true);
//            this.getSettings().setAllowFileAccessFromFileURLs(true);
//        } else {
//            this.enablecrossdomain();
//        }

        this.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (shouldOverrideUrlLoading(view, url)) {
                    view.stopLoading();
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
           }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.contains("file:///")) {
                    return false;
                } else {
                    if (listener != null) {
                        listener.saWebViewWillNavigate(url);
                    }

                    return true;
                }
            }

        });
        this.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onConsoleMessage(ConsoleMessage cm) {
                SALog.Log(cm.message() + " -- From line " + cm.lineNumber() + " of " + cm.sourceId());
                return true;
            }
        });

    }

    public void loadHTML(String html, float adWidth, float adHeight, float frameWidth, float frameHeight){
        /** calc params */
        String _html = html;
        _html = _html.replace("_WIDTH_", "" + (int)(adWidth));
        _html = _html.replace("_HEIGHT_", "" + (int)(adHeight));
        _html = _html.replace("_SCALE_", "" + ((frameWidth/scaleFactor)/adWidth));

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

//    public void enablecrossdomain()
//    {
//        try
//        {
//            Field field = WebView.class.getDeclaredField("mWebViewCore");
//            field.setAccessible(true);
//            Object webviewcore=field.get(this);
//            Method method = webviewcore.getClass().getDeclaredMethod("nativeRegisterURLSchemeAsLocal", String.class);
//            method.setAccessible(true);
//            method.invoke(webviewcore, "http");
//            method.invoke(webviewcore, "https");
//        }
//        catch(Exception e)
//        {
//            SALog.Log("enablecrossdomain error");
//            e.printStackTrace();
//        }
//    }
//
//    //for android 4.1+
//    public void enablecrossdomain41()
//    {
//        try
//        {
//            Field webviewclassic_field = WebView.class.getDeclaredField("mProvider");
//            webviewclassic_field.setAccessible(true);
//            Object webviewclassic=webviewclassic_field.get(this);
//            Field webviewcore_field = webviewclassic.getClass().getDeclaredField("mWebViewCore");
//            webviewcore_field.setAccessible(true);
//            Object mWebViewCore=webviewcore_field.get(webviewclassic);
//            Field nativeclass_field = webviewclassic.getClass().getDeclaredField("mNativeClass");
//            nativeclass_field.setAccessible(true);
//            Object mNativeClass=nativeclass_field.get(webviewclassic);
//
//            Method method = mWebViewCore.getClass().getDeclaredMethod("nativeRegisterURLSchemeAsLocal",new Class[] {int.class,String.class});
//            method.setAccessible(true);
//            method.invoke(mWebViewCore,mNativeClass, "http");
//            method.invoke(mWebViewCore,mNativeClass, "https");
//        }
//        catch(Exception e)
//        {
//            SALog.Log("enablecrossdomain error");
//            e.printStackTrace();
//        }
//    }
}
