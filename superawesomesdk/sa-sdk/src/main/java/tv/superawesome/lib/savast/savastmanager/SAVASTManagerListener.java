package tv.superawesome.lib.savast.savastmanager;

import java.util.List;

/**
 * Created by gabriel.coman on 23/12/15.
 */
public interface SAVASTManagerListener {

    void didParseVASTAndFindAds();
    void didParseVASTButDidNotFindAnyAds();
    void didNotParseVAST();
    void didStartAd();
    void didStartCreative();
    void didReachFirstQuartileOfCreative();
    void didReachMidpointOfCreative();
    void didReachThirdQuartileOfCreative();
    void didEndOfCreative();
    void didHaveErrorForCreative();
    void didEndAd();
    void didEndAllAds();
    void didGoToURL(String url, List<String> clickTracking);
}
