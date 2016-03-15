package tv.superawesome.lib.savast.savastparser;

import android.app.IntentService;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import tv.superawesome.lib.sanetwork.SAApplication;
import tv.superawesome.lib.sanetwork.SAGet;
import tv.superawesome.lib.sanetwork.SAGetResultsReceiver;
import tv.superawesome.lib.sanetwork.SASyncGet;
import tv.superawesome.lib.sanetwork.SAURLUtils;
import tv.superawesome.lib.sautils.ListFilters;
import tv.superawesome.lib.sautils.SALog;
import tv.superawesome.lib.savast.savastparser.models.SAVASTAd;
import tv.superawesome.lib.savast.saxml.SAXML;
import tv.superawesome.lib.savast.saxml.SAXMLIterator;

/**
 * Created by gabriel.coman on 22/12/15.
 */
public class SAVASTParser implements SAGetResultsReceiver.Receiver {

    private SAGetResultsReceiver mReceiver;
    private SAVASTParserListener listener;
    private List<SAVASTAd> ads;

    public void execute(String url, SAVASTParserListener listener) {
        this.listener = listener;

        mReceiver = new SAGetResultsReceiver(new Handler());
        mReceiver.setReceiver(this);
        Intent intent = new Intent(Intent.ACTION_SYNC, null, SAApplication.getSAApplicationContext(), SAVASTParserInternal.class);
        intent.putExtra("url", url);
        intent.putExtra("receiver", mReceiver);

        SAApplication.getSAApplicationContext().startService(intent);
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        switch (resultCode) {
            case SAVASTParserInternal.STATUS_RUNNING:
                break;
            case SAVASTParserInternal.STATUS_FINISHED: {
                /** get ads */
                ads = AdsDataHolder.getInstance().ads;

                /** do other checks and go forward */
                if (listener != null) {
                    if (ads != null && ads.size() > 0) {
                        listener.didParseVASTAndHasResponse(ads);
                    } else {
                        listener.didNotFindAnyValidAds();
                    }
                }

                break;
            }
            case SAVASTParserInternal.STATUS_ERROR: {
                if (listener != null) {
                    listener.didFindInvalidVASTResponse();
                }
                break;
            }
        }
    }

    public static class SAVASTParserInternal extends IntentService  {

        public static final int STATUS_RUNNING = 0;
        public static final int STATUS_FINISHED = 1;
        public static final int STATUS_ERROR = 2;
        private List<SAVASTAd> ads;

        /**
         * Creates an IntentService.  Invoked by your subclass's constructor.
         */
        public SAVASTParserInternal() {
            super(SAVASTParserInternal.class.getName());
        }

        @Override
        protected void onHandleIntent(Intent intent) {

            final ResultReceiver receiver = intent.getParcelableExtra("receiver");
            String url = intent.getStringExtra("url");

            Bundle bundle = new Bundle();

            receiver.send(STATUS_RUNNING, Bundle.EMPTY);

            try {
                AdsDataHolder.getInstance().ads = parseVAST(url);
                receiver.send(STATUS_FINISHED, bundle);
            } catch (IOException | SAXException | ParserConfigurationException e) {
                e.printStackTrace();
                receiver.send(STATUS_ERROR, bundle);
            } catch (Exception e) {
                receiver.send(STATUS_ERROR, bundle);
            }
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
            String VAST = SASyncGet.execute(url);

            if (VAST == null) {
                /**
                 * return empty ads if  VAST string is NULL - this can sometimes happen because of
                 * SSL certificate issues
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

            /** step 3. start finding ads and parsing them */
            SAXML.searchSiblingsAndChildrenOf(root, "Ad", new SAXMLIterator() {
                @Override
                public void foundElement(Element e) {

                    boolean isInLine = SAXML.checkSiblingsAndChildrenOf(e, "InLine");
                    boolean isWrapper = SAXML.checkSiblingsAndChildrenOf(e, "Wrapper");

                    if (isInLine){
                        SAVASTAd inlineAd = SAVASTModelParsing.parseAdXML(e);
                        lads.add(inlineAd);
                    } else {
                        SAVASTAd wrapperAd = SAVASTModelParsing.parseAdXML(e);

                        String VASTAdTagURI = "";
                        Element uriPointer = SAXML.findFirstInstanceInSiblingsAndChildrenOf(e, "VASTAdTagURI");
                        if (uriPointer != null){
                            VASTAdTagURI = uriPointer.getTextContent();
                        }

                        try {
                            List<SAVASTAd> foundAds = parseVAST(VASTAdTagURI);
                            wrapperAd.Creatives = ListFilters.removeAllButFirstElement(wrapperAd.Creatives);

                            /** merge foundAds with wrapper ad */
                            for (Iterator<SAVASTAd> i = foundAds.iterator(); i.hasNext(); ){
                                SAVASTAd foundAd = i.next();
                                foundAd.sumAd(wrapperAd);
                            }

                            /** add to final return array */
                            for (Iterator<SAVASTAd> i = foundAds.iterator(); i.hasNext(); ){
                                lads.add(i.next());
                            }
                        } catch (IOException | ParserConfigurationException | SAXException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            });

            return lads;
        }
    }

    /**
     * Private class that holds ads
     */
    private static class AdsDataHolder {
        public List<SAVASTAd> ads;
        private static final AdsDataHolder holder = new AdsDataHolder();
        public static AdsDataHolder getInstance() {return holder;}
    }
}
