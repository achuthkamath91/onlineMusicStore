<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="/includes/header.jsp"/>
<jsp:include page="/includes/sidebar.jsp"/>

<content>
<h1>Welcome to the Product Page!</h1>
<p>Thanks for visiting. Make yourself at home. Feel free to browse through 
    our musical catalog. When you do, you can listen to samples from the
    albums on our site, or you can download selected sound files and listen 
    to them later. We think our catalog contains some great music, and we 
    hope you like it as much as we do.
</p>

	<div class="catalog-page-outer">
		<c:forEach var="product" items="${products}">
		<div class="catalog-row">
			<div>Product Code: ${product.code}</div>
			<div>Product Description: ${product.description}</div>
			<div>Product Price: ${product.getPrice()}</div>
			<div>
			<form action="<c:url value='/cart.html' />" method="post">
				<input type="hidden" name="productId" value="${product.id}">
				<input type="hidden" name="productCode" value="${product.code}">
				<input type="submit" name="addtocart" value="ADD TO CART">
			</form>
			<%-- <a href="<c:url value='/cart.html?productCode=${product.code}' />">Add to Cart	</a> --%>
			</div>
		</div>
		</c:forEach>
	</div>

</content>
<jsp:include page="/includes/footer.jsp" />