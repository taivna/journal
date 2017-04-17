package se315.journal;

public class Item
{
    int id, mark;
    String name, subjectName, typeName;

    public Item()
    {

    }

    public int getId()
    {
        return this.id;
    }

    public String getSubjectName()
    {
        return this.subjectName;
    }

    public String getTypeName()
    {
        return this.typeName;
    }

    public String getName()
    {
        return this.name;
    }

    public int getMark()
    {
        return this.mark;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public void setSubjectName(String subjectName)
    {
        this.subjectName = subjectName;
    }

    public void setTypeName(String typeName)
    {
        this.typeName = typeName;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setMark(int mark)
    {
        this.mark = mark;
    }
}
