package com.example.edubridge;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {
    Button btn_submit;
    EditText firstName, address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users_data");

        firstName = findViewById(R.id.firstName);
        address = findViewById(R.id.address);
        btn_submit = findViewById(R.id.btn_submit);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fname = firstName.getText().toString();
                String edtAddress = address.getText().toString();

                // add to realtime database and authentication
                UserModalClass userModalClass = new UserModalClass(fname, edtAddress);
                /*/auth.createUserWithEmailAndPassword(fname, edtAddress)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    reference.child(auth.getUid()).setValue(userModalClass);
                                }
                                else {
                                    Toast.makeText(SignUp.this, "Error Occur", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(SignUp.this, "Saved Successful", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignUp.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                });*/

                reference.child(auth.getUid()).setValue(userModalClass)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(SignUp.this, "Data added", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(SignUp.this, "Task Successful", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(SignUp.this, "Task Unsuccessful", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                // shared preferences for login
                SharedPreferences preferences = getSharedPreferences("loginSharedPreferences", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("flag_login", true);
                editor.apply();

                Intent home = new Intent(getApplicationContext(), MainActivity.class);
                home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(home);
            }
        });
    }
}