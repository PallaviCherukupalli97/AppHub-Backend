/**
 *
 * @author Pallavi Cherukupalli
 * */

package csci5308.fall21.appHub.database.dao.applicant;

import java.util.List;
import csci5308.fall21.appHub.model.applicant.ApplicantModel;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public interface IApplicantDao {

     List<ApplicantModel> getAllApplicants() throws SQLException;

     int addApplicantInfo(ApplicantModel applicant) throws SQLException, NoSuchAlgorithmException;

     ApplicantModel updateApplicantInfo(ApplicantModel applicant) throws SQLException;

     ApplicantModel getApplicant(String id) throws SQLException;

}