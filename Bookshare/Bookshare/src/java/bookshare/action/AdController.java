
package bookshare.action;

import bookshare.business.Ad;
import bookshare.business.User;
import bookshare.business.UserAdMap;
import bookshare.data.*;
import bookshare.util.MailUtil;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author sonika
 */
public class AdController {
    
    public static String getSearchResults(HttpServletRequest request){
        
        HttpSession session = request.getSession();
        User user = null;
        String url = null;
        String subject = request.getParameter("subject");
        String rentOrSell = request.getParameter("rentOrSell");
        String show = request.getParameter("show");
        if (subject != null) {
            if (subject.equals("All")) {
                subject = null;
            }
            if (rentOrSell.equals("Both")) {
                rentOrSell = null;
            }
            if (show.equals("all")) {
                user = null;
            } else if (show.equals("mine")) {
                user = (User) session.getAttribute("theUser");
            }
        }
        ArrayList<Ad> ads = BookshareDAO.getAds(subject, rentOrSell, user);
        
        
        User userTemp = (User) session.getAttribute("theUser");
        ArrayList<Integer> myRequestedAds = new ArrayList<Integer>();
        ArrayList<UserAdMap> pendingRequestsTemp = new ArrayList<UserAdMap>();
        for (Ad ad : ads) {
            pendingRequestsTemp = BookshareDAO.getPendingRequests(ad.getAdId());
            for (UserAdMap userAdMap : pendingRequestsTemp) {
                if (userAdMap.getUserId() == userTemp.getUserId()) {
                    myRequestedAds.add(userAdMap.getAdId());
                }
            }
        }
        
        for (Ad ad : ads) {
            if(myRequestedAds.contains(ad.getAdId())){
                ad.setStatus("interestExpressed");
            }
        }
        
        
        session.setAttribute("ads", ads);
        url="/buy-rent.jsp";
        return url;
    }
    
    public static String getMyAds(HttpServletRequest request){
        
        HttpSession session = request.getSession();
        String url = null;
        User user = (User) session.getAttribute("theUser");
        ArrayList<Ad> ads = BookshareDAO.getMyAds(user.getUserId());
        session.setAttribute("ads", ads);
        url="/sell.jsp";
        return url;
    }
    
    static String createAdRedirect(HttpServletRequest request) {
        return "/newad.jsp";    }
    
    public static String createAd(HttpServletRequest request){

        HttpSession session = request.getSession();
        String url = null;
        User user = (User) session.getAttribute("theUser");

        String bookName = request.getParameter("bookName");
        String subject = request.getParameter("subject");   
        String adUrl = request.getParameter("url"); 
        String image = request.getParameter("image"); 
        int price = Integer.parseInt(request.getParameter("price"));
        String rentOrSell = request.getParameter("rentOrSell");   
        String status = "Active"; 
        int ownerId = user.getUserId();

        
        //code to create new Ad and write to DB
        Ad ad = new Ad();
        ad.setBookName(bookName);
        ad.setSubject(subject);
        ad.setUrl(adUrl);
        ad.setImage(image);
        ad.setPrice(price);
        ad.setRentOrSell(rentOrSell);
        ad.setStatus(status);
        ad.setOwnerId(ownerId);
        //store ad in DB
        BookshareDAO.createAd(ad);
        //set as session attribute
        session.setAttribute("Ad", ad);
        //forward url to main.jsp
        url = "/confirmcreate.jsp";
       
        return url;
    }

    static String editAd(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String adId = request.getParameter("adId");
        Ad ad = BookshareDAO.getAd(Integer.parseInt(adId));
        session.setAttribute("ad",ad);
        return "/editad.jsp";
    }

    static String updateAd(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String url = null;
        Ad adTemp = (Ad) session.getAttribute("ad");
        int adId = adTemp.getAdId();
        
        User user = (User) session.getAttribute("theUser");

        String bookName = request.getParameter("bookName");
        String subject = request.getParameter("subject");   
        String adUrl = request.getParameter("url"); 
        String image = request.getParameter("image"); 
        int price = Integer.parseInt(request.getParameter("price"));
        String rentOrSell = request.getParameter("rentOrSell");   
        String status = "Active"; 
        int ownerId = user.getUserId();
        
        //code to create new Ad and write to DB
        Ad ad = new Ad();
        ad.setAdId(adId);
        ad.setBookName(bookName);
        ad.setSubject(subject);
        ad.setUrl(adUrl);
        ad.setImage(image);
        ad.setPrice(price);
        ad.setRentOrSell(rentOrSell);
        ad.setStatus(status);
        ad.setOwnerId(ownerId);
        BookshareDAO.updateAd(ad);
        ArrayList<Ad> ads = BookshareDAO.getMyAds(ad.getOwnerId());
        session.setAttribute("ads", ads);
        url = "/confirmupdate.jsp";
        return url;
        
    }

