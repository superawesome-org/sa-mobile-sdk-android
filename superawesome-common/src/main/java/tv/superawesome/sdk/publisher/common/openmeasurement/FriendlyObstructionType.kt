package tv.superawesome.sdk.publisher.common.openmeasurement

import com.iab.omid.library.superawesome.adsession.FriendlyObstructionPurpose

/**
 * This enum specifies AA SDK friendly versions of the
 * Open Measurement SDK Friendly Obstruction Purposes.
 */
internal enum class FriendlyObstructionType {
    MediaControls,
    CloseAd,
    NotVisible,
    Other;
}

/**
 * Helper function to convert AA SDK enum FriendlyObstructionType
 * to OM enum FriendlyObstructionPurpose.
 * @return FriendlyObstructionPurpose value.
 */
internal fun FriendlyObstructionType.omidFriendlyObstruction(): FriendlyObstructionPurpose =
    when (this) {
        FriendlyObstructionType.MediaControls -> FriendlyObstructionPurpose.VIDEO_CONTROLS
        FriendlyObstructionType.CloseAd -> FriendlyObstructionPurpose.CLOSE_AD
        FriendlyObstructionType.NotVisible -> FriendlyObstructionPurpose.NOT_VISIBLE
        else -> FriendlyObstructionPurpose.OTHER
    }
