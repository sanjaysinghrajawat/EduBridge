package com.example.edubridge;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class StartFundRaiser extends AppCompatActivity {

    private Button addDatabase;
    private EditText indexValue, sname, sdescription, raisedMoney, totalMoney;
    private DatabaseReference fundRaiserRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_fund_raiser);

        // Initialize views
        addDatabase = findViewById(R.id.addDatabase);
        indexValue = findViewById(R.id.indexValue);
        sname = findViewById(R.id.sname);
        sdescription = findViewById(R.id.sdescription);
        raisedMoney = findViewById(R.id.raisedMoney);
        totalMoney = findViewById(R.id.totalMoney);

        // Initialize Firebase database reference
        fundRaiserRef = FirebaseDatabase.getInstance().getReference("fundRaiserStudent");

        // Set click listener for addDatabase button
        addDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Get input values from EditText fields
                String indexValueText = indexValue.getText().toString().trim();
                String snameText = sname.getText().toString().trim();
                String sdescriptionText = sdescription.getText().toString().trim();
                String raisedMoneyText = raisedMoney.getText().toString().trim();
                String totalMoneyText = totalMoney.getText().toString().trim();

                // Create new MoreStudentHomeRV object
                FundRaiserHolder obj = new FundRaiserHolder(sdescriptionText, "", raisedMoneyText, snameText, totalMoneyText);

                // Save object to Firebase database
                fundRaiserRef.child(indexValueText).setValue(obj)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Clear EditText fields
                                indexValue.setText("");
                                sname.setText("");
                                sdescription.setText("");
                                raisedMoney.setText("");
                                totalMoney.setText("");

                                // Display success message
                                Toast.makeText(StartFundRaiser.this, "Fundraiser added successfully", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Display error message
                                Toast.makeText(StartFundRaiser.this, "Failed to add fundraiser: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}
