<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="/includes/header.jsp"/>
<jsp:include page="/includes/sidebar.jsp"/>
<%
	String nextURL = (String) request.getParameter("url");
	String trackNum = (String) request.getParameter("trackNum");
	String productCode = (String) request.getParameter("productCode");
%>
<section>

<h1>Login Form</h1>
<p>Please enter a username and password to continue.</p>
<div class="login-page">
	<form action="<c:url value='/login.html' />"  method="post">
		<label>Username</label>
		<input type="email" name="email"><br>
		
		<input type="hidden" name="trackNum" value="<%=trackNum%>"><br>
		<input type="hidden" name="productCode" value="<%=productCode%>"><br>		
		<input type="hidden" name="nextUrl" value="<%=nextURL%>"><br>
		<input type="submit" value="Login">
	</form>
</div>
</section>


<jsp:include page="/includes/footer.jsp" />