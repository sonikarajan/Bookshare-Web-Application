<%-- Include tag is used to import header page --%>
<%@include file="header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--Code to signup form --%>
<section id="signup_form">
    <section>
        <form action="BookshareController" method="post">
            <input type="hidden" name="action" value="createUser" />
            <label>Name *</label>
            <input type="text" name="name" required/> <br><br>
            <label>Email *</label>
            <input type="email" name="email" required/> <br><br>
            <label>Password *</label>
            <input type="password" name="password" required/> <br><br>
            <label>Confirm Password *</label>
            <input type="password" name="confirm_password" required /> <br>
            <p><span id="msg_display">${msg}</span></p>
            <input type="submit" value="Create Account" id="signup_button">
            <br>
        </form>
    </section>
</section>
<%-- Include tag is used to import footer page --%>
<%@include file="footer.jsp" %>