package tv.superawesome.lib.savast.savastparser;

import android.os.AsyncTask;

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

import tv.superawesome.lib.sanetwork.SASyncGet;
import tv.superawesome.lib.sautils.ListFilters;
import tv.superawesome.lib.savast.savastparser.models.SAVASTAd;
import tv.superawesome.lib.savast.saxml.SAXML;
import tv.superawesome.lib.savast.saxml.SAXMLIterator;

/**
 * Created by gabriel.coman on 22/12/15.
 */
public class SAVASTParser extends AsyncTask<String, Integer, String> {

    private SAVASTParserListener listener;
    private List<SAVASTAd> ads;

    // execute function
    public void execute(String url, SAVASTParserListener listener) {
        this.listener = listener;
        super.execute(url);
    }

    @Override
    protected String doInBackground(String[] url) {

        try {
            ads = parseVAST(url[0]);
        } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }

        return "";
    }

    @Override
    protected void onPostExecute(String response) {
        /** send the success response, with a string as the object */
        if (listener != null) {
            if (ads.size() > 0) {
                listener.didParseVASTAndHasResponse(ads);
            } else {
                listener.didNotFindAnyValidAds();
            }
        }
    }

    /**
     * this function will be called everytime the SAGet class should return
     * some kind of error
     * @param e - the exception
     */
    private void handleError(Exception e) {
        /** print stack */
        if (e != null) {
            e.printStackTrace();
        }

        /** call failure, if listner exists (and normally, it should exist) */
        if (listener != null) {
            listener.didFindInvalidVASTResponse();
        }
    }

    /**
     * The main parseing function
     * @param url - URL to the VAST
     * @return an array of VAST ads
     */
    public static List<SAVASTAd> parseVAST(String url) throws IOException, ParserConfigurationException, SAXException {
        // create the array of ads that should be returned
        final List<SAVASTAd> lads = new ArrayList<SAVASTAd>();

        // step 1: get the XML
        String VAST = SASyncGet.execute(url);
        // SALog.Log("VAST " + VAST);

        // create the Doc builder factory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        // Download the XML file
        Document doc = db.parse(new InputSource(new ByteArrayInputStream(VAST.getBytes("utf-8"))));
        doc.getDocumentElement().normalize();

        // step 2. get the correct reference to the root XML element
        final Element root = (Element) doc.getElementsByTagName("VAST").item(0);

        // step 3. start finding ads and parsing them
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

                        // merge foundAds with wrapper ad
                        for (Iterator<SAVASTAd> i = foundAds.iterator(); i.hasNext(); ){
                            SAVASTAd foundAd = i.next();
                            foundAd.sumAd(wrapperAd);
                        }

                        // add to final return array
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
