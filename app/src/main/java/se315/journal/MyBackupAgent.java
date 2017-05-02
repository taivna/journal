package se315.journal;

import android.app.backup.BackupAgentHelper;
import android.app.backup.BackupDataInput;
import android.app.backup.BackupDataOutput;
import android.app.backup.FileBackupHelper;
import android.os.ParcelFileDescriptor;

import java.io.File;
import java.io.IOException;

public class MyBackupAgent extends BackupAgentHelper
{
    private static final String DB_NAME = "My";

    @Override
    public void onCreate()
    {
        FileBackupHelper dbs = new FileBackupHelper(this, DB_NAME);
        addHelper("dbs", dbs);
    }

    @Override
    public File getFilesDir()
    {
        File path = getDatabasePath(DB_NAME);
        return path.getParentFile();
    }
}
