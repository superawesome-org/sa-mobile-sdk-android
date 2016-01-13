package tv.superawesome.lib.sanetwork;

/**
 * Created by gabriel.coman on 22/12/15.
 */
public class SAUserAgent {

    // constants
    private static final String iOS_Mobile_UserAgent = "Mozilla/5.0 (iPhone; CPU iPhone OS 6_1_4 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) Version/6.0 Mobile/10B350 Safari/8536.25";
    private static final String iOS_Tablet_UserAgent = "Mozilla/5.0 (iPad; CPU OS 7_0 like Mac OS X) AppleWebKit/537.51.1 (KHTML, like Gecko) Version/7.0 Mobile/11A465 Safari/9537.53";

    // only function here - to get the correct user agent
    public static String getUserAgent() {
        if (SASystem.getSystemSize() == SASystemSize.mobile){
            return iOS_Mobile_UserAgent;
        } else if (SASystem.getSystemSize() == SASystemSize.tablet){
            return iOS_Tablet_UserAgent;
        }

        return null;
    }
}
