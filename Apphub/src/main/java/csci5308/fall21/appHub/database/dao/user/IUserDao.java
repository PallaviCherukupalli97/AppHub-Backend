package csci5308.fall21.appHub.database.dao.user;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

import csci5308.fall21.appHub.model.user.User;

public interface IUserDao {

    /**
     * @param user
     * @return
     * @throws SQLException
     * @throws NoSuchAlgorithmException
     */
    public int addUser(User user) throws SQLException, NoSuchAlgorithmException;

    /**
     * @param username
     * @throws SQLException
     */
    public int deleteUser(String id) throws SQLException;

    /**
     * @param username
     * @return
     * @throws SQLException
     */
    public User getUser(String id) throws SQLException;

    /**
     * @return
     * @throws SQLException
     */
    public List<User> getAllUsers() throws SQLException;

    /**
     * @param user
     * @throws SQLException
     */
    public User updateUser(User user) throws SQLException;

    public User updateResetToken(User user) throws SQLException;

    public User updatePassword(User user) throws SQLException;

    public boolean userExists(String email) throws SQLException;

}
