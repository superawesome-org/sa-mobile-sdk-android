import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import tv.superawesome.demoapp.R
import tv.superawesome.demoapp.databinding.PlacementRowBinding
import tv.superawesome.demoapp.model.FeatureType
import tv.superawesome.demoapp.model.PlacementItem

internal class CustomRecyclerViewAdapter: RecyclerView.Adapter<CustomRecyclerViewAdapter.View>() {

    var onPlacementRowClick: ((PlacementItem) -> Unit)? = null
    private var data: List<PlacementItem> = emptyList()
    inner class View(val rowPlacementBinding: PlacementRowBinding)
        : RecyclerView.ViewHolder(rowPlacementBinding.root)

    fun updateData(newData: List<PlacementItem>) {
        data = newData
    }

    fun reloadList() {
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): View {
        return View(PlacementRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        )
    }

    override fun onBindViewHolder(holder: View, position: Int) {
        val item = data[position]
        val title = holder.rowPlacementBinding.labelTextView
        val icon = holder.rowPlacementBinding.typeIcon

        val titleText = if (item.lineItemId != null && item.creativeId != null)
            "${item.placementId} - ${item.lineItemId} - ${item.creativeId}  | ${item.name}" else
            "${item.placementId} | ${item.name}"

        title.text = titleText
        title.setBackgroundColor(Color.WHITE)

        icon.setImageResource(typeToResource(item.type))
        holder.itemView.contentDescription = titleText

        holder.itemView.setOnClickListener {
            onPlacementRowClick?.invoke(item)
        }
    }

    override fun getItemCount(): Int = data.size

    private fun typeToResource(type: FeatureType): Int = when (type) {
        FeatureType.BANNER -> R.drawable.ic_banner
        FeatureType.INTERSTITIAL -> R.drawable.ic_interstitial
        FeatureType.VIDEO -> R.drawable.ic_video
    }
}