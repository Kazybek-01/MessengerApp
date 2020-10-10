package com.example.messengerapp.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.messengerapp.ExampleActivity;
import com.example.messengerapp.MainActivity;
import com.example.messengerapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.rengwuxian.materialedittext.MaterialEditText;

public class LoginFragment extends Fragment {

    MaterialEditText emailEnter, password;
    Button login_btn;
    FirebaseAuth auth;
    ProgressBar progressBarLogin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        emailEnter = view.findViewById(R.id.emailEnter);
        password = view.findViewById(R.id.password);
        login_btn = view.findViewById(R.id.login_btn);
        progressBarLogin = view.findViewById(R.id.progressBarLogin);
        progressBarLogin.setVisibility(View.GONE);

        auth = FirebaseAuth.getInstance();  //инициализация надо делать до листенера

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBarLogin.setVisibility(View.VISIBLE);
                String txt_email = emailEnter.getText().toString();
                String txt_password = password.getText().toString();

                if(TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)){
                    Toast.makeText(getContext(),"Все поля должны быть заполнены",Toast.LENGTH_SHORT).show();
                }
                else {
                    auth.signInWithEmailAndPassword(txt_email,txt_password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        progressBarLogin.setVisibility(View.GONE);
                                        Toast.makeText(getContext(),"Вы успешно авторизовались",Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getActivity(), MainActivity.class));
                                        getActivity().finish();
                                    }
                                }
                            });
                }
            }
        });

        return view;
    }
}