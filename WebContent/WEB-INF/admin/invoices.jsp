<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="/includes/header.jsp"/>
<jsp:include page="/includes/sidebar.jsp"/>

<content>
<h1>Welcome to the Invoice Page!</h1>

<c:url var="adminWelcome" value="/admin/welcome" />
<content>
<h1>All Invoices</h1>
<c:choose>
<c:when test="${invoices.size()==0 }">
	<h1>No Invoices to show</h1>
</c:when>
<c:otherwise>
<div class="invoices-inner">

	<c:forEach var="invoice" items="${invoices}">
	<div class="invoices-row">
		<div>Invoice-id: <b>${invoice.getInvoiceId()}</b></div>
		<div>User Name: <b>${invoice.getUserFullName()}</b></div>
		<div>Invoice Date: <b>${invoice.getInvoiceDate()}</b></div>
		<div>Total Amount: <b>${invoice.getTotalAmount()}</b></div>
		<div>Is Processed?: <b>${invoice.isProcessed()}</b></div>
	</div>	
	</c:forEach>
</div>
</c:otherwise>
</c:choose>
<a href="${adminWelcome}">Back to Admin Home</a>

</content>
<!--  end middle column -->

<jsp:include page="/includes/footer.jsp" />