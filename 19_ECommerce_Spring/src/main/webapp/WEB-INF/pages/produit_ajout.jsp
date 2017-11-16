<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
	<title>Ajout de Produit</title>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
	<h1 align="center">Ajout de produit</h1>
	<br/>
	<form:form class="form-horizontal" method="POST"
		action="ajouterProduit" modelAttribute="produitAjoute">
		<div class="form-group">
			<form:label path="designation" class="col-sm-2 control-label">Designation du produit</form:label>
			<div class="col-sm-4">
				<form:input path="designation" />
				<form:errors path="designation" />
			</div>
		</div>
		<div class="form-group">
			<form:label path="description" class="col-sm-2 control-label">Description du produit</form:label>
			<div class="col-sm-4">
				<form:input path="description" />
				<form:errors path="description" />
			</div>
		</div>
		<div class="form-group">
			<form:label path="prix" class="col-sm-2 control-label">Prix du produit</form:label>
			<div class="col-sm-4">
				<form:input path="prix" />
				<form:errors path="prix" />
			</div>
		</div>
		<div class="form-group">
			<form:label path="quantite" class="col-sm-2 control-label">Quantite du produit</form:label>
			<div class="col-sm-4">
				<form:input path="quantite" />
				<form:errors path="quantite" />
			</div>
		</div>
		<br/>
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-4">
				<input type="submit" value="Ajouter le produit" class="btn btn-primary">
			</div>
		</div>
	</form:form>
</body>
</html>