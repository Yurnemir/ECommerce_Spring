<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<title>Suppression Produit</title>
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
	<h1 align="center">Suppression de Produit</h1>
	<br/>
	<form:form class="form-horizontal" method="POST" action="supprimerProduit" modelAttribute="produitSuppression">
		<div class="form-group">
			<form:label path="idProduit" class="col-sm-2 control-label">Id du Produit</form:label>
			<div class="col-sm-4">
				<form:input path="idProduit"/>
				<form:errors path="idProduit"/>
			</div>
		</div>
		<br/>
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-4">
				<input type="submit" value="Supprimer le produit" class="btn btn-primary">
			</div>
		</div>
	</form:form>
</body>
</html>