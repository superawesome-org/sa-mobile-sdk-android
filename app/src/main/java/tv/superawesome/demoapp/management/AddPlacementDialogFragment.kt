package tv.superawesome.demoapp.management

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.DialogFragment
import tv.superawesome.demoapp.R
import tv.superawesome.demoapp.databinding.FragmentAddPlacementBinding
import tv.superawesome.demoapp.model.FeatureType
import tv.superawesome.demoapp.model.PlacementItem

class AddPlacementDialogFragment : DialogFragment() {
    var onSubmitListener: ((PlacementItem) -> Unit)? = null

    private var _binding: FragmentAddPlacementBinding? = null
    private var isPlacementNameFieldPopulated = false
    private var isPlacementIdFieldPopulated = false
    private var isPlacementTypeSelected = false
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
        _binding = FragmentAddPlacementBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.contentDescription = "AddPlacementView"

        binding.placementNameInput.contentDescription = "AddPlacementView.EditText.PlacementName"
        binding.placementIdInput.contentDescription = "AddPlacementView.EditText.PlacementId"
        binding.lineItemIdIdInput.contentDescription = "AddPlacementView.EditText.LineItemId"
        binding.creativeIdIdInput.contentDescription = "AddPlacementView.EditText.CreativeId"

        binding.bannerTypeRadioButton.contentDescription = "AddPlacementView.RadioButtons.Banner"
        binding.interstitialTypeRadioButton.contentDescription = "AddPlacementView.RadioButtons.Interstitial"
        binding.videoTypeRadioButton.contentDescription = "AddPlacementView.RadioButtons.Video"

        binding.addPlacementButton.contentDescription = "AddPlacementView.Buttons.AddPlacement"
        binding.closeButton.contentDescription = "AddPlacementView.Buttons.Close"

        binding.addPlacementButton.setOnClickListener {
            val placementName = binding.placementNameInput.text
            val placementId = binding.placementIdInput.text
            val lineItemId = binding.lineItemIdIdInput.text
            val creativeId = binding.creativeIdIdInput.text

            val placementItem = PlacementItem()

            if (placementName.isNotBlank()) {
                placementItem.name = placementName.toString()
            }

            if (placementId.isNotBlank()) {
                placementItem.placementId = placementId.toString().toInt()
            }

            if (lineItemId.isNotBlank()) {
                placementItem.lineItemId = lineItemId.toString().toInt()
            }

            if (creativeId.isNotBlank()) {
                placementItem.creativeId = creativeId.toString().toInt()
            }

            when (binding.placementTypeRadioGroup.checkedRadioButtonId) {
                R.id.bannerTypeRadioButton -> placementItem.type = FeatureType.BANNER
                R.id.interstitialTypeRadioButton -> placementItem.type = FeatureType.INTERSTITIAL
                R.id.videoTypeRadioButton -> placementItem.type = FeatureType.VIDEO
            }

            onSubmitListener?.invoke(placementItem)
            dismiss()
        }

        binding.placementNameInput.doAfterTextChanged {
            isPlacementNameFieldPopulated = binding.placementNameInput.text.isNotBlank()
            checkRequiredFields()
        }

        binding.placementIdInput.doAfterTextChanged {
            isPlacementIdFieldPopulated = binding.placementIdInput.text.isNotBlank()
            checkRequiredFields()
        }

        binding.placementTypeRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            isPlacementTypeSelected = checkedId != -1
            checkRequiredFields()
        }

        binding.closeButton.setOnClickListener {
            dismiss()
        }
    }

    private fun checkRequiredFields() {
        binding.addPlacementButton.isEnabled = isPlacementNameFieldPopulated
            && isPlacementIdFieldPopulated
            && isPlacementTypeSelected
    }
}
