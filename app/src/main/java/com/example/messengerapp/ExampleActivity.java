package com.example.messengerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;

public class ExampleActivity extends AppCompatActivity {
//
//    MaterialEditText username, email, password;
//    Button btn_register;
//
//    //нужен для авторзации (регистрации)
//    FirebaseAuth auth;
//
//    //нужен для указания места, где будет храниться наш объект в базе данных
//    DatabaseReference reference;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_register);
//
////        Toolbar toolbar = findViewById(R.id.toolbar);
////        setSupportActionBar(toolbar);
////        getSupportActionBar().setTitle("Регистрация");
////        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        username = findViewById(R.id.username);
//        email = findViewById(R.id.email);
//        password = findViewById(R.id.password);
//        btn_register = findViewById(R.id.register_btn);
//
//        auth = FirebaseAuth.getInstance();
//
//        btn_register.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String txt_username = username.getText().toString();
//                String txt_email = email.getText().toString();
//                String txt_password = password.getText().toString();
//
//                if(TextUtils.isEmpty(txt_username) || TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)){
//                    Toast.makeText(MainActivity.this,"Заполните все поля", Toast.LENGTH_SHORT).show();
//                }
//                else if (txt_password.length() < 6){
//                    Toast.makeText(MainActivity.this,"Пароль должен содержать минимум 6 символов", Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    register(txt_username,txt_email,txt_password);
//                }
//            }
//        });
//
//    }
//    public void register (final String username, final String email, String password){
//
//        auth.createUserWithEmailAndPassword(email,password)
//                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if(task.isSuccessful()){    //если он успешно зарегался
//                            FirebaseUser firebaseUser = auth.getCurrentUser();
//                            String userId = firebaseUser.getUid();
//
//                            reference = FirebaseDatabase.getInstance().getReference("Users")
//                                    .child(userId);
//
//                            HashMap<String,String> map = new HashMap<>();
//                            map.put("id",userId);
//                            map.put("username",username);
//                            map.put("email",email);
//
//                            reference.setValue(map)
//                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task<Void> task) {
//                                            if(task.isSuccessful()){
//                                                Toast.makeText(MainActivity.this,"Регистрация прошла успешно",Toast.LENGTH_SHORT).show();
//                                            }
//                                        }
//                                    });
//                        }
//                    }
//                });
//
//    }
}