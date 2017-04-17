package se315.journal;

import java.util.Date;

public class Attendance
{
    int term;
    String date, name, surName, subject, state;;

    public Attendance()
    {

    }

    public int getTerm()
    {
        return term;
    }

    public String getName()
    {
        return name;
    }

    public String getSurName()
    {
        return surName;
    }

    public String getState()
    {
        return state;
    }

    public String getDate()
    {
        return date;
    }

    public String getSubject()
    {
        return subject;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setSurName(String surName)
    {
        this.surName = surName;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public void setSubject(String subject)
    {
        this.subject = subject;
    }

    public void setTerm(int term)
    {
        this.term = term;
    }
}
