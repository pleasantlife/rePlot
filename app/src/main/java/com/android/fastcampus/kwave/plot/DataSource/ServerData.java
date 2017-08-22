package com.android.fastcampus.kwave.plot.DataSource;

import java.io.Serializable;

/**
 * Created by maxx on 2017. 8. 21..
 */

public class ServerData implements Serializable{

    private String genre;

    private String location;

    private String date_start;

    private String thumbnail_img_2;

    private String thumbnail_img_1;

    private String time_start;

    private String date_end;

    private String id;

    private String fee;

    private String poster_description;

    private String created;

    private String poster_img;

    private String time_end;

    private String grade;

    private String place;

    private String poster_title;



    public String getGenre ()
    {
        return genre;
    }

    public void setGenre (String genre)
    {
        this.genre = genre;
    }

    public String getLocation ()
    {
        return location;
    }

    public void setLocation (String location)
    {
        this.location = location;
    }

    public String getDate_start ()
    {
        return date_start;
    }

    public void setDate_start (String date_start)
    {
        this.date_start = date_start;
    }

    public String getThumbnail_img_2 ()
    {
        return thumbnail_img_2;
    }

    public void setThumbnail_img_2 (String thumbnail_img_2)
    {
        this.thumbnail_img_2 = thumbnail_img_2;
    }

    public String getThumbnail_img_1 ()
    {
        return thumbnail_img_1;
    }

    public void setThumbnail_img_1 (String thumbnail_img_1)
    {
        this.thumbnail_img_1 = thumbnail_img_1;
    }

    public String getTime_start ()
    {
        return time_start;
    }

    public void setTime_start (String time_start)
    {
        this.time_start = time_start;
    }

    public String getDate_end ()
    {
        return date_end;
    }

    public void setDate_end (String date_end)
    {
        this.date_end = date_end;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getFee ()
    {
        return fee;
    }

    public void setFee (String fee)
    {
        this.fee = fee;
    }

    public String getPoster_description ()
    {
        return poster_description;
    }

    public void setPoster_description (String poster_description)
    {
        this.poster_description = poster_description;
    }

    public String getCreated ()
    {
        return created;
    }

    public void setCreated (String created)
    {
        this.created = created;
    }

    public String getPoster_img ()
    {
        return poster_img;
    }

    public void setPoster_img (String poster_img)
    {
        this.poster_img = poster_img;
    }

    public String getTime_end ()
    {
        return time_end;
    }

    public void setTime_end (String time_end)
    {
        this.time_end = time_end;
    }

    public String getGrade ()
    {
        return grade;
    }

    public void setGrade (String grade)
    {
        this.grade = grade;
    }

    public String getPlace ()
    {
        return place;
    }

    public void setPlace (String place)
    {
        this.place = place;
    }

    public String getPoster_title ()
    {
        return poster_title;
    }

    public void setPoster_title (String poster_title)
    {
        this.poster_title = poster_title;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [genre = "+genre+", location = "+location+", date_start = "+date_start+", thumbnail_img_2 = "+thumbnail_img_2+", thumbnail_img_1 = "+thumbnail_img_1+", time_start = "+time_start+", date_end = "+date_end+", id = "+id+", fee = "+fee+", poster_description = "+poster_description+", created = "+created+", poster_img = "+poster_img+", time_end = "+time_end+", grade = "+grade+", place = "+place+", poster_title = "+poster_title+"]";
    }
}
