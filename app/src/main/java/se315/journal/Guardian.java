package se315.journal;

import android.provider.BaseColumns;
import android.provider.ContactsContract;

public class Guardian
{
    String register, name, surName, phoneNumber, eMail, relation, studentRegister;

    // empty constructor
    public Guardian()
    {

    }

    // getter methods

    public String getRegister()
    {
        return this.register;
    }

    public String getName()
    {
        return this.name;
    }

    public String getSurName()
    {
        return this.surName;
    }

    public String getPhoneNumber()
    {
        return this.phoneNumber;
    }

    public String getEMail()
    {
        return this.eMail;
    }

    public String getRelation()
    {
        return this.relation;
    }

    public String getStudentRegister() { return this.studentRegister;}

    // setter methods

    public void setRegister(String register)
    {
        this.register = register;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setSurName(String surName)
    {
        this.surName = surName;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    public void setEMail(String eMail)
    {
        this.eMail = eMail;
    }

    public void setRelation(String relation)
    {
        this.relation = relation;
    }

    public void setStudentRegister(String studentRegister) { this.studentRegister = studentRegister;}
}
