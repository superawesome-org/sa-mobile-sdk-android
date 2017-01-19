/**
 * @Copyright:   SuperAwesome Trading Limited 2017
 * @Author:      Gabriel Coman (gabriel.coman@superawesome.tv)
 */
package tv.superawesome.sdk.cpi;

/**
 * Interface that defines a method to be implemented by the SASourceBundleInspector class
 */
public interface SASourceBundleInspectorInterface {

    /**
     * Callback method that is called when the source bundle inspector finishes searching for
     * a package on the device
     *
     * @param sourceBundle the source bundle that's been found
     */
    void saDidFindAppOnDevice (String sourceBundle);

}
