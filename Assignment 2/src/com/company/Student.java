package com.company;

import java.io.Serializable;

public class Student implements Serializable {

    private String mFullName;
    private int mAge;
    private String mAddress;
    private int mRollNo;
    private String mCourses;

    public Student(String mFullName, int mAge, String mAddress, int mRollNo, String mCourses) {
        this.mFullName = mFullName;
        this.mAge = mAge;
        this.mAddress = mAddress;
        this.mRollNo = mRollNo;
        this.mCourses = mCourses;
    }

    public Student(){

    }

    public void mSetCourses(String mCourses) {
        this.mCourses = mCourses;
    }

    public String  mGetCourses() {
        return mCourses;
    }

    public void mSetFullName(String mFullName) {
        this.mFullName = mFullName;
    }

    public void mSetAge(int mAge) {
        this.mAge = mAge;
    }

    public void mSetAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public void mSetRollNo(int mRollNo) {
        this.mRollNo = mRollNo;
    }

    public String mGetFullName() {
        return mFullName;
    }

    public int mGetAge() {
        return mAge;
    }

    public String mGetAddress() {
        return mAddress;
    }

    public int mGetRollNo() {
        return mRollNo;
    }
}
