package se315.journal;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MarkActivity extends AppCompatActivity
{
    DBHelper dbHelper;
    ListView listView;
    ListAdapter listAdapter;
    ArrayList<Item> items, markItems;
    Student student;
    Subject subject;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark);

        dbHelper = new DBHelper(this);
        listView = (ListView) findViewById(R.id.mark_lv);
        Mark mark = (Mark) getIntent().getSerializableExtra("mark");

        student = dbHelper.getStudent(mark.getStudentRegister());
        subject = dbHelper.getSubject(mark.getSubjectId());
        String title = student.getSurName().substring(0, 1) + "." + student.getName() + ":\t" + subject.getName();
        getSupportActionBar().setTitle(title);

        items = dbHelper.getSubjectItems(subject);
        markItems = dbHelper.getMarkItems(mark);

        for(Item item: items)
        {
            item.setMark(0);

            for(Item markItem: markItems)
            {
                String itemName = dbHelper.getItem(markItem.getId()).getName();
                if(item.getName().equals(itemName))
                    item.setMark(markItem.getMark());
            }
        }

        listAdapter = new ListAdapter(this, R.layout.details2, items);
        listView.setAdapter(listAdapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long id)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(MarkActivity.this);
                LayoutInflater inflater = MarkActivity.this.getLayoutInflater();
                // Inflate and set the layout for the dialog
                View convertView = inflater.inflate(R.layout.custom_dialog, null);
                final EditText etName = (EditText) convertView.findViewById(R.id.stu_detail1);
                final EditText etMark = (EditText) convertView.findViewById(R.id.stu_detail2);
                builder.setView(convertView);

                builder.setTitle(R.string.mark_dialog_title);
                builder.setMessage(R.string.mark_dialog_message);

                final Item item = items.get(position);
                String name = item.getName();
                etName.setText(name);
                etName.setEnabled(false);
                etMark.setHint(R.string.mark_tv_mark);
                // Add the buttons
                builder.setPositiveButton(R.string.stu_ok, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        int mark = Integer.valueOf(etMark.getText().toString());
                        ArrayList<Item> itemList = dbHelper.getSubjectItems(subject);
                        int maxMark = 0;

                        for(Item i: itemList)
                        {
                            if(i.getName().equals(item.getName()))
                                maxMark = i.getMark();
                        }

                        if(mark > maxMark)
                        {
                            Toast.makeText(MarkActivity.this, "Энэ шалгалт/даалгаварын дээд оноо: " + maxMark,
                                    Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            items.get(position).setMark(mark);
                            listAdapter.notifyDataSetChanged();
                        }
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
                return false;
            }
        });
    }

    public void switchToMarkList(View view)
    {
        Intent intent = new Intent(this, MarkListActivity.class);
        startActivity(intent);
    }

    public void saveMarks(View view)
    {
        ArrayList<Mark> marks = new ArrayList<>();

        for(Item item: items)
        {
            Mark mark = new Mark();
            mark.setStudentRegister(student.getRegister());
            mark.setSubjectId(subject.getId());
            mark.setItemId(item.getId());
            mark.setMark(item.getMark());
            marks.add(mark);
        }

        boolean updated = true;

        for(Mark mark: marks)
        {
            if(!dbHelper.markExists(mark))
            {
                dbHelper.addMark(mark);
                updated = false;
            }
            else
            {
                dbHelper.deleteMark(mark);
                dbHelper.addMark(mark);
            }
        }
        if(updated)
            Toast.makeText(this, "Оноо шинэчлэгдлээ", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Оноо нэмэгдлээ", Toast.LENGTH_SHORT).show();
    }
}
