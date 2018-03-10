<%-- Include tag is used to import header page --%>
<%@ include file="header.jsp" %>
     
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
<%-- Section to display studies and participate in that study--%>
<section class="participate">
      
    <%-- Display the studies in the table --%>
    <%-- Clicking on Participate button displays Question.jsp page where 
            you can rate the question--%>
    <h1>Approve Contact Requests</h1>
<table class="responstable">
    <tbody><tr>
            <th>Book Name</th>
            <th>Price</th>
            <th>Requester</th>
            <th></th>
            <th></th>
        </tr>
  
   <c:forEach var="pendingRequest" items="${pendingRequests}">
        <tr>
            <td><c:out value="${pendingRequest.bookName}" /></td>
            <td>$ <c:out value="${pendingRequest.price}" /></td>
            <td><c:out value="${pendingRequest.name}" /></td>
            <td>
                    <form action="BookshareController" method="post">
                        <input type="hidden" name="action" value=approveContactRequest>
                        <input type="hidden" name="mapId" value="${pendingRequest.mapId}">
                        <input type="submit" class="participate_button" value="Approve" />
                    </form>
                </td>
                <%--Inserted Code to create new column for Report button--%>
                <td>
                    <form action="BookshareController" method="post">
                        <input type="hidden" name="action" value=rejectContactRequest> 
                        <input type="hidden" name="mapId" value="${pendingRequest.mapId}">
                        <input type="submit" class="participate_button" value="Reject"/>
                    </form>
                </td>
    </tr>
   </c:forEach>
</tbody>
</table>
</section>
<%-- Include tag is used to import footer page --%>
<%@ include file="footer.jsp" %>