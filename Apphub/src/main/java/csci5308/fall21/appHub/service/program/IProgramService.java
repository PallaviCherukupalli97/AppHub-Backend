package csci5308.fall21.appHub.service.program;

import csci5308.fall21.appHub.model.program.ProgramModel;
import org.springframework.http.HttpStatus;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

public interface IProgramService {
    public List<ProgramModel> getProgram(String university_name) throws SQLException;
    public List<ProgramModel> getAllProgram() throws SQLException;
    public HttpStatus programRegistration(ProgramModel program) throws NoSuchAlgorithmException;
}
