package com.example.kamstrup_site_specification.Sites.Database_site;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

//Plain Old Java Object Class

@Entity(tableName = "site") //Room generating a table called site
public class SiteEntry {

    @PrimaryKey(autoGenerate = true) // generating a unique key for every siteentry
    private int id;
    private String address;
    private String landownerName;
    private String landownerNumber;
    private int sitetype;
    private String height;
    //private String picture;
    private String comments;

    @ColumnInfo(name = "updated_at")
    private Date updatedAt;



    /*Constructor which is ignored by Room
    * used when creating a new entry*/
    @Ignore
    public SiteEntry(String address, String landownerName, String landownerNumber, int sitetype, String height, String comments, Date updatedAt) {
        this.address = address;
        this.landownerName = landownerName;
        this.landownerNumber = landownerNumber;
        this.sitetype = sitetype;
        this.height = height;
        //this.picture = picture;
        this.comments = comments;
        this.updatedAt = updatedAt;
    }

    /*Constructor used by Room
    * used when reading from the database*/
    public SiteEntry(int id, String address, String landownerName, String landownerNumber, int sitetype, String height, String comments, Date updatedAt) {
        this.id = id;
        this.address = address;
        this.landownerName = landownerName;
        this.landownerNumber = landownerNumber;
        this.sitetype = sitetype;
        this.height = height;
       // this.picture = picture;
        this.comments = comments;

        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }


    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public int getSitetype() {
        return sitetype;
    }

    public void setSitetype(int sitetype) {
        this.sitetype = sitetype;
    }

    public String getLandownerNumber() {
        return landownerNumber;
    }

    public void setLandownerNumber(String landownerNumber) {
        this.landownerNumber = landownerNumber;
    }

    public String getLandownerName() {
        return landownerName;
    }

    public void setLandownerName(String landownerName) {
        this.landownerName = landownerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    /*public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }*/

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }


}
