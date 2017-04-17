package se315.journal;

import java.io.Serializable;

public class Student implements Serializable
{
    private String register, name, surName, phoneNumber, eMail, address, gender;
    private  int endrolledYear;

    //empty constructor
    public Student()
    {

    }

    //getter methods
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

    public String getAddress()
    {
        return this.address;
    }

    public String getGender()
    {
        return this.gender;
    }

    public int getEndrolledYear()
    {
        return this.endrolledYear;
    }

    //setter methods
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

    public void setAddress(String address)
    {
        this.address = address;
    }

    public void setEnrolledYear(int endrolledYear)
    {
        this.endrolledYear = endrolledYear;
    }

    public void setGender(String gender)
    {
        this.gender = gender;
    }
}
