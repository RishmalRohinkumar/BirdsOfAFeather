package com.example.birdsofafeatherteam14;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.birdsofafeatherteam14.model.db.AppDatabase;
import com.example.birdsofafeatherteam14.model.db.Student;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentViewAdapter extends RecyclerView.Adapter<StudentViewAdapter.ViewHolder>
                                implements ExitViewUserSubject {
    private final List<? extends Student> students;
    private final List<Integer> sharedCourses;
    public ViewHolder viewHolder;

    private List<ExitViewUserObserver> observerList;

    private MainActivity ma;

    public Map<View, Student> map;

    public StudentViewAdapter(List<? extends Student> students, List<Integer> sharedCourses, MainActivity ma) {
        super();
        this.students = students;
        this.sharedCourses = sharedCourses;
        this.observerList = new ArrayList<>();
        this.ma = ma;

        this.map = new HashMap<>();
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
        this.map.put(holder.itemView, students.get(position));
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
        private ImageView studentWaveImage;
        private final CheckBox studentFavorite;
        private Student student;
        private AppDatabase db;

        private ExitViewUserSubject sb;
        private MainActivity ma;

        ViewHolder(View itemView, ExitViewUserSubject sb, MainActivity ma) {
            super(itemView);
            this.studentNameView = itemView.findViewById(R.id.student_row_name);
            this.studentImageView = itemView.findViewById(R.id.row_view_picture);
            this.studentWaveImage = itemView.findViewById(R.id.row_wave_img);
            this.studentFavorite = itemView.findViewById(R.id.starStudentList);
            itemView.setOnClickListener(this);
            this.studentFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    IFavoriteClickMediator mediator = new FavoriteClickMediator(AppDatabase.singleton(ma));
                    student = mediator.mediateFavoriteToggle(ma, studentFavorite, student);
                }
            });
            this.sb = sb;
            this.ma = ma;
        }

        public void setStudent(Student student, Integer sharedCourses) {
            this.student = student;
            this.studentNameView.setText(student.getName() + " (" + sharedCourses.toString() + ")");
            Picasso.get().load(student.getPhoto()).into(this.studentImageView);
            if (this.student.wave) {
                this.studentWaveImage.setVisibility(View.VISIBLE);
            } else {
                this.studentWaveImage.setVisibility(View.INVISIBLE);
            }
            this.studentFavorite.setChecked(student.getFavourite());
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
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
