package tv.superawesome.lib.sawebview;

/**
 * Created by gabriel.coman on 30/12/15.
 */
public interface SAWebViewListener {
    void saWebViewWillNavigate(String url);
    void saWebViewDidLoad();
    void saWebViewDidFail();
}
