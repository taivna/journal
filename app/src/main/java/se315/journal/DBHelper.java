package se315.journal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL(DELETE_STUDENT_TABLE);
        db.execSQL(DELETE_GUARDIAN_TABLE);
        db.execSQL(DELETE_SUBJECT_TABLE);
        db.execSQL(DELETE_ITEM_TABLE);
        db.execSQL(DELETE_SCHEDULE_TABLE);
        db.execSQL(DELETE_ATTENDANCE_TABLE);
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
                subject.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                subjectList.add(subject);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return subjectList;
    }

    public ArrayList<String> getStudentDetails(Student student)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> studentDetails = new ArrayList<>();

        studentDetails.add("Регистрийн дугаар:\t" + student.getRegister());
        studentDetails.add("Утасны дугаар:\t" + student.getPhoneNumber());
        studentDetails.add("И-мэйл:\t" + student.getEMail());
        studentDetails.add("Гэрийн хаяг:\t" + student.getAddress());
        studentDetails.add("Элссэн огноо:\t" + String.valueOf(student.getEndrolledYear()));
        studentDetails.add("Хүйс:\t" + student.getGender());

        Cursor cursor = db.rawQuery("SELECT * FROM " + GUARDIAN_TABLE + " WHERE " + COLUMN_STUDENT_REGISTER + " = ?",
                new String[] {student.getRegister()});

        if(cursor.moveToFirst())
        {
            studentDetails.add("Асран хамгаалагч:\t" + cursor.getString(cursor.getColumnIndex(COLUMN_SURNAME)) +
            " , " + cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
            studentDetails.add("Утасны дугаар:\t" + cursor.getString(cursor.getColumnIndex(COLUMN_PHONE_NUMBER)));
            studentDetails.add("И-мэйл:\t" + cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)));
            studentDetails.add("Сурагчийн юу  нь болох:\t" + cursor.getString(cursor.getColumnIndex(COLUMN_RELATION)));
        }
        cursor.close();
        db.close();
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
}
