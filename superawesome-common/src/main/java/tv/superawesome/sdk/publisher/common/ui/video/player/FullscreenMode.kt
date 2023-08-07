package tv.superawesome.sdk.publisher.common.ui.video.player

internal enum class FullscreenMode {
    ANY, PORTRAIT, LANDSCAPE;

    val value: Int
        get() = ordinal

    companion object {
        @JvmStatic
        fun fromValue(orientation: Int): FullscreenMode = entries.getOrElse(orientation) { ANY }
    }
}
