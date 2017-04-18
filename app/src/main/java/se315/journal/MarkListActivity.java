package se315.journal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

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
        final ArrayList<String> studentNames = new ArrayList<>();
        markListHashMap = new HashMap<>();
        students = dbHelper.getAllStudents();
        subjects = dbHelper.getAllSubjects();

        if(marks.size() > 0)
        {
            ArrayList<String> subjectNames = new ArrayList<>();

            for(Mark mark: marks)
            {
                Student student = dbHelper.getStudent(mark.getStudentRegister());
                String fullName = student.getSurName() + "\t" + student.getName();
                studentNames.add(fullName);

                Subject subject = dbHelper.getSubject(mark.getSubjectId());
                String subjectName = subject.getName();
                subjectNames.add(subjectName);
            }

            for(String student: studentNames)
            {
                for(String subject: subjectNames)
                {
                    ArrayList<Item> items = dbHelper.getSubjectItems(subject);
                    int total = 0;
                    int index = 0;

                    for(Item item: items)
                        total += item.getMark();

                    subjectNames.set(index, subject + "\t" + total);
                    index++;
                }
                markListHashMap.put(student, subjectNames);
            }
        }
        else
        {
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
        }
        masterDetail = new Master2Detail2(this, studentNames, markListHashMap);
        expListView.setAdapter(masterDetail);

        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener()
        {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPos, int childPos, long id)
            {
                String fullName = studentNames.get(groupPos);
                String surName = fullName.substring(0, fullName.indexOf("\t"));
                String name = fullName.substring(fullName.indexOf("\t") + 1, fullName.length());
                String childText = markListHashMap.get(fullName).get(childPos);
                String subjectName = childText.substring(0, childText.indexOf("\t"));

                Student student = dbHelper.getStudent(name, surName);
                Subject subject = dbHelper.getSubject(subjectName);

                Mark mark = new Mark();
                mark.setStudentRegister(student.getRegister());
                mark.setSubjectId(subject.getId());

                Intent intent = new Intent(getApplicationContext(), MarkActivity.class);
                intent.putExtra("mark", mark);
                startActivity(intent);

                return false;
            }
        });
    }

    public void switchToMain(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
