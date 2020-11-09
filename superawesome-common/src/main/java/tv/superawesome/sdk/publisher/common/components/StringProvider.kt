package tv.superawesome.sdk.publisher.common.components

interface StringProviderType {
    val parentalGateTitle: String
    val parentalGateMessage: String
    val parentalGateErrorTitle: String
    val parentalGateErrorMessage: String
    fun parentalGateMessage(firstNumber: Int, secondNumber: Int): String

    fun bumperPageTimeLeft(seconds: Int): String
    fun bumperPageLeaving(appName: String?): String

    val cancelTitle: String
    val continueTitle: String
    val okTitle: String
}

class StringProvider : StringProviderType {
    override val parentalGateTitle: String = "Parental Gate"
    override val parentalGateMessage: String = "Please solve the following problem to continue: "
    override val parentalGateErrorTitle: String = "Oops! That was the wrong answer."
    override val parentalGateErrorMessage: String = "Please seek guidance from a responsible adult to help you continue."
    override fun parentalGateMessage(firstNumber: Int, secondNumber: Int): String =
            "${parentalGateMessage}$firstNumber + $secondNumber = ? "

    override val cancelTitle: String = "Cancel"
    override val continueTitle: String = "Continue"
    override val okTitle: String = "Ok"

    override fun bumperPageTimeLeft(seconds: Int): String =
            "A new site will open in $seconds seconds. Remember to stay safe online and don’t share your username or password with anyone!"

    override fun bumperPageLeaving(appName: String?): String =
            "Bye! You’re now leaving ${appName ?: "this app"}."
}