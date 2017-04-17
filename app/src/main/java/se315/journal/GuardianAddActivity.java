package se315.journal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class GuardianAddActivity extends AppCompatActivity
{
    DBHelper dbHelper;
    EditText register, name, surName, phoneNumber, eMail, relation, studentRegister;
    Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guardian_add);
        getSupportActionBar().setTitle(R.string.gua_title);

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

        dbHelper.addGuardian(guardian);
        dbHelper.addStudent(student);
        Toast.makeText(getApplicationContext(), "Шинэ сурагч бүртгэлд нэмэгдлээ",
                Toast.LENGTH_SHORT).show();
        finish();
    }
}
