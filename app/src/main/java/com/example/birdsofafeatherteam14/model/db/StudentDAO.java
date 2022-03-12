package com.example.birdsofafeatherteam14.model.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface StudentDAO {
    @Query("SELECT * FROM students WHERE session_id=:sessionId")
    List<Student> getAll(int sessionId);

    @Query("SELECT * FROM students WHERE is_fav=:isFav")
    List<Student> getAll(boolean isFav);

    @Query("SELECT * FROM students WHERE student_id=:id")
    Student get(int id);

    // should technically be just one user, but making it a list because theoretically there could be multiple students in session -1
    @Query("SELECT * FROM students WHERE session_id = -1")
    List<Student> getCurrentUsers();

    @Query("SELECT * FROM students WHERE uuid=:uuid")
    List<Student> getByUuid(String uuid);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Student student);

    @Query("SELECT COUNT(*) from students")
    int count();

    @Query("UPDATE students SET wave=:waveStatus WHERE student_id=:id")
    void updateWave(boolean waveStatus, int id);

    @Query("UPDATE students SET is_fav=:newFav WHERE student_id=:id ")
    void updateFav(boolean newFav, int id);

//    @Update(onConflict = OnConflictStrategy.REPLACE)
//    void updateStudent(Student student);
}
