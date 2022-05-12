/**
 *
 * @author Pallavi Cherukupalli
 * */

package csci5308.fall21.appHub.database.dao.application;

import java.sql.SQLException;
import java.util.List;

import csci5308.fall21.appHub.model.application.Application;
import csci5308.fall21.appHub.model.application.ApplicationDocument;

public interface IApplicationDocumentDao {

    /**
     * @param applicationDocument
     * @return
     * @throws SQLException
     * @throws NoSuchAlgorithmException
     */
    public int addApplicationDocument(ApplicationDocument applicationDocument) throws SQLException;

    /**
     * @param id
     * @return
     * @throws SQLException
     */
    public ApplicationDocument getApplicationDocument(String applicationId, String id) throws SQLException;

    /**
     * @return
     * @throws SQLException
     */
    public List<ApplicationDocument> getAllApplicationDocuments(String applicationId) throws SQLException;

    public List<Application> getApps(String applicantId) throws SQLException;

}
