package com.android.bookr.models;

public class Book {
    final private String title;
    final private String author;
    final private String smallThumbnailUrl;
    final private String thumbnailUrl;
    final private String description;
    final private String storeLink;

    public Book(String title, String author, String smallThumbnailUrl, String thumbnailUrl, String description, String storeLink) {
        this.title = title;
        this.author = author;
        this.smallThumbnailUrl = smallThumbnailUrl;
        this.thumbnailUrl = thumbnailUrl;
        this.description = description;
        this.storeLink = storeLink;
    }

    public Book(String title, String author, String smallThumbnailUrl){
        this(title, author, smallThumbnailUrl, "", "", "");
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getSmallThumbnailUrl() {
        return smallThumbnailUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getDescription() {
        return description;
    }

    public String getStoreLink() {
        return storeLink;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", smallThumbnailUrl='" + smallThumbnailUrl + '\'' +
                ", thumbnailUrl='" + thumbnailUrl + '\'' +
                ", description='" + description + '\'' +
                ", storeLink='" + storeLink + '\'' +
                '}';
    }
}
