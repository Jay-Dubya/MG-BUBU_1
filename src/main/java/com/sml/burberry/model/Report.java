package com.sml.burberry.model;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by michaelgoode on 19/09/2016.
 */
public class Report {
    MultipartFile file;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
