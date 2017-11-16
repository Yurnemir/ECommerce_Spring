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
	<h2>
		<a href="${pageContext.request.contextPath}/accueil">accueil</a>
	</h2>
	<br />
	<h1>Panier</h1>

	<h3><a href="${pageContext.request.contextPath}/panier/facturePDF">Imprimer la facture</a></h3>
	<div align="center">
		<table class="table table-bordered">

			<tr>

				<th>Désignation</th>
				<th>Description</th>
				<th>Prix</th>
				<th>Quantité</th>

			</tr>
			
			<c:forEach var="ligneCommande" items="${panierAffiche.listeLignesCommande}">
				<tr>

					<th>${ligneCommande.produit.designation}</th>
					<th>${ligneCommande.produit.description}</th>
					<th>${ligneCommande.prix}</th>
					<th>${ligneCommande.quantite}</th>


			</c:forEach>


		</table>
	</div>

</body>
</html>