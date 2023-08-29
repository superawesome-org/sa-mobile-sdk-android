package tv.superawesome.sdk.publisher.common.utilities

import java.io.File

class FileWrapper(private val file: File) {

    fun exists(): Boolean = file.exists()

    fun readBytes(): ByteArray = file.readBytes()
}
