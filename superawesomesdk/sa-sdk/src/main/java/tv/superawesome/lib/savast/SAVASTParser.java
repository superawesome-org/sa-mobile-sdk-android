package tv.superawesome.lib.savast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import tv.superawesome.lib.sautils.SAAsyncTask;
import tv.superawesome.lib.sautils.SAApplication;
import tv.superawesome.lib.sautils.SAUtils;
import tv.superawesome.lib.savast.models.SAVASTAd;
import tv.superawesome.lib.savast.models.SAVASTAdType;
import tv.superawesome.lib.savast.models.SAVASTCreative;
import tv.superawesome.lib.savast.models.SAVASTCreativeType;
import tv.superawesome.lib.savast.models.SAVASTImpression;
import tv.superawesome.lib.savast.models.SAVASTLinearCreative;
import tv.superawesome.lib.savast.models.SAVASTMediaFile;
import tv.superawesome.lib.savast.models.SAVASTTracking;

/**
 * Created by gabriel.coman on 22/12/15.
 */
public class SAVASTParser {

    private SAVASTParserListener listener;
    private List<SAVASTAd> ads;

    public void execute(final String url, final SAVASTParserListener listener) {
        this.listener = listener;

        SAAsyncTask task = new SAAsyncTask(SAApplication.getSAApplicationContext(), new SAAsyncTask.SAAsyncTaskListener() {
            @Override
            public Object taskToExecute() throws IOException, SAXException, ParserConfigurationException  {
                List<SAVASTAd> ads = parseVAST(url);
                return ads;
            }

            @Override
            public void onFinish(Object result) {
                List<SAVASTAd> ads = new ArrayList<>();
                if (result != null){
                    ads = (List<SAVASTAd>)result;
                }

                if (listener != null) {
                    listener.didParseVAST(ads);
                }
            }

            @Override
            public void onError() {
                List<SAVASTAd> ads = new ArrayList<>();
                if (listener != null) {
                    listener.didParseVAST(ads);
                }
            }
        });
    }

