package se315.journal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SubjectListActivity extends AppCompatActivity
{
    ExpandableListView masterDetail;
    master1detail3 listAdapter;
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

        listAdapter = new master1detail3(this, subjectNames, subjectListHashMap);
        masterDetail.setAdapter(listAdapter);
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
}
