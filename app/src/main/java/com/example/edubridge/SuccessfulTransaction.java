package com.example.edubridge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SuccessfulTransaction extends AppCompatActivity {

    Button home_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successful_transaction);

        home_btn = findViewById(R.id.home_btn);
        Intent home = new Intent(SuccessfulTransaction.this, MainActivity.class);

        home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(home);
                finish();
            }
        });
    }
}