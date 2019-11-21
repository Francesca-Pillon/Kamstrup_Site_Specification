package com.example.kamstrup_site_specification.add_project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kamstrup_site_specification.MainActivitySite;
import com.example.kamstrup_site_specification.R;
import com.example.kamstrup_site_specification.Sigin_Firebase.Profile;
import com.example.kamstrup_site_specification.Sigin_Firebase.Signin;
import com.example.kamstrup_site_specification.add_project.Database_project.ProjectDatabase;
import com.example.kamstrup_site_specification.add_project.Database_project.ProjectEntry;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import java.util.Date;

public class AddProjectActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    public static final String extra_project_ID = "esktraProjectId"; //extra id for the project id to be received in the intent
    public static final String instance_project_ID = "instanceProjectId"; //Extra for the project id to be recieved after rotation
    public static final int default_project_ID = -1; //constant for defualt project id to be used when not in update mode


    private int projectID = default_project_ID;
    private ProjectDatabase myDatabase; //member variable for the database


    //Fields for views and inputs
    EditText projectName;
    EditText customerName;
    EditText customerEmail;
    EditText projectEngineer;
    EditText meter_points;
    Button save_button;

    //For going to profile and sign out menu
    FirebaseAuth profile_auth;
    FirebaseUser profile_user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_add_project);

        Toolbar toolbar = findViewById(R.id.kamstrup_toolbar);
        setSupportActionBar(toolbar);


        findViews();

        myDatabase = ProjectDatabase.getInstance(getApplicationContext());

        if (savedInstanceState != null && savedInstanceState.containsKey(instance_project_ID)){
            projectID = savedInstanceState.getInt(instance_project_ID, default_project_ID);
        }

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(extra_project_ID)){
            save_button.setText(R.string.update_button); //Setting button to update
            if (projectID == default_project_ID){
                projectID = intent.getIntExtra(extra_project_ID, default_project_ID);

                AddProjectViewModelFactory factory = new AddProjectViewModelFactory(myDatabase, projectID);
                final AddProjectViewModel viewModel = ViewModelProviders.of(this, factory).get(AddProjectViewModel.class);
                viewModel.getProject().observe(this, new Observer<ProjectEntry>() {
                    @Override
                    public void onChanged(ProjectEntry projectEntry) {
                        viewModel.getProject().removeObserver(this);
                        populateUI(projectEntry);
                    }
                });
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        outState.putInt(instance_project_ID, projectID);
        super.onSaveInstanceState(outState);
    }

    //find the views for the different fields
    private void findViews(){

        projectName = findViewById(R.id.enterprojectnr);
        customerName = findViewById(R.id.customerName);
        customerEmail = findViewById(R.id.customerEmail);
        projectEngineer = findViewById(R.id.projectEngineer);
        meter_points = findViewById(R.id.meter_points);

        save_button = findViewById(R.id.saveButton);
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSaveButtonClicked();
            }
        });
    }


    //to show the fields when in updatemode
    private void populateUI(ProjectEntry project){
        if (project == null){
            return;
        }
        projectName.setText(project.getProjectName());
        customerName.setText(project.getCustomerName());
        customerEmail.setText(project.getCustomerEmail());
        projectEngineer.setText(project.getProjectEngineer());
        meter_points.setText(project.getMeter_points());

    }

    /*Called by the savebutton
    * retrieves user input an inserts new data it into the database*/
    public void onSaveButtonClicked(){
        String projectN = projectName.getText().toString();
        String customerN = customerName.getText().toString();
        String customerE = customerEmail.getText().toString();
        String projectEng = projectEngineer.getText().toString();
        String meter_p =  meter_points.getText().toString();


        Date date = new Date();

        /*Calling the constructor from the projectEntry */
        final ProjectEntry project = new ProjectEntry(projectN, customerN, customerE, projectEng, meter_p, date);

        /*Reading from the database*/
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if (projectID == default_project_ID){
                    myDatabase.projectDao().insertProject(project);
                } else{
                    project.setId(projectID);
                    myDatabase.projectDao().updateProject(project);
                }
                finish();

            }
        });




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.project_add_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        profile_auth = FirebaseAuth.getInstance();
        profile_user = profile_auth.getCurrentUser();

        int id = item.getItemId();

        switch (id){
            case R.id.sites:
                Context context = getApplicationContext();
                Class destination = MainActivitySite.class;
                Intent intent1 = new Intent(context, destination);
                startActivity(intent1);
                break;
            case R.id.profile_menu_add:
                Context context1 = getApplicationContext();
                Class destination1 = Profile.class;

                Intent intent = new Intent(context1,destination1);
                startActivity(intent);
                break;
            case R.id.sign_out_menu_add:
                if (profile_user != null) {
                    profile_auth.signOut();
                    String user = profile_user.getEmail();

                    Toast.makeText(this, "Goodbye "+user, Toast.LENGTH_SHORT).show();

                    Context context3 = getApplicationContext();
                    Class destination3 = Signin.class;

                    Intent intent2 = new Intent(context3, destination3);
                    startActivity(intent2);
                }


        }

        return super.onOptionsItemSelected(item);
    }







}
