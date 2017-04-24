package se315.journal;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class AttendanceActivity extends AppCompatActivity
{
    int term,day, index;
    TextView tvDate;
    DBHelper dbHelper;
    RadioGroup radioGroup;
    RadioButton radioButton;
    ArrayList<String> terms, dates;
    Master2Detail3 listAdapter;
    ExpandableListView listView;
    ArrayList<Student> students;
    Button saveBtn, prevBtn, nextBtn;
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
        prevBtn = (Button) findViewById(R.id.att_btn_prev);
        nextBtn = (Button) findViewById(R.id.att_btn_next);
        tvDate = (TextView) findViewById(R.id.att_tv_date);
        radioGroup = (RadioGroup) findViewById(R.id.att_radiogroup);
        radioButton = new RadioButton(this);

        listView = (ExpandableListView) findViewById(R.id.att_exp_lv);
        dbHelper = new DBHelper(this);

        String formattedDate = getCurrentDate();
        day = getDayOfWeek(formattedDate);
        String dayOfWeek = getDayOfWeek(day);
        tvDate.setText(formattedDate + "\n" + dayOfWeek);
        terms = dbHelper.getTerms(day);
        dates = dbHelper.getDates();

        if(dates.size() > 0)
            index = dates.size();

        if(dates.get(index - 1).equals(formattedDate))
            disableButton(nextBtn);


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
        String currentDate = getCurrentDate();

        if(index > 0)
        {
            index--;
            String olderDate = dates.get(index);

            if(olderDate.equals(currentDate))
            {
                //String oldDate = dates.get(index);
                //int dayOfWeek = getDayOfWeek(oldDate);
                //terms = dbHelper.getTerms(dayOfWeek);
                //String day = getDayOfWeek(dayOfWeek);
                //tvDate.setText(oldDate + "\n" + day);
                //loadAttendance(oldDate);
                enableButton(saveBtn);
                disableButton(nextBtn);
                enableButton(prevBtn);
                enableRadioGroup();
            }
            else
            {
                int dayOfWeek = getDayOfWeek(olderDate);
                terms = dbHelper.getTerms(dayOfWeek);
                String day = getDayOfWeek(dayOfWeek);
                tvDate.setText(olderDate + "\n" + day);
                loadAttendance(olderDate);
                disableButton(saveBtn);
                disableButton(prevBtn);
                enableButton(nextBtn);
                disableRadioGroup();
            }
        }
        else
        {
            String olderDate = dates.get(index);
            int dayOfWeek = getDayOfWeek(olderDate);
            terms = dbHelper.getTerms(dayOfWeek);
            String day = getDayOfWeek(dayOfWeek);
            tvDate.setText(olderDate + "\n" + day);
            loadAttendance(olderDate);
            disableButton(saveBtn);
            disableButton(prevBtn);
            enableButton(nextBtn);
            disableRadioGroup();
        }
    }

    public void next(View view)
    {
        String currentDate = getCurrentDate();

        if(index < dates.size() - 1)
        {
            index++;
            String newerDate = dates.get(index);
            int dayOfWeek = getDayOfWeek(newerDate);
            terms = dbHelper.getTerms(dayOfWeek);
            String day = getDayOfWeek(dayOfWeek);
            tvDate.setText(newerDate + "\n" + day);
            loadAttendance(newerDate);

            if(newerDate.equals(currentDate))
            {
                enableButton(prevBtn);
                disableButton(nextBtn);
                enableButton(saveBtn);
                enableRadioGroup();
            }
        }
        else
        {
            terms = dbHelper.getTerms(day);
            String dayOfWeek = getDayOfWeek(day);
            tvDate.setText(currentDate + "\n" + dayOfWeek);
            loadAttendance(currentDate);
            enableButton(saveBtn);
            enableButton(prevBtn);
            disableButton(nextBtn);
            enableRadioGroup();
        }
    }

    public void disableRadioGroup()
    {
        for (int i = 0; i < radioGroup.getChildCount(); i++)
        {
            radioGroup.getChildAt(i).setEnabled(false);
        }
    }

    public void enableRadioGroup()
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

    public int getThemeColor()
    {
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        int color = typedValue.data;
        return color;
    }

    public int getDayDifferenceCount(String older, String newer)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        Date olderDate = null, newerDate = null;
        try
        {
            olderDate = dateFormat.parse(older);
            newerDate = dateFormat.parse(newer);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        Calendar c_cal = Calendar.getInstance();
        c_cal.setTime(olderDate);
        int c_year = c_cal.get(Calendar.YEAR);
        int c_month = c_cal.get(Calendar.MONTH);
        int c_day = c_cal.get(Calendar.DAY_OF_MONTH);

        Calendar date1 = Calendar.getInstance();
        date1.clear();
        date1.set(c_year, c_month, c_day);

        Calendar e_cal = Calendar.getInstance();
        e_cal.setTime(newerDate);

        int e_year = e_cal.get(Calendar.YEAR);
        int e_month = e_cal.get(Calendar.MONTH);
        int e_day = e_cal.get(Calendar.DAY_OF_MONTH);

        Calendar date2 = Calendar.getInstance();
        date2.clear();
        date2.set(e_year, e_month, e_day);

        long diff = date2.getTimeInMillis() - date1.getTimeInMillis();

        float dayCount = (float) diff / (24 * 60 * 60 * 1000);

        return (int) dayCount;
    }

    public int getDayOfWeek(String date)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date oldDate = null;

        try
        {
            oldDate = dateFormat.parse(date);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(oldDate);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek;
    }

    public String getDayOfWeek(int day)
    {
        String dayOfWeek = null;

        switch(day)
        {
            case Calendar.MONDAY:
                dayOfWeek = "Monday";
                break;
            case Calendar.TUESDAY:
                dayOfWeek = "Tuesday";
                break;
            case Calendar.WEDNESDAY:
                dayOfWeek = "Wednesday";
                break;
            case Calendar.THURSDAY:
                dayOfWeek = "Thursday";
                break;
            case Calendar.FRIDAY:
                dayOfWeek = "Friday";
                break;
            case Calendar.SATURDAY:
                dayOfWeek = "Saturday";
                break;
            case Calendar.SUNDAY:
                dayOfWeek = "Sunday";
                break;
        }
        return dayOfWeek;
    }

    public void enableButton(Button button)
    {
        button.setEnabled(true);
        button.setBackgroundColor(getThemeColor());
    }

    public void disableButton(Button button)
    {
        button.setEnabled(false);
        button.setBackgroundColor(Color.GRAY);
    }
}
