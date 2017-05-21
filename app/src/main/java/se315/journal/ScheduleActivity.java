package se315.journal;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ListView;
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
    ArrayList<String> stringArray;

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
        schedule = dbHelper.getSchedule();

        ArrayList<Subject> subjectArray = dbHelper.getAllSubjects();
        stringArray = new ArrayList<>();

        for(Subject subject: subjectArray)
        {
            stringArray.add(subject.getName());
        }
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

    View.OnClickListener clickListener = new View.OnClickListener()
    {
        EditText clickedEt;

        @Override
        public void onClick(View view)
        {
            clickedEt = (EditText) view;
            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(ScheduleActivity.this);
            LayoutInflater inflater = getLayoutInflater();
            View convertView = inflater.inflate(R.layout.list_dialog, null);
            alertDialog.setView(convertView);
            alertDialog.setTitle("Хичээлүүд");

            alertDialog.setNegativeButton("Хаах", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    dialog.dismiss();
                }
            });

            final AlertDialog dialog = alertDialog.show();

            ListView lv = (ListView) convertView.findViewById(R.id.lv);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.my_spinner_dropdown_item,
                    stringArray);
            lv.setAdapter(adapter);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
                {
                    subjectName = stringArray.get(i);

                    clickedEt.setText(subjectName.substring(0, 3));
                    int position = gridLayout.indexOfChild(clickedEt);
                    int rowPosition = position/5;
                    int columnPosition = position%5;
                    Toast.makeText(getApplicationContext(),
                            (columnPosition + 1) + " дахь өдрийн " + (rowPosition + 1) + "-р цаг " + subjectName + " болж өөрчлөгдлөө",
                            Toast.LENGTH_SHORT).show();
                    schedule.scheduleArray[rowPosition][columnPosition] = subjectName;
                    dialog.dismiss();
                }
            });
        }
    };

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
                    editTexts[i][j].setFocusable(false);
                    editTexts[i][j].setOnClickListener(clickListener);
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
