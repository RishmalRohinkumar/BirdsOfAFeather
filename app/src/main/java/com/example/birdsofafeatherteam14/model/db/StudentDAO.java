package com.example.birdsofafeatherteam14.model.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface StudentDAO {
    @Query("SELECT * FROM students")
    List<Student> getAll();

    @Query("SELECT * FROM students WHERE student_id=:id")
    Student get(int id);

    @Insert
    void insert(Student student);

    @Query("SELECT COUNT(*) from students")
    int count();
}
