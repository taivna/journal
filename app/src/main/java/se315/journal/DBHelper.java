package se315.journal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

public class DBHelper extends SQLiteOpenHelper
{
    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "My.db";

    private static final String STUDENT_TABLE = "student";
    private static final String GUARDIAN_TABLE = "guardian";
    private static final String SUBJECT_TABLE = "subject";
    private static final String ITEM_TABLE = "item";
    private static final String SCHEDULE_TABLE = "schedule";
    private static final String ATTENDANCE_TABLE = "attendance";
    private static final String MARK_TABLE = "marks";

    private static final String COLUMN_REGISTER="register";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_SURNAME = "surname";
    private static final String COLUMN_PHONE_NUMBER = "phone_number";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_RELATION = "relation";
    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_ENROLLED_YEAR = "enrolled_year";
    private static final String COLUMN_GENDER = "gender";
    private static final String COLUMN_STUDENT_REGISTER = "student_register";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_SUBJECT_NAME = "subject_name";
    private static final String COLUMN_MARK = "mark";
    private static final String COLUMN_TYPE = "type_name";
    private static final String COLUMN_MON = "monday";
    private static final String COLUMN_TUE = "tuesday";
    private static final String COLUMN_WED = "wednesday";
    private static final String COLUMN_THU = "thursday";
    private static final String COLUMN_FRI = "friday";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_STATE = "state";
    private static final String COLUMN_TERM = "term";
    private static final String COLUMN_SUBJECT_ID = "subject_id";
    private static final String COLUMN_ITEM_ID = "item_id";

    ///////////////////////////////////////////////////////////////////////////////////////////////
    private static final String CREATE_STUDENT_TABLE = "CREATE TABLE " + STUDENT_TABLE +

            "(" +   COLUMN_REGISTER + " TEXT PRIMARY KEY NOT NULL," +
                    COLUMN_NAME + " TEXT NOT NULL," +
                    COLUMN_SURNAME + " TEXT NOT NULL," +
                    COLUMN_PHONE_NUMBER + " TEXT NOT NULL," +
                    COLUMN_EMAIL + " TEXT NOT NULL," +
                    COLUMN_ADDRESS + " TEXT NOT NULL," +
                    COLUMN_ENROLLED_YEAR + " DATE NOT NULL," +
                    COLUMN_GENDER + " TEXT NOT NULL" + ")";

    private static final String DELETE_STUDENT_TABLE = "DROP TABLE IF EXISTS " + STUDENT_TABLE;
///////////////////////////////////////////////////////////////////////////////////////////////////
    private static final String CREATE_GUARDIAN_TABLE = "CREATE TABLE " + GUARDIAN_TABLE +

        "(" +   COLUMN_REGISTER + " TEXT PRIMARY KEY NOT NULL," +
                COLUMN_NAME + " TEXT NOT NULL," +
                COLUMN_SURNAME + " TEXT NOT NULL," +
                COLUMN_PHONE_NUMBER + " TEXT NOT NULL," +
                COLUMN_EMAIL + " TEXT NOT NULL," +
                COLUMN_STUDENT_REGISTER + " TEXT NOT NULL," +
                COLUMN_RELATION + " TEXT NOT NULL" + ")";

    private static final String DELETE_GUARDIAN_TABLE = "DROP TABLE IF EXISTS " + GUARDIAN_TABLE;
///////////////////////////////////////////////////////////////////////////////////////////////////
    private static final String CREATE_SUBJECT_TABLE = "CREATE TABLE " + SUBJECT_TABLE +

        "(" +   COLUMN_ID + " INTEGER PRIMARY KEY NOT NULL," +
                COLUMN_NAME + " TEXT NOT NULL" + ")";

    private  static final String DELETE_SUBJECT_TABLE = "DROP TABLE IF EXISTS " + SUBJECT_TABLE;
///////////////////////////////////////////////////////////////////////////////////////////////////
    private static final String CREATE_ITEM_TABLE = "CREATE TABLE " + ITEM_TABLE +

