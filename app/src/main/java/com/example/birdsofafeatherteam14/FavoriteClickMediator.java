package com.example.birdsofafeatherteam14;

import android.app.Activity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.birdsofafeatherteam14.model.db.AppDatabase;
import com.example.birdsofafeatherteam14.model.db.Student;

public class FavoriteClickMediator implements IFavoriteClickMediator{
    AppDatabase db;
    FavoriteClickMediator(AppDatabase db) {
        this.db = db;
    }

    @Override
    public Student mediateFavoriteToggle(Activity activity, int checkboxId, Student student) {
        CheckBox favourite = (CheckBox) activity.findViewById(checkboxId);
        boolean fav_state = favourite.isChecked();

        student.isFav = fav_state;

        if(fav_state){
            db.studentDAO().update(true, student.getId());
            student = db.studentDAO().get(student.getId());
            favourite.setChecked(true);
            Toast.makeText(activity,
                    "Saved to Favorites", Toast.LENGTH_LONG).show();
        } else {
            db.studentDAO().update(false, student.getId());
            student = db.studentDAO().get(student.getId());
            favourite.setChecked(false);
            Toast.makeText(activity,
                    "Removed from Favorites", Toast.LENGTH_LONG).show();
        }

        return student;
    }
}
