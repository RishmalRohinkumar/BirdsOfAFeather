package com.example.birdsofafeatherteam14;

import android.content.Context;
import android.util.Pair;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.birdsofafeatherteam14.filters.NoneFilterFactory;
import com.example.birdsofafeatherteam14.filters.NoneStudentFilter;
import com.example.birdsofafeatherteam14.filters.QuarterFilterFactory;
import com.example.birdsofafeatherteam14.filters.QuarterStudentFilter;
import com.example.birdsofafeatherteam14.filters.RecentFilterFactory;
import com.example.birdsofafeatherteam14.filters.RecentStudentFilter;
import com.example.birdsofafeatherteam14.filters.SmallFilterFactory;
import com.example.birdsofafeatherteam14.filters.SmallStudentFilter;
import com.example.birdsofafeatherteam14.model.db.AppDatabase;
import com.example.birdsofafeatherteam14.model.db.Course;
import com.example.birdsofafeatherteam14.model.db.Student;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class FilterTests {
    List<Student> students = new ArrayList<>();
    private AppDatabase db;

    // Makes sure the database is set up before each test case
    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = AppDatabase.singleton(context);
        db.useTestSingleton(context);
        db.clearAllTables();
    }

    @Test
    public void testNoFilter(){
        Student s1 = new Student(1, 1, "Bill", "", "A", false);
        Student s2 = new Student(2, 1, "Zach", "", "B", false);
        Student s3 = new Student(3, 1, "Tom", "", "C", false);
        Student user = new Student(-1, -1, "User", "", "E", false);

        Course c1 = new Course(1, 1, 2020, 110, "CSE", "WI", "Large");
        Course c2 = new Course(2, 2, 2020, 110, "CSE", "WI", "Large");
        Course c3 = new Course(3, 3, 2020, 110, "CSE", "WI", "Large");
        Course cUser = new Course(4, -1, 2020, 110, "CSE", "WI", "Large");

        Course c11 = new Course(5, 1, 2020, 110, "CSE", "SP", "Large");
        Course c21 = new Course(6, 2, 2020, 110, "CSE", "SP", "Large");
        Course cUser2 = new Course(7, -1, 2020, 110, "CSE", "SP", "Large");

        Course c12 = new Course(8, 1, 2020, 110, "CSE", "FA", "Large");
        Course cUser3 = new Course(9, -1, 2020, 110, "CSE", "FA", "Large");

        db.studentDAO().insert(s1);
        db.studentDAO().insert(s2);
        db.studentDAO().insert(s3);
        db.coursesDAO().insert(c1);
        db.coursesDAO().insert(c2);
        db.coursesDAO().insert(c3);
        db.studentDAO().insert(user);
        db.coursesDAO().insert(cUser);
        db.coursesDAO().insert(c11);
        db.coursesDAO().insert(c21);
        db.coursesDAO().insert(cUser2);
        db.coursesDAO().insert(c12);
        db.coursesDAO().insert(cUser3);


        NoneFilterFactory factory = new NoneFilterFactory(db);
        NoneStudentFilter filter = (NoneStudentFilter) factory.createFilter();

        List<Student> students = new ArrayList<>();
        students.add(s1);
        students.add(s2);
        students.add(s3);

        List<Pair<Student, Integer>> newList = filter.studentFilter(students);

        List<Student> finalStudent = new ArrayList<>();
        List<Integer> finalCourses = new ArrayList<>();

        for (Pair p : newList) {
            finalStudent.add((Student)(p.first));
            finalCourses.add((Integer)(p.second));
        }


        List<Student> expectedStudentList = new ArrayList<>(Arrays.asList(s1, s2, s3));
        List<Integer> expectedClassList = new ArrayList<>(Arrays.asList(3, 2, 1));

        Assert.assertEquals(finalStudent, expectedStudentList);
        Assert.assertEquals(finalCourses, expectedClassList);
    }

    @Test
    public void testSmallFilter(){
        Student s1 = new Student(1, 1, "Bill", "", "A", false);
        Student s2 = new Student(2, 1, "Zach", "", "B", false);
        Student s3 = new Student(3, 1, "Tom", "", "C", false);
        Student user = new Student(-1, -1, "User", "", "E", false);

        Course c1 = new Course(1, 1, 2020, 110, "CSE", "WI", "Large");
        Course cUser = new Course(2, -1, 2020, 110, "CSE", "WI", "Large");

        Course c11 = new Course(3, 2, 2020, 110, "CSE", "SP", "Medium");
        Course cUser2 = new Course(4, -1, 2020, 110, "CSE", "SP", "Medium");

        Course c12 = new Course(5, 3, 2020, 110, "CSE", "FA", "Small");
        Course cUser3 = new Course(6, -1, 2020, 110, "CSE", "FA", "Small");

        db.studentDAO().insert(s1);
        db.studentDAO().insert(s2);
        db.studentDAO().insert(s3);
        db.coursesDAO().insert(c1);
        db.studentDAO().insert(user);
        db.coursesDAO().insert(cUser);
        db.coursesDAO().insert(c11);
        db.coursesDAO().insert(cUser2);
        db.coursesDAO().insert(c12);
        db.coursesDAO().insert(cUser3);


        SmallFilterFactory factory = new SmallFilterFactory(db);
        SmallStudentFilter filter = (SmallStudentFilter) factory.createFilter();

        List<Student> students = new ArrayList<>();
        students.add(s1);
        students.add(s2);
        students.add(s3);

        List<Pair<Student, Integer>> newList = filter.studentFilter(students);

        List<Student> finalStudent = new ArrayList<>();
        List<Integer> finalCourses = new ArrayList<>();

        for (Pair p : newList) {
            finalStudent.add((Student)(p.first));
            finalCourses.add((Integer)(p.second));
        }


        List<Student> expectedStudentList = new ArrayList<>(Arrays.asList(s3, s2, s1));
        List<Integer> expectedClassList = new ArrayList<>(Arrays.asList(1, 1, 1));

        Assert.assertEquals(finalStudent, expectedStudentList);
        Assert.assertEquals(finalCourses, expectedClassList);
    }

    @Test
    public void testRecentFilter(){
        Student s1 = new Student(1, 1, "Bill", "", "A", false);
        Student s2 = new Student(2, 1, "Zach", "", "B", false);
        Student s3 = new Student(3, 1, "Tom", "", "C", false);
        Student user = new Student(-1, -1, "User", "", "E", false);

        Course c1 = new Course(1, 1, 2020, 110, "CSE", "WI", "Large");
        Course cUser = new Course(2, -1, 2020, 110, "CSE", "WI", "Large");

        Course c11 = new Course(3, 2, 2022, 110, "CSE", "WI", "Medium");
        Course cUser2 = new Course(4, -1, 2022, 110, "CSE", "WI", "Medium");

        Course c12 = new Course(5, 3, 2021, 110, "CSE", "WI", "Small");
        Course cUser3 = new Course(6, -1, 2021, 110, "CSE", "WI", "Small");

        db.studentDAO().insert(s1);
        db.studentDAO().insert(s2);
        db.studentDAO().insert(s3);
        db.coursesDAO().insert(c1);
        db.studentDAO().insert(user);
        db.coursesDAO().insert(cUser);
        db.coursesDAO().insert(c11);
        db.coursesDAO().insert(cUser2);
        db.coursesDAO().insert(c12);
        db.coursesDAO().insert(cUser3);


        RecentFilterFactory factory = new RecentFilterFactory(db);
        RecentStudentFilter filter = (RecentStudentFilter) factory.createFilter();

        List<Student> students = new ArrayList<>();
        students.add(s1);
        students.add(s2);
        students.add(s3);

        List<Pair<Student, Integer>> newList = filter.studentFilter(students);

        List<Student> finalStudent = new ArrayList<>();
        List<Integer> finalCourses = new ArrayList<>();

        for (Pair p : newList) {
            finalStudent.add((Student)(p.first));
            finalCourses.add((Integer)(p.second));
        }


        List<Student> expectedStudentList = new ArrayList<>(Arrays.asList(s2, s3, s1));
        List<Integer> expectedClassList = new ArrayList<>(Arrays.asList(1, 1, 1));

        Assert.assertEquals(finalStudent, expectedStudentList);
        Assert.assertEquals(finalCourses, expectedClassList);
    }

    @Test
    public void testQuarterFilter(){
        Student s1 = new Student(1, 1, "Bill", "", "A", false);
        Student s2 = new Student(2, 1, "Zach", "", "B", false);
        Student s3 = new Student(3, 1, "Tom", "", "C", false);
        Student user = new Student(-1, -1, "User", "", "E", false);

        Course c1 = new Course(1, 1, 2020, 110, "CSE", "WI", "Large");
        Course cUser = new Course(2, -1, 2020, 110, "CSE", "WI", "Large");

        Course c11 = new Course(3, 2, 2022, 110, "CSE", "WI", "Medium");
        Course cUser2 = new Course(4, -1, 2022, 110, "CSE", "WI", "Medium");

        Course c12 = new Course(5, 3, 2022, 110, "CSE", "WI", "Small");
        Course cUser3 = new Course(6, -1, 2022, 110, "CSE", "WI", "Small");

        Course c13 = new Course(7, 3, 2021, 110, "CSE", "FA", "Small");
        Course cUser4 = new Course(8, -1, 2021, 110, "CSE", "FA", "Small");

        Course c14 = new Course(9, 2, 2022, 210, "CSE", "WI", "Medium");
        Course cUser5 = new Course(10, -1, 2022, 210, "CSE", "WI", "Medium");


        db.studentDAO().insert(s1);
        db.studentDAO().insert(s2);
        db.studentDAO().insert(s3);
        db.coursesDAO().insert(c1);
        db.studentDAO().insert(user);
        db.coursesDAO().insert(cUser);
        db.coursesDAO().insert(c11);
        db.coursesDAO().insert(cUser2);
        db.coursesDAO().insert(c12);
        db.coursesDAO().insert(cUser3);
        db.coursesDAO().insert(c13);
        db.coursesDAO().insert(cUser4);
        db.coursesDAO().insert(c14);
        db.coursesDAO().insert(cUser5);


        QuarterFilterFactory factory = new QuarterFilterFactory(db);
        QuarterStudentFilter filter = (QuarterStudentFilter) factory.createFilter();

        List<Student> students = new ArrayList<>();
        students.add(s1);
        students.add(s2);
        students.add(s3);

        List<Pair<Student, Integer>> newList = filter.studentFilter(students);

        List<Student> finalStudent = new ArrayList<>();
        List<Integer> finalCourses = new ArrayList<>();

        for (Pair p : newList) {
            finalStudent.add((Student)(p.first));
            finalCourses.add((Integer)(p.second));
        }


        List<Student> expectedStudentList = new ArrayList<>(Arrays.asList(s2, s3));
        List<Integer> expectedClassList = new ArrayList<>(Arrays.asList(2, 1));

        Assert.assertEquals(finalStudent, expectedStudentList);
        Assert.assertEquals(finalCourses, expectedClassList);
    }

    // Clean up the database after each test case
    @After
    public void closeDb() throws IOException {
        db.clearAllTables();
        db.close();
    }
}
