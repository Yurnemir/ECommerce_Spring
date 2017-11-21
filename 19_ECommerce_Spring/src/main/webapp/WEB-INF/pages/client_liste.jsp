<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Recapitulatif Clients</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
	<!-- =========================== Barre Navigation =========================== -->
	<nav class="navbar navbar-inverse">
	<div class="container-fluid">
		<div class="navbar-header">
			<a class="navbar-brand"
				href="${pageContext.request.contextPath}/admin/connexion">Portail
				Admin</a>
		</div>
		<ul class="nav navbar-nav">
			<c:if test="${sessionScope.role.nomRole == 'ROLE_ADMIN_CATEGORIE'}">
				<li><a
					href="${pageContext.request.contextPath}/admin/categorie/recap"><i
						class="glyphicon glyphicon-book"></i> Categories</a></li>
			</c:if>
			<li><a
				href="${pageContext.request.contextPath}/admin/produit/recap"><i
					class="glyphicon glyphicon-barcode"></i> Produits</a></li>
			<c:if test="${sessionScope.role.nomRole == 'ROLE_ADMIN_CATEGORIE'}">
				<li class="active"><a href="#"><i
						class="glyphicon glyphicon-user"></i> Clients</a></li>
			</c:if>
		</ul>
		<ul class="nav navbar-nav navbar-right">
			<li><a href='<c:url value="/deconnexion"/>'><span
					class="glyphicon glyphicon-log-out"></span>Deconnexion</a></li>
		</ul>
	</div>
	</nav>
	<br />
	<br /> 
	<!-- =========================== Contenu Page =========================== -->
	<h1 align="center">Recapitulatif Clients</h1>
	<br />
	<div align="center">
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
	</div>
	<br/>
	<br/>
	<br/>
	
	<h1 style="text-align: center;">Liste des commande par clients</h1>
	<br/>
	<div class="panel-group" id="accordion">
	<c:forEach var="client" items="${listeClient}">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h4 class="panel-title">
					<a data-toggle="collapse" data-parent="#accordion" href="#collapse${client.idClient}">Client : ${client.nomClient} - ${client.email}</a>
				</h4>
			</div>
			<div id="collapse${client.idClient}" class="panel-collapse collapse">
				<c:forEach var="commande" items="${client.commandes}">
					<h3>Commande du ${commande.dateCommande}</h3>
					<c:forEach var="ligneCommande" items="${commande.listeLigneCommande}">
						<table class="table table-bordered">
							<tr>
								<th>Produit</th>
								<th>Quantite</th>
								<th>Prix Unitaire</th>
								<th>Prix Total</th>
							</tr>
							<tr>
								<th>${ligneCommande.produit.designation}</th>
								<th>${ligneCommande.quantite}</th>
								<th>${ligneCommande.produit.prix}</th>
								<th>${ligneCommande.prix}</th>
							</tr>
						</table>
					</c:forEach>
				</c:forEach>
			</div>
		</div>
	</c:forEach>
	</div>

</body>
</html>