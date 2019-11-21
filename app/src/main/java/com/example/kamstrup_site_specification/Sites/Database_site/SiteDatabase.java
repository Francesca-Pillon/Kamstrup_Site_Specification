package com.example.kamstrup_site_specification.Sites.Database_site;

import android.content.Context;


import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {SiteEntry.class}, version = 1, exportSchema = false) //the list of classes, the version that will be incrementet when updating the database, exportschema which is not used.
@TypeConverters(DateConverter.class) //because Room does not know how to implement the type Date

public abstract class SiteDatabase extends RoomDatabase {


    private static final Object LOCK = new Object();
    private static final String Database_name = "Sitelist";
    private static SiteDatabase sInstance;


    //return an database using the singleton pattern (singleton pattern makes sure it the class only instantiate one object.
    public static SiteDatabase getInstance(Context context){
        if (sInstance == null){
            synchronized (LOCK){
                /*Creates the database but only if it doesnot already exist (sinstance is null/empty)*/
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        SiteDatabase.class,
                        SiteDatabase.Database_name)
                        .build();
            } /*If the database exist (sinstance NOT == to null it will create a connection to the existing database*/
        }
        return sInstance;
    }


    public abstract SiteDao siteDao();

}