        "(" +   COLUMN_ID + " INTEGER PRIMARY KEY NOT NULL," +
                COLUMN_SUBJECT_NAME + " TEXT NOT NULL," +
                COLUMN_TYPE + " TEXT NOT NULL," +
                COLUMN_NAME + " TEXT NOT NULL," +
                COLUMN_MARK + " INTEGER NOT NULL" + ")";

    private static final String DELETE_ITEM_TABLE = "DROP TABLE IF EXISTS " + ITEM_TABLE;
    ///////////////////////////////////////////////////////////////////////////////////////////////
    private static final String CREATE_SCHEDULE_TABLE = "CREATE TABLE " + SCHEDULE_TABLE +

            "(" +   COLUMN_ID + " INTEGER PRIMARY KEY NOT NULL," +
                    COLUMN_MON + " TEXT NOT NULL," +
                    COLUMN_TUE + " TEXT NOT NULL," +
                    COLUMN_WED + " TEXT NOT NULL," +
                    COLUMN_THU + " TEXT NOT NULL," +
                    COLUMN_FRI + " TEXT NOT NULL" + ")";

    private static final String DELETE_SCHEDULE_TABLE = "DROP TABLE IF EXISTS " + SCHEDULE_TABLE;
    ///////////////////////////////////////////////////////////////////////////////////////////////
    private static final String CREATE_ATTENDANCE_TABLE = "CREATE TABLE " + ATTENDANCE_TABLE +

            "(" +   COLUMN_DATE + " DATE NOT NULL," +
                    COLUMN_SURNAME + " TEXT NOT NULL," +
                    COLUMN_NAME + " TEXT NOT NULL," +
                    COLUMN_TERM + " INTEGER NOT NULL," +
                    COLUMN_SUBJECT_NAME + " TEXT NOT NULL," +
                    COLUMN_STATE + " TEXT NOT NULL," +
                    " PRIMARY KEY " + "(" + COLUMN_DATE + "," + COLUMN_SURNAME + "," + COLUMN_NAME + "," +COLUMN_TERM +
            ")" + ")";

    private static final String DELETE_ATTENDANCE_TABLE = "DROP TABLE IF EXISTS " + ATTENDANCE_TABLE;
    ////////////////////////////////////////////////////////////////////////////////////////////////
    private static final String CREATE_MARK_TABLE = "CREATE TABLE " + MARK_TABLE + "(" +
            COLUMN_STUDENT_REGISTER + " TEXT NOT NULL," +
            COLUMN_SUBJECT_ID + " INTEGER NOT NULL," +
            COLUMN_ITEM_ID + " INTEGER NOT NULL," +
            COLUMN_MARK + " INTEGER NOT NULL," +
            "PRIMARY KEY " + "(" + COLUMN_STUDENT_REGISTER + "," + COLUMN_SUBJECT_ID + "," + COLUMN_ITEM_ID + ")" + ")";

