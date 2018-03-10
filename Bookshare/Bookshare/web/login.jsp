<%-- Include tag is used to import header page --%>
<%@include file="header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- Section to input login details --%>
<section id="login_form">
    
    <%-- Code to create login form--%>
    <form action="BookshareController" method="post" id="login_for_user">
        <input type="hidden" name="action" value="login">
        <label >Email Address *</label>
        <input type="email" name="email" required/> <br><br>
        <label >Password *</label>
        <input type="password" name="password" required/><br>
        <!-- display error message -->
        <p><span id="msg_display">${msg}</span></p>
        <label>&nbsp;</label>
        <input type="submit" value="Log in" id="login_button" >
    </form>
     
    <%-- Code to go to Sign up for a new account  --%>
    <p><a href="signup.jsp" id="sign_up_link">Sign up for a new account</a></p>
    
</section>
    
<%-- Include tag is used to import footer page --%>
<%@include file="footer.jsp" %>