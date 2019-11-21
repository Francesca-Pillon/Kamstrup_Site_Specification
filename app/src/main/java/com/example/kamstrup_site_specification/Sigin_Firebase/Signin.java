package com.example.kamstrup_site_specification.Sigin_Firebase;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.PrimaryKey;

import com.example.kamstrup_site_specification.MainActivity;
import com.example.kamstrup_site_specification.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Signin extends AppCompatActivity {

    //Initializing fields
    private EditText signin_email;
    private EditText signin_password;
    private FirebaseAuth signin_auth;
    private FirebaseUser current_user;
    private Button signin_button;
    private Button goto_register_button;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_main);

        setViews();

    }

    private void setViews(){
        signin_email = (EditText)findViewById(R.id.signin_enter_email);
        signin_password = (EditText)findViewById(R.id.signin_enter_password);
        signin_auth = FirebaseAuth.getInstance();
        current_user = signin_auth.getCurrentUser();
        signin_button = (Button)findViewById(R.id.signin_login_button);
        goto_register_button = (Button)findViewById(R.id.signin_register_button);

        signin_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == signin_button){
                    SigninUser();
                }
            }
        });

        goto_register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == goto_register_button){
                    Context context = getApplicationContext();
                    Class destination = Register.class;
                    Intent intent = new Intent(context,destination);
                    startActivity(intent);

                }
            }
        });

    }


    public void SigninUser(){
        String email = signin_email.getText().toString().trim();
        String password = signin_password.getText().toString().trim();

        signin_auth
                .signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    current_user = signin_auth.getCurrentUser();
                    finish();
                    String user = current_user.getEmail();
                    Toast.makeText(Signin.this, "Welcome "+user, Toast.LENGTH_SHORT).show();

                    Context context = getApplicationContext();
                    Class destination = MainActivity.class;
                    Intent intent = new Intent(context,destination);
                    startActivity(intent);

                }
            }
        });
    }


}
