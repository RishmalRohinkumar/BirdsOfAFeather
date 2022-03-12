package com.example.birdsofafeatherteam14;


import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.birdsofafeatherteam14.model.db.AppDatabase;
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
public class FavoritesTests{
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

    // Testing the Query getAll method in the StudentDAO class and the getFavourite method in the
    // Student class because all of the Favorites implementation relies on those two methods
    // User Stories 6 and 7
    @Test
    public void testFavorite() {
        Student s1 = new Student(0, 1, "Bill", "", "X", false);
        Student s2 = new Student(1, 1, "Mike", "", "Y", true);
        Student s3 = new Student(2, 1, "Bobby", "", "Z", true);

        assertEquals(false, s1.getFavourite());
        assertEquals(true, s2.getFavourite());
        assertEquals(true, s3.getFavourite());

        db.studentDAO().insert(s1);
        List<Student> favs = db.studentDAO().getAll(true);
        assertEquals(0, favs.size());

        db.studentDAO().insert(s2);
        favs = db.studentDAO().getAll(true);
        assertEquals(1, favs.size());

        db.studentDAO().insert(s3);
        favs = db.studentDAO().getAll(true);
        assertEquals(2, favs.size());
    }
}
