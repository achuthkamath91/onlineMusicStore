<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="/includes/header.jsp"/>
<jsp:include page="/includes/sidebar.jsp"/>

<content>
<h1>Welcome to the Register Page!</h1>

<div class="register-page-outer">
	<div class="inner">
		<form action="<c:url value='/register.html' />" method="post">
			<label>First Name:</label><input type="text" name="firstname" value="" placeholder="First Name" /><br/>
			<label>Last Name:</label><input type="text" name="lastname" value="" placeholder="Last Name" /><br/>
			<label>Email :</label><input type="email" name="email" value="" placeholder="Email" /><br/>			
			<!--<label>Company Name :</label><input type="text" name="companyname" value="" placeholder="Company Name" /><br/>			
			<label>Address 1 :</label><input type="text" name="address1" value="" placeholder="Address" /><br/>
			<label>Address 2 :</label><input type="text" name="address2" value="" placeholder="Address" /><br/>
			<label>city:</label><input type="text" name="city" value="" placeholder="City"/>-->
			<input type="button" name="reset" value = "Reset"/>
			<input type="submit" name="submit" value ="SUBMIT"/>
		</form>
	</div>
</div>

</content>
<!--  end middle column -->

<jsp:include page="/includes/footer.jsp" />