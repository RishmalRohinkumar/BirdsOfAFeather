package com.example.birdsofafeatherteam14;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.birdsofafeatherteam14.model.IStudent;

import java.util.List;

public class StudentViewAdapter extends RecyclerView.Adapter<StudentViewAdapter.ViewHolder> {
    private final List<? extends IStudent> students;

    public StudentViewAdapter(List<? extends IStudent> students) {
        super();
        this.students = students;
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
        holder.setStudent(students.get(position));
    }

    @Override
    public int getItemCount() {
        return this.students.size();
    }

    public static class ViewHolder
            extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private final TextView studentNameView;
        private IStudent student;

        ViewHolder(View itemView) {
            super(itemView);
            this.studentNameView = itemView.findViewById(R.id.student_row_name);
            itemView.setOnClickListener(this);
        }

        public void setStudent(IStudent student) {
            this.student = student;
            this.studentNameView.setText(student.getName());
        }

        @Override
        public void onClick(View view) {
            Context context = view.getContext();
            Intent intent = new Intent(context, ViewUserActivity.class);
            intent.putExtra("student_id", this.student.getId());
            intent.putExtra("student_name", this.student.getName());
            intent.putExtra("student_picture", this.student.getPhoto());
            intent.putExtra("student_courses", this.student.getCourses().toArray());
            context.startActivity(intent);
        }
    }
}
