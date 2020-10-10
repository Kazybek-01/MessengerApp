package com.example.messengerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.messengerapp.Models.Chat;
import com.example.messengerapp.Models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessengerActivity extends AppCompatActivity {

    CircleImageView profilePhoto;
    TextView username;
    FirebaseUser firebaseUser;
    DatabaseReference reference;
    EditText message;
    ImageButton btnSend;
    Intent intent;
    List<Chat> chats;
    RecyclerView recyclerMessages;
    MessageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);

        Toolbar toolbar = findViewById(R.id.toolbar_chat);
        setSupportActionBar(toolbar); //УКАЗЫВАЕМ ОСНОВНОЙ ТУУЛБАР
        getSupportActionBar().setTitle(""); //УКАЗЫВАЕМ ЧТОБЫ ОСНОВНОЙ ТЕКСТ ИСЧЕЗ
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        profilePhoto = findViewById(R.id.circleImageViewProfile);
        username = findViewById(R.id.username_chat);
        message = findViewById(R.id.text_send);
        btnSend = findViewById(R.id.btn_send);
        recyclerMessages = findViewById(R.id.recyclerView_message);
        recyclerMessages.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        manager.setStackFromEnd(true);
        recyclerMessages.setLayoutManager(manager);

        intent = getIntent();
        final String userId = intent.getStringExtra("userId");

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(userId);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                username.setText(user.getUsername());
                if(user.getImageUrl().equals("default")){
                    profilePhoto.setImageResource(R.mipmap.ic_launcher);
                }
                else {
                    Picasso.get().load(user.getImageUrl()).into(profilePhoto);
                }

                readMessages(firebaseUser.getUid(), userId, user.getImageUrl());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = message.getText().toString();
                if(!msg.equals("")){
                    sendMessage(firebaseUser.getUid(),userId,msg);
                }
                else {
                    Toast.makeText(MessengerActivity.this,"Вы не можете отправить пустое сообщение",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void sendMessage(String sender, String reciver, String messageText){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        HashMap<String,String> map = new HashMap<>();
        map.put("sender",sender);
        map.put("reciver",reciver);
        map.put("message",messageText);

        reference.child("Chats").push().setValue(map);  //push соблюдает порядок и очередность сообщений

        message.setText(""); //после каждой отправки сообщений edit text становится пустым
    }
    public void readMessages(final String myId, final  String userId, final String imageUrl){
        chats = new ArrayList<>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chats.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Chat chat = dataSnapshot.getValue(Chat.class);
                    if(chat.getReciver().equals(myId) && chat.getSender().equals(userId) ||
                    chat.getReciver().equals(userId) && chat.getSender().equals(myId)){
                        chats.add(chat);
                    }
                }
                adapter = new MessageAdapter(MessengerActivity.this,chats,imageUrl);
                recyclerMessages.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MessengerActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}