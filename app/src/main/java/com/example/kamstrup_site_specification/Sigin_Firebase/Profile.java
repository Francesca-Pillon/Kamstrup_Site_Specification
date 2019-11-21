package com.example.kamstrup_site_specification.Sigin_Firebase;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.kamstrup_site_specification.MainActivity;
import com.example.kamstrup_site_specification.MainActivitySite;
import com.example.kamstrup_site_specification.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Profile  extends AppCompatActivity {

    private TextView profile_email;
    private TextView profile_id;
    private Button profile_logout_button;

    FirebaseAuth profile_auth;
    FirebaseUser profile_user;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_profile);

        Toolbar toolbar = findViewById(R.id.kamstrup_toolbar);
        setSupportActionBar(toolbar);

        setViews();

        if (profile_user != null){
            String email = profile_user.getEmail();
            String user_id = profile_user.getUid();

            profile_email.setText(email);
            profile_id.setText(user_id);
        }

    }

    private void setViews(){
        profile_email = (TextView)findViewById(R.id.profile_entered_email);
        profile_id = (TextView)findViewById(R.id.profile_user_id);
        profile_logout_button =(Button)findViewById(R.id.profile_logout_button);

        profile_auth = FirebaseAuth.getInstance();
        profile_user = profile_auth.getCurrentUser();


        profile_logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == profile_logout_button){
                    if (profile_user != null){
                        profile_auth.signOut();

                        Context context = getApplicationContext();
                        Class destination = Signin.class;

                        Intent intent = new Intent(context,destination);
                        startActivity(intent);
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        switch (id){
            case R.id.sites_profile_menu:
                Context context = getApplicationContext();
                Class destination = MainActivitySite.class;
                Intent intent = new Intent(context, destination);
                startActivity(intent);
                break;
            case R.id.projects_profile_menu:
                Context context1 = getApplicationContext();
                Class destination1 = MainActivity.class;

                Intent intent1 = new Intent(context1,destination1);
                startActivity(intent1);
                break;
            case R.id.sign_out_profile_menu:
                if (profile_user != null) {
                    profile_auth.signOut();

                    Context context2 = getApplicationContext();
                    Class destination2 = Signin.class;

                    Intent intent2 = new Intent(context2, destination2);
                    startActivity(intent2);
                }


        }

        return super.onOptionsItemSelected(item);
    }




}
