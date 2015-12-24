package tv.superawesome.lib.savast.savastparser;

import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

import tv.superawesome.lib.sautils.SALog;
import tv.superawesome.lib.savast.savastparser.models.SAVASTAd;
import tv.superawesome.lib.savast.savastparser.models.SAVASTAdType;
import tv.superawesome.lib.savast.savastparser.models.SAVASTCreative;
import tv.superawesome.lib.savast.savastparser.models.SAVASTCreativeType;
import tv.superawesome.lib.savast.savastparser.models.SAVASTImpression;
import tv.superawesome.lib.savast.savastparser.models.SAVASTLinearCreative;
import tv.superawesome.lib.savast.savastparser.models.SAVASTMediaFile;
import tv.superawesome.lib.savast.savastparser.models.SAVASTTracking;
import tv.superawesome.lib.savast.saxml.SAXML;
import tv.superawesome.lib.savast.saxml.SAXMLIterator;
import tv.superawesome.sdk.data.Models.SACreativeFormat;

/**
 * Created by gabriel.coman on 22/12/15.
 */
public class SAVASTModelParsing {

    public static SAVASTAd parseAdXML(Element adElement) {
        final SAVASTAd ad = new SAVASTAd();

        // get ID and sequence
        ad.id = adElement.getAttribute("id");
        ad.sequence = adElement.getAttribute("sequence");

        // get type
        ad.type = SAVASTAdType.Invalid;
        boolean isInLine = SAXML.checkSiblingsAndChildrenOf(adElement, "InLine");
        boolean isWrapper = SAXML.checkSiblingsAndChildrenOf(adElement, "Wrapper");

        if (isInLine) ad.type = SAVASTAdType.InLine;
        if (isWrapper) ad.type = SAVASTAdType.Wrapper;

        // init ad arrays
        ad.Errors = new ArrayList<String>();
        ad.Impressions = new ArrayList<SAVASTImpression>();
        ad.Creatives = new ArrayList<SAVASTCreative>();

        // get errors
        SAXML.searchSiblingsAndChildrenOf(adElement, "Error", new SAXMLIterator() {
            @Override
            public void foundElement(Element e) {
                String error = e.getTextContent();
                ad.Errors.add(error);
            }
        });

        // get impressions
        SAXML.searchSiblingsAndChildrenOf(adElement, "Impression", new SAXMLIterator() {
            @Override
            public void foundElement(Element e) {
                SAVASTImpression impression = new SAVASTImpression();
                impression.URL = e.getTextContent();
                ad.Impressions.add(impression);
            }
        });

        // get creatives
        SAXML.searchSiblingsAndChildrenOf(adElement, "Creative", new SAXMLIterator() {
            @Override
            public void foundElement(Element e) {
                SAVASTLinearCreative linear = parseCreativeXML(e);
                if (linear != null){
                    ad.Creatives.add(linear);
                }
            }
        });

        return ad;
    }

    public static SAVASTLinearCreative parseCreativeXML(Element element){
        // first find out what kind of content this creative has
        // is it Linear, NonLinear or CompanionAds?
        boolean isLinear = SAXML.checkSiblingsAndChildrenOf(element, "Linear");

        // init as a linear Creative
        if (isLinear) {
            final SAVASTLinearCreative creative = new SAVASTLinearCreative();

            // get attributes
            creative.type = SAVASTCreativeType.Linear;
            creative.id = element.getAttribute("id");
            creative.sequence = element.getAttribute("sequence");

            // create arrays
            creative.MediaFiles = new ArrayList<SAVASTMediaFile>();
            creative.TrackingEvents = new ArrayList<SAVASTTracking>();
            creative.ClickTracking = new ArrayList<String>();
            creative.CustomClicks = new ArrayList<String>();

            // populate duration
            SAXML.searchSiblingsAndChildrenOf(element, "Duration", new SAXMLIterator() {
                @Override
                public void foundElement(Element e) {
                    creative.Duration = e.getTextContent();
                }
            });

            // populate click through
            SAXML.searchSiblingsAndChildrenOf(element, "ClickThrough", new SAXMLIterator() {
                @Override
                public void foundElement(Element e) {
                    creative.ClickThrough = e.getTextContent().replace("&amp;","&").replace("%3A",":").replace("%2F","/");
                }
            });

            // populate Click Tracking
            SAXML.searchSiblingsAndChildrenOf(element, "ClickTracking", new SAXMLIterator() {
                @Override
                public void foundElement(Element e) {
                    creative.ClickTracking.add(e.getTextContent());
                }
            });

            // populate Custom Clicks
            SAXML.searchSiblingsAndChildrenOf(element, "CustomClicks", new SAXMLIterator() {
                @Override
                public void foundElement(Element e) {
                    creative.CustomClicks.add(e.getTextContent());
                }
            });

            // populate Media Files
            SAXML.searchSiblingsAndChildrenOf(element, "MediaFile", new SAXMLIterator() {
                @Override
                public void foundElement(Element e) {
                    SAVASTMediaFile mediaFile = new SAVASTMediaFile();
                    mediaFile.width = e.getAttribute("width");
                    mediaFile.height = e.getAttribute("height");
                    mediaFile.URL = e.getTextContent();
                    creative.MediaFiles.add(mediaFile);
                }
            });

            // populate Tracking
            SAXML.searchSiblingsAndChildrenOf(element, "Tracking", new SAXMLIterator() {
                @Override
                public void foundElement(Element e) {
                    SAVASTTracking tracking = new SAVASTTracking();
                    tracking.event = e.getAttribute("event");
                    tracking.URL = e.getTextContent();
                    creative.TrackingEvents.add(tracking);
                }
            });

            return creative;
        }

        return null;
    }
}
