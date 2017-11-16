<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
	<title>Recapitulatif Categories</title>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
	<!-- =========================== Barre Navigation =========================== -->
	<nav class="navbar navbar-inverse">
		<div class="container-fluid">
			<div class="navbar-header">
				<a class="navbar-brand" href="#">Ecommerce</a>
			</div>
			<ul class="nav navbar-nav">
				<c:if test="${sessionScope.role.nomRole == 'ROLE_ADMIN_CATEGORIE'}">
					<li class="dropdown">
						<a class="dropdown-toggle" data-toggle="dropdown" href="#">Categories
							<span class="caret"></span>
						</a>
						<ul class="dropdown-menu">
							<li><a href="${pageContext.request.contextPath}/admin/categorie/recap">Recap</a></li>
							<li><a href="${pageContext.request.contextPath}/admin/categorie/ajout">Ajout</a></li>
							<li><a href="${pageContext.request.contextPath}/admin/categorie/modif">Modification</a></li>
							<li><a href="${pageContext.request.contextPath}/admin/categorie/suppr">Suppression</a></li>
						</ul>
					</li>
				</c:if>
				<li class="dropdown">
					<a class="dropdown-toggle" data-toggle="dropdown" href="#">Produits
						<span class="caret"></span>
					</a>
					<ul class="dropdown-menu">
						<li><a href="${pageContext.request.contextPath}/admin/produit/recap">Recap</a></li>
						<li><a href="${pageContext.request.contextPath}/admin/produit/ajout">Ajout</a></li>
						<li><a href="${pageContext.request.contextPath}/admin/produit/modif">Modification</a></li>
						<li><a href="${pageContext.request.contextPath}/admin/produit/suppr">Suppression</a></li>
					</ul>
				</li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<li><a href='<c:url value="/deconnexion"/>'><span class="glyphicon glyphicon-log-out"></span>Deconnexion</a></li>
			</ul>
		</div>
	</nav>
	<br/><br/>
	
	<!-- =========================== Contenu Page =========================== -->
	<h1 align="center">Liste des categories :</h1>
	<br/>
	<div align="center">
		<table class="table table-bordered">
			<tr>
				<th>ID</th>
				<th>Nom</th>
				<th>Description</th>
				<th>operation</th>
			</tr>
			<c:forEach var="categorie" items="${categories}">
				<tr>
					<td>${categorie.idCategorie}</td>
					<td>${categorie.nomCategorie}</td>
					<td>${categorie.description}</td>
					<td><a href="${pageContext.request.contextPath}/admin/categorie/supprViaLien/${categorie.idCategorie}">Supprimer</a></td>
				</tr>
			</c:forEach>
		</table>
	</div>
</body>
</html>