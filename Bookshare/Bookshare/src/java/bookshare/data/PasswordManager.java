package bookshare.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import bookshare.util.*;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

/**
 * PasswordManager.java
 * This class implements methods for handling all Password related actions.
 * 
 * storePassword() - Stores password for a given userId
 * verifyPassword() -  Confirms password match for a given userId and password input
 * getSalt() -  Retrieves the Salt value for a given userId
 * checkHash() -  Confirms match for inputed hash and saved hash for a given userId.
 * 
 * @author Sonika Rajan
 * @version 1.0
 */
public class PasswordManager {

	/**
	 * Store Password - Stores the user password for the give userId
	 * 		(1)Generates salt
	 * 		(2)Generates hashed password from input password and salt
	 * 		(3)Saves generated hash into DB
	 * @param userId
	 * @param password
	 * @return integer 1 if store is success
	 */
	public static int storePassword(int userId, String password){
		
		ConnectionPool pool = null;
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			// Create a new random salt
			String salt = PasswordUtil.creatSalt();
			// Create a new hash with given password and salt
			String hash = PasswordUtil.hashAndSaltPassword(password, salt);
			
			// Connect to DB
			pool = ConnectionPool.getInstance();
			connection = ConnectionPool.getConnection();
			ps = null;
			// SQL Query for INSERT
			String query = "INSERT INTO user_key_map VALUES (?,?,?)";
			// Creating Prepared Statement
			ps = connection.prepareStatement(query);
			ps.setInt(1, userId);
			ps.setString(2, salt);
			ps.setString(3, hash);
			// Execute Prepared Statement and return status of type int
			return ps.executeUpdate();
		} catch (ClassNotFoundException e) {
			System.out.println(e);
			return 0;
		} catch (NoSuchAlgorithmException e) {
                    System.out.println(e);
                    return 0;
            } catch (SQLException e) {
                System.out.println(e);
                return 0;
            } finally {
			DBUtil.closePreparedStatement(ps);
			pool.freeConnection(connection);
		}
	}

	/**
	 * Verify Password - Verifies if the input password with saved password for the give userId
	 * 		(1)Retrieves salt from DB for given userId
	 * 		(2)Generates hashed password from input password and salt
	 * 		(3)Checks if generated hash matches with saved hash in DB for given userId
	 * @param userId
	 * @param password
	 * @return true if match is success
	 */
	public static Boolean verifyPassword(int userId, String password){
		// Retrieves salt from DB for given userId
		String salt = getSalt(userId);
		if (salt == null) {
			return false;
		}
		try {
			// Generates hashed password from input password and salt
			String hash = PasswordUtil.hashAndSaltPassword(password, salt);
                        // Checks if generated hash matches with saved hash in DB for given userId
                    return checkHash(userId, hash);
		} catch (NoSuchAlgorithmException e) {
			System.out.println(e);
			return null;
		}
	}

	/**
	 * Get Salt - Retrieves salt from DB for the given userID
	 * @param userId
	 * @return salt of type String
	 */
	private static String getSalt(int userId){
		String salt = null;
		ConnectionPool pool = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			// Connect to DB
			pool = ConnectionPool.getInstance();
			connection = ConnectionPool.getConnection();
			ps = null;
			rs = null;
			// SQL Query for retrieving salt from DB
			String query = "SELECT SALT FROM user_key_map WHERE USER_ID = ?";
			// Creating Prepared Statement
			ps = connection.prepareStatement(query);
			ps.setInt(1, userId);
			// Execute Prepared Statement and store result into result set
			rs = ps.executeQuery();
			if (rs.next()) {
				salt = rs.getString("SALT");
			}
			return salt;

		} catch (Exception e) {
			System.out.println(e);
			return null;
		} finally {
			DBUtil.closeResultSet(rs);
			DBUtil.closePreparedStatement(ps);
			pool.freeConnection(connection);
		}
	}

	/**
	 * Check Hash - Verifies if the given hash matches with saved hash for the given userId
	 * @param userId
	 * @param hash
	 * @return true if the match is success
	 */
	private static Boolean checkHash(int userId, String hash){
		ConnectionPool pool = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			// Connect to DB
			pool = ConnectionPool.getInstance();
			connection = ConnectionPool.getConnection();
			ps = null;
			rs = null;
			// SQL Query for retrieving userId from DB for given
			String query = "SELECT USER_ID FROM user_key_map WHERE USER_ID = ?"
					+ " AND HASH = ?";
			ps = connection.prepareStatement(query);
			ps.setInt(1, userId);
			ps.setString(2, hash);
			rs = ps.executeQuery();
			if (rs.next()) {
				if (userId == rs.getInt("USER_ID")) {
					return true;
				}
			}
			return false;
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
