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
import tv.superawesome.demoapp.model.PlacementItem

class AddPlacementDialogFragment : DialogFragment() {
    var onSubmitListener: ((PlacementItem) -> Unit)? = null

    private var _binding: FragmentAddPlacementBinding? = null
    private var isPlacementNameFieldValid = false
    private var isPlacementIdFieldValid = false
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

        binding.addAdButton.setOnClickListener {
            val placementName = binding.placementNameInput.text
            val placementId = binding.placementIdInput.text
            val lineItemId = binding.lineItemIdIdInput.text
            val creativeId = binding.creativeIdIdInput.text

            val placementItem = PlacementItem()

            if(placementName.isNotBlank()) {
                placementItem.name = placementName.toString()
            }

            if(placementId.isNotBlank()) {
                placementItem.placementId = placementId.toString().toInt()
            }

            if(lineItemId.isNotBlank()) {
                placementItem.lineItemId = lineItemId.toString().toInt()
            }

            if(creativeId.isNotBlank()) {
                placementItem.creativeId = creativeId.toString().toInt()
            }

            onSubmitListener?.invoke(placementItem)
            dismiss()
        }

        binding.placementNameInput.doAfterTextChanged {
            isPlacementNameFieldValid = binding.placementNameInput.text.isNotBlank()
            checkRequiredFields()
        }

        binding.placementIdInput.doAfterTextChanged {
            isPlacementIdFieldValid = binding.placementIdInput.text.isNotBlank()
            checkRequiredFields()
        }

        binding.closeButton.setOnClickListener {
            dismiss()
        }
    }

    private fun checkRequiredFields() {
        binding.addAdButton.isEnabled = isPlacementNameFieldValid && isPlacementIdFieldValid
    }
}
