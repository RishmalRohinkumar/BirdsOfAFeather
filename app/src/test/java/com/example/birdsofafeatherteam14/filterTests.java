package com.example.birdsofafeatherteam14;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static org.junit.Assert.assertEquals;

import com.example.birdsofafeatherteam14.model.db.AppDatabase;
import com.example.birdsofafeatherteam14.model.db.Student;

import org.junit.Test;

import java.time.LocalDate;

public class filterTests {

    protected AppDatabase db;

    public void setup(){
        db = AppDatabase.singleton(getApplicationContext());
        Student student = new Student(db.studentDAO().count()+1, , , ,);
        db.studentDAO().insert(student);
    }


    @Test
    public void test() {

    }

}
