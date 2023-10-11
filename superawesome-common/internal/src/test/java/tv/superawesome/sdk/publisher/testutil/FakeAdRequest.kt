package tv.superawesome.sdk.publisher.testutil

import tv.superawesome.sdk.publisher.models.AdRequest

fun fakeAdRequest() = object : AdRequest {
    override val test: Boolean = false
    override val pos: Int = 0
    override val skip: Int = 0
    override val playbackMethod: Int = 0
    override val startDelay: Int = 0
    override val install: Int = 0
    override val w: Int = 0
    override val h: Int = 0
    override val options: Map<String, Any>? = null
    override val openRtbPartnerId: String? = null
}