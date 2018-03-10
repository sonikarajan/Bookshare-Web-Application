package bookshare.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import bookshare.util.*;
import bookshare.business.User;
import java.sql.SQLException;

/**
 * UserManager.java This class implements methods for handling all User related
 * actions.
 *
 * getUser() - Retrieves user object for the given email address
 * getUser() - Retrieves user object for the given userID
 * createUser() - Creates a new user and writes to DB
 * checkUserExists() - Verifies existence of user in DB for a given email ID
 *
 * @author Sonika Rajan
 * @version 1.0
 */
public class UserManager {

    /**
     * Get User - Retrieves user object for the given email address 
     *      (1)Generate query to select user for given emailId
     *      (2)Execute Prepared Statement
     *      (3)Create User object from result
     *      (4)return user
     *
     * @param email
     * @return user of type User
     */
    public static User getUser(String email) {
        User user = new User();
        ConnectionPool pool = null;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            // Connect to DB
            pool = ConnectionPool.getInstance();
            connection = ConnectionPool.getConnection();
            ps = null;
            // Generate query to select user for given emailId
            String query = "SELECT * FROM user WHERE EMAIL_ID = ?";
            // Creating Prepared Statement
            ps = connection.prepareStatement(query);
            ps.setString(1, email);
            // Execute Prepared Statement
            rs = ps.executeQuery();
            if (rs.next()) {
                // Create User object from result
                user.setUserId(rs.getInt("USER_ID"));
                user.setName(rs.getString("NAME"));
                user.setEmailId(rs.getString("EMAIL_ID"));
                user.setType(rs.getString("TYPE"));
            }
            // return user
            return user;

        } catch (ClassNotFoundException e) {
            System.out.println(e);
            return null;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }

    }
    
    /**
     * Get User - Retrieves user object for the given userId 
     *      (1)Generate query to select user for given emailId
     *      (2)Execute Prepared Statement
     *      (3)Create User object from result
     *      (4)return user
     *
     * @param userId
     * @return user of type User
     */
    public static User getUser(int userId) {
        User user = new User();
        ConnectionPool pool = null;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            // Connect to DB
            pool = ConnectionPool.getInstance();
            connection = ConnectionPool.getConnection();
            ps = null;
            // Generate query to select user for given emailId
            String query = "SELECT * FROM user WHERE USER_ID = ?";
            // Creating Prepared Statement
            ps = connection.prepareStatement(query);
            ps.setInt(1, userId);
            // Execute Prepared Statement
            rs = ps.executeQuery();
            if (rs.next()) {
                // Create User object from result
                user.setUserId(rs.getInt("USER_ID"));
                user.setName(rs.getString("NAME"));
                user.setEmailId(rs.getString("EMAIL_ID"));
                user.setType(rs.getString("TYPE"));
            }
            // return user
            return user;

        } catch (ClassNotFoundException e) {
            System.out.println(e);
            return null;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }

    }

    /**
     * Create User - Creates a new user and writes to DB
     *      (1)Set statement to insert user into DB
     *      (2)Create Prepared Statement and assign values to query from user object
     *      (3)Execute Prepared Statement
     *      (4)Retrieve newly created User object from DB
     *      (5)return user
     *
     * @param user
     * @return user of type User
     */
    public static User createUser(User user) {
        ConnectionPool pool = null;
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            // Connect to DB
            pool = ConnectionPool.getInstance();
            connection = ConnectionPool.getConnection();
            // Set statement to insert user into DB
            String query = "INSERT INTO user (NAME,EMAIL_ID,TYPE)" + " VALUES (?,?,?)";
            // Create Prepared Statement and assign values to query from user
            // object
            ps = connection.prepareStatement(query);
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmailId());
            ps.setString(3, user.getType());
            // Execute Prepared Statement
            ps.executeUpdate();
            // Retrieve newly created User object from DB
            user = getUser(user.getEmailId());
            // return user
            return user;

        } catch (ClassNotFoundException e) {
            System.out.println(e);
            return null;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    /**
     * Check if User Exists - Verifies existence of user in DB for a given email ID
     *      (1)Generate query to select user for given emailId
     *      (2)Execute Prepared Statement
     *      (3)If a user is returned flag is set to true
     *      (4)return userExists flag
     * @param email
     * @return userExists flag of type Boolean
     */
    public static Boolean checkUserExists(String email){
        Boolean userExists = false;
        ConnectionPool pool = null;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            // Connect to DB
            pool = ConnectionPool.getInstance();
            connection = ConnectionPool.getConnection();
            ps = null;
            // Generate query to select user for given emailId
            String query = "SELECT * FROM user WHERE EMAIL_ID = ?";
            // Creating Prepared Statement
            ps = connection.prepareStatement(query);
            ps.setString(1, email);
            // Execute Prepared Statement
            rs = ps.executeQuery();
            if (rs.next()) {
                //If a user is returned flag is set to true
                userExists = true;
            }
            // return userExists flag
            return userExists;
        } catch (ClassNotFoundException e) {
            System.out.println(e);
            return null;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
}
