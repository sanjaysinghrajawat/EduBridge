package com.example.edubridge;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SearchFragment extends Fragment {

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TextView startFundRaising, userFullName;
        //logout
        Button btn_logout;
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        userFullName = view.findViewById(R.id.userFullName);
        btn_logout = view.findViewById(R.id.btn_logout);
        startFundRaising = view.findViewById(R.id.startFundRaising);

        //set text on profile
        FirebaseAuth auth;
        FirebaseUser user;
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        // Get the current user's ID
        String uid = user.getUid();
        // Get a reference to the "user_data" node for the current user
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users_data").child(uid);
        // Read the user's data from the database and set the userFullName TextView
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String firstName = dataSnapshot.child("firstName").getValue(String.class);
                    userFullName.setText(firstName);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("SearchFragment", "Database read error: " + databaseError.getMessage());
            }
        });


        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // shared preferences for logout
                SharedPreferences preferences = getActivity().getSharedPreferences("loginSharedPreferences", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("flag_login", false);
                editor.apply();
                Intent login = new Intent(getActivity(), login_using_number.class);
                startActivity(login);
                getActivity().finish();
            }
        });

        Intent fundRaising = new Intent(getContext(), StartFundRaiser.class);
        startFundRaising.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(fundRaising);
            }
        });
        return view;
    }
}