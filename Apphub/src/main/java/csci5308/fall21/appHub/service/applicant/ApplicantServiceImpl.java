package csci5308.fall21.appHub.service.applicant;

import csci5308.fall21.appHub.database.dao.user.IUserDao;
import csci5308.fall21.appHub.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import csci5308.fall21.appHub.database.dao.applicant.IApplicantDao;
import csci5308.fall21.appHub.model.applicant.ApplicantModel;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class ApplicantServiceImpl implements IApplicantService {

    @Autowired
    IApplicantDao applicantDao;
    @Autowired
    IUserDao userDao;

    public HttpStatus editProfile(ApplicantModel applicant) throws SQLException {
        try{
            List<User> users = userDao.getAllUsers();
            Optional<User> applicantExists = users.stream().filter(appli -> appli.getId().equalsIgnoreCase(applicant.getId())).findFirst();
            if(applicantExists.isPresent()) {
                if (applicantExists(applicant.getId())) {
                    applicantDao.updateApplicantInfo(applicant);
                } else {
                    applicantDao.addApplicantInfo(applicant);
                }
                return HttpStatus.OK;
            }else{
                return HttpStatus.BAD_REQUEST;
            }
        }catch(Exception ex){
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }

    }

    public ApplicantModel getProfile(String applicantId) throws SQLException {
        return applicantDao.getApplicant(applicantId);
    }

    public boolean applicantExists(String id) throws SQLException {
        List<ApplicantModel> applicants = applicantDao.getAllApplicants();
        java.util.Optional<ApplicantModel> applicantExist = applicants.stream()
                .filter(applicant -> applicant.getId().equalsIgnoreCase(id)).findFirst();
        return applicantExist.isPresent();
    }

}
