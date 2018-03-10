package bookshare.data;

import java.util.ArrayList;

import bookshare.business.*;

/**
 * BookshareDAO.java
 * This class acts as a virtual interface for all Data Access methods
 * 
 * storePassword() - Stores password for a given userId
 * verifyPassword() -  Confirms password match for a given userId and password input
 * getAds() - Retrieves the list of ads satisfying given filters
 * getMyAds() - Retrieves the list of ads that was created by owner
 * createAd() - Writes the newly created ad into DB
 * getAd() - Retrieves ad for a given adId
 * getReportedAds() - Retrieves the list of ads that was reported to Admin
 * updateAd() - Updates the new values for the add passed in argument into DB
 * getMyRequestedAds() - Retrieves the list of ads for the given userId
 * getUser() - Retrieves user object for the given email address
 * getUser() - Retrieves user object for the given userID
 * createUser() - Creates a new user and writes to DB
 * checkUserExists() - Verifies existence of user in DB for a given email ID
 * getUserAdMap() - Retrieves UserAdMap object for the given mapId
 * getPendingRequests() - Retrieves pending requests for approval by seller/renter
 * expressInterest() - Creates new entry in user_ad_map table
 * approveRequest() - Marks the User ad map as Approved
 * rejectRequest() - Marks the User ad map as Rejected 
 * 
 * @author Sonika Rajan
 * @version 1.0
 */
public class BookshareDAO {

	public static int storePassword(int userId, String password){
		return PasswordManager.storePassword(userId, password);
	}
	
	public static Boolean verifyPassword(int userId, String password){
		return PasswordManager.verifyPassword(userId, password);
	}
	
	public static ArrayList<Ad> getAds(String subject, String rentOrSell,
			User user){
		return AdManager.getAds(subject, rentOrSell, user);
	}
        
        public static ArrayList<Ad> getMyAds(int ownerId){
            return AdManager.getMyAds(ownerId);
        }
	
	public static int createAd(Ad ad){
		return AdManager.createAd(ad);
	}
	
	public static Ad getAd(int adId){
		return AdManager.getAd(adId);
	}
        
        public static ArrayList<Ad> getReportedAds(){
            return AdManager.getReportedAds();
        }
        
        public static int updateAd(Ad ad){
            return AdManager.updateAd(ad);
        }
	
	public static ArrayList<String> getMyRequestedAds(User user){
		return AdManager.getMyRequestedAds(user);
	}
	
	public static User getUser(String email){
		return UserManager.getUser(email);
	}
        
        public static User getUser(int userId){
		return UserManager.getUser(userId);
	}
	
	public static User createUser(User user) {
		return UserManager.createUser(user);
	}
        
        public static Boolean checkUserExists(String email){
            return UserManager.checkUserExists(email);
        }
	
	public static UserAdMap getUserAdMap(int mapId) {
            return UserAdMapManager.getUserAdMap(mapId);
        }
        
        public static ArrayList<UserAdMap> getPendingRequests(int adId){
		return UserAdMapManager.getPendingRequests(adId);
	}
	
	public static int expressInterest(UserAdMap userAdMap){
		return UserAdMapManager.expressInterest(userAdMap);
	}
        
        public static int approveRequest(int mapId){
            return UserAdMapManager.approveRequest(mapId);
        }
        
        public static int rejectRequest(int mapId){
            return UserAdMapManager.rejectRequest(mapId);
        }
}
