<%-- Note: we can't usefully put the page directive with the desired character
    encoding (UTF-8) here in this file even though we want the same one in all 
    pages because the JSP spec says "Only the character encoding specified for 
    the requested page is used; the encodings of files included via the include 
    directive are not taken into consideration." (JSP.4.2 Response Char Encoding)
    JSP will generate a HTML <meta> tag for UTF-8 from this info. 
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<title>Music Shop</title>
<link rel="stylesheet" href="<c:url value='/styles/main.css'/> ">
<script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
 <link rel="shortcut icon" href="<c:url value='/images/image1.png'/>">
<body>
	<header>
		<div class="logo">
			<img src="<c:url value='/images/image2.png'/>" alt="Music icon" width="100%">
		</div>	
		<div class="page">
			<h1>Music Shop</h1>
		</div>
	</header>
	
	