package com.example.picturesharer;

public class Post {
    private String postId;
    private String photoUrl;
    private String latitude;
    private String longitude;
    private String description;
    private String author;
    private String title;

    public Post() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public Post(String postId, String photoUrl, String latitude, String longitude, String description, String author, String title) {
        this.postId = postId;
        this.photoUrl = photoUrl;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
        this.author = author;
        this.title = title;
    }

    public Post(String postId, String photoUrl, String latitude, String longitude, String description, String author) {
        this.postId = postId;
        this.photoUrl = photoUrl;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
        this.author = author;
        this.title = "Untitled Post"; // Default title if not provided
    }
    public String getPostId() {
        return postId;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getDescription() {
        return description;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }
}