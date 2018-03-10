<%-- Include tag is used to import header page --%>
<%@ include file="header.jsp" %>
     

<%-- Section to display studies and participate in that study--%>
<section class="participate">
      
    <%-- Display the studies in the table --%>
    <%-- Clicking on Participate button displays Question.jsp page where 
            you can rate the question--%>
    <h1>Reported Ads</h1>
<table class="responstable">
    <tbody>
        <tr>
            <th>Book Name</th>
            <th>Cover</th>
            <th>Ad ID</th>		
            <th>Action</th>
        </tr>
  
        <c:forEach var="ad" items="${ads}">
         <tr>
             <td><a href="${ad.url}"><c:out value="${ad.bookName}" /></a></td>
            <td><img src="${ad.image}"/></td>
            <td><c:out value="${ad.adId}" /></td>
            <td>
                    <form action="BookshareController" method="post" id="approve">
                       <input type="hidden" name="action" value=removeAd>
                       <input type="hidden" name="adId" value="${ad.adId}">
                        <input type="submit" class="admin_button" value="Remove Ad" />
                    </form>
                    <form action="" method="post" id="disapprove">
                      <input type="hidden" name="action" value=unblockAd>
                      <input type="hidden" name="adId" value="${ad.adId}">
                        <input type="submit" class="admin_button" value="Unblock Ad"/>
                    </form>
            </td>
    </tr>
        </c:forEach>
  
</tbody>
</table>
</section>
<%-- Include tag is used to import footer page --%>
<%@ include file="footer.jsp" %>