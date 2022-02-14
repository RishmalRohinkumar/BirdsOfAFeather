package com.example.birdsofafeatherteam14;

import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.birdsofafeatherteam14.model.db.AppDatabase;
import com.example.birdsofafeatherteam14.model.db.Course;
import com.example.birdsofafeatherteam14.model.db.Student;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

@RunWith(AndroidJUnit4.class)
public class testAddStudentAndCoursesToDb {
    private AppDatabase db;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = AppDatabase.singleton(context);
        db.useTestSingleton(context);
        db.clearAllTables();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void addStudentsAndCoursesAndCheckSize() throws Exception {
        Student student0 = new Student(db.studentDAO().count()+1, "Tyler", "");
        db.studentDAO().insert(student0);
        Course course0 = new Course(student0.studentId, db.coursesDAO().count() + 1, 2022, 120, "CSE", "SP");
        db.coursesDAO().insert(course0);
        Student student1 = new Student(db.studentDAO().count()+1, "Tyler1", "");
        db.studentDAO().insert(student1);
        Course course1 = new Course(student1.studentId, db.coursesDAO().count() + 1, 2022, 139, "CSE", "SP");
        db.coursesDAO().insert(course1);

        assertEquals(2, db.studentDAO().count());
        assertEquals(2, db.coursesDAO().count());


    }
}
