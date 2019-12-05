package com.example.kamstrup_site_specification.Sites;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


import com.example.kamstrup_site_specification.R;
import com.example.kamstrup_site_specification.Sites.Database_site.SiteDatabase;
import com.example.kamstrup_site_specification.Sites.Database_site.SiteEntry;
import com.example.kamstrup_site_specification.Sites.QuoteWebservice.QuoteDisplay;


import java.util.Date;

public class AddSiteActivity extends AppCompatActivity {

    public static final String extra_site_ID = "esktraSiteId"; //extra id for the site id to be received in the intent
    public static final String instance_site_ID = "instanceSiteId"; //Extra for the site id to be recieved after rotation
    public static final int default_site_ID = -1; //constant for defualt site id to be used when not in update mode


    private int siteID = default_site_ID;
    private SiteDatabase myDatabase; //member variable for the database

    //Fields for sitetype
    public static final int minisite = 1;
    public static final int midisite = 2;
    public static final int maxisite = 3;

    //Used for camera input
   private static final int CAMERA_PIC_REQUEST = 1;
    String currentPhotoPath;


    //Fields for views and inputs
    EditText siteAddress;
    EditText landownerName;
    EditText landownerNumber;
    EditText siteHeight;
    EditText pictureComments;
    Button takePicture;
    ImageView display_picture;
    Button save_button;

    Button api_button;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.site);

        findViews();

        myDatabase = SiteDatabase.getInstance(getApplicationContext());

        if (savedInstanceState != null && savedInstanceState.containsKey(instance_site_ID)){
            siteID = savedInstanceState.getInt(instance_site_ID, default_site_ID);
        }

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(extra_site_ID)){
            save_button.setText(R.string.update_button); //Setting button to update
            if (siteID == default_site_ID){
                siteID = intent.getIntExtra(extra_site_ID, default_site_ID);

                AddSiteViewModelFactory factory = new AddSiteViewModelFactory(myDatabase, siteID);
                final AddSiteViewModel viewModel = ViewModelProviders.of(this, factory).get(AddSiteViewModel.class);
                viewModel.getSite().observe(this, new Observer<SiteEntry>() {
                    @Override
                    public void onChanged(SiteEntry siteEntry) {
                        viewModel.getSite().removeObserver(this);
                        populateUI(siteEntry);
                    }
                });
            }
        }

    }




    @Override
    protected void onSaveInstanceState(Bundle outState){
        outState.putInt(instance_site_ID, siteID);
        super.onSaveInstanceState(outState);
    }

    //find the views for the different fields
    private void findViews(){
        siteAddress = findViewById(R.id.enterAddress);
        landownerName = findViewById(R.id.landownerName);
        landownerNumber = findViewById(R.id.landownerNumber);
        siteHeight = findViewById(R.id.siteheight);
        pictureComments = findViewById(R.id.picture_comments);
        display_picture = (ImageView)findViewById(R.id.display_picture);


        takePicture = (Button)findViewById(R.id.takepicture_marking);
        takePicture.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //Toast.makeText(AddSiteActivity.this, "Take a picture with your camera", Toast.LENGTH_SHORT).show();
               Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, CAMERA_PIC_REQUEST);
                }

            }
        });

        save_button = findViewById(R.id.site_save_button);
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSaveButtonClicked();
            }
        });

        api_button = (Button)findViewById(R.id.goto_quote_button);
        api_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(AddSiteActivity.this, "Grab a cup of coffee and see some quotes", Toast.LENGTH_LONG).show();
                Context context = getApplicationContext();
                Class destination = QuoteDisplay.class;

                Intent pokemonIntent = new Intent(context,destination);
                startActivity(pokemonIntent);

            }
        });



    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_PIC_REQUEST && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            display_picture.setImageBitmap(imageBitmap);
        }

    }



    //to show the fields when in updatemode
    private void populateUI(SiteEntry site){
        if (site == null){
            return;
        }
        siteAddress.setText(site.getAddress());
        landownerName.setText(site.getLandownerName());
        landownerNumber.setText(site.getLandownerNumber());
        siteHeight.setText(site.getHeight());
        pictureComments.setText(site.getComments());

    }

    /*Called by the savebutton
    * retrieves user input an inserts new data it into the database*/
    public void onSaveButtonClicked(){
        String siteAddress = this.siteAddress.getText().toString();
        String ownerName = landownerName.getText().toString();
        String ownerNumber = landownerNumber.getText().toString();
        String height = siteHeight.getText().toString();
        String comments = pictureComments.getText().toString();
        int types = getSiteTypeFromViews();
        Date date = new Date();

        /*Calling the constructor from the SiteEntry */
        final SiteEntry site = new SiteEntry(siteAddress, ownerName, ownerNumber, types, height, comments, date);

        /*Reading from the database*/
        SiteExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if (siteID == default_site_ID){
                    myDatabase.siteDao().insertSite(site);
                } else{
                    site.setId(siteID);
                    myDatabase.siteDao().updateSite(site);
                }
                finish();

            }
        });




    }

    //Retrieving the right sitetype
    public int getSiteTypeFromViews(){
        int siteType = 1;
        int checkedID = ((RadioGroup) findViewById(R.id.sitetypeRadio)).getCheckedRadioButtonId();

        switch (checkedID){
            case R.id.minisite:
                siteType = minisite;
                break;
            case R.id.midisite:
                siteType = midisite;
                break;
            case R.id.maxisite:
                siteType = maxisite;
        }
        return siteType;
    }

    //Retrieving the right sitetype
    public void setSiteTypeInViews(int sitetype){

        switch (sitetype){
            case minisite:
                ((RadioGroup) findViewById(R.id.sitetypeRadio)).check(R.id.minisite);
                break;
            case midisite:
                ((RadioGroup) findViewById(R.id.sitetypeRadio)).check(R.id.midisite);
                break;
            case maxisite:
                ((RadioGroup) findViewById(R.id.sitetypeRadio)).check(R.id.maxisite);
                break;
        }
    }








}
