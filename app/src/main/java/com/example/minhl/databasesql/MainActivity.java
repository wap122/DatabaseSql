package com.example.minhl.databasesql;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private EditText edtName;
    private EditText edtFavor;
    private StudentDataBase db;
    private List<Student> listStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initALotOfThing();
        setEvent();
    }

    private void setEvent() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                db.deleteStude(listStudent.get(position));
                if (isNull(db.getStudentCount())) {
                    listView.setAdapter(null);
                    return;
                }
                setAdapter();
            }
        });
    }

    private void initALotOfThing() {
        listView = (ListView) findViewById(R.id.listView);
        edtName = (EditText) findViewById(R.id.edt_name);
        edtFavor = (EditText) findViewById(R.id.edt_favor);
        db = new StudentDataBase(this);
    }

    public void addStudentFromActivity(View view) {

        String name = edtName.getText().toString();
        if (isEmpty(name)) {
            return;
        }
        String favor = edtFavor.getText().toString();
        if (isEmpty(favor)) {
            return;
        }
        Student studentTemp = new Student(name, favor);
        db.addStudent(studentTemp);
        setAdapter();
    }

    public void showStudentSum(View view) {
        setAdapter();
        Toast.makeText(this, String.valueOf(db.getStudentCount()), Toast.LENGTH_SHORT).show();
    }

    private boolean isEmpty(String s) {
        return s.trim().length() == 0;
    }

    public void setAdapter() {
        List<Student> listTemp = db.getAllStudents();
        this.listStudent = listTemp;

        ArrayAdapter<Student> arrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, listTemp);
        listView.setAdapter(arrayAdapter);
    }

    private boolean isNull(int count) {
        return count == 0;
    }
}