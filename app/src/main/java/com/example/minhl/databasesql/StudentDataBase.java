package com.example.minhl.databasesql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

class StudentDataBase extends SQLiteOpenHelper {
    private static StudentDataBase studentInstance;

    private static final String TAG = "SQLite";

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "Student_Manager";

    private static final String TABLE_STUDENT = "Student";

    private static final String COLUMN_STUDENT_ID = "Student_Id";
    private static final String COLUMN_STUDENT_NAME = "Student_Name";
    private static final String COLUMN_STUDENT_FAVOR = "Student_Favor";

    public static synchronized StudentDataBase getInstance(Context context) {
        if (studentInstance == null) {
            studentInstance = new StudentDataBase(context.getApplicationContext());
        }
        return studentInstance;
    }

    StudentDataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Tạo các bảng
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "MyDatabaseHelper.onCreate ... ");

        String script = "CREATE TABLE " + TABLE_STUDENT + "("
                + COLUMN_STUDENT_ID + " INTEGER PRIMARY KEY," + COLUMN_STUDENT_NAME + " TEXT,"
                + COLUMN_STUDENT_FAVOR + " TEXT" + ")";
        db.execSQL(script);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG, "MyDatabaseHelper.onUpgrade ... ");

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENT);

        onCreate(db);
    }


    public void addStudent(Student student) {
        Log.i(TAG, "MyDatabaseHelper.addStudent ... " + student.getStdName());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_STUDENT_NAME, student.getStdName());
        values.put(COLUMN_STUDENT_FAVOR, student.getStdFavor());

        db.insert(TABLE_STUDENT, null, values);

        db.close();
    }


    public Student getStudent(int id) {
        Log.i(TAG, "MyDatabaseHelper.getStudent ... " + id);

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_STUDENT, new String[]{COLUMN_STUDENT_ID,
                        COLUMN_STUDENT_NAME, COLUMN_STUDENT_FAVOR}, COLUMN_STUDENT_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Student student = new Student(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));

        return student;
    }


    public List<Student> getAllStudents() {
        Log.i(TAG, "MyDatabaseHelper.getAllStudents ... ");

        List<Student> studentList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_STUDENT;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Student student = new Student();
                student.setStdId(Integer.parseInt(cursor.getString(0)));
                student.setStdName(cursor.getString(1));
                student.setStdFavor(cursor.getString(2));
                studentList.add(student);
            } while (cursor.moveToNext());
        }

        return studentList;
    }

    public int getStudentCount() {
        Log.i(TAG, "MyDatabaseHelper.getStudentCount ... ");

        String countQuery = "SELECT  * FROM " + TABLE_STUDENT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        return count;
    }


    public int updateStudent(Student student) {
        Log.i(TAG, "MyDatabaseHelper.updateStudent ... " + student.getStdName());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_STUDENT_NAME, student.getStdName());
        values.put(COLUMN_STUDENT_FAVOR, student.getStdFavor());

        return db.update(TABLE_STUDENT, values, COLUMN_STUDENT_ID + " = ?",
                new String[]{String.valueOf(student.getStdId())});
    }

    public void deleteStude(Student student) {
        Log.i(TAG, "MyDatabaseHelper.updateStudent ... " + student.getStdName());

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_STUDENT, COLUMN_STUDENT_ID + " = ?",
                new String[]{String.valueOf(student.getStdId())});
        db.close();
    }
}
