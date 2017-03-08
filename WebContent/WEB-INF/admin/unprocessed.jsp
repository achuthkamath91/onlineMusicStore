<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/sidebar.jsp" /> 


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:url var="adminWelcome" value="/admin/welcome" />
<c:url var="unprocessed" value="/admin/process" />
<content>
<h1>Unprocessed Invoices</h1>

<c:choose>
<c:when test="${invoices.size()==0 }">
<h1>No unprocessed invoices to show</h1>
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
		<div>Update:
			<form action="${unprocessed}" method="post">
				<input type="hidden" name="invoiceId" value="${invoice.getInvoiceId() }">
				<input type="submit" name="process" value="Process">
				</form>
		</div>
	</div>	
	</c:forEach>
	
</div>
</c:otherwise>
</c:choose>
<a href="${adminWelcome}">Back to Admin Home</a>
</content>

<jsp:include page="/includes/footer.jsp" /> 