package se315.journal;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
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
    Master2Detail2 listAdapter;
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

        listAdapter = new Master2Detail2(this, studentNames, studentListHashMap);
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

        masterDetail.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long id)
            {
                int itemType = ExpandableListView.getPackedPositionType(id);

                if(itemType == ExpandableListView.PACKED_POSITION_TYPE_GROUP)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(StudentListActivity.this);
                    builder.setTitle(R.string.stu_dialog_title1);
                    builder.setMessage(R.string.stu_message1);
                    // Add the buttons
                    builder.setPositiveButton(R.string.stu_ok, new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int id)
                        {
                            String fullName = studentNames.remove(position);
                            String surName = fullName.substring(0, fullName.indexOf("\t"));
                            String name = fullName.substring(fullName.indexOf("\t") + 1, fullName.length());
                            studentListHashMap.remove(fullName);
                            listAdapter.notifyDataSetChanged();
                            Toast.makeText(getApplicationContext(), "Сонгосон сурагч бүртгэлээс хасагдлаа", Toast.LENGTH_SHORT).show();
                            Student student = dbHelper.getStudent(name, surName);
                            dbHelper.removeStudent(student);
                        }
                    });
                    builder.setNegativeButton(R.string.stu_cancel, new DialogInterface.OnClickListener()
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
                else if (itemType == ExpandableListView.PACKED_POSITION_TYPE_CHILD)
                {
                    final int childPosition = ExpandableListView.getPackedPositionChild(id);
                    final int groupPosition = ExpandableListView.getPackedPositionGroup(id);

                    AlertDialog.Builder builder = new AlertDialog.Builder(StudentListActivity.this);
                    LayoutInflater inflater = StudentListActivity.this.getLayoutInflater();
                    // Inflate and set the layout for the dialog
                    View convertView = inflater.inflate(R.layout.custom_dialog, null);
                    final EditText etDetail1 = (EditText) convertView.findViewById(R.id.stu_detail1);
                    final EditText etDetail2 = (EditText) convertView.findViewById(R.id.stu_detail2);
                    builder.setView(convertView);

                    builder.setTitle(R.string.stu_dialog_title2);
                    builder.setMessage(R.string.stu_message2);

                    final String key = studentNames.get(groupPosition);
                    String value = studentListHashMap.get(key).get(childPosition);
                    String detail1 = value.substring(0, value.indexOf("\t"));
                    String detail2 = value.substring(value.indexOf("\t") + 1, value.length());
                    etDetail1.setText(detail1);
                    etDetail1.setEnabled(false);
                    etDetail2.setText(detail2);
                    // Add the buttons
                    builder.setPositiveButton(R.string.stu_ok, new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int id)
                        {
                            String detail1 = etDetail1.getText().toString();
                            String detail2 = etDetail2.getText().toString();
                            String value = detail1 + "\t" + detail2;
                            studentListHashMap.get(key).set(childPosition, value);
                            listAdapter.notifyDataSetChanged();
                            Toast.makeText(getApplicationContext(), "Сонгосон мэдээлэл өөрчлөгдлөө", Toast.LENGTH_SHORT).show();
                            //dbHelper.removeItem(itemName);
                        }
                    });
                    builder.setNegativeButton(R.string.stu_cancel, new DialogInterface.OnClickListener()
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


    public static int ordinalIndexOf(String str, String substr, int n)
    {
        int pos = str.indexOf(substr);
        while (--n > 0 && pos != -1)
            pos = str.indexOf(substr, pos + 1);
        return pos;
    }
}



