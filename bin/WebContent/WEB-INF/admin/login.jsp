<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/sidebar.jsp"/>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

	<c:url var="adminWelcome" value="/admin/welcome" />
<content>
	<h1>Admin Login</h1>
	<section class="admin-login">
			<p style="text-align:center;color: red">	${error}</p>

		<form name="login" method="post" action="${adminWelcome}" onSubmit="return formValidation();" class="login">
			

			<p><label for="username">Username:</label><input type="text" name="username" 	id="username" placeholder="Enter Email ID" required> <span	id="username"></span>
			</p>
			<p><label for="password">Password:</label><input type="password" name="password" id="password" placeholder="Enter Email ID" required> <span id="password"></span>
			</p>

			
			<p class="login-submit">
				<button type="submit" class="login-button">Login</button>
			</p>

		</form>
	</section>
</content>
<jsp:include page="/includes/footer.jsp" />