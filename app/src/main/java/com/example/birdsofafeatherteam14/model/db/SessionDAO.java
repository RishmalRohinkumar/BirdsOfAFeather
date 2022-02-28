package com.example.birdsofafeatherteam14.model.db;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SessionDAO {
    @Query("SELECT * FROM sessions")
    List<Session> getAll();

    @Query("SELECT * FROM sessions WHERE session_id=:id")
    Session get(int id);

    @Insert
    void insert(Session session);

    @Query("SELECT COUNT(*) from sessions")
    int count();

    @Query("UPDATE sessions SET name=:newName, is_named=:isNamed WHERE session_id=:id")
    void update(String newName, boolean isNamed, int id);
}
