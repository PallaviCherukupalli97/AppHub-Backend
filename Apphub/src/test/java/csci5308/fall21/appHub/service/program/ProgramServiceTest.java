package csci5308.fall21.appHub.service.program;

import csci5308.fall21.appHub.model.program.ProgramModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProgramServiceTest {

    @Mock
    private IProgramService mockIProgramService;

    @Mock
    private ProgramModel mockProgramModel = ProgramModel.getInstance();

    @Test
    void programServiceWorks() {
        assertNotNull(mockIProgramService);
    }

    /**
     *
     * @author Rishika Bajaj
     * */
    @Test
    public void checkProgramRegistration() throws NoSuchAlgorithmException {
        when(mockIProgramService.programRegistration(mockProgramModel)).thenReturn(HttpStatus.CREATED);
        assertEquals(HttpStatus.CREATED,mockIProgramService.programRegistration(mockProgramModel));
    }
    /**
     *
     * @author Rishika Bajaj
     * */
    @Test
    public void checkGetPrograms() throws SQLException {
        List<ProgramModel> programs = List.of(mockProgramModel);
        when(mockIProgramService.getAllProgram()).thenReturn(programs);
        assertNotNull(mockIProgramService.getAllProgram());
    }
    /**
     *
     * @author Rishika Bajaj
     * */
    @Test
    public void checkGetProgram() throws SQLException {
        List<ProgramModel> program = List.of(mockProgramModel);
        when(mockIProgramService.getProgram("DALU")).thenReturn(program);
        assertNotNull(mockIProgramService.getProgram("DALU"));
    }
}
