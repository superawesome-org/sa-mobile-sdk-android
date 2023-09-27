/*
 * @Copyright:   SADefaults Trading Limited 2017
 * @Author:      Gabriel Coman (gabriel.coman@superawesome.tv)
 */
package tv.superawesome.sdk.publisher;

import androidx.annotation.NonNull;

/**
 * This enum holds all the possible callback values that an ad sends during its lifetime
 *  - adLoaded:         ad was loaded successfully and is ready to be displayed
 *  - adEmpty:          ad was empty
 *  - adFailedToLoad:   ad was not loaded successfully and will not be able to play
 *  - adAlreadyLoaded   ad was previously loaded in an interstitial, video or app wall queue
 *  - adShown:          triggered once when the ad first displays
 *  - adFailedToShow:   for some reason the ad failed to show; technically this should
 *                      never happen nowadays
 *  - adClicked:        triggered every time the ad gets clicked
 *  - adClosed:         triggered once when the ad is closed
 *  - adPaused:         triggered when the ad is paused
 *  - adPlaying:        triggered when the ad is playing or resumes
 */
public enum SAEvent {
    adLoaded {
        @NonNull
        @Override
        public String toString() {
            return "adLoaded";
        }
    },
    adEmpty {
        @NonNull
        @Override
        public String toString() {
            return "adEmpty";
        }
    },
    adFailedToLoad {
        @NonNull
        @Override
        public String toString() {
            return "adFailedToLoad";
        }
    },
    adAlreadyLoaded {
        @NonNull
        @Override
        public String toString() {
            return "adAlreadyLoaded";
        }
    },
    adShown {
        @NonNull
        @Override
        public String toString() {
            return "adShown";
        }
    },
    adFailedToShow {
        @NonNull
        @Override
        public String toString() {
            return "adFailedToShow";
        }
    },
    adClicked {
        @NonNull
        @Override
        public String toString() {
            return "adClicked";
        }
    },
    adEnded {
        @NonNull
        @Override
        public String toString() {
            return "adEnded";
        }
    },
    adClosed {
        @NonNull
        @Override
        public String toString() {
            return "adClosed";
        }
    },
    adPaused {
        @NonNull
        @Override
        public String toString() { return "adPaused"; }
    },
    adPlaying {
        @NonNull
        @Override
        public String toString() { return "adPlaying"; }
    },
    webSDKReady {
        @NonNull
        @Override
        public String toString() { return "webSDKReady"; }
    }
}
