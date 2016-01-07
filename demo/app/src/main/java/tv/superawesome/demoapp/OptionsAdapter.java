package tv.superawesome.demoapp;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import tv.superawesome.models.AdItem;

/**
 * Created by gabriel.coman on 07/01/16.
 */
public class OptionsAdapter extends ArrayAdapter<AdItem> {

    /** constructors */
    public OptionsAdapter(Context context, int resource) {
        super(context, resource);
    }

    public OptionsAdapter(Context context, int resource, List<AdItem> adItems) {
        super(context, resource, adItems);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        if (rowView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            rowView = inflater.inflate(R.layout.listview_cell, parent, false);
        }

        AdItem item = getItem(position);

        TextView textView = (TextView) rowView.findViewById(R.id.cell_title);
        textView.setText(item.title);

        return rowView;
    }
}
