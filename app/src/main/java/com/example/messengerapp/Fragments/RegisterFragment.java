package com.example.messengerapp.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.messengerapp.Models.User;
import com.example.messengerapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;


public class RegisterFragment extends Fragment {

    MaterialEditText username, email, password;
    Button btn_register;
    ProgressBar progressBarRegister;

    //нужен для авторзации (регистрации)
    FirebaseAuth auth;

    //нужен для указания места, где будет храниться наш объект в базе данных
    DatabaseReference reference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_register, container, false);

        username = view.findViewById(R.id.username);
        email = view.findViewById(R.id.emailEnter);
        password = view.findViewById(R.id.password);
        btn_register = view.findViewById(R.id.login_btn);
        progressBarRegister = view.findViewById(R.id.progressBarRegister);
        progressBarRegister.setVisibility(View.GONE);

        auth = FirebaseAuth.getInstance();

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBarRegister.setVisibility(View.VISIBLE);
                String txt_username = username.getText().toString();
                String txt_email = email.getText().toString();
                String txt_password = password.getText().toString();

                if(TextUtils.isEmpty(txt_username) || TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)){
                    Toast.makeText(getContext(),"Заполните все поля", Toast.LENGTH_SHORT).show();
                }
                else if (txt_password.length() < 6){
                    Toast.makeText(getContext(),"Пароль должен содержать минимум 6 символов", Toast.LENGTH_SHORT).show();
                }
                else {
                    register(txt_username,txt_email,txt_password);
                }
            }
        });

        return view;
    }
    public void register (final String username, final String email, String password){

        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){    //если он успешно зарегался
                            progressBarRegister.setVisibility(View.GONE);
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            String userId = firebaseUser.getUid();

                            reference = FirebaseDatabase.getInstance().getReference("Users")
                                    .child(userId);

//                            HashMap<String,String> map = new HashMap<>();
//                            map.put("id",userId);
//                            map.put("username",username);
//                            map.put("email",email);

                            User user = new User();
                            user.setId(userId);
                            user.setUsername(username);
                            user.setEmail(email);
                            user.setImageUrl("default");


                            reference.setValue(user)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(getContext(),"Регистрация прошла успешно",Toast.LENGTH_SHORT).show();
                                                ViewPager layout = (ViewPager) getActivity().findViewById(R.id.view_pager);
                                                layout.setCurrentItem(0);
                                            }
                                        }
                                    });
                        }
                    }
                });

    }
}