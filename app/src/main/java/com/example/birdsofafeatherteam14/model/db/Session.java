package com.example.birdsofafeatherteam14.model.db;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// Essentially links a session id to its corresponding name
@Entity(tableName = "sessions")
public class Session {
    public Session(int sessionId, String name, boolean isNamed) {
        this.name = name;
        this.isNamed = isNamed;
        this.sessionId = sessionId;
    }

    @PrimaryKey
    @ColumnInfo(name = "session_id")
    public int sessionId;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "is_named")
    public boolean isNamed;

    public String getName() {return name;}

    public boolean getIsNamed() {return isNamed;}

    public int getId() {return this.sessionId;}
}
