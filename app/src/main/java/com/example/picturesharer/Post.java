package com.example.picturesharer;

public class Post {
    private String title;
    private String author;
    private String location;
    private String description;
    private String photoUrl;

    // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    public Post() {
    }

    public Post(String title, String author, String location, String description, String photoUrl) {
        this.title = title;
        this.author = author;
        this.location = location;
        this.description = description;
        this.photoUrl = photoUrl;
    }

    public Post(String postId, String photoUrl, String location, String description) {
        this.title = postId;
        this.photoUrl = photoUrl;
        this.location = location;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }
}