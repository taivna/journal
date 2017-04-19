package se315.journal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ScheduleActivity extends AppCompatActivity
{
    String subjectName;
    EditText[][] editTexts;
    DBHelper dbHelper;
    Spinner subjectSpinner;
    Schedule schedule;
    GridLayout gridLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        getSupportActionBar().setTitle(R.string.sch_title);

        gridLayout = (GridLayout) findViewById(R.id.sch_gridLayout);
        editTexts = new EditText[6][5];
        prepareEditTexts();
        dbHelper = new DBHelper(this);
        displaySchedule();
        schedule = new Schedule();
        schedule.scheduleArray = new String[6][5];

        subjectSpinner = (Spinner) findViewById(R.id.sch_spinner);
        ArrayList<Subject> subjectArray = dbHelper.getAllSubjects();
        ArrayList<String> stringArray = new ArrayList<>();

        for(Subject subject: subjectArray)
        {
            stringArray.add(subject.getName());
        }

        ArrayAdapter<String> subjectAdapter = new ArrayAdapter<>(this, R.layout.my_spinner_item,
                stringArray);

        subjectAdapter.setDropDownViewResource(R.layout.my_spinner_dropdown_item);
        subjectSpinner.setAdapter(subjectAdapter);
        subjectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                subjectName = subjectSpinner.getSelectedItem().toString();
                EditText focusedView = (EditText) gridLayout.getFocusedChild();
                focusedView.setText(subjectName.substring(0, 3));
                int position = gridLayout.indexOfChild(focusedView);
                int rowPosition = position/5;
                int columnPosition = position%5;

                schedule.scheduleArray[rowPosition][columnPosition] = subjectName;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
    }

    public void saveSchedule(View view)
    {
        //if the schedule table is not empty, delete the content and then add new contents
        if(!dbHelper.isTableEmpty("schedule"))
        {
            updateSchedule();
            Toast.makeText(getApplicationContext(), "Хичээлийн хуваарь шинэчлэгдлээ.", Toast.LENGTH_SHORT).show();
        }
        else //if the schedule table is empty, add the new contents
        {
            addSchedule();
            Toast.makeText(getApplicationContext(), "Хичээлийн хуваарь хадгалагдлаа.", Toast.LENGTH_SHORT).show();
        }
    }

    public void switchToSubjectList(View view)
    {
        Intent intent = new Intent(this, SubjectListActivity.class);
        startActivity(intent);
    }

    public void displaySchedule()
    {
        if(!dbHelper.isTableEmpty("schedule"))
        {
            Schedule sch = dbHelper.getSchedule();

            for(int i = 0; i < 6; i++)
            {
                for(int j = 0; j < 5; j++)
                {
                    editTexts[i][j].setText(sch.scheduleArray[i][j].substring(0, 3));
                    editTexts[i][j].setInputType(InputType.TYPE_NULL);
                }
            }
        }
    }

    public void addSchedule()
    {
        //adding schedule table rows
        dbHelper.addSchedule(schedule.scheduleArray[0]);
        dbHelper.addSchedule(schedule.scheduleArray[1]);
        dbHelper.addSchedule(schedule.scheduleArray[2]);
        dbHelper.addSchedule(schedule.scheduleArray[3]);
        dbHelper.addSchedule(schedule.scheduleArray[4]);
        dbHelper.addSchedule(schedule.scheduleArray[5]);
    }

    public void updateSchedule()
    {
        dbHelper.clearSchedule();
        //adding schedule table rows
        addSchedule();
    }

    public void prepareEditTexts()
    {
        editTexts[0][0] = (EditText) findViewById(R.id.sch_et_11);
        editTexts[0][1] = (EditText) findViewById(R.id.sch_et_12);
        editTexts[0][2] = (EditText) findViewById(R.id.sch_et_13);
        editTexts[0][3] = (EditText) findViewById(R.id.sch_et_14);
        editTexts[0][4] = (EditText) findViewById(R.id.sch_et_15);

        editTexts[1][0] = (EditText) findViewById(R.id.sch_et_21);
        editTexts[1][1] = (EditText) findViewById(R.id.sch_et_22);
        editTexts[1][2] = (EditText) findViewById(R.id.sch_et_23);
        editTexts[1][3] = (EditText) findViewById(R.id.sch_et_24);
        editTexts[1][4] = (EditText) findViewById(R.id.sch_et_25);

        editTexts[2][0] = (EditText) findViewById(R.id.sch_et_31);
        editTexts[2][1] = (EditText) findViewById(R.id.sch_et_32);
        editTexts[2][2] = (EditText) findViewById(R.id.sch_et_33);
        editTexts[2][3] = (EditText) findViewById(R.id.sch_et_34);
        editTexts[2][4] = (EditText) findViewById(R.id.sch_et_35);

        editTexts[3][0] = (EditText) findViewById(R.id.sch_et_41);
        editTexts[3][1] = (EditText) findViewById(R.id.sch_et_42);
        editTexts[3][2] = (EditText) findViewById(R.id.sch_et_43);
        editTexts[3][3] = (EditText) findViewById(R.id.sch_et_44);
        editTexts[3][4] = (EditText) findViewById(R.id.sch_et_45);

        editTexts[4][0] = (EditText) findViewById(R.id.sch_et_51);
        editTexts[4][1] = (EditText) findViewById(R.id.sch_et_52);
        editTexts[4][2] = (EditText) findViewById(R.id.sch_et_53);
        editTexts[4][3] = (EditText) findViewById(R.id.sch_et_54);
        editTexts[4][4] = (EditText) findViewById(R.id.sch_et_55);

        editTexts[5][0] = (EditText) findViewById(R.id.sch_et_61);
        editTexts[5][1] = (EditText) findViewById(R.id.sch_et_62);
        editTexts[5][2] = (EditText) findViewById(R.id.sch_et_63);
        editTexts[5][3] = (EditText) findViewById(R.id.sch_et_64);
        editTexts[5][4] = (EditText) findViewById(R.id.sch_et_65);
    }
}
