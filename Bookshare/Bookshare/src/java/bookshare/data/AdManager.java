package bookshare.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bookshare.util.ConnectionPool;
import bookshare.util.DBUtil;
import bookshare.business.*;

/**
 * AdManager.java
 * This class implements methods for handling all Ad related actions.
 * 
 * getAds() - Retrieves the list of ads satisfying given filters
 * createAd() - Writes the newly created ad into DB
 * getAd() - Retrieves ad for a given adId
 * getReportedAds() - Retrieves the list of ads that was reported to Admin
 * setValuesToPreparedStatement() - Assign values to prepared statement
 * generateQuery() - Generate query based on given filters
 * getMyRequestedAds() - Retrieves the list of ads for the given userId
 * 
 * @author Sonika Rajan
 * @version 1.0
 */
public class AdManager {

	/**
	 * Get Ads - Retrieves the list of ads satisfying given filters
	 * 		(1)Generate query for the given filters
	 * 		(2)Assign Values to Prepared Statement
	 * 		(3)Execute Prepared Statement
	 * 		(4)Create Ad object for each result and add to Array List
	 * 		(5)Return List of ads
	 * 
	 * @param subject
	 * @param rentOrSell
	 * @param user
         * @param ownerId
	 * @return List of ads of type Ad
	 */
	public static ArrayList<Ad> getAds(String subject, String rentOrSell,
			User user){

		ArrayList<Ad> ads = new ArrayList<Ad>();
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
			// Generate query for the given filters
			String query = generateQuery(subject, rentOrSell, user);
			// Creating Prepared Statement
			ps = connection.prepareStatement(query);
			// Assign Values to Prepared Statement
			ps = setValuesToPreparedStatement(ps, subject, rentOrSell, user);
			// Execute Prepared Statement
			rs = ps.executeQuery();
			while (rs.next()) {
				// Create Ad object for each result and add to Array List
				Ad ad = new Ad();
				ad.setAdId(rs.getInt("AD_ID"));
				ad.setBookName(rs.getString("BOOK_NAME"));
				ad.setSubject(rs.getString("SUBJECT"));
				ad.setUrl(rs.getString("URL"));
				ad.setImage(rs.getString("IMAGE_PATH"));
				ad.setPrice(rs.getInt("PRICE"));
				ad.setRentOrSell(rs.getString("RENT_OR_SELL"));
				ad.setStatus(rs.getString("STATUS"));
				ad.setOwnerId(rs.getInt("OWNER_ID"));
				ads.add(ad);
			}
			// Return List of ads
			return ads;

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
	 * Get My Ads - Retrieves the list of ads that was created by owner
	 * 		(1)Generate query for the given filters
	 * 		(2)Assign Values to Prepared Statement
	 * 		(3)Execute Prepared Statement
	 * 		(4)Create Ad object for each result and add to Array List
	 * 		(5)Return List of ads
	 * 
         * @param ownerId
	 * @return List of ads of type Ad
	 */
	public static ArrayList<Ad> getMyAds(int ownerId){

		ArrayList<Ad> ads = new ArrayList<Ad>();
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
			// Generate query for the given filters
			String query = "SELECT * FROM ad WHERE (STATUS='Active' OR STATUS='Disable') AND OWNER_ID = ?";
			// Creating Prepared Statement
			ps = connection.prepareStatement(query);
			// Assign Values to Prepared Statement
			ps.setInt(1, ownerId);
			// Execute Prepared Statement
			rs = ps.executeQuery();
			while (rs.next()) {
				// Create Ad object for each result and add to Array List
				Ad ad = new Ad();
				ad.setAdId(rs.getInt("AD_ID"));
				ad.setBookName(rs.getString("BOOK_NAME"));
				ad.setSubject(rs.getString("SUBJECT"));
				ad.setUrl(rs.getString("URL"));
				ad.setImage(rs.getString("IMAGE_PATH"));
				ad.setPrice(rs.getInt("PRICE"));
				ad.setRentOrSell(rs.getString("RENT_OR_SELL"));
				ad.setStatus(rs.getString("STATUS"));
				ad.setOwnerId(rs.getInt("OWNER_ID"));
				ads.add(ad);
			}
			// Return List of ads
			return ads;

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
	 * Get Reported Ads - Retrieves the list of ads that was reported to Admin
	 * 		(1)Generate query for the given filters
	 * 		(2)Assign Values to Prepared Statement
	 * 		(3)Execute Prepared Statement
	 * 		(4)Create Ad object for each result and add to Array List
	 * 		(5)Return List of ads
	 * 
	 * @return List of ads of type Ad
	 */
	public static ArrayList<Ad> getReportedAds(){

		ArrayList<Ad> ads = new ArrayList<Ad>();
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
			// Generate query to get reported Ads
			String query = "SELECT * FROM ad WHERE STATUS=\"Reported\"";
			// Creating Prepared Statement
			ps = connection.prepareStatement(query);
			// Execute Prepared Statement
			rs = ps.executeQuery();
			while (rs.next()) {
				// Create Ad object for each result and add to Array List
				Ad ad = new Ad();
				ad.setAdId(rs.getInt("AD_ID"));
				ad.setBookName(rs.getString("BOOK_NAME"));
				ad.setSubject(rs.getString("SUBJECT"));
				ad.setUrl(rs.getString("URL"));
				ad.setImage(rs.getString("IMAGE_PATH"));
				ad.setPrice(rs.getInt("PRICE"));
				ad.setRentOrSell(rs.getString("RENT_OR_SELL"));
				ad.setStatus(rs.getString("STATUS"));
				ad.setOwnerId(rs.getInt("OWNER_ID"));
				ads.add(ad);
			}
			// Return List of ads
			return ads;

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
	 * Create Ad - Writes the newly created ad into DB
	 * 		(1)Set statement to insert ad into DB
	 * 		(2)Create Prepared Statement and assign values to query from ad object
	 * 		(3)Execute Prepared Statement and return status of type int
	 * 
	 * @param ad
	 * @return status of the DB update of the type int
	 */
	public static int createAd(Ad ad){
		ConnectionPool pool = null;
		Connection connection = null;
		PreparedStatement ps = null;
		try {
			// Connect to DB
			pool = ConnectionPool.getInstance();
			connection = ConnectionPool.getConnection();
			ps = null;
			// Set statement to insert ad into DB
			String query = "INSERT INTO ad (BOOK_NAME,SUBJECT,URL,IMAGE_PATH,PRICE,RENT_OR_SELL,STATUS,OWNER_ID)"
					+ " VALUES (?,?,?,?,?,?,?,?)";
			// Create Prepared Statement and assign values to query from ad
			// object
			ps = connection.prepareStatement(query);
			ps.setString(1, ad.getBookName());
			ps.setString(2, ad.getSubject());
			ps.setString(3, ad.getUrl());
			ps.setString(4, ad.getImage());
			ps.setInt(5, ad.getPrice());
			ps.setString(6, ad.getRentOrSell());
			ps.setString(7, ad.getStatus());
			ps.setInt(8, ad.getOwnerId());
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
	 * Get Ad - Retrieves ad for a given adId
	 * 		(1)Generate query to select ad for given adId
	 * 		(2)Execute Prepared Statement
	 * 		(3)Create Ad object from result
	 * 		(4)return ad
	 * 
	 * @param adId
	 * @return ad of type Ad
	 */
	public static Ad getAd(int adId){
		Ad ad = new Ad();
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
			// Generate query to select ad for given adId
			String query = "SELECT * FROM ad WHERE AD_ID = ?";

			// Creating Prepared Statement
			ps = connection.prepareStatement(query);
			ps.setInt(1, adId);
			// Execute Prepared Statement
			rs = ps.executeQuery();
			if (rs.next()) {
				// Create Ad object from result
				ad.setAdId(rs.getInt("AD_ID"));
				ad.setBookName(rs.getString("BOOK_NAME"));
				ad.setSubject(rs.getString("SUBJECT"));
				ad.setUrl(rs.getString("URL"));
				ad.setImage(rs.getString("IMAGE_PATH"));
				ad.setPrice(rs.getInt("PRICE"));
				ad.setRentOrSell(rs.getString("RENT_OR_SELL"));
				ad.setStatus(rs.getString("STATUS"));
				ad.setOwnerId(rs.getInt("OWNER_ID"));
			}
			// return ad
			return ad;

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
	 * Update Ad - Updates the new values for the add passed in argument into DB
	 * 		(1)Set statement to update ad into DB
	 * 		(2)Create Prepared Statement and assign values to query from ad object
	 * 		(3)Execute Prepared Statement and return status of type int
	 * 
	 * @param ad
	 * @return status of the DB update of the type int
	 */
        public static int updateAd(Ad ad){
            ConnectionPool pool = null;
            Connection connection = null;
            PreparedStatement ps = null;
            try {
                // Connect to DB
                pool = ConnectionPool.getInstance();
                connection = ConnectionPool.getConnection();
                ps = null;
                // Set statement to insert ad into DB
                String query = "UPDATE ad SET BOOK_NAME=?,SUBJECT=?,URL=?,IMAGE_PATH=?,PRICE=?,RENT_OR_SELL=?,STATUS=?,OWNER_ID=?"
                        + " WHERE AD_ID = ?";
                // Create Prepared Statement and assign values to query from ad
                // object
                ps = connection.prepareStatement(query);
                ps.setString(1, ad.getBookName());
                ps.setString(2, ad.getSubject());
                ps.setString(3, ad.getUrl());
                ps.setString(4, ad.getImage());
                ps.setInt(5, ad.getPrice());
                ps.setString(6, ad.getRentOrSell());
                ps.setString(7, ad.getStatus());
                ps.setInt(8, ad.getOwnerId());
                ps.setInt(9, ad.getAdId());
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
	 * Set Values to Prepares Statement - Assign values to prepared statement
	 * 		(1)Set subject to ps if present
	 * 		(2)Set rentOrSell filter to ps if present
	 * 		(3)Set user filter if present
	 * 		(4)return ps with assigned values
	 * 
	 * @param ps
	 * @param subject
	 * @param rentOrSell
	 * @param user
	 * @return Prepared Statement with values set
	 */
	private static PreparedStatement setValuesToPreparedStatement(
			PreparedStatement ps, String subject, String rentOrSell, User user){

		Boolean subjectFlag = false;
		Boolean rentOrSellFlag = false;

		try {
			// Set subject to ps if present
			if (subject != null) {
				ps.setString(1, subject);
				subjectFlag = true;
			}
			// Set rentOrSell filter to ps if present
			if (rentOrSell != null) {
				if (subjectFlag == true)
					ps.setString(2, rentOrSell);
				else
					ps.setString(1, rentOrSell);
				rentOrSellFlag = true;
			}
                        // Set user filter if present
			if (user != null) {
				ArrayList<String> myRequestedAds = getMyRequestedAds(user);
				int myRequestedAdsCounter = 0;
				int psPositionIndicator = 1;
				if (myRequestedAds != null) {

					if ((rentOrSellFlag == true) && (subjectFlag == true))
						psPositionIndicator = 3;
					else if ((rentOrSellFlag == true) || (subjectFlag == true))
						psPositionIndicator = 2;

					ps.setString(psPositionIndicator,
							myRequestedAds.get(myRequestedAdsCounter));
					int numberOfMyRequestedAds = myRequestedAds.size() - 1;
					while (numberOfMyRequestedAds > 0) {
						psPositionIndicator = psPositionIndicator + 1;
						myRequestedAdsCounter = myRequestedAdsCounter + 1;
						ps.setString(psPositionIndicator,
								myRequestedAds.get(myRequestedAdsCounter));
						numberOfMyRequestedAds = numberOfMyRequestedAds - 1;
					}
				}
			}
                        
			// return ps with assigned values
			return ps;
		} catch (SQLException e) {
			System.out.println(e);
			return null;
		}
	}

	/**
	 * Generate Query - Generate query based on given filters
	 * 		(1)Create base SQL query
	 * 		(2)Set subject where clause if subject filter present
	 * 		(3)Set rentOrSell where clause if rentOrSell filter present
	 * 		(4)Set user where clause if user filter present
	 * 		(5)return fully formed query
	 * 
	 * @param subject
	 * @param rentOrSell
	 * @param user
	 * @return generated query of type String
	 */
	private static String generateQuery(String subject, String rentOrSell,
			User user){

		// Create base SQL query
		String query = "SELECT * FROM ad WHERE STATUS = \"Active\"";

		// Set subject where clause if subject filter present
		if (subject != null) {
			query = query.concat(" AND SUBJECT = ?");
		}
		// Set rentOrSell where clause if rentOrSell filter present
		if (rentOrSell != null) {
			query = query.concat(" AND RENT_OR_SELL = ?");
		}
                // Set user where clause if user filter present
		if (user != null) {
			ArrayList<String> myRequestedAds = getMyRequestedAds(user);
			if (myRequestedAds != null) {

				query = query.concat(" AND AD_ID IN (?");
				int numberOfMyRequestedAds = myRequestedAds.size() - 1;
				while (numberOfMyRequestedAds > 0) {
					query = query.concat(",?");
					numberOfMyRequestedAds = numberOfMyRequestedAds - 1;
				}
				query = query.concat(")");
			}
		}
                                
		// return fully formed query
		return query;
	}

	/**
	 * My Requested Ads - Retrieves the list of ads for the given userId
	 * 		(1)Set statement to retrieve adId for given userId
	 * 		(2)Execute Prepared Statement
	 * 		(3)Set adId's to list from Result Set
	 * 		(4)Return Set of adId's
	 * 
	 * @param user
	 * @return ArrayList of adId's of type String
	 */
	public static ArrayList<String> getMyRequestedAds(User user){

		ArrayList<String> myRequestedAds = new ArrayList<String>();
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
			// Set statement to retrieve adId for given userId
			String query = "SELECT AD_ID FROM user_ad_map WHERE USER_ID = ?";

			// Creating Prepared Statement
			ps = connection.prepareStatement(query);
			ps.setInt(1, user.getUserId());
			// Execute Prepared Statement
			rs = ps.executeQuery();
			while (rs.next()) {
				// Set adId's to list from Result Set
				myRequestedAds.add(rs.getString("AD_ID"));
			}
			// Return Set of adId's
			return myRequestedAds;

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
