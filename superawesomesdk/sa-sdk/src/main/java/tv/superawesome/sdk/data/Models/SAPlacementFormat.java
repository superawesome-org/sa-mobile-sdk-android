/**
 * @class: SAAd.java
 * @copyright: (c) 2015 SuperAwesome Ltd. All rights reserved.
 * @author: Gabriel Coman
 * @date: 28/09/2015
 *
 */

package tv.superawesome.sdk.data.Models;

/**
 * @brief:
 * Define the Placement Format of a creative
 * It's not used at the moment
 */
public enum SAPlacementFormat {
    web_display {
        @Override
        public String toString() {
            return "web_display";
        }
    },
    floor_display {
        @Override
        public String toString() {
            return "floor_display";
        }
    }
}
