package csci5308.fall21.appHub.model.program;


public class ProgramModel {

    private static final ProgramModel INSTANCE=new ProgramModel();
    private String programName;
    private String course;
    private String fees;
    private String duration;
    private String applicationFees;
    private String deadline;
    private String requirements;
    private String universityName;


    public static ProgramModel getInstance() {
        return INSTANCE;
    }

    public ProgramModel() {

    }

    public ProgramModel(String program_name, String course, String fees, String duration, String application_fees, String deadline, String requirements,String university_name) {
        this.programName = program_name;
        this.course = course;
        this.fees = fees;
        this.duration = duration;
        this.applicationFees = application_fees;
        this.deadline = deadline;
        this.requirements = requirements;
        this.universityName = university_name;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getFees() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getApplicationFees() {
        return applicationFees;
    }

    public void setApplicationFees(String applicationFees) {
        this.applicationFees = applicationFees;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }
}
