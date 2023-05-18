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
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_settings.*
import tv.superawesome.demoapp.R
import tv.superawesome.demoapp.admob.AdMobActivity

class SettingsDialogFragment : DialogFragment() {
    var onDismissListener: (() -> Unit)? = null
    private val adapter = SettingsListAdapter()

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
        resetButton.contentDescription = "Settings.Buttons.Reset"
        closeButton.contentDescription = "Settings.Buttons.Close"

        adapter.onItemSelected = { item, option ->
            DataStore.updateSettings(item, option)
        }
        updateAdapter()

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        adMobButton.setOnClickListener {
            dismiss()
            onTapAdMob()
        }

        resetButton.setOnClickListener {
            DataStore.reset()
            updateAdapter()
        }

        closeButton.setOnClickListener {
            dismiss()
        }
    }

    private fun updateAdapter() {
        adapter.updateData(DataStore.toList())
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onDismissListener?.invoke()
    }
}
