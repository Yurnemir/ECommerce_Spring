<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<title>Panier</title>
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
				<a class="navbar-brand" href="#">Ecommerce</a>
			</div>
			<ul class="nav navbar-nav">
				<li><a href="${pageContext.request.contextPath}/accueil">Accueil</a></li>
				<li class="active"><a href="#">Panier</a></li>
				<li><a href="${pageContext.request.contextPath}/afficheClient">Client</a></li>


			</ul>
			<ul class="nav navbar-nav navbar-right">
				<li><a
					href="${pageContext.request.contextPath}/admin/connexion"><span
						class="glyphicon glyphicon-log-in"></span> Connexion</a></li>
			</ul>
		</div>
	</nav>
	<br />
	<br />

	<!-- =========================== Contenu Page =========================== -->
	<h1 align="center">Panier</h1>
	<br/>
	<div align="center">
		<table class="table table-bordered">
			<tr>

				<th>Désignation</th>
				<th>Description</th>
				<th>Prix</th>
				<th>Quantité</th>
				<th>Retirer du panier</th>
			</tr>
			<c:forEach var="ligneCommande"
				items="${panierAffiche.listeLignesCommande}">
				<tr>
					<td>${ligneCommande.produit.designation}</td>
					<td>${ligneCommande.produit.description}</td>
					<td>${ligneCommande.prix}</td>
					<td>
					<form:form method="GET" action="modifierProduitPanierViaFormulaire" modelAttribute="ligneModifiee">
						<form:label path="quantite">${ligneCommande.quantite}</form:label>
						<form:input type="hidden" path="idLigne" name="identifiantLigne" value="${ligneCommande.idLigne}"/>
						<form:input type="number" path="quantite" name="quantite"/>
						<input type="submit" value="Modifier"/>
					</form:form>
					</td>
					<td><a
						href="${pageContext.request.contextPath}/panier/retirerPanier?pIdSuppression=${ligneCommande.produit.idProduit}">Retirer</a></td>
			</c:forEach>
		</table>
	</div>
	<h3 align="right" style="margin-right:100px;">
		<a href="${pageContext.request.contextPath}/panier/viderPanier">Vider
			le panier</a>
	</h3>
	<br/>
	<br/>
	<div align="center">
		<form:form action="validationCommandePuisEnregistrement" method="POST"
			modelAttribute="clientAAjouter">
			<div class="form-group">
				<h3>Valider la commande en créant un compte</h3>
				Nom :
				<form:input path="nomClient" />
				Adresse :
				<form:input path="adresse" />
				Telephone :
				<form:input path="tel" />
				Mail :
				<form:input path="email" type="email" />
				<button type="submit" class="btn btn-default">Valider la
					commande</button>
			</div>
		</form:form>
	</div>
	<br/>
	<br/>	
	<br/>
	<div align="center">
		<form:form action="validationCommandeClientDansBase" method="POST"
			modelAttribute="clientDejaDansBase">
			<div class="form-group">
				<h3>Utiliser un compte déjà existant pour valider la commande</h3>

				Mail :
				<form:input path="email" />
				Mot de Passe :
				<form:input path="codePerso" type="password" />
				<button type="submit" class="btn btn-default">Valider la
					commande</button>
			</div>

		</form:form>

	</div>
	<h2>${messageErreur}</h2>


</body>
</html>