package com.example.messengerapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.messengerapp.Models.User;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.Holder> {

    List<User> userList;
    Context context;

    public PeopleAdapter(List<User> userList, Context context) {
        this.userList = userList;
        this.context = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.people_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int position) {
        if (userList.get(position).getImageUrl().equals("default")) {
            holder.profile_image_people.setImageResource(R.mipmap.ic_launcher);
        } else {
            Picasso.get().load(userList.get(position).getImageUrl()).into(holder.profile_image_people);
        }

        holder.userNamePeople.setText(userList.get(position).getUsername());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MessengerActivity.class);
                intent.putExtra("userId", userList.get(position).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        CircleImageView profile_image_people;
        TextView userNamePeople;

        public Holder(@NonNull View itemView) {
            super(itemView);
            profile_image_people = itemView.findViewById(R.id.profile_image_people);
            userNamePeople = itemView.findViewById(R.id.userNamePeople);

        }
    }
}
