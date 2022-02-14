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
import java.util.List;

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
        db.clearAllTables();
        db.close();
    }

    @Test
    public void addStudentsAndCoursesAndCheckSize() throws Exception {
        Student student0 = new Student(db.studentDAO().count()+1, "Tyler", "");
        db.studentDAO().insert(student0);
        Course course0 = new Course(db.coursesDAO().count() + 1, student0.studentId, 2022, 120, "CSE", "SP");
        db.coursesDAO().insert(course0);
        Student student1 = new Student(db.studentDAO().count()+1, "Tyler1", "");
        db.studentDAO().insert(student1);
        Course course1 = new Course(db.coursesDAO().count() + 1, student1.studentId, 2022, 139, "CSE", "SP");
        db.coursesDAO().insert(course1);

        assertEquals(2, db.studentDAO().count());
        assertEquals(2, db.coursesDAO().count());
    }

    @Test
    public void testGetCoursesByStudent() throws Exception {
        Student student0 = new Student(db.studentDAO().count()+1, "Tyler", "");
        db.studentDAO().insert(student0);
        Course course0 = new Course(db.coursesDAO().count() + 1, student0.studentId, 2022, 120, "CSE", "SP");
        db.coursesDAO().insert(course0);
        Course course1 = new Course(db.coursesDAO().count() + 1, student0.studentId, 2023, 125, "PHYS", "FA");
        db.coursesDAO().insert(course1);
        Student student1 = new Student(db.studentDAO().count()+1, "Tyler1", "");
        db.studentDAO().insert(student1);
        Course course2 = new Course(db.coursesDAO().count() + 1, student1.studentId, 2022, 139, "CSE", "SP");
        db.coursesDAO().insert(course2);

        List<Course> coursesFromDbTyler = db.coursesDAO().getForStudent(student0.studentId);
        assertEquals(2, coursesFromDbTyler.size());
        Course courseFromDb0 = coursesFromDbTyler.get(0);
        Course courseFromDb1 = coursesFromDbTyler.get(1);
        assertEquals(true, courseFromDb0.equals(course0));
        assertEquals(true, courseFromDb1.equals(course1));
    }
}
