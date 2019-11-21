package com.example.kamstrup_site_specification.Sigin_Firebase;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kamstrup_site_specification.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {

    private EditText register_email;
    private EditText register_password;
    private FirebaseAuth register_auth;
    private Button register_button;
    private Button goto_signin_button;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_registration);

        setViews();


    }

    private void setViews(){
        register_email = (EditText)findViewById(R.id.registration_enter_email);
        register_password = (EditText)findViewById(R.id.registration_enter_password);
        register_button = (Button)findViewById(R.id.register_button);
        goto_signin_button =(Button)findViewById(R.id.registration_login_button);

        register_auth = FirebaseAuth.getInstance();

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == register_button){
                    RegisterUser();
                }
            }
        });

        goto_signin_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == goto_signin_button){
                    Context context = getApplicationContext();
                    Class destination = Signin.class;
                    Intent intent = new Intent(context,destination);
                    startActivity(intent);
                }
            }
        });
    }


    public void RegisterUser(){
        String email = register_email.getText().toString().trim();
        String password = register_password.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            Toast.makeText(this, "E-mail address is missing", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Password is missing", Toast.LENGTH_SHORT).show();
        }

        register_auth
                .createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        try {
                            if (task.isSuccessful()){
                                //User is registered succesfully
                                Toast.makeText(Register.this, "You have been registered", Toast.LENGTH_SHORT).show();
                                finish();

                                Context context = getApplicationContext();
                                Class destination = Signin.class;

                                Intent intent = new Intent(context,destination);
                                startActivity(intent);
                            }else {
                                Toast.makeText(Register.this, "Please try again", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
    }




}
