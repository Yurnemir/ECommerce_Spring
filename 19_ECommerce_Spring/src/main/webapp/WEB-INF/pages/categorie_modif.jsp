<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
	<title>Modification de Categorie</title>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
	<!-- =========================== Barre Navigation =========================== -->
	<nav class="navbar navbar-inverse">
		<div class="container-fluid">
			<div class="navbar-header">
				<a class="navbar-brand" href="${pageContext.request.contextPath}/admin/connexion">Portail Admin</a>
			</div>
			<ul class="nav navbar-nav">
				<li><a href="${pageContext.request.contextPath}/admin/categorie/recap">Retour</a></li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<li><a href='<c:url value="/deconnexion"/>'><span class="glyphicon glyphicon-log-out"></span> Deconnexion</a></li>
			</ul>
		</div>
	</nav>
	<br/><br/>
	
	<!-- =========================== Contenu Page =========================== -->
	<h1 align="center">Modification d'une catégorie :</h1>
	<br/>
	<form:form class="form-horizontal" method="POST" action="modifierCategorie" modelAttribute="categorie">
		<div class="form-group">
			<form:label path="idCategorie" class="col-sm-2 control-label">Id : </form:label>
			<div class="col-sm-4">
				<form:input path="idCategorie" />
				<form:errors path="idCategorie" />
			</div>
		</div>
		<div class="form-group">
			<form:label path="nomCategorie" class="col-sm-2 control-label">Nom : </form:label>
			<div class="col-sm-4">
				<form:input path="nomCategorie" />
				<form:errors path="nomCategorie" />
			</div>
		</div>
		<div class="form-group">
			<form:label path="description" class="col-sm-2 control-label">Description : </form:label>
			<div class="col-sm-4">
				<form:input path="description" />
				<form:errors path="description" />
			</div>
		</div>
			<div class="col-sm-offset-2 col-sm-4">
				<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#fenetreModifCategorie">Modifier la catégorie</button>
				<!-- =========================== Fenetre confirmation modif =========================== -->
				<div id="fenetreModifCategorie" class="modal fade" role="dialog">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal">&times;</button>
								<h4 class="modal-title">Confirmation Modification Categorie</h4>
							</div>
							<div class="modal-body">
								<p align="center">Etes-vous certain de vouloir modifier cette catégorie ?</p>
								<div align="center">
									<input type="submit" value="Oui" class="btn btn-primary">
									<button type="button" class="btn btn-primary" data-dismiss="modal">Non</button>
								</div>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-primary" data-dismiss="modal">Annuler</button>
							</div>
						</div>
					</div>
				</div>
			</div>
	</form:form>
</body>
</html>