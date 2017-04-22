package se315.journal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class AttendanceActivity extends AppCompatActivity
{
    Button saveBtn;
    int term,day, index;
    TextView tvDate;
    DBHelper dbHelper;
    RadioGroup radioGroup;
    RadioButton radioButton;
    ArrayList<String> terms, dates;
    Master2Detail3 listAdapter;
    ExpandableListView listView;
    ArrayList<Student> students;
    String s, subjectName, surName, name, state;
    ArrayList<Attendance> attendanceOld, attendanceNew;
    HashMap<String, List<String>> attendanceHashMap = new HashMap();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        getSupportActionBar().setTitle(R.string.att_title);

        saveBtn = (Button) findViewById(R.id.att_btn_save);
        tvDate = (TextView) findViewById(R.id.att_tv_date);
        radioGroup = (RadioGroup) findViewById(R.id.att_radiogroup);
        radioButton = new RadioButton(this);

        listView = (ExpandableListView) findViewById(R.id.att_exp_lv);
        dbHelper = new DBHelper(this);

        String formattedDate = getCurrentDate();
        tvDate.setText(formattedDate);

        Calendar calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_WEEK);
        terms = dbHelper.getTerms(day);
        dates = dbHelper.getDates();
        index = dates.size();
        Collections.sort(dates);

        loadAttendance(formattedDate);

        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener()
        {
            @Override
            public boolean onChildClick(ExpandableListView parent, View view, final int groupPosition, final int childPosition, long l)
            {
                String string = terms.get(groupPosition);
                term = Integer.valueOf(string.substring(0, 1));
                subjectName = string.substring(string.indexOf("\t") + 1, string.length());

                s = attendanceHashMap.get(terms.get(groupPosition)).get(childPosition);
                surName = s.substring(0, s.indexOf("\t"));
                name = s.substring(s.indexOf("\t") + 1, ordinalIndexOf(s, "\t", 2));
                state = s.substring(ordinalIndexOf(s, "\t", 2) + 1, s.length());
                //radioGroup.clearCheck();

                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
                {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i)
                    {
                        if(radioGroup.getCheckedRadioButtonId() != -1)
                        {
                            radioButton = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());

                            char c = radioButton.getText().toString().charAt(0);

                            switch (c)
                            {
                                case 'И':
                                    state = "Ирсэн";
                                    break;
                                case 'Т':
                                    state = "Тасалсан";
                                    break;
                                case 'Х':
                                    state = "Хоцорсон";
                                    break;
                                case 'Ө':
                                    state = "Өвчтэй";
                                    break;
                            }

                            s = s.substring(0, ordinalIndexOf(s, "\t", 2)) + "\t" + state;
                            attendanceHashMap.get(terms.get(groupPosition)).set(childPosition, s);
                            listAdapter.notifyDataSetChanged();

                            for(Attendance attendance: attendanceNew)
                            {
                                if(attendance.getSurName().equals(surName))
                                    if(attendance.getName().equals(name))
                                        if(attendance.getTerm() == term)
                                            attendance.setState(state);
                            }
                        }
                    }
                });
                return false;
            }
        });
    }

    public void switchToMain(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void saveAttendance(View view)
    {
        boolean updated = true;

        for(Attendance attendance: attendanceNew)
        {
            if(dbHelper.attendanceExists(attendance))
            {
                dbHelper.deleteAttendance(attendance);
                dbHelper.addAttendance(attendance);
            }
            else
            {
                dbHelper.addAttendance(attendance);
                updated = false;
            }
        }
        if(updated)
            Toast.makeText(this, "Ирц шинэчлэгдлээ", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Ирц нэмэгдлээ", Toast.LENGTH_SHORT).show();
    }

    public static int ordinalIndexOf(String str, String substr, int n)
    {
        int pos = str.indexOf(substr);
        while (--n > 0 && pos != -1)
            pos = str.indexOf(substr, pos + 1);
        return pos;
    }

    public void loadAttendance(String formattedDate)
    {
        attendanceOld = dbHelper.getTodaysAttendance(formattedDate);
        attendanceNew = new ArrayList<>();

        //loading attendance
        if(attendanceOld == null)
        {
            students = dbHelper.getAllStudents();

            for(String term: terms)
            {
                ArrayList<String> studentNames = new ArrayList<>();

                for(Student student: students)
                {
                    String studentFullName = student.getSurName() + "\t" + student.getName() + "\t" + "Ирсэн";
                    studentNames.add(studentFullName);

                    Attendance attendance = new Attendance();
                    attendance.setDate(formattedDate);
                    attendance.setSurName(student.getSurName());
                    attendance.setName(student.getName());
                    attendance.setSubject(term.substring(term.indexOf("\t") + 1, term.length()));
                    attendance.setTerm(Integer.valueOf(term.substring(0, 1)));
                    attendance.setState("Ирсэн");
                    attendanceNew.add(attendance);
                }
                attendanceHashMap.put(term, studentNames);
            }
        }
        else
        {
            for(String term: terms)
            {
                ArrayList<String> studentNames = new ArrayList<>();

                for(Attendance attendance: attendanceOld)
                {
                    if(attendance.getTerm() == Integer.valueOf(term.substring(0, 1)))
                    {
                        String studentFullName = attendance.getSurName() + "\t" + attendance.getName() + "\t" + attendance.getState();
                        studentNames.add(studentFullName);
                    }
                }
                attendanceHashMap.put(term, studentNames);
            }
            for(Attendance attendance: attendanceOld)
                attendanceNew.add(attendance);
            attendanceOld.clear();
        }
        listAdapter = new Master2Detail3(this, terms, attendanceHashMap);
        listView.setAdapter(listAdapter);
    }

    public void previous(View view)
    {
        if(index > 0)
        {
            index--;
            day--;
            terms = dbHelper.getTerms(day);
            String date = dates.get(index);
            tvDate.setText(date);
            loadAttendance(date);
            saveBtn.setEnabled(false);
            disableRadrioGroup();
        }
    }

    public void next(View view)
    {
        if(index < dates.size() - 1)
        {
            index++;
            day++;
            terms = dbHelper.getTerms(day);
            String date = dates.get(index);
            tvDate.setText(date);
            loadAttendance(date);
        }
        else
        {
            day++;
            terms = dbHelper.getTerms(day);
            String date = getCurrentDate();
            tvDate.setText(date);
            loadAttendance(date);
            saveBtn.setEnabled(true);
            enableRadrioGroup();
        }
    }

    public void disableRadrioGroup()
    {
        for (int i = 0; i < radioGroup.getChildCount(); i++)
        {
            radioGroup.getChildAt(i).setEnabled(false);
        }
    }

    public void enableRadrioGroup()
    {
        for (int i = 0; i < radioGroup.getChildCount(); i++)
        {
            radioGroup.getChildAt(i).setEnabled(true);
        }
    }

    public String getCurrentDate()
    {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = dateFormat.format(calendar.getTime());
        return formattedDate;
    }
}
