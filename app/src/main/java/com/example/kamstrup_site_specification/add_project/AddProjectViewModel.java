package com.example.kamstrup_site_specification.add_project;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.kamstrup_site_specification.add_project.Database_project.ProjectDatabase;
import com.example.kamstrup_site_specification.add_project.Database_project.ProjectEntry;

public class AddProjectViewModel extends ViewModel {

    private LiveData<ProjectEntry> project;

    public AddProjectViewModel(ProjectDatabase database, int projectID){
        project = database.projectDao().loadProjectById(projectID);
    }

    public LiveData<ProjectEntry> getProject(){
        return project;
    }

}
