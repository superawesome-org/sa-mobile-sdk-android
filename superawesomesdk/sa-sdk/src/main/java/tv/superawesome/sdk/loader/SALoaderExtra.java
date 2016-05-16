package tv.superawesome.sdk.loader;

import java.util.List;

import tv.superawesome.lib.savast.SAVASTParser;
import tv.superawesome.lib.savast.SAVASTParserInterface;
import tv.superawesome.lib.savast.models.SAVASTAd;
import tv.superawesome.sdk.models.SAAd;
import tv.superawesome.sdk.models.SACreativeFormat;
import tv.superawesome.sdk.models.SAData;
import tv.superawesome.sdk.parser.SAHTMLParser;

/**
 * Created by gabriel.coman on 19/04/16.
 */
public class SALoaderExtra {

    /** private vars */
    private SAAd ad = null;
    private SAVASTParser parser = null;
    private SALoaderExtraInterface listener = null;

    /**
     * Function that actually gets the extra data from various places
     * @param _ad - the ad that's got to be extra-added
     * @param _listener - the listener
     */
    public void getExtraData(SAAd _ad, SALoaderExtraInterface _listener) {
        /** get necessary data */
        this.ad = _ad;
        this.listener = _listener;
        ad.creative.details.data = new SAData();
        SACreativeFormat type = ad.creative.creativeFormat;

        switch (type) {
            case invalid: {
                listener.extraDone(ad);
                break;
            }
            case image:
            case rich:
            case tag: {
                ad.creative.details.data.adHtml = SAHTMLParser.formatCreativeDataIntoAdHTML(ad);
                listener.extraDone(ad);
                break;
            }
            case video: {
                parser = new SAVASTParser();

                parser.parseVASTAds(ad.creative.details.vast, new SAVASTParserInterface() {
                    @Override
                    public void didParseVAST(SAVASTAd vastAd) {
                        ad.creative.details.data.vastAd = vastAd;
                        listener.extraDone(ad);
                    }
                });
                break;
            }
        }
    }
}
