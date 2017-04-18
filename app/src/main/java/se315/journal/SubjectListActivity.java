package se315.journal;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SubjectListActivity extends AppCompatActivity
{
    ExpandableListView masterDetail;
    Master1Detail3 listAdapter;
    ArrayList<Subject> subjectList;
    ArrayList<String> subjectNames = new ArrayList<>();
    DBHelper dbHelper;
    HashMap<String, List<String>> subjectListHashMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_list);
        getSupportActionBar().setTitle(R.string.sbjlist_title);

        masterDetail = (ExpandableListView) findViewById(R.id.sbjlist_explv);
        dbHelper = new DBHelper(this);
        subjectList = dbHelper.getAllSubjects();

        for(Subject subject: subjectList)
        {
            subjectNames.add(subject.getName());
            ArrayList<Item> subjectItems = dbHelper.getSubjectItems(subject);
            ArrayList<String> items = new ArrayList<>();

            for(Item item: subjectItems)
            {
                items.add(item.getTypeName() + "\t" + item.getName() + "\t" + item.getMark());
            }
            subjectListHashMap.put(subject.getName(), items);
        }

        listAdapter = new Master1Detail3(this, subjectNames, subjectListHashMap);
        masterDetail.setAdapter(listAdapter);

        masterDetail.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long id)
            {
                int itemType = ExpandableListView.getPackedPositionType(id);

                if( itemType == ExpandableListView.PACKED_POSITION_TYPE_GROUP)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SubjectListActivity.this);
                    builder.setTitle(R.string.sbjlist_dialog_title1);
                    builder.setMessage(R.string.sbjlist_message);
                    // Add the buttons
                    builder.setPositiveButton(R.string.sbjlist_ok, new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int id)
                        {
                            String subjectName = subjectNames.remove(position);
                            subjectListHashMap.remove(subjectName);
                            listAdapter.notifyDataSetChanged();
                            Toast.makeText(getApplicationContext(), "Сонгосон хичээл бүртгэлээс хасагдлаа", Toast.LENGTH_SHORT).show();
                            dbHelper.removeSubject(subjectName);
                        }
                    });
                    builder.setNegativeButton(R.string.sbjlist_cancel, new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int id)
                        {
                            // User cancelled the dialog
                        }
                    });
                    // Create the AlertDialog
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else if(itemType == ExpandableListView.PACKED_POSITION_TYPE_CHILD)
                {
                    final int childPosition = ExpandableListView.getPackedPositionChild(id);
                    final int groupPosition = ExpandableListView.getPackedPositionGroup(id);

                    AlertDialog.Builder builder = new AlertDialog.Builder(SubjectListActivity.this);
                    builder.setTitle(R.string.sbjlist_dialog_title);
                    builder.setMessage(R.string.sbjlist_message);
                    // Add the buttons
                    builder.setPositiveButton(R.string.sbjlist_ok, new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int id)
                        {
                            String key = subjectNames.get(groupPosition);
                            String item = subjectListHashMap.get(key).remove(childPosition);
                            String itemName = item.substring(item.indexOf("\t") + 1, ordinalIndexOf(item, "\t", 2));
                            listAdapter.notifyDataSetChanged();
                            Toast.makeText(getApplicationContext(), "Сонгосон даалгавар/шалгалт бүртгэлээс хасагдлаа", Toast.LENGTH_SHORT).show();
                            dbHelper.removeItem(itemName);
                        }
                    });
                    builder.setNegativeButton(R.string.sbjlist_cancel, new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int id)
                        {
                            // User cancelled the dialog
                        }
                    });
                    // Create the AlertDialog
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                return false;
            }
        });
    }

    public void switchToAddSubject(View view)
    {
        Intent intent = new Intent(this, SubjectAddActivity.class);
        startActivity(intent);
    }

    public void switchToMain(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void switchToSchedule(View view)
    {
        Intent intent = new Intent(this, ScheduleActivity.class);
        startActivity(intent);
    }

    public static int ordinalIndexOf(String str, String substr, int n)
    {
        int pos = str.indexOf(substr);
        while (--n > 0 && pos != -1)
            pos = str.indexOf(substr, pos + 1);
        return pos;
    }
}
