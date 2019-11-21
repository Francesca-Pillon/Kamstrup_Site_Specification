package com.example.kamstrup_site_specification.add_project;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.kamstrup_site_specification.add_project.Database_project.ProjectDatabase;
import com.example.kamstrup_site_specification.add_project.Database_project.ProjectEntry;

import java.util.List;

public class ProjectRepo extends AndroidViewModel {


    private LiveData<List<ProjectEntry>> projects;

    public ProjectRepo(@NonNull Application application) {
        super(application);
        ProjectDatabase database = ProjectDatabase.getInstance(this.getApplication());

        projects = database.projectDao().loadAllProjects();

    }


    public LiveData<List<ProjectEntry>> getProjects(){
        return projects;
    }

}
