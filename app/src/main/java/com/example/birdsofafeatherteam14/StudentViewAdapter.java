package com.example.birdsofafeatherteam14;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.birdsofafeatherteam14.model.db.AppDatabase;
import com.example.birdsofafeatherteam14.model.db.Student;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class StudentViewAdapter extends RecyclerView.Adapter<StudentViewAdapter.ViewHolder>
                                implements ExitViewUserSubject {
    private final List<? extends Student> students;
    private final List<Integer> sharedCourses;
    public ViewHolder viewHolder;

    private List<ExitViewUserObserver> observerList;

    private MainActivity ma;

    public StudentViewAdapter(List<? extends Student> students, List<Integer> sharedCourses, MainActivity ma) {
        super();
        this.students = students;
        this.sharedCourses = sharedCourses;
        this.observerList = new ArrayList<>();
        this.ma = ma;
    }

    @NonNull
    @Override
    public StudentViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.student_row, parent, false);
        viewHolder = new ViewHolder(view, this, ma);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewAdapter.ViewHolder holder, int position) {
        holder.setStudent(students.get(position), sharedCourses.get(position));
    }

    @Override
    public void register(ExitViewUserObserver ob) {
        this.observerList.add(ob);
    }

    @Override
    public void unregister(ExitViewUserObserver ob) {
        this.observerList.remove(ob);
    }

    @Override
    public void notifyObservers() {
        for (ExitViewUserObserver ob : this.observerList) {
            ob.onExitViewUser();
        }
    }

    @Override
    public int getItemCount() {
        return this.students.size();
    }

    public static class ViewHolder
            extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private final TextView studentNameView;
        private final ImageView studentImageView;
        private final CheckBox studentFavorite;
        private Student student;
        private AppDatabase db;

        private ExitViewUserSubject sb;
        private MainActivity ma;

        ViewHolder(View itemView, ExitViewUserSubject sb, MainActivity ma) {
            super(itemView);
            this.studentNameView = itemView.findViewById(R.id.student_row_name);
            this.studentImageView = itemView.findViewById(R.id.row_view_picture);
            this.studentFavorite = itemView.findViewById(R.id.starStudentList);
            itemView.setOnClickListener(this);
            this.sb = sb;
            this.ma = ma;
        }

        public void setStudent(Student student, Integer sharedCourses) {
            this.student = student;
            this.studentNameView.setText(student.getName() + " (" + sharedCourses.toString() + ")");
            Picasso.get().load(student.getPhoto()).into(this.studentImageView);
            this.studentFavorite.setChecked(student.getFavourite());
        }

        public void favClicked(View itemView) {
            boolean fav_state = studentFavorite.isChecked();
            int studentId = student.getId();
            Context context = itemView.getContext();
            db = AppDatabase.singleton(context);

            if(fav_state){
                db.studentDAO().update(true, studentId);
                this.student = db.studentDAO().get(studentId);
                studentFavorite.setChecked(true);
                Toast.makeText(context,
                        "Saved to Favorites", Toast.LENGTH_LONG).show();
            } else {
                db.studentDAO().update(false, studentId);
                this.student = db.studentDAO().get(studentId);
                studentFavorite.setChecked(false);
                Toast.makeText(context,
                        "Removed from Favorites", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onClick(View view) {
            Context context = view.getContext();
            Intent intent = new Intent(context, ViewUserActivity.class);
            intent.putExtra("student_id", this.student.getId());
           // context.startActivity(intent);
            ma.startActivityForResult(intent, MainActivity.START_VIEW_USER);

            sb.notifyObservers();
        }


    }
}
