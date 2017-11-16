package fr.adaming.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import fr.adaming.modele.LigneCommande;
import fr.adaming.modele.Panier;
import fr.adaming.modele.Produit;
import fr.adaming.service.IProduitService;
@Controller
@RequestMapping("/panier")
public class ControlleurPanier {

	@Autowired
	private IProduitService serviceProduit;

	
	@RequestMapping(value="/ajoutViaLien")
	public ModelAndView ajoutPanierParLigne(Model modele ,@RequestParam("pIdProduit")int idProduit, HttpSession session){
		Produit produitTemp = new Produit();
		produitTemp.setIdProduit(idProduit);
		
		Produit produitDemande = serviceProduit.rechercherProduitAvecId(produitTemp);
		//On récupère le panier de la session.
		
		//System.out.println(produitDemande);
		//On récupère le panier depuis le modele.
		Panier panierSession = (Panier) session.getAttribute("panier");
		List<LigneCommande> listeLigneCommande = panierSession.getListeLignesCommande();
		
		LigneCommande ligneCommande = new LigneCommande();
		ligneCommande.setProduit(produitDemande);
		ligneCommande.setQuantite(12);
		ligneCommande.setPrix(ligneCommande.getQuantite()*produitDemande.getPrix());
		listeLigneCommande.add(ligneCommande);
		panierSession.setListeLignesCommande(listeLigneCommande);
		
		System.out.println(panierSession.getListeLignesCommande().size());
		 
		//Ajoute le panier à la session
		session.setAttribute("panier", panierSession);
		List<Produit> listeProduit = serviceProduit.listerProduits();
		
		return new ModelAndView("accueil","listeProduit",listeProduit);
	}
	
}
