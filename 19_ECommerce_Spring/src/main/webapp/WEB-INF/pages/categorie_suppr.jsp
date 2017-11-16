<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
	<title>Suppression de Categorie</title>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
	<h1 align="center">Suppression d'une catégorie :</h1>
	<br/>
	<form:form class="form-horizontal" method="POST" action="supprimerCategorie" modelAttribute="categorie">
		<div class="form-group">
			<form:label path="idCategorie" class="col-sm-2 control-label">Id : </form:label>
			<div class="col-sm-4">
				<form:input path="idCategorie" />
				<form:errors path="idCategorie" />
			</div>
		</div>
			<div class="col-sm-offset-2 col-sm-4">
				<input type="submit" value="Supprimer Categorie" class="btn btn-primary" />
			</div>
	</form:form>
</body>
</html>