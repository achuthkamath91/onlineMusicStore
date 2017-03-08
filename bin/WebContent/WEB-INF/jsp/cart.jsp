<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="/includes/header.jsp"/>
<jsp:include page="/includes/sidebar.jsp"/>

<content>
<h1>Welcome to the Cart Page!</h1>

<div class="cart-page-outer">
	<div class="cart-inner">
	<c:url var="catalogURL" value="/catalog.html" />
	<c:url var="cartURL" value="/cart.html" />
	<c:choose>
	<c:when test="${cart.items.size()==0 }">
		<h1>Your cart is empty!!!</h1>
	</c:when>
	<c:otherwise>
	<c:forEach var="item" items="${cart.items}">
	<div class="cart-row">
		<div>Item Description: <%-- ${item.product.description} --%><a href="${productURL}?productCode=${item.product.code}" title="product page">${item.product.description}</a></div>
		<div>Item Quantity:
			<form action="${cartURL}" method="post">
				<input type="hidden" name="productCode" value="${item.product.code}">
				<input type="text" size="2" name="quantity" value="${item.quantity }">
				<input type="submit" value="UPDATE">
			</form>
		</div>
		<div>Product Price: ${item.product.getPrice()}</div>
		<div>Total: ${item.calculateItemTotal()}</div>
		<div><a href="${cartURL}?productCode=${item.product.code}">DELETE</a>
		</div>
	</div>
	</c:forEach>
	</c:otherwise>
	</c:choose>
	
	<div class="buttons">
		<a href="${catalogURL}">Continue Shopping</a>
		<a href="<c:url value='/checkout.html' />">Checkout</a>
	</div>
	
	</div>
</div>

</content>
<!--  end middle column -->

<jsp:include page="/includes/footer.jsp" />