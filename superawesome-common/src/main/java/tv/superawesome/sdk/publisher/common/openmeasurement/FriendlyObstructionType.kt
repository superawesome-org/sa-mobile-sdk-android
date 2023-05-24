package tv.superawesome.sdk.publisher.common.openmeasurement

import com.iab.omid.library.superawesome.adsession.FriendlyObstructionPurpose

enum class FriendlyObstructionType {
    MediaControls,
    CloseAd,
    NotVisible,
    Other;
}

internal fun FriendlyObstructionType.OMIDFriendlyObstruction(): FriendlyObstructionPurpose =
    when (this) {
        FriendlyObstructionType.MediaControls -> FriendlyObstructionPurpose.VIDEO_CONTROLS
        FriendlyObstructionType.CloseAd -> FriendlyObstructionPurpose.CLOSE_AD
        FriendlyObstructionType.NotVisible -> FriendlyObstructionPurpose.NOT_VISIBLE
        else -> FriendlyObstructionPurpose.OTHER
    }
