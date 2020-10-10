package com.example.messengerapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.messengerapp.Models.Chat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.Holder> {

    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;

    private Context context;
    private List<Chat> chats;
    private String imageUrl;

    FirebaseUser firebaseUser;

    public MessageAdapter(Context context, List<Chat> chats, String imageUrl) {
        this.context = context;
        this.chats = chats;
        this.imageUrl = imageUrl;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(viewType == MSG_TYPE_RIGHT){
            view = LayoutInflater.from(context).inflate(R.layout.chat_item_right,parent,false);
        }
        else {
            view = LayoutInflater.from(context).inflate(R.layout.chat_item_left,parent,false);
        }
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Chat chat = chats.get(position);

        holder.show_message.setText(chat.getMessage());

        if(imageUrl.equals("default")){
            holder.chat_profile_image.setImageResource(R.mipmap.ic_launcher);
        }
        else {
            Picasso.get().load(imageUrl).into(holder.chat_profile_image);
        }
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    public static class Holder extends RecyclerView.ViewHolder {

        CircleImageView chat_profile_image;
        TextView show_message;

        public Holder(@NonNull View itemView) {
            super(itemView);
            chat_profile_image = itemView.findViewById(R.id.chat_profile_image);
            show_message = itemView.findViewById(R.id.show_message);
        }
    }

    @Override
    public int getItemViewType(int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(chats.get(position).getSender().equals(firebaseUser.getUid())){
            return MSG_TYPE_RIGHT;
        }
        else {
            return MSG_TYPE_LEFT;
        }
    }
}
