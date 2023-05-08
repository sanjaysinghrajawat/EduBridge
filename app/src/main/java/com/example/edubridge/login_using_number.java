package com.example.edubridge;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class login_using_number extends AppCompatActivity {

    Button btn_continue;
    EditText editTextPhone;
    ProgressBar progress_bar_send_otp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_using_number);

        btn_continue = findViewById(R.id.btn_continue);
        editTextPhone = findViewById(R.id.editTextPhone);
        progress_bar_send_otp = findViewById(R.id.progress_bar_send_otp);

        // continue button click event
        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!editTextPhone.getText().toString().trim().isEmpty()) // empty nhi hona chahiye
                {
                    if(editTextPhone.getText().toString().trim().length() == 10) // length 10 hona chahiye fir OTP verification Screen pr jayega
                    {
                        progress_bar_send_otp.setVisibility(View.VISIBLE);
                        btn_continue.setVisibility(View.INVISIBLE);

                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                "+91" + editTextPhone.getText().toString(),
                                60,
                                TimeUnit.SECONDS,
                                login_using_number.this,
                                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                    @Override
                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                        progress_bar_send_otp.setVisibility(View.GONE);
                                        btn_continue.setVisibility(View.VISIBLE);
                                    }

                                    @Override
                                    public void onVerificationFailed(@NonNull FirebaseException e) {
                                        progress_bar_send_otp.setVisibility(View.GONE);
                                        btn_continue.setVisibility(View.VISIBLE);
                                        Toast.makeText(login_using_number.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onCodeSent(@NonNull String backEndOtp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                        progress_bar_send_otp.setVisibility(View.GONE);
                                        btn_continue.setVisibility(View.VISIBLE);
                                        Intent otpActivity = new Intent(getApplicationContext(), OTP_verify.class);
                                        otpActivity.putExtra("mobileNumber", editTextPhone.getText().toString());
                                        otpActivity.putExtra("backEndOtp", backEndOtp);
                                        startActivity(otpActivity);
                                    }
                                }
                        );

                    }
                    else
                    {
                        Toast.makeText(login_using_number.this, "Please Enter Correct Number", Toast.LENGTH_SHORT).show();  // length kamm hui toh show kre
                    }
                }
                else
                {
                    Toast.makeText(login_using_number.this, "Enter Mobile Number", Toast.LENGTH_SHORT).show();  // number dala hi nahi toh
                }
            }
        });

    }
}