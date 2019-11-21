package com.example.kamstrup_site_specification.Sites;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.kamstrup_site_specification.Sites.Database_site.SiteDatabase;
import com.example.kamstrup_site_specification.Sites.Database_site.SiteEntry;

public class AddSiteViewModel extends ViewModel {

    private LiveData<SiteEntry> site;

    public AddSiteViewModel(SiteDatabase database, int siteID){
        site = database.siteDao().loadSitesById(siteID);
    }

    public LiveData<SiteEntry> getSite(){
        return site;
    }

}
