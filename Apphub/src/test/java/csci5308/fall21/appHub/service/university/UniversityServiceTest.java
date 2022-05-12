package csci5308.fall21.appHub.service.university;

import csci5308.fall21.appHub.model.university.UniversityModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UniversityServiceTest {

    @Mock
    private IUniversityService mockIUniversityService;

    @Mock
    private UniversityModel mockUniversityModel = UniversityModel.getInstance();

    @Test
    void universityServiceWorks() {
        assertNotNull(mockIUniversityService);
    }
    /**
     *
     * @author Rishika Bajaj
     * */
    @Test
    public void checkGetUniversities() throws SQLException {
        List<UniversityModel> universities = List.of(mockUniversityModel);
        when(mockIUniversityService.getAllUniversities()).thenReturn(universities);
        assertNotNull(mockIUniversityService.getAllUniversities());
    }
    /**
     *
     * @author Rishika Bajaj
     * */
    @Test
    public void checkGetUniversity() throws SQLException {
        List<UniversityModel> university = List.of(mockUniversityModel);
        when(mockIUniversityService.getUniversity("2")).thenReturn(university);
        assertNotNull(mockIUniversityService.getUniversity("2"));
    }
}
