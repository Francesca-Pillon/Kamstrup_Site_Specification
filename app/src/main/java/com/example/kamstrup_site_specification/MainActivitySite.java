package com.example.kamstrup_site_specification;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.kamstrup_site_specification.Sigin_Firebase.Profile;
import com.example.kamstrup_site_specification.Sigin_Firebase.Signin;
import com.example.kamstrup_site_specification.Sites.AddSiteActivity;
import com.example.kamstrup_site_specification.Sites.Database_site.SiteDatabase;
import com.example.kamstrup_site_specification.Sites.Database_site.SiteEntry;
import com.example.kamstrup_site_specification.Sites.SiteAdapter;
import com.example.kamstrup_site_specification.Sites.SiteRepo;
import com.example.kamstrup_site_specification.add_project.AppExecutors;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

public class MainActivitySite extends AppCompatActivity implements SiteAdapter.ItemClickListener{

    private static final String TAG = MainActivitySite.class.getSimpleName();

    private RecyclerView recyclerView;
    private SiteAdapter siteAdapter;
    private SiteDatabase siteDatabase;

    //For the menu and viewing the profile
    FirebaseAuth profile_auth;
    FirebaseUser profile_user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_activity_main);
        //Setting the toolbar
        Toolbar toolbar = findViewById(R.id.kamstrup_toolbar);
        setSupportActionBar(toolbar);

        //Setting the floating plus button
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addSiteIntent = new Intent(MainActivitySite.this, AddSiteActivity.class);
                startActivity(addSiteIntent);
            }
        });

        //Setting the recyclerView
        recyclerView = findViewById(R.id.project_recyclerview);

        //Setting the recylerview to a linearlayout to make sure it becomes a linear list
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Setting the adapter to the recyclerview
        siteAdapter = new SiteAdapter(this, this);
        recyclerView.setAdapter(siteAdapter);

        DividerItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(decoration);


        /*and touchhelper which delete a project from the database when swiping*/
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT |ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) {
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        int position = viewHolder.getAdapterPosition();
                        List<SiteEntry> sites = siteAdapter.getSiteEntries();
                        siteDatabase.siteDao().deleteSite(sites.get(position));

                    }
                });

            }
        }).attachToRecyclerView(recyclerView);


        /*Intializing the databaase*/
        siteDatabase = siteDatabase.getInstance(getApplicationContext());

        setupViewModel();


    }

    private void setupViewModel(){

        SiteRepo viewModel = ViewModelProviders.of(this).get(SiteRepo.class);

        viewModel.getSites().observe(this, new Observer<List<SiteEntry>>() {
            @Override
            public void onChanged(List<SiteEntry> siteEntries) {
                siteAdapter.setSites(siteEntries);
            }
        });
    }


    public void onItemClickListener(int itemId){
        Intent intent = new Intent(MainActivitySite.this, AddSiteActivity.class);
        intent.putExtra(AddSiteActivity.extra_site_ID, itemId);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.project_list_menu, menu);
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
            case R.id.sign_out_menu:
                if (profile_user != null){
                    profile_auth.signOut();

                    String user = profile_user.getEmail();

                    Toast.makeText(this, "Goodbye "+user, Toast.LENGTH_SHORT).show();

                    Context context = getApplicationContext();
                    Class destination = Signin.class;

                    Intent intent = new Intent(context,destination);
                    startActivity(intent);
                    break;
                }
            case R.id.profile_menu:
                Context context = getApplicationContext();
                Class destination = Profile.class;

                Intent intent = new Intent(context,destination);
                startActivity(intent);

        }


        return super.onOptionsItemSelected(item);
    }
}
