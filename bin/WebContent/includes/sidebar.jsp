	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<aside id="sidebar">
    <nav>
        <c:url var="welcomeURL" value="/welcome.html" />
        <c:url var="homeURL" value="/home.html" />
        <c:url var="catalogURL" value="/catalog.html" />
        <c:url var="registerURL" value='/register.html'/>
        <c:url var="logoutURL" value='/logout.html'/>

        <h4>Links</h4>
		<div class = "side-links" >
			<div class="menu">
				<a class = "side-link" href="${welcomeURL}" title = "Site Home page" >Site Home</a>
			</div>			
			<div class="menu">
				<a class = "side-link" href="${homeURL}" title = "Home page" >Home Page</a>
			</div>
			<div class="menu">
				<a class = "side-link" href="${catalogURL}" title = "Student page" >Catalog Page</a>
			</div>
			<div class="menu">
				<a class = "side-link" href="${registerURL}" title = "List page" > Register page</a>
				</div>
			<div class="menu">
				<a class = "side-link" href="${logoutURL}" title = "Logout" > Logout</a>
			</div>
		</div>
    </nav>
</aside>