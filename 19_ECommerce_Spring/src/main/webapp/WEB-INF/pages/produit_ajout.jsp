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
	<!-- =========================== Barre Navigation =========================== -->
	<nav class="navbar navbar-inverse">
		<div class="container-fluid">
			<div class="navbar-header">
				<a class="navbar-brand" href="${pageContext.request.contextPath}/admin/connexion">Portail Admin</a>
			</div>
			<ul class="nav navbar-nav">
				<li><a href="${pageContext.request.contextPath}/admin/produit/recap">Retour</a></li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<li><a href='<c:url value="/deconnexion"/>'><span class="glyphicon glyphicon-log-out"></span> Deconnexion</a></li>
			</ul>
		</div>
	</nav>
	<br/><br/>
	
	<!-- =========================== Contenu Page =========================== -->
	<h1 align="center">Ajout de produit</h1>
	<br/>
	<form:form class="form-horizontal" method="POST"
		action="ajouterProduit" modelAttribute="produitAjoute" enctype="multipart/form-data">
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
		<div class="form-group">
			<form:label path="categorie.idCategorie" class="col-sm-2 control-label">Id de la categorie</form:label>
			<div class="col-sm-4">
				<form:select path="categorie.idCategorie">
					<form:options items="${listeCategorie}" itemValue="idCategorie" itemLabel="nomCategorie" />
				</form:select>
				<form:errors path="categorie.idCategorie" />
			</div>
		</div>
		<div class="form-group">
			<form:label path="imageFichier" class="col-sm-2 control-label">Image</form:label>
			<div class="col-sm-4">
				<input type="file" name="file" />
			</div>
		</div>
		
		<br/>
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-4">
				<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#fenetreAjoutProduit">Ajouter le produit</button>
				<!-- =========================== Fenetre confirmation ajout =========================== -->
				<div id="fenetreAjoutProduit" class="modal fade" role="dialog">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal">&times;</button>
								<h4 class="modal-title">Confirmation Ajout Produit</h4>
							</div>
							<div class="modal-body">
								<p align="center">Etes-vous certain de vouloir ajouter ce produit ?</p>
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
		</div>
	</form:form>
</body>
</html>