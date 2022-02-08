package com.example.birdsofafeatherteam14;

import static com.example.birdsofafeatherteam14.Utilities.showAlert;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class ProfileNameActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_name);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account == null) {
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
        } else {
            setTextToFirstName(account);
        }
    }

    private void setTextToFirstName(GoogleSignInAccount account) {
        TextView textView = (TextView) findViewById(R.id.editTextTextPersonName);
        String fullName = account.getDisplayName();
        if (fullName != null) {
            String[] splitName = fullName.split(" ");
            String firstName = "";
            if (splitName.length >= 1) {
                firstName = splitName[0];
            }
            textView.setText(firstName);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            setTextToFirstName(completedTask.getResult(ApiException.class));
        } catch (ApiException e) {
            // the text input remains empty
        }
    }

    public void onEnterNameButtonClicked(View view) {
        EditText enterNameEditText = findViewById(R.id.editTextTextPersonName);
        String name = enterNameEditText.getText().toString();
        if (name.equals("")) {
            // Name is invalid, mark appropriately on the UI
            //TextView errorTextView = findViewById(R.id.invalid_name_textview);
            //errorTextView.setText("You must enter a name");
            showAlert(this, "You must enter a name!");
        } else {
            // Go to the next activity, passing through the name as an extra
            Intent intent = new Intent(this, ProfilePictureActivity.class);
            intent.putExtra("student_name", name);
            startActivity(intent);
        }
    }
}