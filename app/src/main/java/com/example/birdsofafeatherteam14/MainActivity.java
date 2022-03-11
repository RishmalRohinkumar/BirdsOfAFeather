package com.example.birdsofafeatherteam14;

import static com.example.birdsofafeatherteam14.Utilities.showAlert;

import static java.util.Collections.*;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.text.InputType;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;


import com.example.birdsofafeatherteam14.model.db.AppDatabase;
import com.example.birdsofafeatherteam14.model.db.Course;
import com.example.birdsofafeatherteam14.model.db.Session;
import com.example.birdsofafeatherteam14.model.db.Student;
import com.example.birdsofafeatherteam14.model.db.StudentDAO;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    // Stuff for bluetooth
    private MessageListener messageListener;
    private MessageListener realListener;
    private static final String TAG = "BOAF-14";

    public static final int START_MOCK_BLUETOOTH = 99;

    protected RecyclerView studentRecyclerView;
    protected RecyclerView.LayoutManager studentLayoutManager;
    protected StudentViewAdapter studentViewAdapter;

    protected AppDatabase db;

    private static boolean searching = false; // the search button toggles this between true and false

    public static int CURRENT_USER_SESSION_ID = -1; // current user is always in a session with ID -1

    private String chosenNameResult; // for the dialog where the user selects a session
    private String newSessionNameResult; // for the dialog where the user selects a name for the new session

    private String currentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "In onCreate() of MainActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = AppDatabase.singleton(getApplicationContext());

        setTitle("Birds of a Feather");

        setUpRealBluetooth();

        setNoCurrentSession();

        currentFilter = "None";

        setFilterSpinner();

        // Check if we have added the current user to a session with id -1
        if (db.studentDAO().getCurrentUsers().isEmpty()) {
            Log.i(TAG, "No current student set");
            // The current student hasn't been created, so we should go through the profile
            // creation process. Once we've looped back around to the main activity, the current
            // user will already be stored in the database
            Intent intent = new Intent(this, ProfileNameActivity.class);
            startActivity(intent);
        }
    }

    private void setFilterSpinner(){
        Spinner filter_spinner = (Spinner) findViewById(R.id.filter_spinner);

        ArrayAdapter<CharSequence> filter_adapter = ArrayAdapter.createFromResource(this,
                R.array.filters_array, android.R.layout.simple_spinner_item);

        filter_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        filter_spinner.setAdapter(filter_adapter);

        filter_spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                        updateStudentViews(getResources().getStringArray(R.array.filters_key)[pos]);
                    }
                    public void onNothingSelected(AdapterView<?> adapterView){}
                }
        );
    }

    private List<Student> prepareStudentList(){
        List<Student> students = new ArrayList<>();

        if (db != null) {
            Log.i(TAG, "Database not null in prepareStudentList");

            Session currSession = getCurrentSession();
            if (currSession == null) {return students;}

            students = db.studentDAO().getAll(currSession.sessionId);

            Log.i(TAG, "Received list of students from the database of size: " + students.size());

            Student user = db.studentDAO().getCurrentUsers().get(0);

            //Remove user from student list
            students.remove(user);

            return students;

        } else {
            Log.i(TAG, "Database null in updateStudentViews()");
        }
        return students;
    }

    private List<List<Course>> prepareClassOverlapList(List<Student> students){
        List<List<Course>> classes = new ArrayList<>();
        Student user = db.studentDAO().getCurrentUsers().get(0);
        //List out common classes
        for(Student student : students){
            StudentCourseComparator comparator = new StudentCourseComparator
                    (db.coursesDAO().getForStudent(user.studentId), db.coursesDAO().getForStudent(student.getId()));
            List<Course> overlapList = comparator.compare();
            classes.add(overlapList);
        }
        return classes;
    }

    private List<Pair<Student, Integer>> noneStudentFilter(List<Student> students){
        List<Pair<Student, Integer>> commonClasses = new ArrayList<>();

        List<List<Course>> classes = prepareClassOverlapList(students);


        for (int i = 0; i < classes.size(); i++){
            if (classes.get(i).size() != 0){
                commonClasses.add(new Pair<>(students.get(i), classes.get(i).size()));
            }
        }

        Collections.sort(commonClasses, Comparator.comparing(p -> -p.second));

        return commonClasses;
    }

    private List<Pair<Student, Integer>> recentStudentFilter(List<Student> students){
        List<Pair<Student, Integer>> commonClasses = noneStudentFilter(students);

        List<Pair<Student, Integer>> recentCommonClasses = new ArrayList<>();
        for(int i = db.sessionDAO().count(); i > 0; i--){
            for(Pair<Student, Integer> p: commonClasses){
                if(p.first.getSesssionId() == i) {
                    recentCommonClasses.add(p);
                }
            }
        }

        return recentCommonClasses;

    }

    private List<Pair<Student, Integer>> smallStudentFilter(List<Student> students){
        List<Pair<Student, Integer>> commonClasses = new ArrayList<>();



        return commonClasses;
    }

    private List<Pair<Student, Integer>> quarterStudentFilter(List<Student> students){
        List<Pair<Student, Integer>> commonClasses = new ArrayList<>();

        List<List<Course>> classes = prepareClassOverlapList(students);

        return commonClasses;
    }

    private List<Pair<Student, Integer>> leastStudentFilter(List<Student> students){
        List<Pair<Student, Integer>> commonClasses = new ArrayList<>();

        List<List<Course>> classes = prepareClassOverlapList(students);


        for (int i = 0; i < classes.size(); i++){
            if (classes.get(i).size() != 0){
                commonClasses.add(new Pair<>(students.get(i), classes.get(i).size()));
            }
        }

        Collections.sort(commonClasses, Collections.reverseOrder(p -> -p.second));


        return commonClasses;
    }


    // Updates the recycler views with the other students that have overlapping classes
    // should only be called when the start button is clicked
    private void updateStudentViews(String filter) {
        List<Student> students = prepareStudentList();
        List<Pair<Student, Integer>> commonClasses = new ArrayList<>();

        switch (filter){
            case "None":
                commonClasses = noneStudentFilter(students);
                break;
            case "Recent":
                commonClasses = recentStudentFilter(students);
                break;
            case "Small":
                commonClasses = smallStudentFilter(students);
                break;
            case "Quarter":
                commonClasses = quarterStudentFilter(students);
                break;
            case "Least":
                commonClasses = leastStudentFilter(students);
                break;
        }

        currentFilter = filter;

        List<Student> finalStudent = new ArrayList<>();
        List<Integer> finalCourses = new ArrayList<>();

        for (Pair p : commonClasses) {
            finalStudent.add((Student) p.first);
            finalCourses.add((Integer) p.second);
        }

        Log.i(TAG, "Common courses found: starting to set up RecyclerViews");
        studentRecyclerView = findViewById(R.id.students_view);

        studentLayoutManager = new LinearLayoutManager(this);
        studentRecyclerView.setLayoutManager(studentLayoutManager);

        studentViewAdapter = new StudentViewAdapter(finalStudent, finalCourses);
        studentRecyclerView.setAdapter(studentViewAdapter);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "in onDestroy() of MainActivity");
        // This is important for any test cases involving the main activity.
        // Need to close the database so all of the tests running one after each other
        // don't fuck up the database
        if (db != null) {
            //db.close();
        }
    }

    // receives information from the bluetooth activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "In onActivityResult() of MainActivity");

        if (requestCode == START_MOCK_BLUETOOTH) {
            if(resultCode == Activity.RESULT_OK){
                Log.i(TAG, "Received ok result from MockBlueToothActivity");
                // We have a result from the mock bluetooth activity if we're in here
                List<String> mockStudents = data.getStringArrayListExtra("messages");
                if (mockStudents != null && !mockStudents.isEmpty()) {
                    Log.i(TAG, "Received valid mock Students from Mock Bluetooth Activity");
                    setUpMockBluetooth(mockStudents);
                } else {
                    Log.i(TAG, "Received empty or invalid mock Students from Mock Bluetooth Activity");
                }
            }
        }
    }

    // Deals with converting the CSV string from the Mock Bluetooth Task
    // and adds the corresponded students and courses to the databse
    private void addStudentCSVStringToDb(String str) {
        // All these indices have to do with the format specified in this piazza post:
        // https://piazza.com/class/kx9gvm79v371z5?cid=466
        Log.i(TAG, "in addStudentCSVStringToDb");

        Session currSession = getCurrentSession();
        if (currSession == null) {return;}

        final int YEAR_INDEX = 0;
        final int QUARTER_INDEX = 1;
        final int SUBJECT_INDEX = 2;
        final int NUMBER_INDEX = 3;
        final int SIZE_INDEX = 4;

        try {
            String[] splitByNewline = str.split("\n");
            String uuid = splitByNewline[0].split(",")[0];
            String name = splitByNewline[1].split(",")[0];
            String url = splitByNewline[2].split(",")[0];
            Student student = new Student(db.studentDAO().count()+1, currSession.getId(), name, url, uuid);

            List<Course> courses = new ArrayList<Course>();
            int currCourseId = db.coursesDAO().count() + 1;
            for (int i = 3; i < splitByNewline.length; i++) {
                String[] courseInfo = splitByNewline[i].split(",");

                int courseYear = Integer.parseInt(courseInfo[YEAR_INDEX]);
                String courseQuarter = courseInfo[QUARTER_INDEX];
                String courseSubject = courseInfo[SUBJECT_INDEX];
                int courseNum = Integer.parseInt(courseInfo[NUMBER_INDEX]);
                String courseSize = courseInfo[SIZE_INDEX];
                // POST INCREMENT SO IT'S ONE GREATER FOR THE NEXT COURSE
                // DOING IT THIS WAY BECAUSE WE ARE NOT ADDING COURSES TO THE DB
                // IN THIS LOOP, WE DO IT AT THE END SO THE .count FUNCTION WILL
                // KEEP RETURNING THE SAME VALUE
                Course course = new Course(currCourseId++, student.studentId,
                        courseYear, courseNum, courseSubject, courseQuarter, courseSize);
                courses.add(course);
            }

            // only add to db if all good and nothing went wrong earlier

            db.studentDAO().insert(student);
            Log.i(TAG, "Successfully Added a student from MockBluetooth to the database");
            for (Course c : courses) {
                db.coursesDAO().insert(c);
                Log.i(TAG, "Successfully Added a course from MockBluetooth to the database");
            }

        } catch (IndexOutOfBoundsException | NumberFormatException e) {
            showAlert(this,
                    "Error in CSV formatting. Please make sure there is only one newline between each row. If you can't get it to work, then please ask Tyler.");
            Log.i(TAG, "Error in Mock Bluetooth result");
        }

    }

    // Sends us to the mock bluetooth activity and sets up
    // the necessary information so that we can receive the result from it
    public void goToMockBluetoothActivity(View view) {
        Log.i(TAG, "in goToMockBluetoothActivity");
        Intent intent = new Intent(this, MockBluetoothActivity.class);
        startActivityForResult(intent, START_MOCK_BLUETOOTH);
    }

    // Sends fake bluetooth messages based upon the CSV string data from the bluetooth activity
    public void setUpMockBluetooth(List<String> mockStudents) {
        Log.i(TAG, "in setUpMockBluetooth");
        this.messageListener = new MockMessageListenerClass(this.realListener, mockStudents);
    }


    // Runs when the start button is clicked and displays the screen with all of the
    // other students which have overlapping courses.
    public void onStartClicked(View view) {
        Log.i(TAG, "In onStartClicked");
        toggleSearchStatus();
    }

    // Toggle the searching on/off
    private void toggleSearchStatus() {
        this.searching = !this.searching;
        Button searchButton = findViewById(R.id.searchButton);

        if (this.searching) {
            // Figure out the session the user wants to edit
            List<Session> sessionList = db.sessionDAO().getAll();
            Session newSession = null;
            if (sessionList.isEmpty()) {
                // No sessions already saved in the database, just use the timestamp as the current
                // name but mark it as unnamed for the future so it can be named when stopped

                // possibly can create a session factory in order to have more design patterns in our code
                // TODO
                String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
                newSession = new Session(db.sessionDAO().count(), timeStamp, false);
                setCurrentSession(newSession);
                db.sessionDAO().insert(newSession);
                updateStudentViews(currentFilter);
            } else {
                // Ask the user to pick a session from the list of sessions, or a new session
                pickSessionFromList();
            }

            searchButton.setText("Stop");
        } else {
            // Give the session a name if it is unnamed
            Session currSession = getCurrentSession();
            if (!currSession.getIsNamed()) {
                newSessionNameDialog();
            } else {
                setNoCurrentSession();
            }
            searchButton.setText("Start");
            clearStudentViews();
        }
    }

    //
    private void newSessionNameDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Name Session");

        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected;
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Session currSession = getCurrentSession();
                String newName = input.getText().toString();
                if (newName != null && !newName.equals("")) {
                    AppDatabase database = AppDatabase.singleton(getApplicationContext());
                    database.sessionDAO().update(newName, true, currSession.getId());
                }
                setNoCurrentSession();
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                setNoCurrentSession();
            }
        });

        builder.show();
    }

    // Creates an alert dialog which allows the user to select from the saved sessions, or to create a new
    // one
    private void pickSessionFromList() {
        // i hate my life
        List<String> sessionNames = new ArrayList<String>();
        for (Session s : db.sessionDAO().getAll()) {
            sessionNames.add(s.getName());
        }
        sessionNames.add("New Session");
        // IMPORTANT: I DON'T WANT TO COVER THIS EDGE CASE SO MAKE SURE THAT YOU DO NOT NAME A SESSION
        // "New Session" DURING THE DEMO PLEEEEAASSEEE

        final ArrayAdapter<String> adp = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, sessionNames);

        final Spinner sp = new Spinner(this);
        sp.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        sp.setAdapter(adp);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(sp);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Session newSession = null;
                AppDatabase database = AppDatabase.singleton(getApplicationContext());
                chosenNameResult = sp.getSelectedItem().toString();
                // Look at chosen name result and return the corresponding session
                if (chosenNameResult.equals("New Session")) {
                    String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
                    newSession = new Session(database.sessionDAO().count(), timeStamp, false);
                    database.sessionDAO().insert(newSession);
                }
                else {
                    // go through session list and find the session this name corresponds to
                    List<Session> sessions = database.sessionDAO().getAll();
                    for (Session s : sessions) {
                        if (s.getName().equals(chosenNameResult)) {
                            newSession = s;
                            break;
                        }
                    }
                }

                if (newSession == null) {
                    Log.i(TAG, "newSession is NULL!!");
                    return;
                }

                setCurrentSession(newSession);
                updateStudentViews(currentFilter);
            }
        });
        builder.create().show();
    }

    // Store the current session's id into shared preferences
    private void setCurrentSession(Session session) {
        SharedPreferences preferences = getSharedPreferences("BOAF_PREFERENCES", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("currentSession", session.sessionId);
        editor.apply();

        TextView text = findViewById(R.id.sessionNameText);
        text.setText(session.getName());
    }

    // Sets the current session as a negative number to signify that there is no current session
    private void setNoCurrentSession() {
        SharedPreferences preferences = getSharedPreferences("BOAF_PREFERENCES", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("currentSession", -1);
        editor.apply();

        TextView text = findViewById(R.id.sessionNameText);
        text.setText("No session active");
    }

    private Session getCurrentSession() {
        SharedPreferences preferences = getSharedPreferences("BOAF_PREFERENCES", MODE_PRIVATE);
        int id = preferences.getInt("currentSession", -1);
        if (id == -1) {
            return null;
        }
        return db.sessionDAO().get(id);
    }

    private void clearStudentViews() {
        studentRecyclerView = findViewById(R.id.students_view);

        studentLayoutManager = new LinearLayoutManager(this);
        studentRecyclerView.setLayoutManager(studentLayoutManager);

        // pass in empty lists so nothing is displayed
        studentViewAdapter = new StudentViewAdapter(new ArrayList<Student>(), new ArrayList<Integer>());
        studentRecyclerView.setAdapter(studentViewAdapter);
    }


    // Set up the real blue tooth listener to add people to the database
    // as messages are received.
    public void setUpRealBluetooth() {
        Log.i(TAG, "in setUpRealBluetooth");
        // Set up bluetooth
        this.realListener = new MessageListener() {
            @Override
            public void onFound(@NonNull Message message) {
                // Put stuff in the database
                Log.i(TAG, "Found a bluetooth message!");
                if (db != null) {
                    Log.i(TAG, "Found a bluetooth message, and am able to act upon it because db is set up.");
                    if (searching) {
                        Log.i(TAG, "Actively searching, so will check this message");
                        String student_data = new String(message.getContent());
                        addStudentCSVStringToDb(student_data);
                        // whenever receive a bluetooth message, update the student views
                        updateStudentViews(currentFilter);
                    } else {
                        Log.i(TAG, "Not actively searching, doing nothing with this message");
                    }
                } else {
                    Log.i(TAG, "Found a bluetooth message, and am not able to act upon it because db is not set up");
                }
            }

            @Override
            public void onLost(@NonNull Message message) {
                Log.i(TAG, "Bluetooth message lost: currently doing nothing with this.");
                // Does nothing atm
            }
        };
    }
}