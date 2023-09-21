import android.graphics.Color
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import tv.superawesome.demoapp.R
import tv.superawesome.demoapp.databinding.PlacementRowBinding
import tv.superawesome.demoapp.model.FeatureType
import tv.superawesome.demoapp.model.PlacementItem

internal class CustomRecyclerViewAdapter: RecyclerView.Adapter<CustomRecyclerViewAdapter.View>() {

    private var data: ArrayList<PlacementItem> = ArrayList()
    private var onItemClickListener: ((PlacementItem) -> Unit)? = null

    inner class View(val rowPlacementBinding: PlacementRowBinding)
        : RecyclerView.ViewHolder(rowPlacementBinding.root)

    fun setOnItemClickListener(listener: ((PlacementItem) -> Unit)?) {
        onItemClickListener = listener
    }

    fun updateData(newData: ArrayList<PlacementItem>) {
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
        val userCreatedIcon = holder.rowPlacementBinding.userCreatedIcon

        val titleText = if (item.lineItemId != null && item.creativeId != null)
            "${item.placementId} - ${item.lineItemId} - ${item.creativeId}  | ${item.name}" else
            "${item.placementId} | ${item.name}"

        title.text = titleText
        title.setBackgroundColor(Color.WHITE)

        icon.setImageResource(typeToResource(item.type))

        if (item.isUserCreated) { userCreatedIcon.visibility = VISIBLE } else {
            userCreatedIcon.visibility = GONE
        }

        holder.itemView.contentDescription = titleText

        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(item)
        }
    }

    override fun getItemCount(): Int = data.size

    private fun typeToResource(type: FeatureType): Int = when (type) {
        FeatureType.BANNER -> R.drawable.ic_banner
        FeatureType.INTERSTITIAL -> R.drawable.ic_interstitial
        FeatureType.VIDEO -> R.drawable.ic_video
    }
}