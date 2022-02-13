package com.example.birdsofafeatherteam14;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.function.Consumer;

import com.example.birdsofafeatherteam14.model.db.Course;
import com.example.birdsofafeatherteam14.StudentViewAdapter.ViewHolder.*;

public class CoursesViewAdapter extends RecyclerView.Adapter<CoursesViewAdapter.ViewHolder> {
    private final List<Course> courses;
    //private final Consumer<Course> onCourseRemove;


    public CoursesViewAdapter(List<Course> courses) {
        super();
        this.courses = courses;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.courses_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setCourse(courses.get(position));

    }

    @Override
    public int getItemCount() {
        return this.courses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView courseTextView;
        private Course course;

        ViewHolder(View itemView) {
            super(itemView);
            this.courseTextView = itemView.findViewById(R.id.courses_row_text);
        }

        public void setCourse(Course course) {
            this.course = course;
            this.courseTextView.setText(course.getCourse());
        }


    }
}
