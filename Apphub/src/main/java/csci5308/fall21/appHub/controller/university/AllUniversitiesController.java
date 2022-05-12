package csci5308.fall21.appHub.controller.university;

import csci5308.fall21.appHub.model.university.UniversityModel;
import csci5308.fall21.appHub.service.university.IUniversityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
public class AllUniversitiesController {
    @Autowired
    IUniversityService iUniversityService;
    /**
     *
     * @author Rishika Bajaj
     * */
    @GetMapping(path = "/api/universities")
    public List<UniversityModel> getAllUniversities() throws SQLException {
        List<UniversityModel> allUniversities = iUniversityService.getAllUniversities();
        return allUniversities;
    }

}