package csci5308.fall21.appHub.model.university;

public class UniversityModel {
    private static final UniversityModel INSTANCE=new UniversityModel();
    private String university_name;
    private String location;

    public static UniversityModel getInstance() {
        return INSTANCE;
    }

    public UniversityModel() {

    }

    public UniversityModel(String university_name, String location) {
        this.university_name = university_name;
        this.location = location;
    }


    public String getUniversity_name() {
        return university_name;
    }

    public void setUniversity_name(String university_name) {
        this.university_name = university_name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
