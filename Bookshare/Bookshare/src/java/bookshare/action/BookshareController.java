
package bookshare.action;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 
 * @author Sonika Rajan
 */
public class BookshareController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String url = "";

        // get current action
        String action = request.getParameter("action");
        
        if (action == null) {
            url = "/home.jsp";  // default action
        }
        //Controls Login related tasks
        else if (action.equals("login")){
            url = AccessController.handleLogin(request);
        }
        //Control Logout related tasks
        else if (action.equals("logout")){
            url = AccessController.handleLogout(request);
        }
        //Controls Create User related Tasks
        else if (action.equals("createUser")){
            url = AccessController.createUser(request);
        }
        //Controls Ad search related tasks
        else if (action.equals("search")){
            url = AdController.getSearchResults(request);
        }
        //Controls contact seller related tasks
        else if (action.equals("contactSeller")){
            url = TransactionController.contactSeller(request);
        }
        //Controls report to admin related tasks
        else if (action.equals("reportToAdmin")){
            url = TransactionController.reportToAdmin(request);
        }
        //Controls My Ads redirection
        else if (action.equals("myAds")){
            url = AdController.getMyAds(request);
        }
        //Controls create ad redirection
        else if (action.equals("createAdRedirect")){
            url = AdController.createAdRedirect(request);
        }
        //Controls Create Ad related tasks
        else if (action.equals("createAd")){
            url = AdController.createAd(request);
        }
        //Controls edit Ad redirection
        else if (action.equals("editAd")){
            url = AdController.editAd(request);
        }
        //Controls update Ad related tasks
        else if (action.equals("updateAd")){
            url = AdController.updateAd(request);
        }
        //Controls Ad disable related tasks
        else if (action.equals("disable")){
            url = AdController.disableAd(request);
        }
        //Controls Ad  enable related tasks
        else if (action.equals("enable")){
            url = AdController.enableAd(request);
        }
        //Controls approve contact redirection
        else if (action.equals("approveRequestRedirect")){
            url = AdController.approveRequest(request);
        }
        //Controls approve contact related tasks
        else if (action.equals("approveContactRequest")){
            url = AdController.approveContactRequest(request);
        }
        //Controls reject contact related tasks
        else if (action.equals("rejectContactRequest")){
            url = AdController.rejectContactRequest(request);
        }
        //Controls Remove Ad related tasks
        else if (action.equals("removeAd")){
            url = AdminController.removeAd(request);
        }
        //Controls Unblock Ad related tasks
        else if (action.equals("unblockAd")){
            url = AdminController.unblockAd(request);
        }
        

        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }

}
