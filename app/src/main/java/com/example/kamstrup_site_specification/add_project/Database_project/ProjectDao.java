package com.example.kamstrup_site_specification.add_project.Database_project;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ProjectDao {

    @Query("SELECT * FROM project ORDER BY projectName") //Loades all projects creating a list of projects on the mainscreen
    LiveData<List<ProjectEntry>> loadAllProjects();

    @Insert
    void insertProject(ProjectEntry projectEntry); //Inserting a new project into the database

    @Update(onConflict = OnConflictStrategy.REPLACE) //update an existing project
    void updateProject(ProjectEntry projectEntry);


    @Delete
    void deleteProject(ProjectEntry projectEntry); //deleting a project from the database

   @Query("SELECT * FROM project WHERE id = :id") //Retrieving an project from the database based on the id
   LiveData<ProjectEntry> loadProjectById(int id);


}