    /**
     * The main parseing function
     * @param url - URL to the VAST
     * @return an array of VAST ads
     */
    public List<SAVASTAd> parseVAST(String url) throws IOException, ParserConfigurationException, SAXException {
        /** create the array of ads that should be returned */
        final List<SAVASTAd> lads = new ArrayList<SAVASTAd>();

        /** step 1: get the XML */
        String VAST = SAUtils.syncGet(url);

        if (VAST == null) {
            /**
             * return empty ads if  VAST string is NULL - this can sometimes happen because of SSL certificate issues
             */
            return lads;
        }

        /** create the Doc builder factory */
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        /** Parse the XML file */
        Document doc = db.parse(new InputSource(new ByteArrayInputStream(VAST.getBytes("utf-8"))));
        doc.getDocumentElement().normalize();

        /** step 2. get the correct reference to the root XML element */
        final Element root = (Element) doc.getElementsByTagName("VAST").item(0);

        /** step 3. check if the loaded XML document has "Ad" children */
        boolean hasAds = SAXML.checkSiblingsAndChildrenOf(root, "Ad");
        if (!hasAds) {
            return lads;
        }

        /** step 4. start finding ads and parsing them */
        SAXML.searchSiblingsAndChildrenOf(root, "Ad", new SAXML.SAXMLIterator() {
            @Override
            public void foundElement(Element e) {

                boolean isInLine = SAXML.checkSiblingsAndChildrenOf(e, "InLine");
                boolean isWrapper = SAXML.checkSiblingsAndChildrenOf(e, "Wrapper");

                if (isInLine){
                    SAVASTAd inlineAd = parseAdXML(e);
                    lads.add(inlineAd);
                } else if (isWrapper) {
                    SAVASTAd wrapperAd = parseAdXML(e);

                    String VASTAdTagURI = "";
                    Element uriPointer = SAXML.findFirstInstanceInSiblingsAndChildrenOf(e, "VASTAdTagURI");
                    if (uriPointer != null){
                        VASTAdTagURI = uriPointer.getTextContent();
                    }

                    try {
                        List<SAVASTAd> foundAds = parseVAST(VASTAdTagURI);
                        wrapperAd.Creatives = SAUtils.removeAllButFirstElement(wrapperAd.Creatives);

                        /** merge foundAds with wrapper ad */
                        for (SAVASTAd foundAd : foundAds) {
                            foundAd.sumAd(wrapperAd);
                        }

                        /** add to final return array */
                        for (SAVASTAd foundAd : foundAds) {
                            lads.add(foundAd);
                        }
                    } catch (IOException | ParserConfigurationException | SAXException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    /** do nothing */
                }
            }
        });

        return lads;
    }

    /**
     * Function that parses a VAST XML "Ad" element into a SAVASTAd model
     * @param adElement XML elemend
     * @return a SAVAST ad object
     */
    public static SAVASTAd parseAdXML(Element adElement) {
        final SAVASTAd ad = new SAVASTAd();

        /** get ID and sequence */
        ad.id = adElement.getAttribute("id");
        ad.sequence = adElement.getAttribute("sequence");

        /** get type */
        ad.type = SAVASTAdType.Invalid;
        boolean isInLine = SAXML.checkSiblingsAndChildrenOf(adElement, "InLine");
        boolean isWrapper = SAXML.checkSiblingsAndChildrenOf(adElement, "Wrapper");

        if (isInLine) ad.type = SAVASTAdType.InLine;
        if (isWrapper) ad.type = SAVASTAdType.Wrapper;

        /** init ad arrays */
        ad.Errors = new ArrayList<String>();
        ad.Impressions = new ArrayList<SAVASTImpression>();
        ad.Creatives = new ArrayList<SAVASTCreative>();

        /** get errors */
        SAXML.searchSiblingsAndChildrenOf(adElement, "Error", new SAXML.SAXMLIterator() {
            @Override
            public void foundElement(Element e) {
                String error = e.getTextContent();
                ad.Errors.add(error);
            }
        });

        /** get impressions */
        SAXML.searchSiblingsAndChildrenOf(adElement, "Impression", new SAXML.SAXMLIterator() {
            @Override
            public void foundElement(Element e) {
                SAVASTImpression impression = new SAVASTImpression();
                impression.URL = e.getTextContent();
                ad.Impressions.add(impression);
            }
        });

        /** get creatives */
        SAXML.searchSiblingsAndChildrenOf(adElement, "Creative", new SAXML.SAXMLIterator() {
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

    /**
     * Function that parses a XML "Linear" VAST element and returns a SAVASTLinearCreative model
     * @param element a XML element
     * @return a valid SAVASTLinearCreative model
     */
    public static SAVASTLinearCreative parseCreativeXML(Element element){
        /**
         * first find out what kind of content this creative has
         * is it Linear, NonLinear or CompanionAds?
         */
        boolean isLinear = SAXML.checkSiblingsAndChildrenOf(element, "Linear");

        /** init as a linear Creative */
        if (isLinear) {
            final SAVASTLinearCreative creative = new SAVASTLinearCreative();

            /** get attributes */
            creative.type = SAVASTCreativeType.Linear;
            creative.id = element.getAttribute("id");
            creative.sequence = element.getAttribute("sequence");

            /** create arrays */
            creative.MediaFiles = new ArrayList<SAVASTMediaFile>();
            creative.TrackingEvents = new ArrayList<SAVASTTracking>();
            creative.ClickTracking = new ArrayList<String>();
            creative.CustomClicks = new ArrayList<String>();

            /** populate duration */
            SAXML.searchSiblingsAndChildrenOf(element, "Duration", new SAXML.SAXMLIterator() {
                @Override
                public void foundElement(Element e) {
                    creative.Duration = e.getTextContent();
                }
            });

            /** populate click through */
            SAXML.searchSiblingsAndChildrenOf(element, "ClickThrough", new SAXML.SAXMLIterator() {
                @Override
                public void foundElement(Element e) {
                    creative.ClickThrough = e.getTextContent().replace("&amp;","&").replace("%3A",":").replace("%2F", "/");
                }
            });

            /** populate Click Tracking */
            SAXML.searchSiblingsAndChildrenOf(element, "ClickTracking", new SAXML.SAXMLIterator() {
                @Override
                public void foundElement(Element e) {
                    creative.ClickTracking.add(e.getTextContent());
                }
            });

            /** populate Custom Clicks */
            SAXML.searchSiblingsAndChildrenOf(element, "CustomClicks", new SAXML.SAXMLIterator() {
                @Override
                public void foundElement(Element e) {
                    creative.CustomClicks.add(e.getTextContent());
                }
            });

            /** populate Tracking */
            SAXML.searchSiblingsAndChildrenOf(element, "Tracking", new SAXML.SAXMLIterator() {
                @Override
                public void foundElement(Element e) {
                    SAVASTTracking tracking = new SAVASTTracking();
                    tracking.event = e.getAttribute("event");
                    tracking.URL = e.getTextContent();
                    creative.TrackingEvents.add(tracking);
                }
            });

            /** populate Media Files */
            SAXML.searchSiblingsAndChildrenOf(element, "MediaFile", new SAXML.SAXMLIterator() {
                @Override
                public void foundElement(Element e) {
                    SAVASTMediaFile mediaFile = new SAVASTMediaFile();
                    mediaFile.width = e.getAttribute("width");
                    mediaFile.height = e.getAttribute("height");
                    mediaFile.type = e.getAttribute("type");
                    mediaFile.URL = e.getTextContent();

                    if (mediaFile.type.contains("mp4") || mediaFile.type.contains(".mp4")) {
                        creative.MediaFiles.add(mediaFile);
                    }
                }
            });

            /** add the playable media file */
            if (creative.MediaFiles.size() > 0) {
                creative.playableMediaURL = creative.MediaFiles.get(0).URL;
            }

            /** return creative */
            return creative;
        }

        return null;
    }

    /**
     * ************************************************************
     * Public interface
     * ************************************************************
     */
    public interface SAVASTParserListener {

        /**
         * Called when the parser has successfully parsed a VAST tag
         * @param ads - returns (as a callback parameter) a list of ads
         */
        void didParseVAST(List<SAVASTAd> ads);
    }
}
