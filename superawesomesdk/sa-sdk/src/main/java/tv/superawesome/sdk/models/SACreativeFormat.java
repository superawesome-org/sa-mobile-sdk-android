/**
 * @class: SAAd.java
 * @copyright: (c) 2015 SuperAwesome Ltd. All rights reserved.
 * @author: Gabriel Coman
 * @date: 28/09/2015
 *
 */

package tv.superawesome.sdk.models;

/**
 * An enum that defines the number of formats an ad can be in
 *  - invalid: defined by the SDK in case of some error
 *  - image: the creative is a simple banner image
 *  - video: the creative is a video that must be streamed
 *  - rich: a mini-HTML page with user interaction
 *  - tag: a rich-media (usually) served as a JS file via a 3rd party service
 */
public enum SACreativeFormat {

    invalid {
        @Override
        public String toString() {
            return "invalid";
        }
    },
    image {
        @Override
        public String toString() {
            return "image";
        }
    },
    video {
        @Override
        public String toString() {
            return "video";
        }
    },
    rich{
        @Override
        public String toString() {
            return "rich";
        }
    },
    tag {
        @Override
        public String toString() {
            return "tag";
        }
    }
}
