/**
 * @class: SAVASTListner.java
 * @copyright: (c) 2015 SuperAwesome Ltd. All rights reserved.
 * @author: Gabriel Coman
 * @date: 29/10/2015
 *
 */
package tv.superawesome.sdk.data.Parser;

/**
 * This is an interface used by SAVASTParser. Functions defined by SAVASTListner are used by the
 * parser to send the message that a click URL has been found
 */
public interface SAVASTListener {
    /**
     * @brief: After SALVASTParser parses a VAST Tag and finds the click URL, this function is
     * called to send a callback param vastURL
     * @param clickURL - sends back the VAST click URL that was found
     */
    void findCorrectVASTClick(String clickURL);
}
