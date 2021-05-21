/*
 * @Copyright:   SuperAwesome Trading Limited 2017
 * @Author:      Gabriel Coman (gabriel.coman@superawesome.tv)
 */
package tv.superawesome.lib.sasession.session;

/**
 * Interface for the SASession class
 */
public interface SASessionInterface {

    /**
     * Method that is used to send a message to the SDK saying the session is finally ready
     * and ad loading should commence
     */
    void didFindSessionReady();
}
