package tv.superawesome.demoapp.util

object ParentalGateUtil {
    fun solve(inputText: String): Int {
        val split = inputText.split("+")
        if (split.size != 2) return 0
        val number1 = split[0].replace(("[^\\d.]").toRegex(), "").toInt()
        val number2 = split[1].replace(("[^\\d.]").toRegex(), "").toInt()
        return number1 + number2
    }
}