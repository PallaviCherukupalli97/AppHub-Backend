package csci5308.fall21.appHub.service.applicant;

import java.sql.SQLException;

import org.springframework.http.HttpStatus;

import csci5308.fall21.appHub.model.applicant.ApplicantModel;

public interface IApplicantService {
    public HttpStatus editProfile(ApplicantModel applicant) throws SQLException;

    public ApplicantModel getProfile(String applicantId) throws SQLException;
}
