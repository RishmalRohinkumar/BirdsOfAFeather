package com.example.birdsofafeatherteam14.model.db;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// Essentially links a session id to its corresponding name
@Entity(tableName = "sessions")
public class Session {
    public Session(String name) {
        this.name = name;
    }

    @PrimaryKey
    @ColumnInfo(name = "session_id")
    public int sessionId;

    @ColumnInfo(name = "name")
    public String name;

    public String getName() {return name;}

    public int getId() {return this.sessionId;}
}
