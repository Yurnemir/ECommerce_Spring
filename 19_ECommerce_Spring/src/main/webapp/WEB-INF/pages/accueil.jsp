<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>

<head>
	<title>Accueil</title>
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
				<li class="active"><a href="#">Accueil</a></li>
				<li><a href="${pageContext.request.contextPath}/panier/affichagePanier">Panier</a></li>
				
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<li><a href="${pageContext.request.contextPath}/admin/connexion"><span class="glyphicon glyphicon-log-in"></span> Connexion</a></li>
			</ul>
		</div>
	</nav>
	<br/><br/>
	
	<!-- =========================== Contenu Page =========================== -->
	<h1>Interface avec filtrage par catégorie</h1>
	<br/>
	<div class="panel-group" id="accordion">
	<c:forEach var="categorie" items="${listeCategorie}">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h4 class="panel-title">
					<a data-toggle="collapse" data-parent="#accordion" href="#collapse${categorie.idCategorie}">${categorie.nomCategorie} : ${categorie.description}</a>
				</h4>
			</div>
			<div id="collapse${categorie.idCategorie}" class="panel-collapse collapse">
				<c:forEach var="produit" items="${listeProduit}">
					<c:if test="${produit.categorie.idCategorie == categorie.idCategorie}">
						<div class="panel-body">
							<div class="col-sm-2">
								<img width="128" height="128" alt="img_produit" src="${pageContext.request.contextPath}/images/produit_${produit.idProduit}.jpg">
							</div>
							<div class="col-sm-4">
								<h4>Designation : ${produit.designation}</h4>
								<h4>Description : ${produit.description}</h4>
								<h4>Prix : ${produit.prix}</h4>
								<h4>Quantite : ${produit.quantite}</h4>
							</div>
							<div class="col-sm-6">
								<h1>Ajout au panier (TODO)</h1>
							</div>
						</div>
					</c:if>
				</c:forEach>
			</div>
		</div>
	</c:forEach>
	</div>
	<br/>
	<br/>
	<br/>
	<h1>Interface avec tableur</h1>
	<br/>
	<table class="table table-bordered">
		<tr>
			<th>Désignation</th>
			<th>Description</th>
			<th>Prix</th>
			<th>Quantité</th>
			<th>Image</th>
			<th>Ajouter (lien)</th>
			<th>Ajouter (form)</th>
		</tr>
		<c:forEach var="produit" items="${listeProduit}">
			<tr>
				<th>${produit.designation}</th>
				<th>${produit.description}</th>
				<th>${produit.prix}</th>
				<th>${produit.quantite}</th>
				<th>
					<img width="128" height="128" alt="img_produit" src="${pageContext.request.contextPath}/images/produit_${produit.idProduit}.jpg">
				</th>
				<th><a href="${pageContext.request.contextPath}/ajoutViaLien?pIdProduit=${produit.idProduit}">Ajouter</a><th>
				<th><form method="GET" action="ajouterProduitPanierViaFormulaire" >
					<input type="hidden" name="identifiantProduit" value="${produit.idProduit}">
					<input type="number" name="quantite">
					<input type="submit">
				</form>		
				</th>
			</tr>
		</c:forEach>
	</table>
</body>
</html>