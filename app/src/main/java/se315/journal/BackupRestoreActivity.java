package se315.journal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BackupRestoreActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backup_restore);

        Button backupBtn = new Button(this);

        backupBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                MyBackupAgent myBackupAgent = new MyBackupAgent();
                myBackupAgent.onCreate();
            }
        });
    }
}
