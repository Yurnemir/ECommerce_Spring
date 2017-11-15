<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<form action="connexion" method="POST">
		Identifiant : <input type="text" name="j_username"> <br />
		Mot de passe : <input type="password" name="j_password"> <br />
		<input type="submit" value="Se connecter">
	</form>
	
	<c:if test="${not empty erreur}">
		<h1>Login ou Mot de passe erroné</h1>

	</c:if>


</body>
</html>