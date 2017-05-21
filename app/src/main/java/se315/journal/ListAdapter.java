package se315.journal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ListAdapter extends ArrayAdapter
{
    public ListAdapter(Context context, int resource, List<Item> items)
    {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            LayoutInflater inflater;
            inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.details3, null);
        }

        Item item = (Item) getItem(position);

        if(item != null)
        {
            TextView name = (TextView) convertView.findViewById(R.id.list_detail1);
            TextView type = (TextView) convertView.findViewById(R.id.list_detail2);
            TextView mark = (TextView) convertView.findViewById(R.id.list_detail3);

            if(name != null)
            {
                name.setText(item.getName());
            }

            if(name != null)
            {
                type.setText(item.getTypeName());
            }

            if(mark != null)
            {
                mark.setText(String.valueOf(item.getMark()));
            }
        }
        return convertView;
    }
}

