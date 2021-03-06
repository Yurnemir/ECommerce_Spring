<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
	<title>Recapitulatif Produits</title>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta http-equiv="refresh">
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
					<li><a href="${pageContext.request.contextPath}/admin/categorie/recap"><i class="glyphicon glyphicon-book"></i> Categories</a></li>
				</c:if>
				<li class="active"><a href="#"><i class="glyphicon glyphicon-barcode"></i> Produits</a></li>
				<c:if test="${sessionScope.role.nomRole == 'ROLE_ADMIN_CATEGORIE'}">
					<li><a href="${pageContext.request.contextPath}/client/recap"><i class="glyphicon glyphicon-user"></i> Clients</a></li>
				</c:if>
				<li><a href="${pageContext.request.contextPath}/admin/produit/pdf/recap"><i class="glyphicon glyphicon-print"></i> Export pdf</a></li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<li><a href='<c:url value="/deconnexion"/>'><span class="glyphicon glyphicon-log-out"></span> Deconnexion</a></li>
			</ul>
		</div>
	</nav>
	<br/><br/>
	
	<!-- =========================== Contenu Page =========================== -->
	<h1 align="center">Recapitulatif Produits</h1>
	<br/>
	<div align="center">
		<table class="table table-bordered">
			<tr>
				<th>ID</th>
				<th>D�signation</th>
				<th>Description</th>
				<th>Prix</th>
				<th>Quantit�</th>
				<th>Image</th>
				<th>Cat�gorie</th>
				<th>Operation</th>
			</tr>
			<c:forEach var="produit" items="${listeProduit}">
				<tr>
					<td>${produit.idProduit}</td>
					<td>${produit.designation}</td>
					<td>${produit.description}</td>
					<td>${produit.prix}</td>
					<td>${produit.quantite}</td>
					<td>
						<img width="128" height="128" alt="img_produit" src="${pageContext.request.contextPath}/images/produit_${produit.idProduit}.jpg">
					</td>
					<td>${produit.categorie.nomCategorie}</td>
					<td>
						<a class="btn btn-primary" href="${pageContext.request.contextPath}/admin/produit/modifViaLien?pId=${produit.idProduit}">
							<span class="glyphicon glyphicon-cog"></span>
						</a>
						<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#fenetreSupprProduit_${produit.idProduit}">
							<span class="glyphicon glyphicon-trash"></span>
						</button>
						<!-- =========================== Fenetre confirmation suppression =========================== -->
						<div id="fenetreSupprProduit_${produit.idProduit}" class="modal fade" role="dialog">
							<div class="modal-dialog">
								<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal">&times;</button>
										<h4 class="modal-title">Confirmation Suppression Produit</h4>
									</div>
									<div class="modal-body">
										<p align="center">Etes-vous certain de vouloir supprimer ce produit ?</p>
										<div align="center">
											<a class="btn btn-primary" href="${pageContext.request.contextPath}/admin/produit/supprViaLien/${produit.idProduit}">Oui</a>
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
		<a class="btn btn-primary" style="width:100%; margin-bottom:150px;" href="${pageContext.request.contextPath}/admin/produit/ajout">
			<span class="glyphicon glyphicon-plus"></span>
		</a>
	</div>
	
</body>
</html>