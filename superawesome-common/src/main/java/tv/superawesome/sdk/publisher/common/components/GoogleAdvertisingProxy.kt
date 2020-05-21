package tv.superawesome.sdk.publisher.common.components

import android.content.Context
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface GoogleAdvertisingProxyType {
    suspend fun findAdvertisingId(): String?
}

class GoogleAdvertisingProxy(private val context: Context) : GoogleAdvertisingProxyType {
    companion object {
        const val GOOGLE_ADVERTISING_CLASS = "com.google.android.gms.ads.identifier.AdvertisingIdClient"
        const val GOOGLE_ADVERTISING_ID_CLASS = "com.google.android.gms.ads.identifier.AdvertisingIdClient\$Info"
        const val GOOGLE_ADVERTISING_INFO_METHOD = "getAdvertisingIdInfo"
        const val GOOGLE_ADVERTISING_TRACKING_METHOD = "isLimitAdTrackingEnabled"
        const val GOOGLE_ADVERTISING_ID_METHOD = "getId"
    }

    override suspend fun findAdvertisingId(): String? = try {
        val advertisingIdClass = Class.forName(GOOGLE_ADVERTISING_CLASS)
        val getAdvertisingIdInfoMethod = advertisingIdClass.getMethod(GOOGLE_ADVERTISING_INFO_METHOD, Context::class.java)

        val advertisingIdInfoClass = Class.forName(GOOGLE_ADVERTISING_ID_CLASS)
        val isLimitAdTrackingEnabledMethod = advertisingIdInfoClass.getMethod(GOOGLE_ADVERTISING_TRACKING_METHOD)
        val getIdMethod = advertisingIdInfoClass.getMethod(GOOGLE_ADVERTISING_ID_METHOD)

        withContext(Dispatchers.IO) {
            val adInfo = getAdvertisingIdInfoMethod.invoke(advertisingIdClass, context)
            val limitAdTracking = isLimitAdTrackingEnabledMethod.invoke(adInfo) as Boolean

            if (!limitAdTracking) {
                getIdMethod.invoke(adInfo) as String?
            } else {
                null
            }
        }
    } catch (exception: Exception) {
        Log.i("SuperAwesome", "Google services not available")
        null
    }
}