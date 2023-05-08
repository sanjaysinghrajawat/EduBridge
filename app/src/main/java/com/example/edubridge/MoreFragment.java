package com.example.edubridge;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MoreFragment extends Fragment {

    //ArrayList<MoreStudentRV> arrMoreStudents = new ArrayList<>();
    public MoreFragment() {
        // Required empty public constructor
    }
    MoreStudentRVAdapter adapter;
    RecyclerView rv_more;
    DatabaseReference database;
    ArrayList<MoreStudentRV> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_more, container, false);

        rv_more = view.findViewById(R.id.rv_more);
        database = FirebaseDatabase.getInstance().getReference("students");
        list  = new ArrayList<>();
        adapter = new MoreStudentRVAdapter(getContext(), list);
        rv_more.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_more.setAdapter(adapter);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    MoreStudentRV moreStudentRV = dataSnapshot.getValue(MoreStudentRV.class);
                    list.add(moreStudentRV);
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