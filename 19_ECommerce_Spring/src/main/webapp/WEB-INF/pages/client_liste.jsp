<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<title>Insert title here</title>
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
				<li class="dropdown"><a class="dropdown-toggle"
					data-toggle="dropdown" href="#">Categories <span class="caret"></span>
				</a>
					<ul class="dropdown-menu">
						<li><a
							href="${pageContext.request.contextPath}/admin/categorie/recap">Recap</a></li>
						<li><a
							href="${pageContext.request.contextPath}/admin/categorie/ajout">Ajout</a></li>
						<li><a
							href="${pageContext.request.contextPath}/admin/categorie/modif">Modification</a></li>
						<li><a
							href="${pageContext.request.contextPath}/admin/categorie/suppr">Suppression</a></li>
					</ul></li>
			</c:if>
			<li class="dropdown"><a class="dropdown-toggle"
				data-toggle="dropdown" href="#">Produits <span class="caret"></span>
			</a>
				<ul class="dropdown-menu">
					<li><a
						href="${pageContext.request.contextPath}/admin/produit/recap">Recap</a></li>
					<li><a
						href="${pageContext.request.contextPath}/admin/produit/ajout">Ajout</a></li>
					<li><a
						href="${pageContext.request.contextPath}/admin/produit/modif">Modification</a></li>
					<li><a
						href="${pageContext.request.contextPath}/admin/produit/suppr">Suppression</a></li>
				</ul></li>

			<c:if test="${sessionScope.role.nomRole == 'ROLE_ADMIN_CATEGORIE'}">
				<li class="dropdown"><a class="dropdown-toggle"
					data-toggle="dropdown" href="#">Clients <span class="caret"></span>
				</a>
					<ul class="dropdown-menu">
						<li><a
							href="${pageContext.request.contextPath}/admin/categorie/clients/recap">Liste
								des clients</a></li>
						<li><a
							href="${pageContext.request.contextPath}/admin/categorie/clients/recapCommande">Bilan d'activité</a></li>
					</ul></li>
			</c:if>

		</ul>



		<ul class="nav navbar-nav navbar-right">
			<li><a href='<c:url value="/deconnexion"/>'><span
					class="glyphicon glyphicon-log-out"></span> Deconnexion</a></li>
		</ul>
	</div>
	</nav>
	<br />
	<br />

	<h1 align="center">Liste des clients :</h1>

	<table class="table table-bordered">
		<tr>
			<th>Id du client</th>
			<th>Nom</th>
			<th>Adresse</th>
			<th>Mail</th>
			<th>Téléphone</th>
			<th>Code Personnel</th>
		</tr>

		<c:forEach var="client" items="${listeClient}">
			<tr>
				<th>${client.idClient}</th>
				<th>${client.nomClient}</th>
				<th>${client.adresse}</th>
				<th>${client.email}</th>
				<th>${client.tel}</th>
				<th>${client.codePerso}</th>



			</tr>
		</c:forEach>


	</table>


</body>
</html>