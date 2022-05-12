package csci5308.fall21.appHub.controller.applicant;

import csci5308.fall21.appHub.model.applicant.ApplicantModel;
import csci5308.fall21.appHub.service.applicant.IApplicantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

@RestController
@CrossOrigin
public class ApplicantController {

    @Autowired
    IApplicantService applicationService;

    /**
     *
     * @author Pallavi Cherukupalli
     * */

    @PostMapping(path = "/api/applicant")
    public ResponseEntity<Object> updateApplicantInfo(@RequestBody ApplicantModel applicant) throws SQLException {
        Map<String, String> response = new HashMap<String, String>();
        HttpStatus status = applicationService.editProfile(applicant);
        if (status == HttpStatus.OK) {
            response.put("message", "Applicant updated Successfully");
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } else {
            response.put("message", "Error occurred while processing");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    /**
     *
     * @author Pallavi Cherukupalli
     * */
    @GetMapping(path = "/api/applicant/{applicantId}")
    public ResponseEntity<Object> getApplicantInfo(@PathVariable String applicantId) throws SQLException {
        ApplicantModel applicant = applicationService.getProfile(applicantId);
        Map<String, String> response = new HashMap<String, String>();
        if (applicant == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Applicant not found");
        } else {
            try {
                return ResponseEntity.status(HttpStatus.OK).body(applicant);
            } catch (Exception ex) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        }
    }
}
