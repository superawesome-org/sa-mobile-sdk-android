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
 * An enum that defines the number of formats an ad can be in
 *  - format_unknown: defined by the SDK in case of some error
 *  - image_with_link: the creative is a simple banner image
 *  - video: the creative is a video that must be streamed
 *  - rich_media: a mini-HTML page with user interaction
 *  - rich_media_resizing: a mini-HTML page that will expand out of bounds
 *  - swf: deprecated
 *  - tag: a rich-media served as a JS file
 */
public enum SACreativeFormat {

    format_unknown {
        @Override
        public String toString() {
            return "format_unknown";
        }
    },
    image_with_link {
        @Override
        public String toString() {
            return "image_with_link";
        }
    },
    video {
        @Override
        public String toString() {
            return "video";
        }
    },
    rich_media {
        @Override
        public String toString() {
            return "rich_media";
        }
    },
    rich_media_resizing {
        @Override
        public String toString() {
            return "rich_media_resizing";
        }
    },
    swf {
        @Override
        public String toString() {
            return "swf";
        }
    },
    tag {
        @Override
        public String toString() {
            return "tag";
        }
    }
}
