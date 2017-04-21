package se315.journal;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class GuardianInfoActivity extends AppCompatActivity
{
    ListView listView;
    ListAdapter2 listAdapter;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guardian_info);

        final Guardian guardian = (Guardian) getIntent().getSerializableExtra("guardian");
        String title = guardian.getSurName().substring(0, 1) + "." + guardian.getName();
        getSupportActionBar().setTitle(title);

        dbHelper = new DBHelper(this);
        listView = (ListView) findViewById(R.id.gi_listview);
        final ArrayList<String> guardianDetails = dbHelper.getGuardianDetails(guardian);
        listAdapter = new ListAdapter2(this, R.layout.details2, guardianDetails);
        listView.setAdapter(listAdapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long id)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(GuardianInfoActivity.this);
                LayoutInflater inflater = GuardianInfoActivity.this.getLayoutInflater();
                // Inflate and set the layout for the dialog
                View convertView = inflater.inflate(R.layout.custom_dialog, null);
                final EditText etTitle = (EditText) convertView.findViewById(R.id.stu_detail1);
                final EditText etDetail = (EditText) convertView.findViewById(R.id.stu_detail2);
                builder.setView(convertView);

                builder.setTitle(R.string.gi_dialog_title);
                builder.setMessage(R.string.gi_dialog_message);

                final String s = guardianDetails.get(position);
                String title = s.substring(0, s.indexOf("\t"));
                String detail = s.substring(s.indexOf("\t") + 1, s.length());
                etTitle.setText(title);
                etTitle.setEnabled(false);
                etDetail.setText(detail);

                // Add the buttons
                builder.setPositiveButton(R.string.gi_ok_btn, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        String name = etTitle.getText().toString();
                        String newValue = etDetail.getText().toString();
                        guardianDetails.set(position, name + "\t" + newValue);
                        listAdapter.notifyDataSetChanged();
                        Toast.makeText(getApplicationContext(), "Сонгосон мэдээлэл өөрчлөгдлөө", Toast.LENGTH_SHORT).show();

                        String filter = guardian.getRegister();
                        ContentValues args = new ContentValues();

                        switch(position)
                        {
                            case 0:
                                args.put("phone_number", newValue);
                                break;
                            case 1:
                                args.put("email", newValue);
                                break;
                            case 2:
                                args.put("register", newValue);
                                break;
                            case 3:
                                args.put("relation", newValue);
                                break;
                        }
                        dbHelper.updateGuardian(filter, args);

                    }
                });
                builder.setNegativeButton(R.string.gi_cancel_btn, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        // User cancelled the dialog
                    }
                });
                // Create the AlertDialog
                AlertDialog dialog = builder.create();
                dialog.show();
                return false;
            }
        });
    }
}
