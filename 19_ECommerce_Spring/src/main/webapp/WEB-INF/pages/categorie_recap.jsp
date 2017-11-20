<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
	<title>Recapitulatif Categories</title>
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
				<c:if test="${sessionScope.role.nomRole == 'ROLE_ADMIN_CATEGORIE'}">
					<li class="active"><a href="#"><i class="glyphicon glyphicon-book"></i> Categories</a></li>
				</c:if>
				<li><a href="${pageContext.request.contextPath}/admin/produit/recap"><i class="glyphicon glyphicon-barcode"></i> Produits</a></li>
				<c:if test="${sessionScope.role.nomRole == 'ROLE_ADMIN_CATEGORIE'}">
					<li><a href="${pageContext.request.contextPath}/client/recap"><i class="glyphicon glyphicon-user"></i> Clients</a></li>
				</c:if>
				<li><a href="${pageContext.request.contextPath}/admin/produit/pdf/recap"><i class="glyphicon glyphicon-print"></i> Export pdf</a></li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<li><a href='<c:url value="/deconnexion"/>'><span class="glyphicon glyphicon-log-out"></span>Deconnexion</a></li>
			</ul>
		</div>
	</nav>
	<br/><br/>
	
	<!-- =========================== Contenu Page =========================== -->
	<h1 align="center">Recapitulatif Categories</h1>
	<br/>
	<div align="center">
		<table class="table table-bordered">
			<tr>
				<th>ID</th>
				<th>Nom</th>
				<th>Description</th>
				<th>operation</th>
			</tr>
			<c:forEach var="categorie" items="${categories}">
				<tr>
					<td>${categorie.idCategorie}</td>
					<td>${categorie.nomCategorie}</td>
					<td>${categorie.description}</td>
					<td>
						<a class="btn btn-primary" href="${pageContext.request.contextPath}/admin/categorie/modifViaLien?pId=${categorie.idCategorie}">
							<span class="glyphicon glyphicon-cog"></span>
						</a>
						<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#fenetreSupprCategorie_${categorie.idCategorie}">
							<span class="glyphicon glyphicon-trash"></span>
						</button>
						<!-- =========================== Fenetre confirmation suppression =========================== -->
						<div id="fenetreSupprCategorie_${categorie.idCategorie}" class="modal fade" role="dialog">
							<div class="modal-dialog">
								<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal">&times;</button>
										<h4 class="modal-title">Confirmation Suppression Categorie</h4>
									</div>
									<div class="modal-body">
										<p align="center">Etes-vous certain de vouloir supprimer cette catégorie ?</p>
										<div align="center">
											<a class="btn btn-primary" href="${pageContext.request.contextPath}/admin/categorie/supprViaLien/${categorie.idCategorie}">Oui</a>
											<button type="button" class="btn btn-primary" data-dismiss="modal">Non</button>
										</div>
									</div>
									<div class="modal-footer">
										<button type="button" class="btn btn-primary" data-dismiss="modal">Annuler</button>
									</div>
								</div>
							</div>
						</div>
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>
	
	<div align="center">
		<a class="btn btn-primary" style="width:100%; margin-bottom:150px;" href="${pageContext.request.contextPath}/admin/categorie/ajout">
			<span class="glyphicon glyphicon-plus"></span>
		</a>
	</div>
	
</body>
</html>