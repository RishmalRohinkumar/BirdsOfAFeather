package com.example.birdsofafeatherteam14.model.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface StudentDAO {
    @Query("SELECT * FROM students WHERE session_id=:sessionId")
    List<Student> getAll(int sessionId);

    @Query("SELECT * FROM students WHERE student_id=:id AND session_id=:sessionId")
    Student get(int id, int sessionId);

    // should technically be just one user, but making it a list because theoretically there could be multiple students in session -1
    @Query("SELECT * FROM students WHERE session_id = -1")
    List<Student> getCurrentUsers();

    @Query("SELECT * FROM students WHERE uuid=:uuid")
    List<Student> getByUuid(String uuid);

    @Insert
    void insert(Student student);

    @Query("SELECT COUNT(*) from students")
    int count();
}
