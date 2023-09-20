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
import tv.superawesome.demoapp.MyApplication
import tv.superawesome.demoapp.R
import tv.superawesome.demoapp.admob.AdMobActivity
import tv.superawesome.demoapp.databinding.FragmentSettingsBinding

class SettingsDialogFragment : DialogFragment() {
    var onDismissListener: (() -> Unit)? = null
    private val adapter = SettingsListAdapter()

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

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
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onTapAdMob() {
        startActivity(Intent(context, AdMobActivity::class.java))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.contentDescription = "SettingsView"
        binding.adMobButton.contentDescription = "Settings.Buttons.AdMob"
        binding.resetButton.contentDescription = "Settings.Buttons.Reset"
        binding.closeButton.contentDescription = "Settings.Buttons.Close"

        adapter.onItemSelected = { item, option ->
            DataStore.updateSettings(item.item, option)
        }
        adapter.onEnvironmentSelected = { value ->
            val application = view.context.applicationContext as MyApplication
            application.switchEnvironment(value)
        }
        updateAdapter()

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        binding.adMobButton.setOnClickListener {
            dismiss()
            onTapAdMob()
        }

        binding.resetButton.setOnClickListener {
            DataStore.reset()
            updateAdapter()
        }

        binding.closeButton.setOnClickListener {
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
