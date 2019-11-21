package com.example.kamstrup_site_specification.add_project.Database_project;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.kamstrup_site_specification.MainActivitySite;

import java.util.Date;
import java.util.List;

//Plain Old Java Object Class

@Entity(tableName = "project") //Room generating a table called project
public class ProjectEntry {

    @PrimaryKey(autoGenerate = true) // generating a unique key for every projectentry
    private int id;
    private String projectName;
    private String customerName;
    private String customerEmail;
    private String projectEngineer;
    private String meter_points;
    @ColumnInfo(name = "updated_at")
    private Date updatedAt;

   // private MainActivitySite siteList;

    /*Constructor which is ignored by Room
    * used when creating a new entry*/
    @Ignore
    public ProjectEntry(String projectName, String customerName, String customerEmail, String projectEngineer, String meter_points, Date updatedAt) {
        this.projectName = projectName;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.projectEngineer = projectEngineer;
        this.meter_points = meter_points;
        this.updatedAt = updatedAt;
       // this.siteList = siteList;
    }

    /*Constructor used by Room
    * used when reading from the database*/
    public ProjectEntry(int id, String projectName, String customerName, String customerEmail, String projectEngineer, String meter_points, Date updatedAt) {
        this.id = id;
        this.projectName = projectName;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.projectEngineer = projectEngineer;
        this.meter_points = meter_points;
        this.updatedAt = updatedAt;
        //this.siteList = siteList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getProjectEngineer() {
        return projectEngineer;
    }

    public void setProjectEngineer(String projectEngineer) {
        this.projectEngineer = projectEngineer;
    }

    public String getMeter_points() {
        return meter_points;
    }

    public void setMeter_points(String meter_points) {
        this.meter_points = meter_points;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    /*public MainActivitySite getSiteList() {
        return siteList;
    }

    public void setSiteList(MainActivitySite siteList) {
        this.siteList = siteList;
    }*/
}
