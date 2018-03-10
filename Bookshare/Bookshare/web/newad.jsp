
<%-- Include tag is used to import header page --%>
<%@ include file="header.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- script tag is used to link javascript file --%>
<script type="text/javascript" src="js/answers.js"></script>

<nav id="menu">
<ul>
  <li><a href="BookshareController?action=search">Buy/Rent</a></li>
  <li class="selected"><a href="BookshareController?action=myAds" id="buy">Sell</a></li>
  <li><br></li>
  <li><br></li>
  <li><a href="BookshareController?action=myAds">My Ads</a></li>
  <li><a href="BookshareController?action=createAdRedirect">Create Ad</a></li>
  <li><a href="BookshareController?action=approveRequestRedirect">Approve Request</a></li>
</ul>
</nav>
<h1>Create Ad</h1>
<%-- Section to create new study --%>
<section id="newstudy_form">

    <form action="BookshareController" method="post">
        <input type="hidden" name="action" value="createAd" />
        <label>Book Name *</label>
        <input type="text" name="bookName" required /><br>
        <label>Subject *</label>
        <select name="subject" required>
            <option>Network Based Application Development</option>
            <option>Human Computer Interaction</option>
            <option>Software System Design and Implementation</option>
            <option>Principles of Information Security</option>
        </select><br>
        <label>URL *</label>
        <input type="text" name="url" required/><br>
        <%-- Img tag is used to import image --%>
        <label>Image *</label>
        <input type="text" name="image" required /><br>
        <label>Price *</label>  
        <input type="text" name="price"  required/><br>
        <label>Rent/Sell *</label> 
        <select name="rentOrSell" required>
            <option>Rent</option>
            <option>Sell</option>
        </select><br>
       <input type="submit" value="Create" id="search_button" />
    </form>
</section>
<%-- Include tag is used to import footer page --%>
<%@ include file="footer.jsp" %>