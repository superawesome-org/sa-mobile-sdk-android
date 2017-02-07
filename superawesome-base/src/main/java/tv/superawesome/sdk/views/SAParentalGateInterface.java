/**
 * @Copyright:   SuperAwesome Trading Limited 2017
 * @Author:      Gabriel Coman (gabriel.coman@superawesome.tv)
 */
package tv.superawesome.sdk.views;

/**
 * Interface describing thne methods that a view conforming to the parental gate protocol will
 * need to implement.
 */
public interface SAParentalGateInterface {

    /**
     * Method part of SAParentalGateInterface called when the gate is opened
     *
     * @param position int representing the ad position in the ads response array
     */
    void parentalGateOpen (int position);

    /**
     * Method part of SAParentalGateInterface called when the gate is successful
     *
     * @param position    int representing the ad position in the ads response array
     * @param destination URL destination to go to after the parental gate is OK
     */
    void parentalGateSuccess (int position, String destination);

    /**
     * Method part of SAParentalGateInterface called when the gate is failed
     *
     * @param position int representing the ad position in the ads response array
     */
    void parentalGateFailure (int position);

    /**
     * Method part of SAParentalGateInterface called when the gate is closed
     *
     * @param position int representing the ad position in the ads response array
     */
    void parentalGateCancel (int position);

}
