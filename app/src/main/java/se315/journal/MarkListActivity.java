package se315.journal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MarkListActivity extends AppCompatActivity
{
    DBHelper dbHelper;
    ArrayList<Mark> marks;
    Master2Detail2 masterDetail;
    ArrayList<Subject> subjects;
    ArrayList<Student> students;
    ExpandableListView expListView;
    HashMap<String, List<String>> markListHashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_list);
        getSupportActionBar().setTitle(R.string.mark_title);

        dbHelper = new DBHelper(this);
        marks = dbHelper.getMarks();
        expListView = (ExpandableListView)findViewById(R.id.marklist_explv);
        ArrayList<String> studentNames = new ArrayList<>();
        markListHashMap = new HashMap<>();

        if(marks == null)
        {

        }
        else
        {
            students = dbHelper.getAllStudents();
            subjects = dbHelper.getAllSubjects();

            for(Student student: students)
            {
                ArrayList<String> subjectNames = new ArrayList<>();
                String studentFullName = student.getSurName() + "\t" + student.getName();
                studentNames.add(studentFullName);

                for(Subject subject: subjects)
                {
                    String subjectName = subject.getName() + "\t0";
                    subjectNames.add(subjectName);
                }
                markListHashMap.put(studentFullName, subjectNames);
            }
            masterDetail = new Master2Detail2(this, studentNames, markListHashMap);
            expListView.setAdapter(masterDetail);
        }


    }

    public void switchToMain(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
