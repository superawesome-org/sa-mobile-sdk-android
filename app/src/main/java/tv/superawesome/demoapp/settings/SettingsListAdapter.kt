package tv.superawesome.demoapp.settings

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import tv.superawesome.demoapp.MyApplication
import tv.superawesome.demoapp.R
import tv.superawesome.demoapp.SDKEnvironment
import tv.superawesome.demoapp.SDKFlavor

class SettingsListAdapter :
    RecyclerView.Adapter<SettingsListAdapter.ViewHolder>() {

    private var dataSet: List<SettingsItem<Any>> = emptyList()
    var onItemSelected: ((item: SettingsItem<Any>, value: Any) -> Unit)? = null
    var onEnvironmentSelected: ((value: SDKEnvironment) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(dataSet: List<SettingsItem<Any>>) {
        this.dataSet = dataSet
        notifyDataSetChanged()
    }

    class ViewHolder(
        view: View,
        val onItemSelected: (item: SettingsItem<Any>, option: Any) -> Unit,
        val onEnvironmentSelected: ((item: SettingsItem<SDKEnvironment>, option: SDKEnvironment) -> Unit),
    ) : RecyclerView.ViewHolder(view) {
        private val labelTextView: TextView
        private val buttonOne: MaterialButton
        private val buttonTwo: MaterialButton
        private val buttonThree: MaterialButton
        private val buttonFour: MaterialButton

        init {
            labelTextView = view.findViewById(R.id.labelTextView)
            buttonOne = view.findViewById(R.id.buttonOne)
            buttonTwo = view.findViewById(R.id.buttonTwo)
            buttonThree = view.findViewById(R.id.buttonThree)
            buttonFour = view.findViewById(R.id.buttonFour)
        }

        fun update(item: SettingsItem<Any>) {
            val options = item.item.options()
            labelTextView.text = item.item.label

            updateButton(buttonOne, item, options[0]) {
                onItemSelected(item, options[0].value)
            }
            updateButton(buttonTwo, item, options[1]) {
                onItemSelected(item, options[1].value)
            }

            if (options.size >= 3) {
                updateButton(buttonThree, item, options[2]) {
                    onItemSelected(item, options[2].value)
                }
                buttonThree.isVisible = true
            } else {
                buttonThree.isVisible = false
            }

            if (options.size >= 4) {
                updateButton(buttonFour, item, options[3]) {
                    onItemSelected(item, options[3].value)
                }
                buttonFour.isVisible = true
            } else {
                buttonFour.isVisible = false
            }
        }

        fun updateEnvironmentToggle(item: SettingsItem<SDKEnvironment>) {
            val options = item.item.options()
            labelTextView.text = item.item.label
            updateButton(buttonOne, item as SettingsItem<Any>, options[0]) {
                onEnvironmentSelected(
                    item,
                    options[0].value as SDKEnvironment,
                )
            }

            updateButton(buttonTwo, item as SettingsItem<Any>, options[1]) {
                onEnvironmentSelected(
                    item,
                    options[1].value as SDKEnvironment,
                )
            }
        }

        private fun updateButton(
            button: MaterialButton,
            item: SettingsItem<Any>,
            option: SettingsItemOption<Any>,
            onClick: () -> Unit
        ) {
            button.text = option.label
            button.contentDescription = option.contentDescription
            button.isChecked = item.selected == option.value
            button.setOnClickListener {
                onClick()
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.settings_list_item, viewGroup, false)

        return ViewHolder(view,
            onItemSelected = { item, option ->
                onItemSelected?.invoke(item, option)
            },
            onEnvironmentSelected = { _, option ->
                onEnvironmentSelected?.invoke(option)
            },
        )
    }

    override fun getItemCount(): Int = dataSet.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataSet[position]
        holder.update(item)

        if (MyApplication.flavor == SDKFlavor.COMMON) {
            if (position == 0) {
                holder.updateEnvironmentToggle(item as SettingsItem<SDKEnvironment>)
            } else {
                holder.update(item)
            }
        } else {
            holder.update(item)
        }
    }
}
