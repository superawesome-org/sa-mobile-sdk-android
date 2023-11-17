package tv.superawesome.demoapp.model

data class StubFile(
    val route: String,
    val filePath: String,
    val mimeType: String? = null,
    val useReadFile: Boolean = false,
)
