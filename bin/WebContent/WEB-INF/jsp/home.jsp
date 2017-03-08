<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="/includes/header.jsp"/>
<jsp:include page="/includes/sidebar.jsp"/>

<content>
<h1>Hello user welcome to the Music Store!</h1>
<h2>Please select a Service:</h2>
	<c:url var="welcomeURL" value="/welcome.html" />
	<c:url var="catalogURL" value="/catalog.html" />
	<c:url var="cartURL" value="/cart.html" />

	<div class="user-menu">
		<div class="menu">
			<a href="${catalogURL}"> Browse Catalog </a>
		</div>
	<div class="menu">
		<a href="${cartURL}"> Show Cart</a>
	</div>	
	<div class="menu">
		<a href="${welcomeURL}"> Back to Site Homepage</a>
	</div>

	</div>
	
</content>
<jsp:include page="/includes/footer.jsp" />