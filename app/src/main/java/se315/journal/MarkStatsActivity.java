package se315.journal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class MarkStatsActivity extends AppCompatActivity
{
    ExpandableListView masterDetail;
    Master1Detail2 listAdapter;
    ArrayList<Subject> subjectList;
    ArrayList<String> subjectNames = new ArrayList<>();
    DBHelper dbHelper;
    HashMap<String, List<String>> subjectListHashMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_stats);
        getSupportActionBar().setTitle(R.string.markstats_title);

        masterDetail = (ExpandableListView) findViewById(R.id.markstats_exp_lv);
        dbHelper = new DBHelper(this);
        subjectList = dbHelper.getAllSubjects();

        for(Subject subject: subjectList)
        {
            subjectNames.add(subject.getName());
            ArrayList<Mark> subjectMarks = dbHelper.getSubjectMarks(subject);
            ArrayList<Integer> marks = new ArrayList<>();
            ArrayList<String> stats = new ArrayList<>();

            int lowest, highest, total = 0, number = 0;
            float average;

            if(subjectMarks.size() > 0)
            {
                for(Mark mark: subjectMarks)
                {
                    marks.add(dbHelper.getTotal(mark));
                    number++;
                }

                for(Integer mark: marks)
                {
                    total += mark;
                }

                average = (float) total/number;
                Collections.sort(marks);
                lowest = marks.get(0);
                highest = marks.get(marks.size() - 1);
            }
            else
            {
                average = 0.0F;
                lowest = 0;
                highest = 0;
            }

            DecimalFormat decimalFormat = new DecimalFormat("#.#");

            stats.add("Дундаж оноо: " + "\t" + decimalFormat.format(average));
            stats.add("Доод оноо: " + "\t" + String.valueOf(lowest));
            stats.add("Дээд оноо: " + "\t" + String.valueOf(highest));

            subjectListHashMap.put(subject.getName(), stats);
        }

        listAdapter = new Master1Detail2(this, subjectNames, subjectListHashMap);
        masterDetail.setAdapter(listAdapter);
    }

    public void switchToMarkList(View view)
    {
        Intent intent = new Intent(this, MarkListActivity.class);
        startActivity(intent);
    }
}
