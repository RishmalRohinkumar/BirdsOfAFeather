package com.example.birdsofafeatherteam14;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

public class MockBluetoothActivity extends AppCompatActivity {
    public ArrayList<String> messages = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock_bluetooth);
    }

    public void onEnterButtonClicked(View view) {
        EditText mock_message = findViewById(R.id.mock_message);
        messages.add(mock_message.getText().toString().replaceAll("\\n", "\n"));
        mock_message.setText("");
    }

    public void onBackButtonClicked(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putStringArrayListExtra("messages", messages);
        startActivity(intent);
    }
}