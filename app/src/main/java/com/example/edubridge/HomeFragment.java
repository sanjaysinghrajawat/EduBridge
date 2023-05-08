package com.example.edubridge;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

//    ArrayList<MoreStudentHomeRV> arrMoreStudentsHome = new ArrayList<>();
    public HomeFragment() {
        // Required empty public constructor
    }
    MoreStudentHomeRVAdapter adapter;
    RecyclerView rv_horizontalHome;
    DatabaseReference database;
    ArrayList<MoreStudentHomeRV> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        rv_horizontalHome = view.findViewById(R.id.rv_horizontalHome);
        database = FirebaseDatabase.getInstance().getReference("students");
        list  = new ArrayList<>();
        adapter = new MoreStudentHomeRVAdapter(getContext(), list);
        rv_horizontalHome.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rv_horizontalHome.setAdapter(adapter);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    MoreStudentHomeRV moreStudentHomeRV = dataSnapshot.getValue(MoreStudentHomeRV.class);
                    list.add(moreStudentHomeRV);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }
}