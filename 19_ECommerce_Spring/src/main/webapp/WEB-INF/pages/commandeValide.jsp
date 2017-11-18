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

<title>Facture</title>
</head>
<body>

	<h2 align="center">Facture</h2>
	<h3 align="center">Résumé de votre commande</h3>
	<table class="table table-bordered">
		<tr>

			<th>Désignation</th>
			<th>Description</th>
			<th>Prix unitaire</th>
			<th>Quantité</th>
			<th>Prix total</th>

		</tr>
		<c:forEach var="ligneCommande"
			items="${sessionScope.panier.listeLignesCommande}">

			<th>${ligneCommande.produit.designation}</th>
			<th>${ligneCommande.produit.description}</th>
			<th>${ligneCommande.produit.prix}</th>
			<th>${ligneCommande.quantite}</th>
			<th>${ligneCommande.prix}</th>




		</c:forEach>


	</table>


	<div class="col-sm-8 control-label">
		<h3>Prix Total à payer ${prix}</h3>
	</div>
	<br/>
	<div class="col-sm-8 control-label">
	<h3>
		<a href="${pageContext.request.contextPath}/panier/facturePDF">Imprimer
			la facture</a>
	</h3>
	
		<h3>
		<a href="${pageContext.request.contextPath}/panier/envoiMail">Envoie
			facture par mail ()</a>
	</h3>
	
	</div>
	
</body>
</html>