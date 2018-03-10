<%-- Include tag is used to import header page --%>
<%@ include file="header.jsp" %>

<nav id="menu">
    <ul>
        <li class="selected"><a href="BookshareController?action=search" id="buy">Buy/Rent</a></li>
        <li><a href="BookshareController?action=myAds">Sell</a></li>
    </ul>

</nav>
<%-- Section to display studies and participate in that study--%>
<section class="participate">
      
    <%-- Display the studies in the table --%>
    <%-- Clicking on Participate button displays Question.jsp page where 
            you can rate the question--%>
  <h1>Buy/Rent</h1>   
    <div class="filter-body">
        <div id="top-filter">
            <form action="BookshareController" method="post">
           <input type="hidden" name="action" value="search"/>
           <label id="filter-label">Subject</label>
             <select name="subject">
                        <option value="All">All</option>
                        <option value="Network Based Application Development">Network Based Application Development</option>
                        <option value="Human Computer Interaction">Human Computer Interaction</option>
                        <option value="Software System Design and Implementation">Software System Design and Implementation</option>
                        <option value="Principles of Information Security">Principles of Information Security</option>
             </select><br>
            <label id="filter-label">Choose Option</label>
            <select name="rentOrSell">
                        <option value="Both">Buy/Rent</option>
                        <option value="Sell">Buy</option>
                        <option value="Rent">Rent</option>
            </select><br>
            <label id="filter-label">Show Ads</label>
            <select name="show">
                        <option value="all">All</option>
                        <option value="mine">My Favourites</option>
            </select><br>
            <input type="submit" value="Search" id="search_button" />
            <br>
        </form>
        </div>
    </div>

    

<table class="responstable">
    <tbody><tr>
            <th>Book Name</th>
            <th>Subject</th>		
            <th>Cover</th>
            <th>Price</th>
            <th>Buy/Rent</th>
            <th></th>
            <th></th>
        </tr>
  
        <c:forEach var="ad" items="${ads}">
        <tr>
            <td><a href="${ad.url}"><c:out value="${ad.bookName}" /></a></td>
            <td><c:out value="${ad.subject}" /></td>
            <td><img src="${ad.image}"</td>
            <td>$ <c:out value="${ad.price}" /></td>
            <td>           
                <c:if test="${ad.rentOrSell=='Sell'}">Buy</c:if>
                <c:if test="${ad.rentOrSell=='Rent'}">Rent</c:if>
            </td>  
                   
            <td>
                <c:if test="${ad.status=='interestExpressed'}">
                    Interest expressed
                </c:if>
                <c:if test="${ad.status=='Active'}">
                    <form action="BookshareController" method="post">
                        <input type="hidden" name="action" value="contactSeller">
                        <input type="hidden" name="adId" value="${ad.adId}">
                        <input type="submit" class="participate_button" value="Contact Seller" />
                    </form>
                </c:if>
            </td>
            <%--Inserted Code to create new column for Report button--%>
            <td>
                <form action="BookshareController" method="post">
                 <input type="hidden" name="action" value="reportToAdmin">
                 <input type="hidden" name="adId" value="${ad.adId}">
                    <input type="submit" class="participate_button" value="Report"/>
                </form>
            </td>
        </tr>
        </c:forEach>
    </tbody>
</table>
</section>
<%-- Include tag is used to import footer page --%>
<div id="footer">
<%@ include file="footer.jsp" %>
</div>