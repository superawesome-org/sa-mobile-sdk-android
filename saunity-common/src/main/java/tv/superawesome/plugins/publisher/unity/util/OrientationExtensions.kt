package tv.superawesome.plugins.publisher.unity.util

import tv.superawesome.sdk.publisher.common.models.Orientation

fun getOrientationFromValue(value: Int): Orientation? = Orientation.fromValue(value)
