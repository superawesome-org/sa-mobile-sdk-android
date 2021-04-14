package tv.superawesome.demoapp.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import tv.superawesome.demoapp.R

enum class Type {
    BANNER, INTERSTITIAL, VIDEO
}

open class AdapterItem
class HeaderItem(var title: String) : AdapterItem()
class PlacementItem(var name: String, var pid: Int, var type: Type) : AdapterItem()


internal class CustomListAdapter<T : AdapterItem?>(context: Context?) : ArrayAdapter<T?>(context!!, 0) {
    private var data: List<T> = emptyList()
    fun updateData(newData: List<T>) {
        data = newData
    }

    fun reloadList() {
        notifyDataSetChanged()
    }

    override fun getItem(i: Int): T? = data[i]

    override fun getViewTypeCount(): Int = 2

    override fun getItemViewType(i: Int): Int = if (getItem(i) is HeaderItem) 0 else 1

    override fun getCount(): Int = data.size

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.row_placement, parent, false)
        }
        val title = view!!.findViewById<TextView>(R.id.RowTitle)
        when (val item = getItem(position)) {
            is HeaderItem -> {
                title.text = item.title
                view.setBackgroundColor(Color.LTGRAY)
            }
            is PlacementItem -> {
                val titleText = item.pid.toString() + " | " + item.name
                title.text = titleText
                title.setBackgroundColor(Color.WHITE)
            }
        }
        return view
    }
}