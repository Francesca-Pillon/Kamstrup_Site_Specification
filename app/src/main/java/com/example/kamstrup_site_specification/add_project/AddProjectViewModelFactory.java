package com.example.kamstrup_site_specification.add_project;

import android.view.View;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.kamstrup_site_specification.add_project.Database_project.ProjectDatabase;


/*Passing the id of the project to the ViewModel*/

public class AddProjectViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final ProjectDatabase database;
    private final int projectID;

    public AddProjectViewModelFactory(ProjectDatabase database, int projectID){
        this.database = database;
        this.projectID = projectID;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass){
        return (T) new AddProjectViewModel(this.database,this.projectID);
    }

}
