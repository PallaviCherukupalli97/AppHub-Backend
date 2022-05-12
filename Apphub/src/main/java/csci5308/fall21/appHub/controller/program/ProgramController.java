package csci5308.fall21.appHub.controller.program;

import csci5308.fall21.appHub.model.program.ProgramModel;
import csci5308.fall21.appHub.service.program.IProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
public class ProgramController {

    @Autowired
    IProgramService iProgramService;
    /**
     *
     * @author Rishika Bajaj
     * */
    @GetMapping(path = "/api/program")
    public List<ProgramModel> getPrograms(@RequestParam String university_name) throws SQLException {
        List<ProgramModel> particularProgram = iProgramService.getProgram(university_name);
        return particularProgram;
    }

}
