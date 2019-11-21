package com.example.kamstrup_site_specification.Sites;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.kamstrup_site_specification.Sites.Database_site.SiteDatabase;


/*Passing the id of the site to the ViewModel*/

public class AddSiteViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final SiteDatabase database;
    private final int siteID;

    public AddSiteViewModelFactory(SiteDatabase database, int siteID){
        this.database = database;
        this.siteID = siteID;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass){
        return (T) new AddSiteViewModel(this.database,this.siteID);
    }

}
