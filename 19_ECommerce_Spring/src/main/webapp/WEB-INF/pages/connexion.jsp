<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<title>Connextion Administrateur</title>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
	<h1 align="center">Connexion Administrateur</h1>
	<br/>
	<form class="form-horizontal" action="connexion" method="POST">
		<div class="form-group">
			<label class="col-sm-2 control-label">Identifiant :</label>
			<div class="col-sm-4">
				<input type="text" name="j_username">
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 control-label">Mot de passe :</label>
			<div class="col-sm-4">
				<input type="password" name="j_password">
			</div>
		</div>
		<br/>
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-4">
				<input type="submit" value="Se connecter" class="btn btn-primary">
			</div>
		</div>
	</form>
	
	<c:if test="${not empty erreur}">
		<h1>Login ou Mot de passe erroné</h1>
	</c:if>

</body>
</html>