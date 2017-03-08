<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/sidebar.jsp" /> 


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

	<c:url var="adminWelcome" value="/admin/welcome" />
<content>
<h1>All downloads</h1>
<c:choose>
<c:when test="${downloads.size()==0 }">
	<h1>No downloads to show</h1>
</c:when>
<c:otherwise>
<div class="downloads-inner">

	<c:forEach var="download" items="${downloads}">
	<div class="download-row">
		<div>UserName: <b>${download.getUserFullName()}</b></div>
		<div>Download Date: <b>${download.getDownloadDate()}</b></div>
		<div>ProductCode: <b>${download.getProductCode()}</b></div>
		<div>TrackTitle: <b>${download.getTrackTitle()}</b></div>
	</div>	
	</c:forEach>
</div>
</c:otherwise>
</c:choose>
<a href="${adminWelcome}">Back to Admin Home</a>
</content>

<jsp:include page="/includes/footer.jsp" /> 