package tv.superawesome.demoapp.model

data class TestData(val placement: String, val fileName: String) {
    companion object {
        val empty = TestData("", "")
    }
}