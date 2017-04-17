package se315.journal;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class master2detail3 extends BaseExpandableListAdapter
{
    TextView listChild1, listChild2, listChild3, listHeader1, listHeader2;
    private Context context;
    private List<String> listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> listDataChild;

    public master2detail3(Context context, List<String> listDataHeader, HashMap<String, List<String>> listChildData)
    {
        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listDataChild = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition)
    {
        return this.listDataChild.get(this.listDataHeader.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition)
    {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
    {

        final String s = (String) getChild(groupPosition, childPosition);

        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.details3, null);
        }

        listChild1 = (TextView) convertView.findViewById(R.id.list_detail1);
        listChild1.setText(s.substring(0, s.indexOf("\t")));

        listChild2 = (TextView) convertView.findViewById(R.id.list_detail2);
        listChild2.setText(s.substring(s.indexOf("\t") + 1, ordinalIndexOf(s, "\t", 2)));

        listChild3 = (TextView) convertView.findViewById(R.id.list_detail3);
        listChild3.setText(s.substring(ordinalIndexOf(s, "\t", 2) + 1, s.length()));

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition)
    {
        return this.listDataChild.get(this.listDataHeader.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition)
    {
        return this.listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount()
    {
        return this.listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition)
    {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
    {
        String s = (String) getGroup(groupPosition);

        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.master2, null);
        }

        listHeader1 = (TextView) convertView.findViewById(R.id.list_master1);
        listHeader1.setTypeface(null, Typeface.BOLD);
        listHeader1.setText(s.substring(0, s.indexOf("\t")));

        listHeader2 = (TextView) convertView.findViewById(R.id.list_master2);
        listHeader2.setTypeface(null, Typeface.BOLD);
        listHeader2.setText(s.substring(s.indexOf("\t") + 1, s.length()));

        return convertView;
    }

    @Override
    public boolean hasStableIds()
    {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition)
    {
        return true;
    }

    public static int ordinalIndexOf(String str, String substr, int n)
    {
        int pos = str.indexOf(substr);
        while (--n > 0 && pos != -1)
            pos = str.indexOf(substr, pos + 1);
        return pos;
    }
}
