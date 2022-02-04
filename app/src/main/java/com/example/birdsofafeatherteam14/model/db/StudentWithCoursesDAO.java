package com.example.birdsofafeatherteam14.model.db;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface StudentWithCoursesDAO {

    @Transaction
    @Query("SELECT * FROM students")
    List<StudentWithCourses> getAll();

    @Query("SELECT * FROM students WHERE id=:id")
    StudentWithCourses get(int id);

    @Query("SELECT COUNT(*) from students")
    int count();
}