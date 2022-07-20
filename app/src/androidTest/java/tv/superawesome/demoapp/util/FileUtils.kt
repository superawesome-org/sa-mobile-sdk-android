package tv.superawesome.demoapp.util

import java.io.IOException

object FileUtils {
    @Throws(IOException::class)
    fun readFile(fileName: String): String =
        javaClass.classLoader?.getResourceAsStream(fileName)?.bufferedReader()
            .use { bufferReader -> bufferReader?.readText() } ?: ""
}
