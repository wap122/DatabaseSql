package com.example.minhl.databasesql;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
    private final List<Student> listStudent = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initALotOfThing();
    }

    private void setAdapter() {

        List<Student> listTemp = db.getAllStudents();
        listStudent.addAll(listTemp);
        ArrayAdapter<Student> arrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, listStudent);
        listView.setAdapter(arrayAdapter);
    }

    private void initALotOfThing() {
        listView = (ListView) findViewById(R.id.listView);
        edtName = (EditText) findViewById(R.id.edt_name);
        edtFavor = (EditText) findViewById(R.id.edt_favor);
        db = new StudentDataBase(this);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                db.deleteStude(db.getStudent(position+1));
                return true;
            }
        });
    }

    public void addStudentFromActivity(View view) {
        String name = edtName.getText().toString();
        String favor = edtFavor.getText().toString();
        if (name == null) {
           return;
        }
        if (favor == null) {
            return;
        }
        Student studentTemp = new Student(name, favor);
        db.addStudent(studentTemp);
        setAdapter();
    }

    public void showStudentSum(View view) {
        Toast.makeText(this, String.valueOf(db.getStudentCount()), Toast.LENGTH_SHORT).show();
    }
}