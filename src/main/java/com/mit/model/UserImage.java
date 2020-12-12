package com.mit.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Arrays;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserImage {
    private String fileName;
    private String fileType;
    private byte[] image;

    public UserImage() {
    }

    public UserImage(String fileName, String fileType, byte[] image) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.image = image;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "UserImage{" +
                "fileName='" + fileName + '\'' +
                ", fileType='" + fileType + '\'' +
                ", image=" + Arrays.toString(image).length() +
                '}';
    }
}
