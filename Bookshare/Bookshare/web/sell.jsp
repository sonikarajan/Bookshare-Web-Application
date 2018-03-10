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
    <h1>My Ads</h1>
    
<table class="responstable">
    <tbody><tr>
            <th>Book Name</th>
            <th>Subject</th>		
            <th>Cover</th>
            <th>Price</th>
            <th>Rent/Sell</th>
            <th></th>
            <th></th>
        </tr>
        <c:forEach var="ad" items="${ads}">
   <tr>
            <td><a href="${ad.url}"><c:out value="${ad.bookName}" /></a></td>
            <td><c:out value="${ad.subject}" /></td>
            <td><img src="${ad.image}"</td>
            <td>$ <c:out value="${ad.price}" /></td>
            <td><c:out value="${ad.rentOrSell}" /></td>
            <td>
                    <form action="BookshareController" method="post">
                       <input type="hidden" name="action" value=editAd>
                       <input type="hidden" name="adId" value="<c:out value='${ad.adId}' />">
                        <input type="submit" class="participate_button" value="Edit" />
                    </form>
                </td>
                <%--Inserted Code to create new column for Report button--%>
                <td>
                    <c:if test="${ad.status=='Active'}">
                        <form action="BookshareController" method="post">
                            <input type="hidden" name="adId" value="<c:out value='${ad.adId}' />">
                            <input type="hidden" name="action" value=disable>
                            <input type="submit" class="participate_button" value="Disable"/>
                        </form>
                    </c:if>
                    <c:if test="${ad.status=='Disable'}">
                        <form action="BookshareController" method="post">
                            <input type="hidden" name="adId" value="<c:out value='${ad.adId}' />">
                            <input type="hidden" name="action" value=enable>
                            <input type="submit" class="participate_button" value="Enable"/>
                        </form>
                    </c:if>
                    
                </td>
    </tr>
  </c:forEach>
</tbody>
</table>
</section>
<%-- Include tag is used to import footer page --%>

<%@ include file="footer.jsp" %>