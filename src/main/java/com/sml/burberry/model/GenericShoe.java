package com.sml.burberry.model;

/**
 * Created by michaelgoode on 29/11/2016.
 */
public class GenericShoe {
    String imagecode;
    String imagename;
    String uploadDate;
    String search = "reference"; // default to reference search

    public GenericShoe() {
    }

    public String getImagecode() {
        return imagecode;
    }

    public void setImagecode(String imagecode) {
        this.imagecode = imagecode;
    }

    public String getImagename() {
        return imagename;
    }

    public void setImagename(String imagename) {
        this.imagename = imagename;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
