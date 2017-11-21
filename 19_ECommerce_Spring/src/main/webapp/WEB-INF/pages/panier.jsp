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
						<form:errors path="quantite"/>
						<input type="submit" value="Modifier"/>
					</form:form>
					</td>
					<td><a
						href="${pageContext.request.contextPath}/panier/retirerPanier?pIdSuppression=${ligneCommande.produit.idProduit}">Retirer</a></td>
			</c:forEach>
		</table>
	</div>
	<div align="right" style="margin-right:100px;">
		<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#fenetreViderPanier">
			<span class="glyphicon glyphicon-trash">Vider le panier</span>
		</button>
	</div>
	<!-- =========================== Fenetre confirmation commande (nouveau client) =========================== -->
	<div id="fenetreViderPanier" class="modal fade" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Confirmation Vidage Panier</h4>
				</div>
				<div class="modal-body">
					<p align="center">Etes-vous certain de vouloir vider le panier ?</p>
					<div align="center">
						<a class="btn btn-primary" href="${pageContext.request.contextPath}/panier/viderPanier">Oui</a>
						<button type="button" class="btn btn-primary" data-dismiss="modal">Non</button>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" data-dismiss="modal">Annuler</button>
				</div>
			</div>
		</div>
	</div>
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
<!-- 				<button type="submit" class="btn btn-default">Valider la -->
<!-- 					commande</button> -->
				<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#fenetreValidationCommandeNouveauClient">Valider Commande</button>
				<!-- =========================== Fenetre confirmation commande (client existant) =========================== -->
				<div id="fenetreValidationCommandeNouveauClient" class="modal fade" role="dialog">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal">&times;</button>
								<h4 class="modal-title">Validation Commande</h4>
							</div>
							<div class="modal-body">
								<p align="center">Etes-vous certain de vouloir passer cette commande ?</p>
								<div align="center">
									<input type="submit" value="Oui" class="btn btn-primary">
									<button type="button" class="btn btn-primary" data-dismiss="modal">Non</button>
								</div>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-primary" data-dismiss="modal">Annuler</button>
							</div>
						</div>
					</div>
				</div>
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
<!-- 				<button type="submit" class="btn btn-default">Valider la -->
<!-- 					commande</button> -->
				<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#fenetreValidationCommandeNouveaClientExistant">Valider Commande</button>
				<!-- =========================== Fenetre confirmation ajout =========================== -->
				<div id="fenetreValidationCommandeNouveaClientExistant" class="modal fade" role="dialog">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal">&times;</button>
								<h4 class="modal-title">Validation Commande</h4>
							</div>
							<div class="modal-body">
								<p align="center">Etes-vous certain de vouloir passer cette commande ?</p>
								<div align="center">
									<input type="submit" value="Oui" class="btn btn-primary">
									<button type="button" class="btn btn-primary" data-dismiss="modal">Non</button>
								</div>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-primary" data-dismiss="modal">Annuler</button>
							</div>
						</div>
					</div>
				</div>
			</div>

		</form:form>

	</div>
	<h2>${messageErreur}</h2>


</body>
</html>