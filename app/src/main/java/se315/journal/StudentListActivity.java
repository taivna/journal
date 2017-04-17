package se315.journal;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StudentListActivity extends AppCompatActivity
{
    ArrayList<Student> studentList;
    ArrayList<String> studentNames = new ArrayList<>();
    ExpandableListView masterDetail;
    master2detail2 listAdapter;
    DBHelper dbHelper;
    Student studentToRemove;
    HashMap<String, List<String>> studentListHashMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        getSupportActionBar().setTitle(R.string.stu_title);

        masterDetail = (ExpandableListView) findViewById(R.id.stu_exp_lv);

        dbHelper = new DBHelper(this);
        studentList = dbHelper.getAllStudents();

        for(Student student: studentList)
        {
            String studentFullName = student.getSurName() + "\t" + student.getName();
            studentNames.add(studentFullName);
            ArrayList<String> studentDetails = dbHelper.getStudentDetails(student);
            studentListHashMap.put(studentFullName, studentDetails);
        }

        listAdapter = new master2detail2(this, studentNames, studentListHashMap);
        masterDetail.setAdapter(listAdapter);

        // Listview Group click listener
        masterDetail.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener()
        {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id)
            {
                String fullName = studentNames.get(groupPosition);
                String surName = fullName.substring(0, fullName.indexOf("\t"));
                String name = fullName.substring(fullName.indexOf("\t") + 1, fullName.length());

                studentToRemove = dbHelper.getStudent(name, surName);
                return false;
            }
        });

        masterDetail.setOnChildClickListener(new ExpandableListView.OnChildClickListener()
        {
            @Override
            public boolean onChildClick(ExpandableListView parent, View view, int groupPosition, int childPosition, long l)
            {
                //Toast.makeText(getApplicationContext(), studentListHashMap.get(studentNames.get(groupPosition)).get(childPosition),
                        //Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    public void switchToAddStudent(View view)
    {
        Intent intent = new Intent(this, StudentAddActivity.class);
        startActivity(intent);
    }

    public void switchToMain(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    // Remove checked students
    public void removeSelectedStudent(View view)
    {
        studentList.remove(studentToRemove);
        dbHelper.removeStudent(studentToRemove);
        listAdapter.notifyDataSetChanged();
        Toast.makeText(getApplicationContext(), "Сонгосон сурагч бүртгэлээс хасагдлаа",
                Toast.LENGTH_SHORT).show();
    }
}



