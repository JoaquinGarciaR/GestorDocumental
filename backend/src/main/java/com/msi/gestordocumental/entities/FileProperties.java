package com.msi.gestordocumental.entities;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "file")
public class FileProperties {
    private String uploadDir;

    public String getUploadDir(){ return uploadDir;}

    public void setUploadDir(String dir){ this.uploadDir = dir; }
}
