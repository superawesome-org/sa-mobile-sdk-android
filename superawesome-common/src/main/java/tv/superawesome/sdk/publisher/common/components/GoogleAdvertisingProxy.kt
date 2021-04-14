package tv.superawesome.sdk.publisher.common.components

import android.content.Context
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface GoogleAdvertisingProxyType {
    suspend fun findAdvertisingId(): String?
}

class GoogleAdvertisingProxy(private val context: Context) : GoogleAdvertisingProxyType {

    override suspend fun findAdvertisingId(): String? = kotlin.runCatching {
        withContext(Dispatchers.IO) {
            val adInfo = AdvertisingIdClient.getAdvertisingIdInfo(context)
            val limitAdTracking = adInfo.isLimitAdTrackingEnabled
            if (!limitAdTracking) {
                adInfo.id
            } else {
                null
            }
        }
    }.getOrNull()
}
