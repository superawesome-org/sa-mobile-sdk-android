package tv.superawesome.lib.saopenmeasurement

import android.content.Context
import tv.superawesome.sdk.publisher.base.R
import java.io.IOException
import java.nio.charset.Charset

/**
 * OmidJsLoader - utility for loading the Omid JavaScript resource
 */
object OmidJsLoader {
    /**
     * getOmidJs - gets the Omid JS resource as a string
     * @param context - used to access the JS resource
     * @return - the Omid JS resource as a string
     */
    fun getOmidJs(context: Context): String {
        val res = context.resources
        try {
            res.openRawResource(R.raw.omsdk_v1).use { inputStream ->
                val bytes = ByteArray(inputStream.available())
                val bytesRead = inputStream.read(bytes)
                return String(bytes, 0, bytesRead, Charset.forName("UTF-8"))
            }
        } catch (error: IOException) {
            throw UnsupportedOperationException("OMID resource not found", error)
        }
    }
}
