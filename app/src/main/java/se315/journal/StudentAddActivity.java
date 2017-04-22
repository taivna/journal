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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.w3c.dom.Text;

public class StudentAddActivity extends AppCompatActivity
{
    Button continueBtn;
    RadioGroup gender;
    String radioButtonText = "";
    EditText register, name, surName, phoneNumber, eMail, address, enrolledYear;

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

        continueBtn = (Button) findViewById(R.id.stadd_btn_cont);
        continueBtn.setEnabled(false);
        continueBtn.setBackgroundColor(Color.GRAY);

        //setting up textchangedlistener for every edittext
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
                enableContinueBtn();
            }
        });

        surName.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                enableContinueBtn();
            }
        });

        phoneNumber.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                enableContinueBtn();
            }
        });

        eMail.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                enableContinueBtn();
            }
        });

        address.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                enableContinueBtn();
            }
        });

        register.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                enableContinueBtn();
            }
        });

        enrolledYear.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                enableContinueBtn();
            }
        });
    }

    public void enableContinueBtn()
    {
        boolean enabled = true;

        if(name.getText().toString().length() == 0)
            enabled = false;


        if(surName.getText().toString().length() == 0)
            enabled = false;


        if(phoneNumber.getText().toString().length() == 0)
            enabled = false;


        if(eMail.getText().toString().length() == 0)
            enabled = false;


        if(address.getText().toString().length() == 0)
            enabled = false;


        if(register.getText().toString().length() == 0)
            enabled = false;


        if(enrolledYear.getText().toString().length() == 0)
            enabled = false;

        if(enabled)
        {
            continueBtn.setEnabled(true);
            continueBtn.setBackgroundColor(getThemeColor());
        }
        else
        {
            continueBtn.setEnabled(false);
            continueBtn.setBackgroundColor(Color.GRAY);
        }
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

    public void onResume()
    {
        super.onResume();

        register.setText("");
        name.setText("");
        surName.setText("");
        phoneNumber.setText("");
        eMail.setText("");
        address.setText("");
        enrolledYear.setText("");
    }

    public int getThemeColor()
    {
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        int color = typedValue.data;
        return color;
    }
}
