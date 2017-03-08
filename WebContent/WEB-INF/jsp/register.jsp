<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="/includes/header.jsp"/>
<jsp:include page="/includes/sidebar.jsp"/>
<%
	String nextURL = (String) request.getParameter("url");
	String trackNum = (String) request.getParameter("trackNum");
	String productCode = (String) request.getParameter("productCode");	
	String error = (String) session.getAttribute("error");
%>
<content>
<h1>Welcome to the Register Page!</h1>

<div class="register-page-outer">
	<div class="inner">
	<% if(error != null){%>
		<div><p><%= error%></p></div>
	<% }%>
		<form name="register" action="<c:url value='/register.html' />" onSubmit="return formValidation();" method="post">
			<label>First Name:</label><input type="text" name="firstname" id="firstname" placeholder="First Name" /><br/>
			<label>Last Name:</label><input type="text" name="lastname" id="lastname" placeholder="Last Name" /><br/>
			<label>Email :</label><input type="email" name="email" id="email" placeholder="Email" /><br/>			
			<input type="button" name="reset" value = "Reset"/>
			<input type="submit" name="submit" value ="SUBMIT"/>
			
		<input type="hidden" name="trackNum" value="<%= trackNum%>"><br>
		<input type="hidden" name="productCode" value="<%=productCode%>"><br>	
		</form>
	</div>
</div>

</content>
<!--  end middle column -->

<jsp:include page="/includes/footer.jsp" />