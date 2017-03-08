<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/sidebar.jsp" /> 


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<content>
<h1>Your Order</h1>
<c:choose>
<c:when test="${invoice==null}">
<h1>Your order failed</h1>
</c:when>
<c:otherwise>
<h1>Your order has been placed successfully.Thank you</h1>

<p> ${user.firstname} </p>
<p> ${user.lastname} </p>
<p> ${user.emailAddress} </p>
<p> ${invoice.invoiceId} </p>
<p> ${invoice.totalAmount}</p>

<div>
	<c:forEach var="item" items="${invoice.getLineItems()}">
	
		<div>Id : ${item.lineitemId}</div></br>
		<div>Description: ${item.product.description}</div></br>
		<div>Qty: ${item.quantity}</div></br>
		<div>Price: ${item.product.getPrice()}</div></br>
		<div>Amount: ${item.calculateItemTotal()}</div></br>	
	
	</c:forEach>
	
	
</div>
</c:otherwise>
</c:choose>
<br><br>
</content>

<jsp:include page="/includes/footer.jsp" />