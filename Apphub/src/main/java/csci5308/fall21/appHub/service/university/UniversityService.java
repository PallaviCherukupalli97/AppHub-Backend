package csci5308.fall21.appHub.service.university;

import csci5308.fall21.appHub.database.dao.university.IUniversityDao;
import csci5308.fall21.appHub.model.university.UniversityModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class UniversityService implements IUniversityService {

    @Autowired
    IUniversityDao iUniversityDao;
    /**
     *
     * @author Rishika Bajaj
     * */
    public List<UniversityModel> getAllUniversities() throws SQLException {
        return iUniversityDao.getAllUniversities();
    }
    /**
     *
     * @author Rishika Bajaj
     * */
    public List<UniversityModel> getUniversity(String user_id) throws SQLException {
        return iUniversityDao.getUniversity(user_id);
    }
}

