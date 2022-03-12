package com.example.birdsofafeatherteam14;


import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.util.Pair;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.birdsofafeatherteam14.filters.NoneFilterFactory;
import com.example.birdsofafeatherteam14.filters.NoneStudentFilter;
import com.example.birdsofafeatherteam14.model.db.AppDatabase;
import com.example.birdsofafeatherteam14.model.db.Course;
import com.example.birdsofafeatherteam14.model.db.Student;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class WavePinnedTest {
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

    // Testing that waves will appear at the top of the list
    @Test
    public void testWavePinned() {
        // Change students' courses for specific ordering
        Student s1 = new Student(0, 1, "Bill", "", "X", false);
        Course c1 = new Course(0, 0, 2020, 110, "CSE", "WI", "Large");
        Student s2 = new Student(1, 1, "Mike", "", "Y", false);
        Course c2 = new Course(1, 1, 2020, 110, "CSE", "WI", "Large");
        Student s3 = new Student(2, 1, "Bobby", "", "Z", false);
        Course c3 = new Course(2, 2, 2020, 110, "CSE", "WI", "Large");
        Student user = new Student(-1, -1, "User", "", "E", false);
        Course cUser = new Course(3, -1, 2020, 110, "CSE", "WI", "Large");
        s3.wave = true;
        db.studentDAO().insert(s1);
        db.studentDAO().insert(s2);
        db.studentDAO().insert(s3);
        db.coursesDAO().insert(c1);
        db.coursesDAO().insert(c2);
        db.coursesDAO().insert(c3);
        db.studentDAO().insert(user);
        db.coursesDAO().insert(cUser);

        // Show that s3 is at the top of the list
        NoneFilterFactory factory = new NoneFilterFactory(db);
        NoneStudentFilter filter = (NoneStudentFilter) factory.createFilter();

        List<Student> students = new ArrayList<>();
        students.add(s1);
        students.add(s2);
        students.add(s3);

        // Sort according to none filter(which should put the waved students on top)
        List<Pair<Student, Integer>> newList = filter.studentFilter(students);
        assertEquals(true, newList.get(0).first.wave);
    }
}
