
package bookshare.action;

import bookshare.business.Ad;
import bookshare.data.BookshareDAO;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author sonika
 */
public class AdminController {

    static String removeAd(HttpServletRequest request) {

        HttpSession session = request.getSession();
        String url = null;
        String adId = request.getParameter("adId");
        Ad ad = BookshareDAO.getAd(Integer.parseInt(adId));
        ad.setStatus("Delete");
        int updateAd = BookshareDAO.updateAd(ad);
        ArrayList<Ad> ads = BookshareDAO.getReportedAds();
        session.setAttribute("ads", ads);
        session.setAttribute("actionStatus", "Delete");
        url = "/confirmremoval.jsp";
        return url;
    }

    static String unblockAd(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String url = null;
        String adId = request.getParameter("adId");
        Ad ad = BookshareDAO.getAd(Integer.parseInt(adId));
        ad.setStatus("Active");
        int updateAd = BookshareDAO.updateAd(ad);
        ArrayList<Ad> ads = BookshareDAO.getReportedAds();
        session.setAttribute("ads", ads);
        session.setAttribute("actionStatus", "Active");
        url = "/confirmunblock.jsp";
        return url;
    }
    
}
