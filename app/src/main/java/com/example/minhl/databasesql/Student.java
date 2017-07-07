package com.example.minhl.databasesql;

import java.io.Serializable;

/**
 * Created by minhl on 07/07/2017.
 */

public class Student implements Serializable {
    private int stdId;
    private String stdName;
    private String stdFavor;

    public Student() {
    }

    public Student(String stdName, String stdFavor) {
        this.stdName = stdName;
        this.stdFavor = stdFavor;
    }

    public Student(int stdID, String stdName, String stdFavor) {
        this(stdName, stdFavor);
        this.stdId = stdID;

    }

    public void setStdId(int stdId) {
        this.stdId = stdId;
    }

    public void setStdName(String stdName) {
        this.stdName = stdName;
    }

    public void setStdFavor(String stdFavor) {
        this.stdFavor = stdFavor;
    }

    public int getStdId() {
        return stdId;
    }

    public String getStdName() {
        return stdName;
    }

    public String getStdFavor() {
        return stdFavor;
    }

    @Override //thì ra là cái này, quá hiểm
    public String toString() {
        return stdName;
    }
}
