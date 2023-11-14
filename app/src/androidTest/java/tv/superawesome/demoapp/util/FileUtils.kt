package tv.superawesome.demoapp.util

import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

object FileUtils {
    @Throws(IOException::class)
    fun readFile(fileName: String): String =
        javaClass.classLoader?.getResourceAsStream(fileName)?.bufferedReader()
            .use { bufferReader -> bufferReader?.readText() } ?: ""

    @Throws(IOException::class)
    fun readBytes(fileName: String): ByteArray? =
        javaClass.classLoader?.getResourceAsStream(fileName)?.readBytes()

    @Throws(IOException::class)
    fun copyFile(inFile: InputStream?, outFile: OutputStream) {
        val buffer = ByteArray(1024)
        var read: Int? = null
        while (inFile?.read(buffer).also { read = it!! } != -1) {
            read?.let { outFile.write(buffer, 0, it) }
        }
    }
}
