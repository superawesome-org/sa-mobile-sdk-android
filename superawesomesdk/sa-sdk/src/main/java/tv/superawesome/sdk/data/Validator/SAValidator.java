/**
 * @class: SAValidator.java
 * @copyright: (c) 2015 SuperAwesome Ltd. All rights reserved.
 * @author: Gabriel Coman
 * @date: 29/10/2015
 *
 */
package tv.superawesome.sdk.data.Validator;

/**
 * Imports needed for this class' implementation
 */
import tv.superawesome.lib.sautils.SALog;
import tv.superawesome.sdk.data.Models.SAAd;
import tv.superawesome.sdk.data.Models.SACreativeFormat;

/**
 * This class is used in validating all Ad data
 */
public class SAValidator {

    /**
     * @brief: This function tests wherer an Ad is valid; It does so by looking at some crucial ad
     * data that has to be there, as well as data that has to exist depending on the actual type
     * of the ad
     * @param ad - the ad that you want to test
     * @return - true or false, depending on the success of the tests
     */
    public static boolean isAdDataValid(SAAd ad) {

        /** 0. If ad is not null */
        if (ad == null) {
            return false;
        }

        /** 1. if Ad has no Creative, data is not valid */
        if (ad.creative == null) {
            return false;
        }

        /** 2. if format is unknown, data is not valid */
        if (ad.creative.format == SACreativeFormat.invalid) {
            return false;
        }

        /** 3. if creative has no details, data is not valid */
        if (ad.creative.details == null) {
            return false;
        }

        switch (ad.creative.format) {
            case image:{
                /**
                 * if Ad is image with link, but no image filed could be found,
                 * data is not valid
                 */
                if (ad.creative.details.image == null)
                    return false;
                break;
            }
            case video:{
                /**
                 * 4.2. if Ad is video and either the video or the vast
                 * tags could not be found, data is not valid
                 */
                if (ad.creative.details.vast == null)
                    return false;
                break;
            }
            case rich:{
                /**
                 * 4.3. if Ad is rich media and no url can be found,
                 * then data is not valid
                 */
                if (ad.creative.details.url == null)
                    return false;
                break;
            }
            case tag:{
                /**
                 * 4.5. if Ad is tag and no tag can be found,
                 * then data is not valid
                 */
                if (ad.creative.details.tag == null)
                    return false;
                break;
            }
            default:{
                break;
            }
        }

        return true;
    }

}
