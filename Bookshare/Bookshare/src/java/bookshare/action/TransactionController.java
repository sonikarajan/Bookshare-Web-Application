
package bookshare.action;

import bookshare.business.Ad;
import bookshare.data.*;
import bookshare.business.User;
import bookshare.business.UserAdMap;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author sonika
 */
public class TransactionController {
    
    public static String contactSeller(HttpServletRequest request){
        HttpSession session = request.getSession();
        String url = null;
        User user = (User) session.getAttribute("theUser");
        
        int adId = Integer.parseInt(request.getParameter("adId"));
        int userId = user.getUserId();
        String status = "Pending";
        
        UserAdMap userAdMap = new UserAdMap();
        userAdMap.setAdId(adId);
        userAdMap.setUserId(userId);
        userAdMap.setStatus(status);
        
        BookshareDAO.expressInterest(userAdMap);
        
        ArrayList<Ad> ads = BookshareDAO.getAds(null, null, null);
        
        
        User userTemp = (User) session.getAttribute("theUser");
        ArrayList<Integer> myRequestedAds = new ArrayList<Integer>();
        ArrayList<UserAdMap> pendingRequestsTemp = new ArrayList<UserAdMap>();
        for (Ad ad : ads) {
            pendingRequestsTemp = BookshareDAO.getPendingRequests(ad.getAdId());
            for (UserAdMap userAdMapTemp : pendingRequestsTemp) {
                if (userAdMapTemp.getUserId() == userTemp.getUserId()) {
                    myRequestedAds.add(userAdMapTemp.getAdId());
                }
            }
        }
        
        for (Ad ad : ads) {
            if(myRequestedAds.contains(ad.getAdId())){
                ad.setStatus("interestExpressed");
            }
        }        
        
        session.setAttribute("ads", ads);
        url = "/confirmcontact.jsp";
        
        return url;
    }
    
    public static String reportToAdmin(HttpServletRequest request){
        HttpSession session = request.getSession();
        String url = null;
        String adId = request.getParameter("adId");
        Ad ad = BookshareDAO.getAd(Integer.parseInt(adId));
        ad.setStatus("Reported");
        int updateAd = BookshareDAO.updateAd(ad);
        
        ArrayList<Ad> ads = BookshareDAO.getAds(null, null, null);
        session.setAttribute("ads", ads);
        url = "/confirmreport.jsp";
        return url;        
    }
    
}
