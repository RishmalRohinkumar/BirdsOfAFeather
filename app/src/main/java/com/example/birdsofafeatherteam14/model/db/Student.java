package com.example.birdsofafeatherteam14.model.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "students")
public class Student {

    public Student(int studentId, String name, String photo) {
        this.studentId = studentId;
        this.name = name;
        this.photo = photo;
    }

    @PrimaryKey
    @ColumnInfo(name = "id")
    public int studentId;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "photo")
    public String photo;
}