    private static final String DELETE_MARK_TABLE = "DROP TABLE IF EXISTS " + MARK_TABLE;
    ////////////////////////////////////////////////////////////////////////////////////////////////
    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_STUDENT_TABLE);
        db.execSQL(CREATE_GUARDIAN_TABLE);
        db.execSQL(CREATE_SUBJECT_TABLE);
        db.execSQL(CREATE_ITEM_TABLE);
        db.execSQL(CREATE_SCHEDULE_TABLE);
        db.execSQL(CREATE_ATTENDANCE_TABLE);
        db.execSQL(CREATE_MARK_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL(DELETE_STUDENT_TABLE);
        db.execSQL(DELETE_GUARDIAN_TABLE);
        db.execSQL(DELETE_SUBJECT_TABLE);
        db.execSQL(DELETE_ITEM_TABLE);
        db.execSQL(DELETE_SCHEDULE_TABLE);
        db.execSQL(DELETE_ATTENDANCE_TABLE);
        db.execSQL(DELETE_MARK_TABLE);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        onUpgrade(db, oldVersion, newVersion);
    }

    // Add guardian
    public void addGuardian(Guardian guardian)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(COLUMN_REGISTER, guardian.register);
        values.put(COLUMN_NAME, guardian.name);
        values.put(COLUMN_SURNAME, guardian.surName);
        values.put(COLUMN_PHONE_NUMBER, guardian.phoneNumber);
        values.put(COLUMN_EMAIL, guardian.eMail);
        values.put(COLUMN_STUDENT_REGISTER, guardian.studentRegister);
        values.put(COLUMN_RELATION, guardian.relation);

        db.insert(GUARDIAN_TABLE, null, values);
        db.close();
    }

    //Add student
    public void addStudent(Student student)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(COLUMN_REGISTER, student.getRegister());
        values.put(COLUMN_NAME, student.getName());
        values.put(COLUMN_SURNAME, student.getSurName());
        values.put(COLUMN_PHONE_NUMBER, student.getPhoneNumber());
        values.put(COLUMN_EMAIL, student.getEMail());
        values.put(COLUMN_ADDRESS, student.getAddress());
        values.put(COLUMN_ENROLLED_YEAR, student.getEndrolledYear());
        values.put(COLUMN_GENDER, student.getGender());

        db.insert(STUDENT_TABLE, null, values);
        db.close();
    }

    // Get the list of all students
    public ArrayList<Student> getAllStudents()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Student> studentList = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + STUDENT_TABLE, null);

        if(cursor .moveToFirst()) //if the query result is not empty
        {
            while(!cursor.isAfterLast()) //while cursor hasn't reached the end of query result
            {
                Student student = new Student();

                student.setRegister(cursor.getString(cursor.getColumnIndex(COLUMN_REGISTER)));
                student.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                student.setSurName(cursor.getString(cursor.getColumnIndex(COLUMN_SURNAME)));
                student.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_PHONE_NUMBER)));
                student.setEMail(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)));
                student.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS)));
                student.setEnrolledYear(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ENROLLED_YEAR))));
                student.setGender(cursor.getString(cursor.getColumnIndex(COLUMN_GENDER)));

                studentList.add(student);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return studentList;
    }

    // Remove a student and his/her guardian
    public void removeStudent(Student student)
    {
        SQLiteDatabase db = this. getWritableDatabase();
        db.delete(STUDENT_TABLE, COLUMN_REGISTER + " =?", new String[] {student.getRegister()});
        db.delete(GUARDIAN_TABLE, COLUMN_STUDENT_REGISTER + " =?", new String[] {student.getRegister()});
        db.delete(ATTENDANCE_TABLE, COLUMN_SURNAME + " =?" + " AND " + COLUMN_NAME + " =?",
                new String[]{student.getSurName(), student.getName()});
        db.delete(MARK_TABLE, COLUMN_STUDENT_REGISTER + " =?", new String[]{student.getRegister()});
        db.close();
    }

    // Add a subject
    public void addSubject(Subject subject)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, subject.getName());

        db.insert(SUBJECT_TABLE, null, values);
        db.close();
    }

    // Add an item
    public void addItem(Item item)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();

        values.put(COLUMN_SUBJECT_NAME, item.getSubjectName());
        values.put(COLUMN_TYPE, item.getTypeName());
        values.put(COLUMN_NAME, item.getName());
        values.put(COLUMN_MARK, item.getMark());

        db.insert(ITEM_TABLE, null, values);
        db.close();
    }

    // Get the list of all subjects
    public ArrayList<Subject> getAllSubjects()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Subject> subjectList = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + SUBJECT_TABLE, null);

        if(cursor .moveToFirst())
        {
            while(!cursor.isAfterLast())
            {
                Subject subject = new Subject();
                subject.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                subject.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                subjectList.add(subject);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return subjectList;
    }

    public Subject getSubject(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + SUBJECT_TABLE  + " WHERE " + COLUMN_ID + " =?",
                new String[] {String.valueOf(id)});

        Subject subject = new Subject();

        if(cursor.moveToFirst())
        {
            subject.setId(id);
            subject.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
        }
        cursor.close();
        db.close();
        return subject;
    }

    public Subject getSubject(String name)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + SUBJECT_TABLE  + " WHERE " + COLUMN_NAME + " =?",
                new String[] {name});

        Subject subject = new Subject();

        if(cursor.moveToFirst())
        {
            subject.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
            subject.setName(name);
        }
        cursor.close();
        db.close();
        return subject;
    }

    public ArrayList<String> getStudentDetails(Student student)
    {
        ArrayList<String> studentDetails = new ArrayList<>();

        studentDetails.add("Регистрийн дугаар:\t" + student.getRegister());
        studentDetails.add("Утасны дугаар:\t" + student.getPhoneNumber());
        studentDetails.add("И-мэйл:\t" + student.getEMail());
        studentDetails.add("Гэрийн хаяг:\t" + student.getAddress());
        studentDetails.add("Элссэн огноо:\t" + String.valueOf(student.getEndrolledYear()));
        studentDetails.add("Хүйс:\t" + student.getGender());

        return studentDetails;
    }

    public Student getStudent(String name, String surName)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Student student = new Student();

        Cursor cursor = db.rawQuery("SELECT * FROM " + STUDENT_TABLE + " WHERE " + COLUMN_NAME + " = ?" + " AND "
                + COLUMN_SURNAME + " = ?", new String[] {name, surName});

        if(cursor.moveToFirst())
        {
            student.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
            student.setSurName(cursor.getString(cursor.getColumnIndex(COLUMN_SURNAME)));
            student.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_PHONE_NUMBER)));
            student.setEMail(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)));
            student.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS)));
            student.setRegister(cursor.getString(cursor.getColumnIndex(COLUMN_REGISTER)));
            student.setEnrolledYear(cursor.getInt(cursor.getColumnIndex(COLUMN_ENROLLED_YEAR)));
            student.setGender(cursor.getString(cursor.getColumnIndex(COLUMN_GENDER)));
        }

        cursor.close();
        db.close();
        return student;
    }

    public Student getStudent(String register)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Student student = new Student();

        Cursor cursor = db.rawQuery("SELECT * FROM " + STUDENT_TABLE + " WHERE " + COLUMN_REGISTER + " = ?",
                new String[] {register});

        if(cursor.moveToFirst())
        {
            student.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
            student.setSurName(cursor.getString(cursor.getColumnIndex(COLUMN_SURNAME)));
            student.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_PHONE_NUMBER)));
            student.setEMail(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)));
            student.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS)));
            student.setRegister(cursor.getString(cursor.getColumnIndex(COLUMN_REGISTER)));
            student.setEnrolledYear(cursor.getInt(cursor.getColumnIndex(COLUMN_ENROLLED_YEAR)));
            student.setGender(cursor.getString(cursor.getColumnIndex(COLUMN_GENDER)));
        }

        cursor.close();
        db.close();
        return student;
    }

    public ArrayList<Item> getSubjectItems(Subject subject)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Item> subjectItems = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + ITEM_TABLE + " WHERE " + COLUMN_SUBJECT_NAME + " =?",
                new String[] {subject.getName()});

        if(cursor .moveToFirst())
        {
            while(!cursor.isAfterLast())
            {
                Item item = new Item();
                item.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                item.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                item.setSubjectName(cursor.getString(cursor.getColumnIndex(COLUMN_SUBJECT_NAME)));
                item.setTypeName(cursor.getString(cursor.getColumnIndex(COLUMN_TYPE)));
                item.setMark(cursor.getInt(cursor.getColumnIndex(COLUMN_MARK)));
                subjectItems.add(item);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return subjectItems;
    }

    public boolean isTableEmpty(String tableName)
    {
        SQLiteDatabase  db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + tableName, null);

        if(cursor != null)
        {
            cursor.moveToFirst();
            int count = cursor.getInt(0);
            // if the table is not empty
            if(count > 0)
            {
                return false;
            }
            cursor.close();
        }
        db.close();
        return true;
    }

    public void addSchedule(String[] array)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();

        values.put(COLUMN_MON, array[0]);
        values.put(COLUMN_TUE, array[1]);
        values.put(COLUMN_WED, array[2]);
        values.put(COLUMN_THU, array[3]);
        values.put(COLUMN_FRI, array[4]);

        db.insert(SCHEDULE_TABLE, null, values);
        db.close();

    }

    public void clearSchedule()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(SCHEDULE_TABLE, null, null);
        db.close();
    }

    public Schedule getSchedule()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Schedule schedule = new Schedule();
        schedule.scheduleArray = new String[6][5];

        Cursor cursor = db.rawQuery("SELECT * FROM " + SCHEDULE_TABLE, null);

        if(cursor .moveToFirst())
        {
            int rowIndex = 0;

            while(!cursor.isAfterLast())
            {
                schedule.scheduleArray[rowIndex][0] = cursor.getString(cursor.getColumnIndex(COLUMN_MON));
                schedule.scheduleArray[rowIndex][1] = cursor.getString(cursor.getColumnIndex(COLUMN_TUE));
                schedule.scheduleArray[rowIndex][2] = cursor.getString(cursor.getColumnIndex(COLUMN_WED));
                schedule.scheduleArray[rowIndex][3] = cursor.getString(cursor.getColumnIndex(COLUMN_THU));
                schedule.scheduleArray[rowIndex][4] = cursor.getString(cursor.getColumnIndex(COLUMN_FRI));
                rowIndex++;
                cursor.moveToNext();
            }
        }

        cursor.close();
        db.close();
        return schedule;
    }

    public ArrayList<String> getTerms(int day)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> terms = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + SCHEDULE_TABLE, null);

        switch (day)
        {
            case Calendar.MONDAY:
                if(cursor.moveToFirst())
                {
                    int i = 1;
                    while(!cursor.isAfterLast())
                    {
                        terms.add(String.valueOf(i) + "-p цаг\t" + cursor.getString(cursor.getColumnIndex(COLUMN_MON)));
                        i++;
                        cursor.moveToNext();
                    }
                }
                break;
            case Calendar.TUESDAY:
                if(cursor.moveToFirst())
                {
                    int i = 1;
                    while(!cursor.isAfterLast())
                    {
                        terms.add(String.valueOf(i) + "-p цаг\t" + cursor.getString(cursor.getColumnIndex(COLUMN_TUE)));
                        i++;
                        cursor.moveToNext();
                    }
                }
                break;
            case Calendar.WEDNESDAY:
                if(cursor.moveToFirst())
                {
                    int i = 1;
                    while(!cursor.isAfterLast())
                    {
                        terms.add(String.valueOf(i) + "-p цаг\t" + cursor.getString(cursor.getColumnIndex(COLUMN_WED)));
                        i++;
                        cursor.moveToNext();
                    }
                }
                break;
            case Calendar.THURSDAY:
                if(cursor.moveToFirst())
                {
                    int i = 1;
                    while(!cursor.isAfterLast())
                    {
                        terms.add(String.valueOf(i) + "-p цаг\t" + cursor.getString(cursor.getColumnIndex(COLUMN_THU)));
                        i++;
                        cursor.moveToNext();
                    }
                }
                break;
            case Calendar.FRIDAY:
                if(cursor.moveToFirst())
                {
                    int i = 1;
                    while(!cursor.isAfterLast())
                    {
                        terms.add(String.valueOf(i) + "-p цаг\t" + cursor.getString(cursor.getColumnIndex(COLUMN_FRI)));
                        i++;
                        cursor.moveToNext();
                    }
                }
                break;
        }
        cursor.close();
        db.close();
        return terms;
    }

    public ArrayList<Attendance> getTodaysAttendance(String date)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Attendance> attendanceList = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + ATTENDANCE_TABLE + " WHERE " + COLUMN_DATE + " =?",
                new String[] {date});

        if(cursor .moveToFirst())
        {
            while(!cursor.isAfterLast())
            {
                Attendance attendance = new Attendance();
                attendance.setDate(cursor.getString(cursor.getColumnIndex(COLUMN_DATE)));
                attendance.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                attendance.setSurName(cursor.getString(cursor.getColumnIndex(COLUMN_SURNAME)));
                attendance.setState(cursor.getString(cursor.getColumnIndex(COLUMN_STATE)));
                attendance.setTerm(cursor.getInt(cursor.getColumnIndex(COLUMN_TERM)));
                attendance.setSubject(cursor.getString(cursor.getColumnIndex(COLUMN_SUBJECT_NAME)));

                attendanceList.add(attendance);
                cursor.moveToNext();
            }
            cursor.close();
            db.close();
            return attendanceList;
        }
        else
        {
            cursor.close();
            db.close();
            return null;
        }
    }

    public void addAttendance(Attendance attendance)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_DATE, attendance.getDate());
        values.put(COLUMN_SURNAME, attendance.getSurName());
        values.put(COLUMN_NAME, attendance.getName());
        values.put(COLUMN_TERM, attendance.getTerm());
        values.put(COLUMN_SUBJECT_NAME, attendance.getSubject());
        values.put(COLUMN_STATE, attendance.getState());

        db.insert(ATTENDANCE_TABLE, null, values);
        db.close();
    }

    public void deleteAttendance(Attendance attendance)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(ATTENDANCE_TABLE, COLUMN_DATE + " =? AND " + COLUMN_SURNAME + " =? AND " + COLUMN_NAME + " =? AND " + COLUMN_TERM + " =?",
                    new String[] {attendance.getDate(), attendance.getSurName(), attendance.getName(), String.valueOf(attendance.getTerm())});
        db.close();
    }

    public boolean attendanceExists(Attendance attendance)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + ATTENDANCE_TABLE + " WHERE " + COLUMN_DATE + " =? AND " + COLUMN_SURNAME + " =? AND "
                        + COLUMN_NAME + " =? AND " + COLUMN_TERM + " =?",
                new String[] {attendance.getDate(), attendance.getSurName(), attendance.getName(), String.valueOf(attendance.getTerm())});

        if(cursor.moveToFirst())
        {
            cursor.close();
            db.close();
            return true;
        }
        else
        {
            cursor.close();
            db.close();
            return false;
        }
    }

    public void removeSubject(String name)
    {
        SQLiteDatabase db = this. getWritableDatabase();
        db.delete(ITEM_TABLE, COLUMN_SUBJECT_NAME + " =?", new String[] {name});
        db.delete(SUBJECT_TABLE, COLUMN_NAME + " =?", new String[] {name});
        db.close();
    }

    public void removeItem(String name)
    {
        Item item = getItem(name);
        removeItemMarks(item);
        SQLiteDatabase db = this. getWritableDatabase();
        db.delete(ITEM_TABLE, COLUMN_NAME + " =?", new String[] {name});
        db.close();
    }

    public ArrayList<Mark> getMarks()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Mark> marks = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + MARK_TABLE, null);

        if(cursor .moveToFirst())
        {
            while(!cursor.isAfterLast())
            {
                Mark mark = new Mark();
                mark.setStudentRegister(cursor.getString(cursor.getColumnIndex(COLUMN_STUDENT_REGISTER)));
                mark.setSubjectId(cursor.getInt(cursor.getColumnIndex(COLUMN_SUBJECT_ID)));
                mark.setItemId(cursor.getInt(cursor.getColumnIndex(COLUMN_ITEM_ID)));
                mark.setMark(cursor.getInt(cursor.getColumnIndex(COLUMN_MARK)));

                marks.add(mark);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return marks;
    }

    public void addMark(Mark mark)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_STUDENT_REGISTER, mark.getStudentRegister());
        values.put(COLUMN_SUBJECT_ID, mark.getSubjectId());
        values.put(COLUMN_ITEM_ID, mark.getItemId());
        values.put(COLUMN_MARK, mark.getMark());

        db.insert(MARK_TABLE, null, values);
        db.close();
    }

    public void deleteMark(Mark mark)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(MARK_TABLE, COLUMN_STUDENT_REGISTER + " =? AND " + COLUMN_SUBJECT_ID + " =? AND " + COLUMN_ITEM_ID + " =?",
                new String[] {mark.getStudentRegister(), String.valueOf(mark.getSubjectId()), String.valueOf(mark.getItemId())});

        db.close();
    }

    // return true if mark exists in db
    public boolean markExists(Mark mark)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + MARK_TABLE + " WHERE " + COLUMN_STUDENT_REGISTER + " =? AND "
                        + COLUMN_SUBJECT_ID + " =? AND "
                        + COLUMN_ITEM_ID + " =?",
                new String[] {mark.getStudentRegister(), String.valueOf(mark.getSubjectId()), String.valueOf(mark.getItemId())});

        if(cursor.moveToFirst())
        {
            cursor.close();
            db.close();
            return true;
        }
        else
        {
            cursor.close();
            db.close();
            return false;
        }
    }

    public int getTotal(Mark mark)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        int total = 0;

        Cursor cursor = db.rawQuery("SELECT * FROM " + MARK_TABLE + " WHERE " + COLUMN_STUDENT_REGISTER + " =? AND "
                + COLUMN_SUBJECT_ID + " =?", new String[] {mark.getStudentRegister(), String.valueOf(mark.getSubjectId())});

        if(cursor .moveToFirst())
        {
            while(!cursor.isAfterLast())
            {
                int score = cursor.getInt(cursor.getColumnIndex(COLUMN_MARK));
                total = total + score;
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return total;
    }

    public ArrayList<Item> getMarkItems(Mark mark)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Item> items = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + MARK_TABLE + " WHERE " + COLUMN_STUDENT_REGISTER + " =? AND "
                + COLUMN_SUBJECT_ID + " =?", new String[] {mark.getStudentRegister(), String.valueOf(mark.getSubjectId())});

        if(cursor .moveToFirst())
        {
            while(!cursor.isAfterLast())
            {
                Item item = new Item();
                item.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ITEM_ID)));
                item.setMark(cursor.getInt(cursor.getColumnIndex(COLUMN_MARK)));
                items.add(item);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return items;
    }

    public ArrayList<Mark> getSubjectMarks(Subject subject)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Mark> marks = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + MARK_TABLE + " WHERE " + COLUMN_SUBJECT_ID + " =?", new String[]
                {String.valueOf(subject.getId())});

        if(cursor .moveToFirst())
        {
            while(!cursor.isAfterLast())
            {
                Mark mark = new Mark();
                mark.setStudentRegister(cursor.getString(cursor.getColumnIndex(COLUMN_STUDENT_REGISTER)));
                mark.setSubjectId(cursor.getInt(cursor.getColumnIndex(COLUMN_SUBJECT_ID)));
                mark.setItemId(cursor.getInt(cursor.getColumnIndex(COLUMN_ITEM_ID)));
                mark.setMark(cursor.getInt(cursor.getColumnIndex(COLUMN_MARK)));
                marks.add(mark);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return marks;
    }

    public Item getItem(int itemId)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Item item = new Item();

        Cursor cursor = db.rawQuery("SELECT * FROM " + ITEM_TABLE + " WHERE " + COLUMN_ID + " =?",
                new String[] {String.valueOf(itemId)});

        if(cursor .moveToFirst())
        {
            item.setId(itemId);
            item.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
            item.setSubjectName(cursor.getString(cursor.getColumnIndex(COLUMN_SUBJECT_NAME)));
            item.setTypeName(cursor.getString(cursor.getColumnIndex(COLUMN_TYPE)));
            item.setMark(cursor.getInt(cursor.getColumnIndex(COLUMN_MARK)));
        }
        cursor.close();
        db.close();
        return item;
    }

    public Item getItem(String itemName)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Item item = new Item();

        Cursor cursor = db.rawQuery("SELECT * FROM " + ITEM_TABLE + " WHERE " + COLUMN_NAME + " =?",
                new String[] {itemName});

        if(cursor .moveToFirst())
        {
            item.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
            item.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
            item.setSubjectName(cursor.getString(cursor.getColumnIndex(COLUMN_SUBJECT_NAME)));
            item.setTypeName(cursor.getString(cursor.getColumnIndex(COLUMN_TYPE)));
            item.setMark(cursor.getInt(cursor.getColumnIndex(COLUMN_MARK)));
        }
        cursor.close();
        db.close();
        return item;
    }

    public void removeItemMarks(Item item)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(MARK_TABLE, COLUMN_ITEM_ID + " =?", new String[] {String.valueOf(item.getId())});
        db.close();
    }

    public boolean studentExists(Student student)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + STUDENT_TABLE + " WHERE " + COLUMN_REGISTER + " =?",
                new String[] {student.getRegister()});

        if(cursor.moveToFirst())
        {
            cursor.close();
            db.close();
            return true;
        }
        else
        {
            cursor.close();
            db.close();
            return false;
        }
    }

    public void updateStudent(String filter, ContentValues args)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(STUDENT_TABLE, args, "register=?", new String[]{filter});
        db.close();
    }

    public Guardian getGuardian(String studentRegister)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Guardian guardian = new Guardian();

        Cursor cursor = db.rawQuery("SELECT * FROM " + GUARDIAN_TABLE + " WHERE " + COLUMN_STUDENT_REGISTER + " =?",
                new String[] {studentRegister});

        if(cursor .moveToFirst())
        {
            guardian.setRegister(cursor.getString(cursor.getColumnIndex(COLUMN_REGISTER)));
            guardian.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
            guardian.setSurName(cursor.getString(cursor.getColumnIndex(COLUMN_SURNAME)));
            guardian.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_PHONE_NUMBER)));
            guardian.setEMail(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)));
            guardian.setRelation(cursor.getString(cursor.getColumnIndex(COLUMN_RELATION)));
            guardian.setStudentRegister(cursor.getString(cursor.getColumnIndex(COLUMN_STUDENT_REGISTER)));
        }
        cursor.close();
        db.close();
        return guardian;
    }

    public ArrayList<String> getGuardianDetails(Guardian guardian)
    {
        ArrayList<String> guardianDetails = new ArrayList<>();

        guardianDetails.add("Утасны дугаар:\t" + guardian.getPhoneNumber());
        guardianDetails.add("И-мэйл:\t" + guardian.getEMail());
        guardianDetails.add("Регистрийн дугаар:\t" + guardian.getRegister());
        guardianDetails.add("Сурагчийн юу нь болох:\t" + guardian.getRelation());

        return guardianDetails;
    }

    public void updateGuardian(String filter, ContentValues args)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(GUARDIAN_TABLE, args, "register=?", new String[]{filter});
        db.close();
    }

    public ArrayList<String> getDates()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> dates = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + ATTENDANCE_TABLE, null);

        if(cursor.moveToFirst())
        {
            while(!cursor.isAfterLast())
            {
                String date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE));

                if(!dates.contains(date))
                    dates.add(date);

                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return dates;
    }

    public void importStudentInfo(String fileName) throws IOException
    {
        FileReader file = new FileReader(fileName);
        BufferedReader buffer = new BufferedReader(file);
        SQLiteDatabase db = this.getWritableDatabase();
        String line = "";
        String columns = "register, name, surname, phone_number, email, address, enrolled_year, gender";
        String str1 = "INSERT INTO " + STUDENT_TABLE + " (" + columns + ") values(";
        String str2 = ");";

        db.beginTransaction();

        while((line = buffer.readLine()) != null)
        {
            StringBuilder sb = new StringBuilder(str1);
            String[] str = line.split(",");
            sb.append("'" + str[0] + "',");
            sb.append("'" + str[1] + "',");
            sb.append("'" + str[2] + "',");
            sb.append("'" + str[3] + "',");
            sb.append("'" + str[4] + "',");
            sb.append("'" + str[5] + "',");
            sb.append("'" + str[6] + "',");
            sb.append("'" + str[7] + "'");
            sb.append(str2);
            db.execSQL(sb.toString());
            db.close();
        }

        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }
}
