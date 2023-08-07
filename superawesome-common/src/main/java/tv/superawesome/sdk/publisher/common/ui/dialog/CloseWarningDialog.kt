package tv.superawesome.sdk.publisher.common.ui.dialog

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES

internal object CloseWarningDialog {
    private const val CloseButtonTitle = "Close Video"
    private const val ResumeButtonTitle = "Resume Video"
    private const val AlertTitle = "Close Video?"
    private const val AlertDescription = "You will lose your reward"

    private var dialog: AlertDialog? = null
    private var listener: Interface? = null

    fun show(context: Context) {
        val alert: AlertDialog.Builder = if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP_MR1) {
            AlertDialog.Builder(context, android.R.style.Theme_DeviceDefault_Dialog_Alert)
        } else {
            AlertDialog.Builder(context)
        }
        alert.setTitle(AlertTitle)
        alert.setCancelable(false)
        alert.setMessage(AlertDescription)
        alert.setPositiveButton(ResumeButtonTitle) { dialog: DialogInterface, _: Int ->
            listener?.onResumeSelected()
            dialog.dismiss()
        }
        alert.setNegativeButton(CloseButtonTitle) { dialog: DialogInterface, _: Int ->
            listener?.onCloseSelected()
            dialog.dismiss()
        }
        dialog = alert.create()
        dialog?.show()
    }

    fun close() {
        if (dialog?.isShowing == true) {
            dialog?.cancel()
        }
        dialog = null
    }

    fun setListener(listener: Interface?) {
        CloseWarningDialog.listener = listener
    }

    interface Interface {
        fun onResumeSelected()
        fun onCloseSelected()
    }
}