    static String disableAd(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String url = null;
        String adId = request.getParameter("adId");
        Ad ad = BookshareDAO.getAd(Integer.parseInt(adId));
        ad.setStatus("Disable");
        int updateAd = BookshareDAO.updateAd(ad);
        ArrayList<Ad> ads = BookshareDAO.getMyAds(ad.getOwnerId());
        session.setAttribute("ads", ads);
        session.setAttribute("actionStatus", "Disable");
        url = "/confirmed.jsp";
        return url;
    }

    static String enableAd(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String url = null;
        String adId = request.getParameter("adId");
        Ad ad = BookshareDAO.getAd(Integer.parseInt(adId));
        ad.setStatus("Active");
        int updateAd = BookshareDAO.updateAd(ad);
        ArrayList<Ad> ads = BookshareDAO.getMyAds(ad.getOwnerId());
        session.setAttribute("ads", ads);
        session.setAttribute("actionStatus", "Enable");
        url = "/confirmed.jsp";
        return url;
    }

    static String approveRequest(HttpServletRequest request) {
        HttpSession session = request.getSession();
        ArrayList<UserAdMap> pendingRequestsTemp = new ArrayList<UserAdMap>();
        ArrayList<UserAdMap> pendingRequests = new ArrayList<UserAdMap>();
        User user = (User) session.getAttribute("theUser");
        ArrayList<Ad> ads = BookshareDAO.getMyAds(user.getUserId());
        for (Ad ad : ads) {
            pendingRequestsTemp = BookshareDAO.getPendingRequests(ad.getAdId());
            for(UserAdMap userAdMap : pendingRequestsTemp){
                pendingRequests.add(userAdMap);
            }
        }
        session.setAttribute("pendingRequests", pendingRequests);
        return "/approverequest.jsp";
    }

    static String approveContactRequest(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String url = null;
        String mapId = request.getParameter("mapId");
        BookshareDAO.approveRequest(Integer.parseInt(mapId));

        ArrayList<UserAdMap> pendingRequests = new ArrayList<UserAdMap>();
        ArrayList<UserAdMap> pendingRequestsTemp = new ArrayList<UserAdMap>();
        User user = (User) session.getAttribute("theUser");
        ArrayList<Ad> ads = BookshareDAO.getMyAds(user.getUserId());
        for (Ad ad : ads) {
            pendingRequestsTemp = BookshareDAO.getPendingRequests(ad.getAdId());
            for (UserAdMap userAdMap : pendingRequestsTemp) {
                pendingRequests.add(userAdMap);
            }
        }
        session.setAttribute("pendingRequests", pendingRequests);
        url = "/confirmapprove.jsp";

        UserAdMap userAdMap = BookshareDAO.getUserAdMap(Integer.parseInt(mapId));
        User userForEmail = BookshareDAO.getUser(userAdMap.getUserId());
        Ad ad = BookshareDAO.getAd(userAdMap.getAdId());
        User userForOwner = BookshareDAO.getUser(ad.getOwnerId());
        try {
            MailUtil.sendMail(userForEmail.getEmailId(), "Bookshare", "Contact request confirmed", 
                    "Hi " + userForEmail.getName() + ",\n\nYour request for contact details have been approved by "
                    + userForOwner.getName() + " for the Ad for the book titled: " + ad.getBookName() + ".\n\n"
                    + "Contact Email Address: " + userForOwner.getEmailId()
                    + "\n\n Regards,\nBookshare Team", false);
        } catch (MessagingException ex) {
            Logger.getLogger(AdController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return url;
    }

    static String rejectContactRequest(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String url = null;
        String mapId = request.getParameter("mapId");
        BookshareDAO.rejectRequest(Integer.parseInt(mapId));

        ArrayList<UserAdMap> pendingRequests = new ArrayList<UserAdMap>();
        ArrayList<UserAdMap> pendingRequestsTemp = new ArrayList<UserAdMap>();
        User user = (User) session.getAttribute("theUser");
        ArrayList<Ad> ads = BookshareDAO.getMyAds(user.getUserId());
        for (Ad ad : ads) {
            pendingRequestsTemp = BookshareDAO.getPendingRequests(ad.getAdId());
            for(UserAdMap userAdMap : pendingRequestsTemp){
                pendingRequests.add(userAdMap);
            }
        }
        session.setAttribute("pendingRequests", pendingRequests);
        url = "/confirmreject.jsp";
        
        UserAdMap userAdMap = BookshareDAO.getUserAdMap(Integer.parseInt(mapId));
        User userForEmail = BookshareDAO.getUser(userAdMap.getUserId());
        Ad ad = BookshareDAO.getAd(userAdMap.getAdId());
        User userForOwner = BookshareDAO.getUser(ad.getOwnerId());
        try {
            MailUtil.sendMail(userForEmail.getEmailId(), "Bookshare", "Contact request rejected", 
                    "Hi " + userForEmail.getName() + ",\n\nYour request for contact details have been rejected by "
                    + userForOwner.getName() + " for the Ad for the book titled: " + ad.getBookName()
                    + "\n\n Regards,\nBookshare Team", false);
        } catch (MessagingException ex) {
            Logger.getLogger(AdController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return url;
    }

        
}
