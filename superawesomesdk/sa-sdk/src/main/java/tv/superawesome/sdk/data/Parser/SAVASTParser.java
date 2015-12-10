/**
 * @class: SAVASTParser.java
 * @copyright: (c) 2015 SuperAwesome Ltd. All rights reserved.
 * @author: Gabriel Coman
 * @date: 29/10/2015
 *
 */
package tv.superawesome.sdk.data.Parser;

/**
 * Imports needed for this implementation
 */
import com.google.gson.JsonObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import tv.superawesome.sdk.data.Network.SANetListener;
import tv.superawesome.sdk.data.Network.SANetwork;

/**
 * This class is used to parse VAST data and find the click URL
 */
public class SAVASTParser {

    /**
     * Private variables
     */
    private XmlPullParserFactory xmlFactoryObject;
    private XmlPullParser myparser;

    public SAVASTParser() throws XmlPullParserException {
        xmlFactoryObject = XmlPullParserFactory.newInstance();
        myparser = xmlFactoryObject.newPullParser();
    }

    /**
     * @brief: This function parses the VAST Tag downloaded from vastURL and uses listener to
     * get back the result as a callback
     * @param vastURL - the URL of the VAST Tag
     * @param listener - a reference to a listnere
     */
    public void findCorrectVASTClick(String vastURL, final SAVASTListener listener) {

        SANetwork.sendGET(vastURL, new JsonObject(), new SANetListener() {
            @Override
            public void success(Object data) {
                System.out.println((String)data);
                listener.findCorrectVASTClick("http://superawesome.tv");
            }

            @Override
            public void failure() {

            }
        });

    }
}
