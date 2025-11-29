package com.example.strayanimals.data;

import android.location.Location;
import android.media.Image;

import androidx.annotation.NonNull;

import java.sql.Timestamp;

public class Post implements Cloneable {
    private int id;
    private String title;
    private String content;
    private String author;
    private Timestamp createTime;
    private Image image; // Placeholder
    private Location location;
    private String address;
    private int reposts = 0;
    private int likes = 0;
    private int comments = 0;

    public Post() { }

    public Post(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.createTime = new Timestamp(new java.util.Date().getTime());
    }

    public Post(String title, String content, String author, Location location, String address) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.location = location;
        this.address = address;
        this.createTime = new Timestamp(new java.util.Date().getTime());
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getReposts() {
        return reposts;
    }

    public void setReposts(int reposts) {
        this.reposts = reposts;
    }

    public void addRepost() {
        this.reposts++;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public void addLike() {
        this.likes++;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public void addComment() {
        this.comments++;
    }

    @NonNull
    @Override
    public Object clone() {
        Post p = null;
        try {
            p = (Post) super.clone();
        } catch (CloneNotSupportedException e) { } // Won't happen
        return p;
    }
}
