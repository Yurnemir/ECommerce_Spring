<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
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
			<a class="navbar-brand" href="#">Ecommerce</a>
		</div>
		<ul class="nav navbar-nav">
			<li><a href="${pageContext.request.contextPath}/accueil">Accueil</a></li>
			<li><a
				href="${pageContext.request.contextPath}/panier/affichagePanier">Panier</a></li>
			<li class="active"><a
				href="${pageContext.request.contextPath}/afficheClient">Client</a></li>


		</ul>
		<ul class="nav navbar-nav navbar-right">
			<li><a href="${pageContext.request.contextPath}/admin/connexion"><span
					class="glyphicon glyphicon-log-in"></span> Connexion</a></li>
		</ul>
	</div>
	</nav>
	<br />
	<br />




	<h1>${messageErreur}</h1>
	<div align="center">
		<h3>Indiquer vos identifiants pour savoir quelles commandes vous avez effectué</h3>
		<form:form class="form-horizontal" action="affichageCommande"
			method="POST" modelAttribute="clientChercheCommande">
			<div class="form-group">

				Mail :
				<form:input path="email" />
				Code Personnel :
				<form:input path="codePerso" />
				<input type="submit" value="Soumettre le formulaire">
			</div>
		</form:form>
	</div>
	<c:forEach var="commande" items="${listeCommande}">
		<h1 align="center" style="color: red">Commande du
			${commande.dateCommande}</h1>
		<table class="table table-bordered">
			<tr>
				<th>Produit</th>
				<th>Quantite</th>
				<th>Prix Unitaire</th>
				<th>Prix Total</th>

			</tr>

			<c:forEach var="ligneCommande" items="${commande.listeLigneCommande}">
				<tr>
					<th>${ligneCommande.produit.designation}</th>
					<th>${ligneCommande.quantite}</th>
					<th>${ligneCommande.produit.prix}</th>
					<th>${ligneCommande.prix}</th>

				</tr>
			</c:forEach>

		</table>
	</c:forEach>



</body>
</html>