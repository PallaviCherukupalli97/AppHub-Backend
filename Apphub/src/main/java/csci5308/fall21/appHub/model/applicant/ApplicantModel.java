/**
 *
 * @author Pallavi Cherukupalli
 * */

package csci5308.fall21.appHub.model.applicant;

public class ApplicantModel {

    private String id;
    private String country;
    private String passportNumber;
    private String address;
    private String highestLevelEducation;
    private String grade;
    private String ielts;
    private String gre;
    private String gmat;

    public ApplicantModel(String id, String country, String passportNumber, String address,
            String highestLevelEducation, String grade, String ielts, String gre, String gmat) {
        this.id = id;
        this.country = country;
        this.passportNumber = passportNumber;
        this.address = address;
        this.highestLevelEducation = highestLevelEducation;
        this.grade = grade;
        this.ielts = ielts;
        this.gre = gre;
        this.gmat = gmat;
    }

    public String getId() {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHighestLevelEducation() {
        return highestLevelEducation;
    }

    public void setHighestLevelEducation(String highestLevelEducation) {
        this.highestLevelEducation = highestLevelEducation;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getGre() {
        return gre;
    }

    public void setGre(String gre) {
        this.gre = gre;
    }

    public String getGmat() {
        return gmat;
    }

    public void setGmat(String gmat) {
        this.gmat = gmat;
    }

    public String getIelts() {
        return ielts;
    }

    public void setIelts(String ielts) {
        this.ielts = ielts;
    }

}
