<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
	<title>Admin</title>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
	<h2><a href='<c:url value="/deconnexion"/>'>deconnexion</a></h2>
	<br/>
	<h1>Categories</h1>
	<h2><a href="${pageContext.request.contextPath}/admin/categorie/recap">recap</a></h2>
	<h2><a href="${pageContext.request.contextPath}/admin/categorie/ajout">ajout</a></h2>
	<h2><a href="${pageContext.request.contextPath}/admin/categorie/modif">modif</a></h2>
	<h2><a href="${pageContext.request.contextPath}/admin/categorie/suppr">suppr</a></h2>
	<br/>
	<h1>Produits</h1>
	<h2><a href="${pageContext.request.contextPath}/admin/produit/recap">recap</a></h2>
	<h2><a href="${pageContext.request.contextPath}/admin/produit/ajout">ajout</a></h2>
	<h2><a href="${pageContext.request.contextPath}/admin/produit/modif">modif</a></h2>
	<h2><a href="${pageContext.request.contextPath}/admin/produit/suppr">suppr</a></h2>
</body>
</html>