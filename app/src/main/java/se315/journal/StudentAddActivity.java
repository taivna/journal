package se315.journal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class StudentAddActivity extends AppCompatActivity
{
    EditText register, name, surName, phoneNumber, eMail, address, enrolledYear;
    RadioGroup gender;
    String radioButtonText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_add);
        getSupportActionBar().setTitle(R.string.stadd_title);

        name = (EditText) findViewById(R.id.stadd_et_fn);
        surName = (EditText) findViewById(R.id.stadd_et_ln);
        phoneNumber = (EditText) findViewById(R.id.stadd_et_phone);
        eMail = (EditText) findViewById(R.id.stadd_et_email);
        address = (EditText) findViewById(R.id.stadd_et_address);
        register = (EditText) findViewById(R.id.stadd_et_register);
        enrolledYear = (EditText) findViewById(R.id.stadd_et_enrolldate);
        gender = (RadioGroup) findViewById(R.id.stadd_radioGroup);
    }

    public void switchToAddGuardian(View view)
    {
        Student student = new Student();

        student.setName(name.getText().toString());
        student.setSurName(surName.getText().toString());
        student.setPhoneNumber(phoneNumber.getText().toString());
        student.setEMail(eMail.getText().toString());
        student.setAddress(address.getText().toString());
        student.setRegister(register.getText().toString());
        student.setEnrolledYear(Integer.parseInt(enrolledYear.getText().toString()));

        if (gender.getCheckedRadioButtonId() == -1)
        {
            Toast.makeText(this, "Please select gender", Toast.LENGTH_SHORT).show();
        }
        else
        {
            // get selected radio button from radioGroup
            int selectedId = gender.getCheckedRadioButtonId();
            // find the radiobutton by returned id
            RadioButton selectedRadioButton = (RadioButton)findViewById(selectedId);
            radioButtonText = selectedRadioButton.getText().toString();
            student.setGender(radioButtonText);
        }

        Intent intent = new Intent(this, GuardianAddActivity.class);
        intent.putExtra("Student", student);
        startActivity(intent);
    }

    public void switchToStudent(View view)
    {
        Intent intent = new Intent(this, StudentListActivity.class);
        startActivity(intent);
    }
}
