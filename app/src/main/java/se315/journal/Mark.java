package se315.journal;

public class Mark
{
    String studentRegister;
    int subjectId, itemId, mark;

    public Mark(){}

    public String getStudentRegister()
    {
        return studentRegister;
    }

    public int getSubjectId()
    {
        return subjectId;
    }

    public int getItemId()
    {
        return itemId;
    }

    public int getMark()
    {
        return mark;
    }

    public void setStudentRegister(String studentRegister)
    {
        this.studentRegister = studentRegister;
    }

    public void setSubjectId(int subjectId)
    {
        this.subjectId = subjectId;
    }

    public void setItemId(int itemId)
    {
        this.itemId = itemId;
    }

    public void setMark(int mark)
    {
        this.mark = mark;
    }
}
