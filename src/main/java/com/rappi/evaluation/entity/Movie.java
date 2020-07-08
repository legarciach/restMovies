package com.rappi.evaluation.entity;

import java.util.Date;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.Gson;



@Table(name = "movies")
@Entity
public class Movie {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    

    @Column(name = "rating")
    private Double rating;

    @Column(name = "watched_date")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date watched_date = null;

    @Column(name = "comments")
    private String comments;

    @JsonIgnore
    @Column(name = "imdbID")
    private String imdbID;

    public Movie() {    }

    public Movie(Double rating, Date watched_date, String comments, String imdbID) {
        this.rating = rating;
        this.watched_date = watched_date;
        this.comments = comments;
        this.imdbID = imdbID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Date getWatched_date() {
        return watched_date;
    }

    public void setWatched_date(Date watched_date) {
        this.watched_date = watched_date;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }
    
    public String toJson() {
        return new Gson().toJson(this);
    }
    
}
