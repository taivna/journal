package se315.journal;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class GuardianAddActivity extends AppCompatActivity
{
    Button saveBtn;
    Student student;
    DBHelper dbHelper;
    EditText register, name, surName, phoneNumber, eMail, relation, studentRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guardian_add);
        getSupportActionBar().setTitle(R.string.gua_title);

        saveBtn = (Button) findViewById(R.id.gua_btn_save);
        name = (EditText) findViewById(R.id.gua_et_fn);
        surName = (EditText) findViewById(R.id.gua_et_ln);
        phoneNumber = (EditText) findViewById(R.id.gua_et_phone);
        eMail = (EditText) findViewById(R.id.gua_et_email);
        relation = (EditText) findViewById(R.id.gua_et_relation);
        register = (EditText) findViewById(R.id.gua_et_register);
        studentRegister = (EditText) findViewById(R.id.gua_et_student_register);

        student = (Student) getIntent().getSerializableExtra("Student");
        name.setText(student.getSurName());
        studentRegister.setText(student.getRegister());

        dbHelper = new DBHelper(this);

        saveBtn.setEnabled(false);
        saveBtn.setBackgroundColor(Color.GRAY);

        name.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                enableSaveBtn();
            }
        });

        surName.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                enableSaveBtn();
            }
        });

        phoneNumber.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                enableSaveBtn();
            }
        });

        eMail.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                enableSaveBtn();
            }
        });

        relation.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                enableSaveBtn();
            }
        });

        register.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                enableSaveBtn();
            }
        });

        studentRegister.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                enableSaveBtn();
            }
        });
    }

    public void switchToAddStudent(View view)
    {
        Intent intent = new Intent(this, StudentAddActivity.class);
        startActivity(intent);
    }

    public void insertData(View view)
    {
        Guardian guardian = new Guardian();

        guardian.setName(name.getText().toString());
        guardian.setSurName(surName.getText().toString());
        guardian.setPhoneNumber(phoneNumber.getText().toString());
        guardian.setEMail(eMail.getText().toString());
        guardian.setRelation(relation.getText().toString());
        guardian.setRegister(register.getText().toString());
        guardian.setStudentRegister(studentRegister.getText().toString());

        if(!dbHelper.studentExists(student))
        {
            dbHelper.addGuardian(guardian);
            dbHelper.addStudent(student);
            Toast.makeText(getApplicationContext(), "Шинэ сурагч бүртгэлд нэмэгдлээ", Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    public int getThemeColor()
    {
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        int color = typedValue.data;
        return color;
    }

    public void enableSaveBtn()
    {
        boolean enabled = true;

        if(name.getText().toString().length() == 0)
        {
            enabled = false;
        }

        if(surName.getText().toString().length() == 0)
        {
            enabled = false;
        }

        if(phoneNumber.getText().toString().length() == 0)
        {
            enabled = false;
        }

        if(eMail.getText().toString().length() == 0)
        {
            enabled = false;
        }

        if(relation.getText().toString().length() == 0)
        {
            enabled = false;
        }

        if(register.getText().toString().length() == 0)
        {
            enabled = false;
        }

        if(studentRegister.getText().toString().length() == 0)
        {
            enabled = false;
        }

        if(enabled)
        {
            saveBtn.setEnabled(true);
            saveBtn.setBackgroundColor(getThemeColor());
        }
        else
        {
            saveBtn.setEnabled(false);
            saveBtn.setBackgroundColor(Color.GRAY);
        }

    }
}
