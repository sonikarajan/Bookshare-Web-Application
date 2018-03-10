
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%-- title of the Page--%>
        <title>Bookshare</title>
        <%-- importing CSS stylesheet --%>
        <link rel="stylesheet" href="styles/main.css">
    </head>
    <body>
        <%-- Code to specify Header section of the page--%>
        <div id="header">
            <nav id="header_menu">
                <ul  class="left" >
                    <li><a href="">Bookshare</a></li>
                </ul>
                <ul class="right">                    
                    
                    <%-- Display Header Links when user is not logged in--%>
                    <c:if test="${theUser == null && theAdmin == null}">
                        <li><a href="login.jsp">Login</a></li>
                    </c:if>
                    
                    <%-- Display Header Links when user is logged in--%>
                    <c:if test="${theUser != null}">
                        <li id="display">Hello, ${theUser.name}</li>
                        <li><a href="BookshareController?action=logout">Logout</a></li>
                    </c:if>
                    
                    
                    <%-- Display Header Links when user is logged in as ADMIN--%>    
                    <c:if test="${theAdmin != null}">
                        <li id="display">Hello, ${theAdmin.name}</li>
                        <li><a href="BookshareController?action=logout">Logout</a></li>
                    </c:if>
                
                </ul>
            </nav>
        </div>

