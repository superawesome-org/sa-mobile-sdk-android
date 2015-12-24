/**
 * @class: SASystemType.java
 * @copyright: (c) 2015 SuperAwesome Ltd. All rights reserved.
 * @author: Gabriel Coman
 * @date: 29/10/2015
 *
 */
package tv.superawesome.lib.sanetwork;

/**
 * enum with types - for this case it's just android or undefined
 */
public enum SASystemType {
    undefined {
        @Override
        public String toString() {
            return "undefined";
        }
    },
    android {
        @Override
        public String toString() {
            return "android";
        }
    }
}
