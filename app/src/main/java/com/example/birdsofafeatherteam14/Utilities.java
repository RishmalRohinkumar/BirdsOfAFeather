package com.example.birdsofafeatherteam14;

import android.app.Activity;
import android.app.AlertDialog;

// Class for helpful functions used throughout the program
public class Utilities {
    // Creates an error dialog box
    public static void showAlert(Activity activity, String message) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(activity);

        alertBuilder
                .setTitle("Alert!")
                .setMessage(message)
                .setPositiveButton("Ok", (dialog, id) -> {
                    dialog.cancel();
                })
                .setCancelable(true);

        AlertDialog alertDialog = alertBuilder.create();
        alertDialog.show();
    }
}
