<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="/includes/header.jsp"/>
<jsp:include page="/includes/sidebar.jsp"/>
<%
	String nextURL = (String) request.getParameter("url");
%>

<content>
	<h1>Welcome to the Product Page!</h1>
	<p >Product Code: <b>${product.getCode()}</b></p>
	<p>Product Description: <b>${product.getDescription()}</b></p>
	<p>Price: <b>${product.getPrice()}</b></p>
	<div class="catalog-page-outer">
		
		<c:url var="listenURL" value="/listen.html" />
		<c:forEach var="track" items="${product.getTracks()}">
		<div class="track-row">
			<div>Song Title: ${track.getTitle()}</div>
			<div>Click to play : <a href="${listenURL}?proCode=${product.getCode()}&trackNum=${track.getTrackNumber()}">Play</a></div>
		</div>
		</c:forEach>
		<a href="<%=nextURL%>" title="<%=nextURL%>" >Back to Catalog</a>
	</div>

</content>
<jsp:include page="/includes/footer.jsp" />