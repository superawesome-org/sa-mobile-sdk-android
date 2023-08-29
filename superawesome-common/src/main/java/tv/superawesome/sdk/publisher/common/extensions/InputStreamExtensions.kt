package tv.superawesome.sdk.publisher.common.extensions

import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream

@Suppress("MagicNumber")
@Throws(IOException::class)
internal fun InputStream.readAllBytesLegacy(): ByteArray {
    val bufLen = 4 * 0x400 // 4KB
    val buf = ByteArray(bufLen)
    var readLen: Int

    ByteArrayOutputStream().use { outputStream ->
        this.use { inputStream ->
            while (inputStream.read(buf, 0, bufLen).also { readLen = it } != -1) {
                outputStream.write(buf, 0, readLen)
            }
        }
        return outputStream.toByteArray()
    }
}
