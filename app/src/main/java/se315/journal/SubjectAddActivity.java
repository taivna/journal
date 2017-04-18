package se315.journal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class SubjectAddActivity extends AppCompatActivity
{
    DBHelper dbHelper;
    EditText itemName, subjectName, mark;
    Button saveSubject, saveItem;
    Spinner subjectSpinner, typeSpinner;
    ArrayAdapter<String>  subjectAdapter;
    ArrayList<String> stringArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_add);
        getSupportActionBar().setTitle(R.string.addsbj_title);

        subjectName = (EditText) findViewById(R.id.addsbj_et_subj_name);
        itemName = (EditText) findViewById(R.id.addsbj_et_item_name);
        mark = (EditText) findViewById(R.id.addsbj_et_mark);
        saveSubject = (Button) findViewById(R.id.addsbj_btn_save_subj);
        saveItem = (Button) findViewById(R.id.addsbj_btn_save_item);
        subjectSpinner = (Spinner) findViewById(R.id.addsbj_sp_subject);
        typeSpinner = (Spinner) findViewById(R.id.addsbj_sp_type);

        //setting up the typeSpinner
        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(this,
                R.array.item_type_array, R.layout.my_spinner_item);
        // Specify the layout to use when the list of choices appears
        typeAdapter.setDropDownViewResource(R.layout.my_spinner_dropdown_item);
        // Apply the adapter to the spinner
        typeSpinner.setAdapter(typeAdapter);

        dbHelper = new DBHelper(this);

        //setting up the subjectSpinner
        ArrayList<Subject> subjectArray = dbHelper.getAllSubjects();

        for(Subject subject: subjectArray)
        {
            stringArray.add(subject.getName());
        }

        subjectAdapter = new ArrayAdapter<>(this, R.layout.my_spinner_item,
                stringArray);

        subjectAdapter.setDropDownViewResource(R.layout.my_spinner_dropdown_item);
        subjectSpinner.setAdapter(subjectAdapter);
    }

    public void switchToSubjectList(View view)
    {
        Intent intent = new Intent(this, SubjectListActivity.class);
        startActivity(intent);
    }

    public void saveSubject(View view)
    {
        Subject subject = new Subject();
        subject.setName(subjectName.getText().toString());
        dbHelper.addSubject(subject);
        stringArray.add(subject.getName());
        subjectName.setText("");
        subjectAdapter.notifyDataSetChanged();
        Toast.makeText(getApplicationContext(), "Шинэ хичээл бүртгэлд нэмэгдээ", Toast.LENGTH_SHORT).show();
    }

    public void saveItem(View view)
    {
        Item item = new Item();

        item.setTypeName(typeSpinner.getSelectedItem().toString());
        item.setSubjectName(subjectSpinner.getSelectedItem().toString());
        item.setName(itemName.getText().toString());
        item.setMark(Integer.parseInt(mark.getText().toString()));
        dbHelper.addItem(item);
        itemName.setText("");
        Toast.makeText(getApplicationContext(), "Шинэ " + item.getTypeName() + " бүртгэлд нэмэгдлээ", Toast.LENGTH_SHORT).show();
    }
}
