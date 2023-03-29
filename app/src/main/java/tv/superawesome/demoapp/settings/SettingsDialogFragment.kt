package tv.superawesome.demoapp.settings

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.fragment_settings.*
import tv.superawesome.demoapp.MyApplication
import tv.superawesome.demoapp.R
import tv.superawesome.lib.sasession.defines.SAConfiguration
import tv.superawesome.sdk.publisher.state.CloseButtonState

class SettingsDialogFragment : DialogFragment() {
    var onDismissListener: (() -> Unit)? = null

    private val app: MyApplication?
        get() = activity?.application as? MyApplication

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun getTheme(): Int = R.style.AppTheme_DialogOverlay

    @SuppressLint("InflateParams")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_settings, null, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        resetButton.setOnClickListener {
            app?.resetSettings()
        }
        closeButton.setOnClickListener {
            dismiss()
        }
        uiTestingButton.setOnClickListener {
            app?.updateSettings { it.copy(environment = SAConfiguration.UITESTING) }
        }
        productionButton.setOnClickListener {
            app?.updateSettings { it.copy(environment = SAConfiguration.PRODUCTION) }
        }

        closeImmediatelyButton.setOnClickListener {
            app?.updateSettings { it.copy(closeButtonState = CloseButtonState.VisibleImmediately) }
        }

        closeDelayedButton.setOnClickListener {
            app?.updateSettings { it.copy(closeButtonState = CloseButtonState.VisibleWithDelay) }
        }

        closeHiddenButton.setOnClickListener {
            app?.updateSettings { it.copy(closeButtonState = CloseButtonState.Hidden) }
        }

        bumperEnableButton.setOnClickListener {
            app?.updateSettings { it.copy(bumperEnabled = true) }
        }

        bumperDisableButton.setOnClickListener {
            app?.updateSettings { it.copy(bumperEnabled = false) }
        }

        parentalEnableButton.setOnClickListener {
            app?.updateSettings { it.copy(parentalEnabled = true) }
        }

        parentalDisableButton.setOnClickListener {
            app?.updateSettings { it.copy(parentalEnabled = false) }
        }

        playbackEnableButton.setOnClickListener {
            app?.updateSettings { it.copy(playEnabled = true) }
        }

        playbackDisableButton.setOnClickListener {
            app?.updateSettings { it.copy(playEnabled = false) }
        }

        muteEnableButton.setOnClickListener {
            app?.updateSettings { it.copy(muteOnStart = true) }
        }

        muteDisableButton.setOnClickListener {
            app?.updateSettings { it.copy(muteOnStart = false) }
        }

        enableCloseAtEndButton.setOnClickListener {
            app?.updateSettings { it.copy(closeAtEnd = true) }
        }

        disableCloseAtEndButton.setOnClickListener {
            app?.updateSettings { it.copy(closeAtEnd = false) }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onDismissListener?.invoke()
    }
}