package com.spill.domain;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CuratedCollection {
    private int id;
    private String title;
    private String description;
    @SerializedName("preview_photos")
    private List<Photo> previewPhotos;
    private Links links;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<Photo> getPreviewPhotos() {
        return previewPhotos;
    }

    public String getBrowserLinks() {
        return links.html;
    }

    private class Links {
        String html;
    }
}
