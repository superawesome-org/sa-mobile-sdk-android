/**
 * @class: SASystemSize.java
 * @copyright: (c) 2015 SuperAwesome Ltd. All rights reserved.
 * @author: Gabriel Coman
 * @date: 29/10/2015
 *
 */
package tv.superawesome.lib.sanetwork;

/**
 * system size - mobile or tablet basically
 */
public enum SASystemSize {
    undefined {
        @Override
        public String toString() {
            return "undefined";
        }
    },
    mobile {
        @Override
        public String toString() {
            return "mobile";
        }
    },
    tablet {
        @Override
        public String toString() {
            return "tablet";
        }
    }
}
