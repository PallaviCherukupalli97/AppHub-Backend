package csci5308.fall21.appHub.controller.university;

import csci5308.fall21.appHub.model.university.UniversityModel;
import csci5308.fall21.appHub.service.university.IUniversityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
public class UniversityController {
    @Autowired
    IUniversityService universityService;
    /**
     *
     * @author Rishika Bajaj
     * */
    @GetMapping( path = "/api/university")
    public List<UniversityModel> getUniversity(@RequestParam String user_id) throws SQLException {
        List<UniversityModel> particularUniversity = universityService.getUniversity(user_id);
        return particularUniversity;
    }
}
