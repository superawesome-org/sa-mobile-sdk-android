package tv.superawesome.sdk.loader;

import android.util.Log;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import tv.superawesome.lib.savast.SAVASTParser;
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
    private SALoaderExtraListener listener = null;

    /**
     * Function that actually gets the extra data from various places
     * @param _ad - the ad that's got to be extra-added
     * @param _listener - the listener
     */
    public void getExtraData(SAAd _ad, SALoaderExtraListener _listener) {
        /** get necessary data */
        this.ad = _ad;
        this.listener = _listener;
        ad.creative.details.data = new SAData();
        SACreativeFormat type = ad.creative.format;

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
                parser.execute(ad.creative.details.vast, new SAVASTParser.SAVASTParserListener() {
                    @Override
                    public void didParseVAST(List<SAVASTAd> ads) {
                        ad.creative.details.data.vastAds = ads;
                        listener.extraDone(ad);
                    }
                });
                break;
            }
        }
    }

    /**
     * Interface for the Extra loader
     */
    public interface SALoaderExtraListener {
        /**
         * When all the data is fully loaded
         * @param finalAd - final Ad that's being sent to the user via callback
         */
        void extraDone(SAAd finalAd);
    }
}
