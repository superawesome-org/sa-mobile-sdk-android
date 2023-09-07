package tv.superawesome.plugins.publisher.unity.util

import tv.superawesome.sdk.publisher.models.Orientation

internal fun getOrientationFromValue(value: Int): Orientation? = Orientation.fromValue(value)
