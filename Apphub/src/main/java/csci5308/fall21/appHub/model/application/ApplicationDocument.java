package csci5308.fall21.appHub.model.application;

import java.util.Arrays;

public class ApplicationDocument {
    private String id;
    private String title;
    private String description;
    private String documentName;
    private String applicationId;
    private String type;
    private byte[] data;

    public ApplicationDocument() {
    }

    public ApplicationDocument(String id, String applicationId, String title, String description, String documentName,
            String type,
            byte[] data) {
        this.id = id;
        this.applicationId = applicationId;
        this.title = title;
        this.description = description;
        this.documentName = documentName;
        this.type = type;
        this.data = data;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ApplicationDocument [applicationId=" + applicationId + ", data=" + Arrays.toString(data)
                + ", description=" + description + ", documentName=" + documentName + ", id=" + id + ", title=" + title
                + ", type=" + type + "]";
    }
}
