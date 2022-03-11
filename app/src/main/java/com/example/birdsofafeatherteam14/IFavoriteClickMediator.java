package com.example.birdsofafeatherteam14;

import android.app.Activity;
import android.view.View;

import com.example.birdsofafeatherteam14.model.db.Student;

// Defines an interface for a handler that deals with toggling on and off favorite status
public interface IFavoriteClickMediator {

    // Returns the updated student after toggling their favorite
    Student mediateFavoriteToggle(Activity activity, int checkboxId, Student student);
}
