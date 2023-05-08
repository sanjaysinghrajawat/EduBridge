    package com.example.edubridge;

    import androidx.annotation.NonNull;
    import androidx.appcompat.app.AppCompatActivity;

    import android.content.Intent;
    import android.content.SharedPreferences;
    import android.os.Bundle;
    import android.text.Editable;
    import android.text.TextWatcher;
    import android.view.View;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.ProgressBar;
    import android.widget.TextView;
    import android.widget.Toast;

    import com.google.android.gms.tasks.OnCompleteListener;
    import com.google.android.gms.tasks.Task;
    import com.google.firebase.FirebaseException;
    import com.google.firebase.auth.AuthResult;
    import com.google.firebase.auth.FirebaseAuth;
    import com.google.firebase.auth.PhoneAuthCredential;
    import com.google.firebase.auth.PhoneAuthProvider;
    import com.google.firebase.database.DatabaseReference;
    import com.google.firebase.database.FirebaseDatabase;

    import java.util.concurrent.TimeUnit;

    public class OTP_verify extends AppCompatActivity {

        EditText inputotp1, inputotp2, inputotp3, inputotp4, inputotp5, inputotp6;
        TextView set_phn_number;;
        Button otp_submit;
        String backEndOtp;
        ProgressBar progess_bar_verify_otp;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_otp_verify);

            inputotp1 = findViewById(R.id.inputotp1);
            inputotp2 = findViewById(R.id.inputotp2);
            inputotp3 = findViewById(R.id.inputotp3);
            inputotp4 = findViewById(R.id.inputotp4);
            inputotp5 = findViewById(R.id.inputotp5);
            inputotp6 = findViewById(R.id.inputotp6);
            otp_submit = findViewById(R.id.otp_submit);
            progess_bar_verify_otp = findViewById(R.id.progess_bar_verify_otp);

            // receive phn number from previous screen and show to the this screen
            set_phn_number = findViewById(R.id.set_phn_number);
            String phn_number = getIntent().getStringExtra("mobileNumber").toString();
            set_phn_number.setText(String.format("91-%s", phn_number));

            // get otp
            backEndOtp = getIntent().getStringExtra("backEndOtp");

            // after click on submit btn
            otp_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!inputotp1.getText().toString().trim().isEmpty() &&
                            !inputotp2.getText().toString().trim().isEmpty() &&
                            !inputotp3.getText().toString().trim().isEmpty() &&
                            !inputotp4.getText().toString().trim().isEmpty() &&
                            !inputotp5.getText().toString().trim().isEmpty() &&
                            !inputotp6.getText().toString().trim().isEmpty())
                    {
                        // Toast.makeText(OTP_verify.this, "OTP verify", Toast.LENGTH_SHORT).show();
                        // put user enter code in string
                        String userEnterOtpCode = inputotp1.getText().toString()+inputotp2.getText().toString()+inputotp3.getText().toString()+inputotp4.getText().toString()+inputotp5.getText().toString()+inputotp6.getText().toString();
                        if(backEndOtp != null)
                        {
                            progess_bar_verify_otp.setVisibility(View.VISIBLE);
                            otp_submit.setVisibility(View.INVISIBLE);

                            PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(backEndOtp, userEnterOtpCode);
                            FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            progess_bar_verify_otp.setVisibility(View.GONE);
                                            otp_submit.setVisibility(View.VISIBLE);

                                            // successful login
                                            if(task.isSuccessful())
                                            {
    //                                            // shared preferences for login
    //                                            SharedPreferences preferences = getSharedPreferences("loginSharedPreferences", MODE_PRIVATE);
    //                                            SharedPreferences.Editor editor = preferences.edit();
    //                                            editor.putBoolean("flag_login", true);
    //                                            editor.apply();
    //
    //                                            Intent home = new Intent(getApplicationContext(), MainActivity.class);
    //                                            home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
    //                                            startActivity(home);

                                                Intent signUp = new Intent(getApplicationContext(), SignUp.class);
                                                startActivity(signUp);
                                            }
                                            else
                                            {
                                                Toast.makeText(OTP_verify.this, "Please Enter Correct OTP", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                        else
                        {
                            Toast.makeText(OTP_verify.this, "Error...Please Check Internet", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(OTP_verify.this, "Please Enter All Numbers", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            // cursor move automatically when input is filled
            cursorOtpMove();

            findViewById(R.id.resend_otp).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            "+91" + set_phn_number.getText().toString(),
                            60,
                            TimeUnit.SECONDS,
                            OTP_verify.this,
                            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                @Override
                                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                                }

                                @Override
                                public void onVerificationFailed(@NonNull FirebaseException e) {

                                    Toast.makeText(OTP_verify.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onCodeSent(@NonNull String newBackEndOtp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                    backEndOtp = newBackEndOtp;
                                    Toast.makeText(OTP_verify.this, "OTP send Successfully", Toast.LENGTH_SHORT).show();
                                }
                            }
                    );
                }
            });
        }

        private void cursorOtpMove() {
            inputotp1.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(!s.toString().trim().isEmpty())
                    {
                        inputotp2.requestFocus();
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            inputotp2.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(!s.toString().trim().isEmpty())
                    {
                        inputotp3.requestFocus();
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            inputotp3.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(!s.toString().trim().isEmpty())
                    {
                        inputotp4.requestFocus();
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            inputotp4.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(!s.toString().trim().isEmpty())
                    {
                        inputotp5.requestFocus();
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            inputotp5.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(!s.toString().trim().isEmpty())
                    {
                        inputotp6.requestFocus();
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
    }