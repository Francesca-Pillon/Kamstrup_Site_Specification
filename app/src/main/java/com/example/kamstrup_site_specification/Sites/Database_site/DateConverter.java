package com.example.kamstrup_site_specification.Sites.Database_site;

import androidx.room.TypeConverter;

import java.util.Date;

public class DateConverter {


    /*Used when writing FROM the database
    * convertes the date to a Date object*/
    @TypeConverter
    public static Date toDate(Long timestamp){
        return timestamp == null ? null : new Date(timestamp);

    }

    /*Used when writing TO the database
    * convertes a Date object to the type Long*/
    @TypeConverter
    public static Long toTimeStamp(Date date){
        return date == null ? null : date.getTime();
    }
}
