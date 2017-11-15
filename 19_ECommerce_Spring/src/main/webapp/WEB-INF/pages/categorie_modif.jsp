<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
	<title>Modification Categorie</title>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
	<h1 align="center">Modification d'une catégorie :</h1>
	<form:form class="form-horizontal" method="POST" action="modifierCategorie" modelAttribute="categorie">
		<div class="form-group">
			<form:label path="idCategorie" class="col-sm-2 control-label">Id : </form:label>
			<div class="col-sm-10">
				<form:input path="idCategorie" />
				<form:errors path="idCategorie" />
			</div>
		</div>
		<div class="form-group">
			<form:label path="nomCategorie" class="col-sm-2 control-label">Nom : </form:label>
			<div class="col-sm-10">
				<form:input path="nomCategorie" />
				<form:errors path="nomCategorie" />
			</div>
		</div>
		<div class="form-group">
			<form:label path="description" class="col-sm-2 control-label">Description : </form:label>
			<div class="col-sm-10">
				<form:input path="description" />
				<form:errors path="description" />
			</div>
		</div>
		<input type="submit" value="Modifier Categorie" class="btn btn-primary" />
	</form:form>
</body>
</html>