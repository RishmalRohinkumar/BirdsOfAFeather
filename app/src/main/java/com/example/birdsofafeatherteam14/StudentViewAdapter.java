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

import java.util.List;

public class StudentViewAdapter extends RecyclerView.Adapter<StudentViewAdapter.ViewHolder> {
    private final List<? extends Student> students;
    private final List<Integer> sharedCourses;

    public StudentViewAdapter(List<? extends Student> students, List<Integer> sharedCourses) {
        super();
        this.students = students;
        this.sharedCourses = sharedCourses;
    }

    @NonNull
    @Override
    public StudentViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.student_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewAdapter.ViewHolder holder, int position) {
        holder.setStudent(students.get(position), sharedCourses.get(position));
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
        private Student student;
        private AppDatabase db;

        ViewHolder(View itemView) {
            super(itemView);
            this.studentNameView = itemView.findViewById(R.id.student_row_name);
            this.studentImageView = itemView.findViewById(R.id.row_view_picture);
            itemView.setOnClickListener(this);

            CheckBox fav = (CheckBox) itemView.findViewById(R.id.starStudentList);

            if(student.getFavourite()) {
                fav.setChecked(true);
            } else {
                fav.setChecked(false);
            }
        }

        public void onFavRowClick(View itemView) {
            CheckBox favourite = (CheckBox) itemView.findViewById(R.id.starStudentList);
            boolean fav_state = favourite.isChecked();
            Context context = itemView.getContext();
            db = AppDatabase.singleton(context);
            int studentId = student.getId();

            if(fav_state == true){
                db.studentDAO().update(true, studentId);
                favourite.setChecked(true);
                Toast.makeText(context,
                        "Saved to Favorites", Toast.LENGTH_LONG).show();
            } else {
                db.studentDAO().update(false, studentId);
                favourite.setChecked(false);
                Toast.makeText(context,
                        "Removed from Favorites", Toast.LENGTH_LONG).show();
            }
        }

        public void setStudent(Student student, Integer sharedCourses) {
            this.student = student;
            this.studentNameView.setText(student.getName() + " (" + sharedCourses.toString() + ")");
            Picasso.get().load(student.getPhoto()).into(this.studentImageView);
        }

        @Override
        public void onClick(View view) {
            Context context = view.getContext();
            Intent intent = new Intent(context, ViewUserActivity.class);
            intent.putExtra("student_id", this.student.getId());
            context.startActivity(intent);
        }
    }
}
