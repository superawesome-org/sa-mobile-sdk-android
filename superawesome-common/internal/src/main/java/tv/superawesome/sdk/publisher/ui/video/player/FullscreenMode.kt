package tv.superawesome.sdk.publisher.ui.video.player

enum class FullscreenMode {
    ANY, PORTRAIT, LANDSCAPE;

    val value: Int
        get() = ordinal

    companion object {
        @JvmStatic
        fun fromValue(orientation: Int): FullscreenMode = entries.getOrElse(orientation) { ANY }
    }
}
