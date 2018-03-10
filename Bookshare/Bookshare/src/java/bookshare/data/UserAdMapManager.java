package bookshare.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import bookshare.util.ConnectionPool;
import bookshare.util.DBUtil;
import bookshare.business.*;
import java.sql.SQLException;

/**
 * UserAdMapManager.java
 * This class implements methods for handling all UserAdMap related actions.
 * 
 * getUserAdMap() - Retrieves UserAdMap object for the given mapId
 * getPendingRequests() - Retrieves pending requests for approval by seller/renter
 * expressInterest() - Creates new entry in user_ad_map table
 * approveRequest() - Marks the User ad map as Approved
 * rejectRequest() - Marks the User ad map as Rejected 
 * 
 * @author Sonika Rajan
 * @version 1.0
 */
public class UserAdMapManager {

        /**
         * Get UserAdMap - Retrieves UserAdMap object for the given mapId
         *      (1)Generate query to select user for given emailId 
         *      (2)Execute Prepared Statement 
         *      (3)Create UserAdMap object from result 
         *      (4)return user
         *
         * @param mapId
         * @return user of type userAdMap
         */
        public static UserAdMap getUserAdMap(int mapId) {
            UserAdMap userAdMap = new UserAdMap();
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
                String query = "SELECT * FROM user_ad_map WHERE MAP_ID = ?";
                // Creating Prepared Statement
                ps = connection.prepareStatement(query);
                ps.setInt(1, mapId);
                // Execute Prepared Statement
                rs = ps.executeQuery();
                if (rs.next()) {
                    // Create User object from result
                    userAdMap.setMapId(rs.getInt("MAP_ID"));
                    userAdMap.setAdId(rs.getInt("AD_ID"));
                    userAdMap.setUserId(rs.getInt("USER_ID"));
                    userAdMap.setStatus(rs.getString("STATUS"));
                }
                // return userAdMap
                return userAdMap;

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
	 * Get Pending Requests - Retrieves pending requests for approval by seller/renter
	 * 		(1)Set query to retrieve UserAdMap data from DB
	 * 		(2)Execute Prepared Statement
	 * 		(3)Create userAdMap object from result
	 * 		(4)Add userAdMap object to the ArrayList
	 * 		(5)return List of userAdMap objects
	 * 
	 * @param adId
	 * @return List of userAdMap objects
	 */
	public static ArrayList<UserAdMap> getPendingRequests(int adId){
		ArrayList<UserAdMap> userAdMaps = new ArrayList<UserAdMap>();
		
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
			// Set query to retrieve UserAdMap data from DB
			String query = "SELECT MAP_ID,BOOK_NAME, PRICE, NAME,STATUS,result.USER_ID,AD_ID FROM"
					+ " (SELECT MAP_ID,BOOK_NAME, PRICE, USER_ID,STATUS,adRef.AD_ID FROM"
					+ " (SELECT AD_ID, BOOK_NAME, PRICE FROM ad WHERE AD_ID = ?) adRef"
					+ " INNER JOIN (SELECT AD_ID, USER_ID,MAP_ID,STATUS FROM user_ad_map"
					+ " WHERE STATUS = \"pending\") mapRef"
					+ " ON adRef.AD_ID = mapRef.AD_ID) result"
					+ " INNER JOIN (SELECT USER_ID, NAME FROM user) userRef"
					+ " ON result.USER_ID = userRef.USER_ID";

			// Creating Prepared Statement
			ps = connection.prepareStatement(query);
			ps.setInt(1, adId);
			// Execute Prepared Statement
			rs = ps.executeQuery();
			while (rs.next()) {
				UserAdMap userAdMap = new UserAdMap();
				// Create userAdMap object from result
				userAdMap.setMapId(rs.getInt("MAP_ID"));
				userAdMap.setBookName(rs.getString("BOOK_NAME"));
				userAdMap.setPrice(rs.getInt("PRICE"));
				userAdMap.setName(rs.getString("NAME"));
				userAdMap.setStatus(rs.getString("STATUS"));
                                userAdMap.setUserId(rs.getInt("USER_ID"));
				userAdMap.setAdId(rs.getInt("AD_ID"));
				//Add userAdMap object to the ArrayList
				userAdMaps.add(userAdMap);
			}
			// return List of userAdMap objects
			return userAdMaps;

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
	 * Express Interest -  Creates new entry in user_ad_map table
	 * 		(1)Set statement to insert user-ad-map into DB
	 * 		(2)Create Prepared Statement and assign values to query from ad object
	 * 		(3)Execute Prepared Statement and return status of type int
	 * 
	 * @param userAdMap
	 * @return Update status of type int
	 */
	public static int expressInterest(UserAdMap userAdMap){
		ConnectionPool pool = null;
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			// Connect to DB
			pool = ConnectionPool.getInstance();
			connection = ConnectionPool.getConnection();
			ps = null;
			// Set statement to insert user-ad-map into DB
			String query = "INSERT INTO user_ad_map (AD_ID,USER_ID,STATUS)"
					+ " VALUES (?,?,?)";
			// Create Prepared Statement and assign values to query from ad object
			ps = connection.prepareStatement(query);
			ps.setInt(1, userAdMap.getAdId());
			ps.setInt(2, userAdMap.getUserId());
			ps.setString(3, userAdMap.getStatus());
			// Execute Prepared Statement and return status of type int
			return ps.executeUpdate();

		} catch (ClassNotFoundException e) {
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
         * Approve Request - Marks the User ad map as Approved
         *      (1)Set statement to update map status to Approved
         *      (2)Create Prepared Statement and assign values to query DB
         *      (3)Execute Prepared Statement and return status of type int
         * 
         * @param mapId
         * @return status of type int
         */
        public static int approveRequest(int mapId){
            ConnectionPool pool = null;
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			// Connect to DB
			pool = ConnectionPool.getInstance();
			connection = ConnectionPool.getConnection();
			ps = null;
			// Set statement to update map status to Approved
			String query = "UPDATE user_ad_map SET STATUS = \"Approved\""
					+ " WHERE MAP_ID = ?";
			// Create Prepared Statement and assign values to query DB
			ps = connection.prepareStatement(query);
			ps.setInt(1, mapId);
			// Execute Prepared Statement and return status of type int
			return ps.executeUpdate();

		} catch (ClassNotFoundException e) {
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
         * Reject Request - Marks the User ad map as Rejected
         *      (1)Set statement to update map status to Rejected
         *      (2)Create Prepared Statement and assign values to query DB
         *      (3)Execute Prepared Statement and return status of type int
         * 
         * @param mapId
         * @return status of type int
         */
        public static int rejectRequest(int mapId){
            ConnectionPool pool = null;
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			// Connect to DB
			pool = ConnectionPool.getInstance();
			connection = ConnectionPool.getConnection();
			ps = null;
			// Set statement to update map status tp Rejected
			String query = "UPDATE user_ad_map SET STATUS = \"Rejected\""
					+ " WHERE MAP_ID = ?";
			// Create Prepared Statement and assign values to query DB
			ps = connection.prepareStatement(query);
			ps.setInt(1, mapId);
			// Execute Prepared Statement and return status of type int
			return ps.executeUpdate();

		} catch (ClassNotFoundException e) {
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
}
