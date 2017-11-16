<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
	<title>Modification Produit</title>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
	<h1>Modification de produit</h1>
	<form:form class="form-horizontal" action="modifierProduit"
		modelAttribute="produitModif" method="POST">

		<div class="form-group">
			<form:label path="idProduit" class="col-sm-2 control-label">Id du Produit</form:label>
			<form:input path="idProduit" />
		</div>

		<div class="form-group">
			<form:label path="designation" class="col-sm-2 control-label">Designation du produit</form:label>
			<form:input path="designation" />
		</div>

		<div class="form-group">
			<form:label path="description" class="col-sm-2 control-label">Description du produit</form:label>
			<form:input path="description" />
		</div>

		<div class="form-group">
			<form:label path="prix" class="col-sm-2 control-label">Prix du produit</form:label>
			<form:input path="prix" />
		</div>
		
		<div class="form-group">
			<form:label path="quantite" class="col-sm-2 control-label">Quantite du produit</form:label>
			<form:input path="quantite" />
		</div>
		
		<input type="submit" value="Modifier le produit"
			class="btn btn-default">
	</form:form>
</body>
</html>