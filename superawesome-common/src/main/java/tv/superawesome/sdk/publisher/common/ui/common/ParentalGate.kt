package tv.superawesome.sdk.publisher.common.ui.common

import android.app.AlertDialog
import android.content.Context
import android.text.InputType
import android.widget.EditText
import tv.superawesome.sdk.publisher.common.components.NumberGeneratorType
import tv.superawesome.sdk.publisher.common.components.StringProviderType

class ParentalGate(
    private val numberGenerator: NumberGeneratorType,
    private val stringProvider: StringProviderType,
) {
    interface Listener {
        fun parentalGateOpen()
        fun parentalGateCancel()
        fun parentalGateSuccess()
        fun parentalGateFail()
    }

    init {
        newQuestion()
    }

    var listener: Listener? = null

    private var firstNumber: Int = 0
    private var secondNumber: Int = 0
    private var solution: Int = 0

    private var alertDialog: AlertDialog? = null
    private var errorDialog: AlertDialog? = null

    fun newQuestion() {
        firstNumber = numberGenerator.nextIntForParentalGate()
        secondNumber = numberGenerator.nextIntForParentalGate()
        solution = firstNumber + secondNumber
    }

    /**
     * Method that shows the parental gate popup and fires the necessary events
     */
    fun show(context: Context?) {
        listener?.parentalGateOpen()

        val alertDialog = AlertDialog.Builder(context)
        alertDialog.setTitle(stringProvider.parentalGateTitle)
        alertDialog.setCancelable(false)
        alertDialog.setMessage(stringProvider.parentalGateMessage(firstNumber, secondNumber))

        // Set an EditText view to get user input
        val input = EditText(context)
        input.tag = 998877
        input.inputType = InputType.TYPE_CLASS_NUMBER
        alertDialog.setView(input)

        // create positive button
        alertDialog.setPositiveButton(stringProvider.continueTitle) { dialog, _ ->
            val userValue: Int = input.text.toString().toIntOrNull() ?: 0
            if (userValue == solution) {
                listener?.parentalGateSuccess()
            } else {
                // go on error way
                val errorDialog = AlertDialog.Builder(context)
                errorDialog.setTitle(stringProvider.parentalGateErrorTitle)
                errorDialog.setMessage(stringProvider.parentalGateErrorMessage)

                // set button action
                errorDialog.setPositiveButton(stringProvider.okTitle) { innerDialog, _ ->
                    listener?.parentalGateFail()
                    innerDialog.dismiss()
                }
                this.errorDialog = errorDialog.create()
                this.errorDialog?.show()
            }

            // dismiss
            dialog.dismiss()
        }

        // create negative button
        alertDialog.setNegativeButton(stringProvider.cancelTitle) { dialog, _ -> // dismiss
            dialog.dismiss()
            listener?.parentalGateCancel()
        }
        this.alertDialog = alertDialog.create()
        this.alertDialog?.show()
    }

    fun stop() {
        listener = null
        alertDialog?.dismiss()
        alertDialog = null
        errorDialog?.dismiss()
        errorDialog = null
    }
}