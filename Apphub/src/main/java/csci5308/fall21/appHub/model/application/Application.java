/**
 *
 * @author Pallavi Cherukupalli
 * */

package csci5308.fall21.appHub.model.application;

public class Application {
    private String id;
    private String status;
    private String userId;
    private String programName;
    private String universityName;

    public Application(String id, String status, String userId, String programName,
            String universityName) {
        this.id = id;
        this.status = status;
        this.userId = userId;
        this.programName = programName;
        this.universityName = universityName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramId(String programName) {
        this.programName = programName;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }
}
