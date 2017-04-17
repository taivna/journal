package se315.journal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void switchToAttendance(View view)
    {
        Intent intent = new Intent(this, AttendanceActivity.class);
        startActivity(intent);
    }

    public void switchToStudents(View view)
    {
        Intent intent = new Intent(this, StudentListActivity.class);
        startActivity(intent);
    }

    public void switchToSubjects(View view)
    {
        Intent intent = new Intent(this, SubjectListActivity.class);
        startActivity(intent);
    }

    public void switchToMarks(View view)
    {
        Intent intent = new Intent(this, MarkActivity.class);
        startActivity(intent);
    }
}
