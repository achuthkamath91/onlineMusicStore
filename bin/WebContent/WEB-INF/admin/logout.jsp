<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/sidebar.jsp" />

<!-- start the middle column -->

<section>
<c:url var="welcome" value="/welcome" />
<h1>You are now logged out </h1>
<p>(i.e., your session is terminated)</p>
<a href="${welcomeURL}">Back to home site</a>
</section>

<!-- end the middle column -->

<jsp:include page="/includes/footer.jsp" />
