package se315.journal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter2 extends ArrayAdapter
{
    public ListAdapter2(Context context, int resource)
    {
        super(context, resource);
    }

    public ListAdapter2(Context context, int resource, ArrayList<String> details)
    {
        super(context, resource, details);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            LayoutInflater inflater;
            inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.details2, null);
        }

        String detail = (String) getItem(position);

        if(detail != null)
        {
            TextView title = (TextView) convertView.findViewById(R.id.list_detail1);
            TextView value = (TextView) convertView.findViewById(R.id.list_detail2);

            if(title != null)
            {
                title.setText(detail.substring(0, detail.indexOf("\t")));
            }

            if(detail != null)
            {
                value.setText(detail.substring(detail.indexOf("\t") + 1, detail.length()));
            }
        }
        return convertView;
    }
}

