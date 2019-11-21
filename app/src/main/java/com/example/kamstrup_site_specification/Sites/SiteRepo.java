package com.example.kamstrup_site_specification.Sites;

import android.app.Application;


import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.kamstrup_site_specification.Sites.Database_site.SiteDatabase;
import com.example.kamstrup_site_specification.Sites.Database_site.SiteEntry;

import java.util.List;

public class SiteRepo extends AndroidViewModel {

    private LiveData<List<SiteEntry>> sites;

    public SiteRepo(@NonNull Application application) {
        super(application);
        SiteDatabase database = SiteDatabase.getInstance(this.getApplication());

        sites = database.siteDao().loadAllSites();

    }


    public LiveData<List<SiteEntry>> getSites(){
        return sites;
    }

}
