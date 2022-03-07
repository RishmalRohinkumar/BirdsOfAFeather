package com.example.birdsofafeatherteam14;

import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.birdsofafeatherteam14.model.db.AppDatabase;
import com.example.birdsofafeatherteam14.model.db.Course;
import com.example.birdsofafeatherteam14.model.db.Session;
import com.example.birdsofafeatherteam14.model.db.Student;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

// These tests ensure that items can be added to the database and then retrieved.
// They are an example of instrumented tests because they are run on the emulator itself,
// not mocked through RoboElectric.
@RunWith(AndroidJUnit4.class)
public class SessionsTests{
    private AppDatabase db;

    // Makes sure the database is set up before each test case
    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = AppDatabase.singleton(context);
        db.useTestSingleton(context);
        db.clearAllTables();
    }

    // Clean up the database after each test case
    @After
    public void closeDb() throws IOException {
        db.clearAllTables();
        db.close();
    }

    // Make sure you can get the current student and only the current student from session -1
    @Test
    public void testGetCurrentUser() throws Exception {
        Session currUserSession = new Session(-1, "", true);
        db.sessionDAO().insert(currUserSession);
        Student currUser = new Student(0, -1, "Tyler", "", "Z");
        db.studentDAO().insert(currUser);

        Session otherSession = new Session(0, "CSE 105", true);
        db.sessionDAO().insert(otherSession);
        Student otherStudent = new Student(1, 0, "Anthony", "", "Y");
        db.studentDAO().insert(otherStudent);

        List<Student> currUsers = db.studentDAO().getCurrentUsers();
        assertEquals(1, currUsers.size());
        assertEquals(currUser, currUsers.get(0));
    }

    // Make sure we can have separate students in many sessions
    @Test
    public void testSeparateSessions() throws Exception {
        Session session0 = new Session(0, "CSE 105", true);
        db.sessionDAO().insert(session0);
        Student student0 = new Student(0, 0, "Anthony", "", "A");
        db.studentDAO().insert(student0);
        Student student1 = new Student(1, 0, "Jerry", "", "B");
        db.studentDAO().insert(student1);
        Student student2 = new Student(2, 0, "Ben", "", "C");
        db.studentDAO().insert(student2);

        Session session1 = new Session(1, "CSE 110", true);
        db.sessionDAO().insert(session1);

        Session session2 = new Session(2, "CSE 134B", true);
        db.sessionDAO().insert(session2);
        Student student3 = new Student(3, 2, "CSE 99", "", "D");
        db.studentDAO().insert(student3);


        List<Student> session0Students = db.studentDAO().getAll(0);
        List<Student> session1Students = db.studentDAO().getAll(1);
        List<Student> session2Students = db.studentDAO().getAll(2);

        assertEquals(3, session0Students.size());
        assertEquals(0, session1Students.size());
        assertEquals(1, session2Students.size());
    }

    // test to make sure we can rename the sessions in the database
    @Test
    public void testUpdateSession() {
        Session s = new Session(0, "timestamp", false);
        db.sessionDAO().insert(s);
        assertEquals("timestamp", db.sessionDAO().get(0).getName());
        db.sessionDAO().update("CSE 105", true, 0);
        assertEquals("CSE 105", db.sessionDAO().get(0).getName());
    }

}
