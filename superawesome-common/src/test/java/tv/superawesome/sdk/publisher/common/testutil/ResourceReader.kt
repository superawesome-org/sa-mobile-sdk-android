package tv.superawesome.sdk.publisher.common.testutil

import java.io.InputStream
import java.util.Scanner

object ResourceReader {
    fun readResource(name: String?): String {
        val inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream(name)
        return convertStreamToString(inputStream)
    }

    private fun convertStreamToString(inputStream: InputStream): String {
        val kCHARSET = "UTF-8"
        val kDELIMITER = "\\A"
        val s = Scanner(inputStream, kCHARSET).useDelimiter(kDELIMITER)
        return if (s.hasNext()) s.next() else ""
    }
}
