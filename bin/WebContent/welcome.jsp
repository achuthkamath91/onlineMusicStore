<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/sidebar.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<section>	
	<c:url var="adminURL" value="admin/login" />
	<c:url var="userURL" value="/home.html" />
	<h1>Welcome to the Music Shop System</h1>
	<h2>Great Music's for Free!</h2>
	<p>Order a music, check on its progress. All you need is to register
		Select the type of genere.</p>
		<a href = "${adminURL}" title = "Admin login" >Admin Page</a>
		<a href = "${userURL}" title = "User home" >User Page</a>
</section>
<jsp:include page="/includes/footer.jsp" />

