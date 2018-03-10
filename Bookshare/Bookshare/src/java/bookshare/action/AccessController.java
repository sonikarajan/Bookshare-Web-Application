/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookshare.action;

import bookshare.business.Ad;
import bookshare.business.User;
import bookshare.business.UserAdMap;
import bookshare.data.BookshareDAO;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author sonika
 */
public class AccessController {

    public static String handleLogin(HttpServletRequest request) {

        String url = null;
        HttpSession session = request.getSession();
        int errorFlag = 0;
        String email = request.getParameter("email");   //get user entered email
        String password = request.getParameter("password"); //get user entered password

        // get user for specified email
        User user = BookshareDAO.getUser(email);

        //code to validate email and password combination with database.
        if (user == null) {
            errorFlag = 1;
        } else if (!BookshareDAO.verifyPassword(user.getUserId(), password)) {
            errorFlag = 1;
        } //Code to check if User Type is Participant
        else if (user.getType().equals("User")) {
            //set as session attribute
            session.setAttribute("theUser", user);
            ArrayList<Ad> ads = BookshareDAO.getAds(null, null, null);

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
                if (myRequestedAds.contains(ad.getAdId())) {
                    ad.setStatus("interestExpressed");
                }
            }

            session.setAttribute("myRequestedAds", myRequestedAds);

            session.setAttribute("ads", ads);
            //forward to main.jsp
            url = "/buy-rent.jsp";
        } //Code to check if User Type is Admin
        else if (user.getType().equals("Admin")) {

            //set as session attribute
            session.setAttribute("theAdmin", user);
            
            ArrayList<Ad> ads = BookshareDAO.getReportedAds();
            session.setAttribute("ads", ads);
            //forward to admin.jsp
            url = "/admin-bs.jsp";
        }
        //Code to handle invalid credentials
        if (errorFlag == 1) {
            //message to be displayed
            String message = "Invalid Credentials";
            //set as session attribute
            session.setAttribute("msg", message);
            //forward url to login.jsp
            url = "/login.jsp";
        }
        
        return url;
    }

    public static String handleLogout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String url = null;
        if (session.getAttribute("theUser") != null || session.getAttribute("theAdmin") != null) {
            //forward url to home.jsp and destroy session
            session.invalidate();
            url = "/home.jsp";
        } else {
            //forward url to home.jsp
            url = "/home.jsp";
        }
        return url;
    }

    public static String createUser(HttpServletRequest request){

        int emailErrorFlag = 0;
        int pwdErrorFlag = 0;
        HttpSession session = request.getSession();
        String url = null;

        String name = request.getParameter("name");
        String email = request.getParameter("email");   //get user entered email
        String password = request.getParameter("password"); //get user entered password
        String confirm_password = request.getParameter("confirm_password"); //get user confirm password

        //code to validate email and password mismatch.
        if (BookshareDAO.checkUserExists(email)) {
            emailErrorFlag = 1;
        }
        if (!password.equals(confirm_password)) {
            pwdErrorFlag = 1;
        }
        if (emailErrorFlag == 1) {
            //message to be displayed
            String message = "Email already exists. Try Logging In or Register Using a Different Email.";
            //set as session attribute
            session.setAttribute("msg", message);
            //forward url to sign.jsp
            url = "/signup.jsp";
        } else if (pwdErrorFlag == 1) {
            //message to be displayed
            String message = "Passwords do not match";
            //set as session attribute
            session.setAttribute("msg", message);
            //forward url to sign.jsp
            url = "/signup.jsp";
        } else {
            //code to create new user and write to DB
            User user = new User();
            user.setName(name);
            user.setEmailId(email);
            user.setType("User");
            user = BookshareDAO.createUser(user);
            //hash and Salt the given password and store in DB
            int storePassword = BookshareDAO.storePassword(user.getUserId(), password);
            //set as session attribute
            session.setAttribute("theUser", user);
            ArrayList<Ad> ads = BookshareDAO.getAds(null, null, null);
            session.setAttribute("ads", ads);
            //forward url to main.jsp
            url = "/buy-rent.jsp";
        }
        return url;
    }
}
