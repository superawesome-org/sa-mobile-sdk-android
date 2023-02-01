package com.superawesome.composeexample.enums

import com.superawesome.composeexample.R

enum class FeatureType {
    Banner, Interstitial, Video;

    val titleRes: Int
        get() =
            when (this) {
                Banner -> R.string.features_banner_title
                Interstitial -> R.string.features_interstitial_title
                Video -> R.string.features_video_title
            }
}
