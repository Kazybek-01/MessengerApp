package com.example.messengerapp.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.messengerapp.Models.User;
import com.example.messengerapp.PeopleAdapter;
import com.example.messengerapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PeopleFragment extends Fragment {

    DatabaseReference reference;
    List<User> userList = new ArrayList<>();
    PeopleAdapter adapter;
    RecyclerView recyclerViewPeople;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_people, container, false);
        userList.clear();
        reference = FirebaseDatabase.getInstance().getReference("Users");

        recyclerViewPeople = view.findViewById(R.id.recyclerViewPeople);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    userList.add(dataSnapshot.getValue(User.class));
                }
                recyclerViewPeople.setLayoutManager(new LinearLayoutManager(getContext()));
                adapter = new PeopleAdapter(userList, getContext());
                recyclerViewPeople.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
}