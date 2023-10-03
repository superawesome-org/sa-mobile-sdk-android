package tv.superawesome.sdk.publisher.extensions

import tv.superawesome.sdk.publisher.models.AdResponse
import tv.superawesome.sdk.publisher.models.CreativeFormatType

val AdResponse.isValidVastAd
    get() = !(ad.creative.format == CreativeFormatType.Video &&
            ad.creative.details.tag == null &&
            ad.creative.details.vast == null)