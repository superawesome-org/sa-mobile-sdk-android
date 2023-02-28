package tv.superawesome.demoapp.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import tv.superawesome.demoapp.R
import tv.superawesome.demoapp.model.FeatureType
import tv.superawesome.demoapp.model.PlacementItem

internal class CustomListAdapter(context: Context?) :
    ArrayAdapter<PlacementItem>(context!!, 0) {

    private var data: List<PlacementItem> = emptyList()
    fun updateData(newData: List<PlacementItem>) {
        data = newData
    }

    fun reloadList() {
        notifyDataSetChanged()
    }

    override fun getItem(i: Int): PlacementItem = data[i]

    override fun getViewTypeCount(): Int = 1

    override fun getItemViewType(i: Int): Int = 0

    override fun getCount(): Int = data.size

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.row_placement, parent, false)
        }
        val title = view!!.findViewById<TextView>(R.id.labelTextView)
        val icon = view.findViewById<ImageView>(R.id.typeIcon)

        val item = getItem(position)
        val titleText = if (item.lineItemId != null && item.creativeId != null)
            "${item.placementId} - ${item.lineItemId} - ${item.creativeId}  | ${item.name}" else
            "${item.placementId} | ${item.name}"
        title.text = titleText
        title.setBackgroundColor(Color.WHITE)

        icon.setImageResource(typeToResource(item.type))
        view.contentDescription = titleText

        return view
    }

    private fun typeToResource(type: FeatureType): Int = when (type) {
        FeatureType.BANNER -> R.drawable.ic_banner
        FeatureType.INTERSTITIAL -> R.drawable.ic_interstitial
        FeatureType.VIDEO -> R.drawable.ic_video
    }
}