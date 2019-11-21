package com.example.kamstrup_site_specification.Sites.Database_site;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

@Dao
public interface SiteDao {


    //ROOM - Database
    @Query("SELECT * FROM site ORDER BY siteType") //Loades all sites creating a list of sites on the sitescreen
    LiveData<List<SiteEntry>> loadAllSites();

    @Insert
    void insertSite(SiteEntry siteEntry); //Inserting a new site into the database

    @Update(onConflict = OnConflictStrategy.REPLACE) //update an existing site
    void updateSite(SiteEntry siteEntry);


    @Delete
    void deleteSite(SiteEntry siteEntry); //deleting a site from the database

    @Query("SELECT * FROM site WHERE id = :id") //Retrieving an site from the database based on the id
    LiveData<SiteEntry> loadSitesById(int id);




}
