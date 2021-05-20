/*
 * @Copyright:   SuperAwesome Trading Limited 2017
 * @Author:      Gabriel Coman (gabriel.coman@superawesome.tv)
 */
package tv.superawesome.lib.sasession.capper;

/**
 * Interface for the SACapper class
 */
public interface SACapperInterface {

    /**
     * Method that is used to send a message to the SDK saying the DAU ID was able to find a
     * valid int value to return
     */
    void didFindDAUID(int dauID);
}
