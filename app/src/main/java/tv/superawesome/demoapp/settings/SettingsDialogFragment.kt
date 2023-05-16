package tv.superawesome.demoapp.settings

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.fragment_settings.*
import tv.superawesome.demoapp.MyApplication
import tv.superawesome.demoapp.R
import tv.superawesome.demoapp.admob.AdMobActivity
import tv.superawesome.sdk.publisher.common.models.CloseButtonState

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
        savedInstanceState: Bundle?,
    ): View? = inflater.inflate(R.layout.fragment_settings, null, false)

    private fun onTapAdMob() {
        startActivity(Intent(context, AdMobActivity::class.java))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.contentDescription = "SettingsView"

        adMobButton.contentDescription = "Settings.Buttons.AdMob"
        adMobButton.setOnClickListener {
            dismiss()
            onTapAdMob()
        }

        resetButton.contentDescription = "Settings.Buttons.Reset"
        resetButton.setOnClickListener {
            app?.resetSettings()
        }

        closeButton.contentDescription = "Settings.Buttons.Close"
        closeButton.setOnClickListener {
            dismiss()
        }

        closeImmediatelyButton.contentDescription = "SettingsItem.Buttons.CloseImmediately"
        closeImmediatelyButton.setOnClickListener {
            app?.updateSettings { it.copy(closeButtonState = CloseButtonState.VisibleImmediately) }
        }

        closeDelayedButton.contentDescription = "SettingsItem.Buttons.CloseDelayed"
        closeDelayedButton.setOnClickListener {
            app?.updateSettings { it.copy(closeButtonState = CloseButtonState.VisibleWithDelay) }
        }

        closeHiddenButton.contentDescription = "SettingsItem.Buttons.CloseHidden"
        closeHiddenButton.setOnClickListener {
            app?.updateSettings { it.copy(closeButtonState = CloseButtonState.Hidden) }
        }

        bumperEnableButton.contentDescription = "SettingsItem.Buttons.BumperEnable"
        bumperEnableButton.setOnClickListener {
            app?.updateSettings { it.copy(bumperEnabled = true) }
        }

        bumperDisableButton.contentDescription = "SettingsItem.Buttons.BumperDisable"
        bumperDisableButton.setOnClickListener {
            app?.updateSettings { it.copy(bumperEnabled = false) }
        }

        parentalEnableButton.contentDescription = "SettingsItem.Buttons.ParentalGateEnable"
        parentalEnableButton.setOnClickListener {
            app?.updateSettings { it.copy(parentalEnabled = true) }
        }

        parentalDisableButton.contentDescription = "SettingsItem.Buttons.ParentalGateDisable"
        parentalDisableButton.setOnClickListener {
            app?.updateSettings { it.copy(parentalEnabled = false) }
        }

        playbackEnableButton.contentDescription = "SettingsItem.Buttons.PlaybackEnable"
        playbackEnableButton.setOnClickListener {
            app?.updateSettings { it.copy(playEnabled = true) }
        }

        playbackDisableButton.contentDescription = "SettingsItem.Buttons.PlaybackDisable"
        playbackDisableButton.setOnClickListener {
            app?.updateSettings { it.copy(playEnabled = false) }
        }

        muteEnableButton.contentDescription = "SettingsItem.Buttons.MuteEnable"
        muteEnableButton.setOnClickListener {
            app?.updateSettings { it.copy(muteOnStart = true) }
        }

        muteDisableButton.contentDescription = "SettingsItem.Buttons.MuteDisable"
        muteDisableButton.setOnClickListener {
            app?.updateSettings { it.copy(muteOnStart = false) }
        }

        videoCloseDialogEnableButton.contentDescription = "SettingsItem.Buttons.VideoCloseDialogEnable"
        videoCloseDialogEnableButton.setOnClickListener {
            app?.updateSettings { it.copy(videoWarnOnClose = true) }
        }

        videoCloseDialogDisableButton.contentDescription = "SettingsItem.Buttons.VideoCloseDialogDisable"
        videoCloseDialogDisableButton.setOnClickListener {
            app?.updateSettings { it.copy(videoWarnOnClose = false) }
        }

        videoCloseAtEndEnableButton.contentDescription = "SettingsItem.Buttons.VideoCloseAtEndEnable"
        videoCloseAtEndEnableButton.setOnClickListener {
            app?.updateSettings { it.copy(closeAtEnd = true) }
        }

        videoCloseAtEndDisableButton.contentDescription = "SettingsItem.Buttons.VideoCloseAtEndDisable"
        videoCloseAtEndDisableButton.setOnClickListener {
            app?.updateSettings { it.copy(closeAtEnd = false) }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onDismissListener?.invoke()
    }
}
