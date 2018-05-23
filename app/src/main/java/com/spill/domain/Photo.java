package com.spill.domain;

public class Photo {
    private int id;
    private PhotoUrls urls;

    public int getId() {
        return id;
    }

    public String getUrlForSmall() {
        return urls.small;
    }

    static class PhotoUrls {
        private String small;
    }
}
