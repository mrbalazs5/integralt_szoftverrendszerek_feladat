package hu.unimiskolc.iit.controller.core;

public class FileUploadResponse {
    private String fileName;

    public FileUploadResponse(String fileName) {
        this.fileName = fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return this.fileName;
    }
}