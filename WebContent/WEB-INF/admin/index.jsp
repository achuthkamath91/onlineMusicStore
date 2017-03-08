<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/sidebar.jsp" />

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<section>
	<c:url var="allinvoices" value="/admin/invoices" />
	<c:url var="unprocessed" value="/admin/process" />
	<c:url var="downloads" value="/admin/downloads" />
	<c:url var="initialize" value="/admin/initializedb" />
	<c:url var="adminWelcome" value="/admin/welcome" />
	<c:url var="logout" value="/admin/logout" />
	<h1>Welcome to the Music Shop Admin System</h1>
	<h2>Great Music's for Free!</h2>
	
	<div class="menu">
	
		<a href="${initialize}">Initialize database</a><br/>
		<a href="${downloads}">downloads</a><br/>
		<a href="${allinvoices}">All Invoices</a><br/>
		<a href="${unprocessed}">Unprocessed Invoices</a><br/>
		<a href="${logout}">logout</a><br/>
	</div>
	
</section>
<jsp:include page="/includes/footer.jsp" />

