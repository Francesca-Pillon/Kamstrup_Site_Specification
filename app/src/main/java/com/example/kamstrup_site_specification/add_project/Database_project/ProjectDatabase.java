package com.example.kamstrup_site_specification.add_project.Database_project;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {ProjectEntry.class}, version = 1, exportSchema = false) //the list of classes, the version that will be incrementet when updating the database, exportschema which is not used.
@TypeConverters(DateConverter.class) //because Room does not know how to implement the type Date

public abstract class ProjectDatabase extends RoomDatabase {

    private static final Object LOCK = new Object();
    private static final String Database_name = "Projectlist";
    private static ProjectDatabase sInstance;


    //return an database using the singleton pattern (singleton pattern makes sure it the class only instantiate one object.
    public static ProjectDatabase getInstance(Context context){
        if (sInstance == null){
            synchronized (LOCK){
                /*Creates the database but only if it doesnot already exist (sinstance is null/empty)*/
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        ProjectDatabase.class,
                        ProjectDatabase.Database_name)
                        .build();
            } /*If the database exist (sinstance NOT == to null it will create a connection to the existing database*/
        }
        return sInstance;
    }


    public abstract ProjectDao projectDao();

}
