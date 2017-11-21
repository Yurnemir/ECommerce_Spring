<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>Facture</title>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
	<h3 class="btn btn-primary"><a href="${pageContext.request.contextPath}/retourAccueil" >Retourner à l'accueil</a> </h3>
	<br/>
	<br/>
	<h2 align="center">Facture</h2>
	<br/>
	<h3>Résumé de votre commande :</h3>
	<table class="table table-bordered">
		<tr>
			<th>Désignation</th>
			<th>Description</th>
			<th>Prix unitaire</th>
			<th>Quantité</th>
			<th>Prix total</th>
		</tr>
		<c:forEach var="ligneCommande" items="${sessionScope.panier.listeLignesCommande}">
			<tr>
				<td>${ligneCommande.produit.designation}</td>
				<td>${ligneCommande.produit.description}</td>
				<td>${ligneCommande.produit.prix}</td>
				<td>${ligneCommande.quantite}</td>
				<td>${ligneCommande.prix}</td>
			</tr>
		</c:forEach>
	</table>
	<div align="center">
		<h3>Prix Total à payer ${prix} euros</h3>
	</div>
	<br/>
	<div>
		<h3 style="text-align:left; margin-left:50px;">
			<a class="btn btn-primary"  href="${pageContext.request.contextPath}/panier/facturePDF">Imprimer la facture</a>
		</h3>
		<h3 style="text-align:right; margin-right:50px;">
			<a class="btn btn-primary"  href="${pageContext.request.contextPath}/panier/envoiMail">Envoi mail de confirmation</a>
		</h3>
	</div>
</body>
</html>