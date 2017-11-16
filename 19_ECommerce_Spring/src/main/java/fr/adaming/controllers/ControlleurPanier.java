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
		LigneCommande ligneAModifier = new LigneCommande();
		boolean verifLigne =false;;
		
		Produit produitDemande = serviceProduit.rechercherProduitAvecId(produitTemp);
		
		//On récupère le panier de la session.
		
		//System.out.println(produitDemande);
		//On récupère le panier depuis le modele.
		Panier panierSession = (Panier) session.getAttribute("panier");
		List<LigneCommande> listeLigneCommande = panierSession.getListeLignesCommande();
		
		for(LigneCommande ligne:listeLigneCommande){
			System.out.println("Id de la ligne"+ligne.getProduit().getIdProduit() + "vs produit demandé" + idProduit);
			if(ligne.getProduit().getIdProduit()==idProduit){
				System.out.println("Produit déjà dans la commande il faut modifier la ligne");
				verifLigne = true;
				// Si la ligne existe déjà on la récupère
				ligneAModifier = ligne;
				break;
			}else {
				System.out.println("Le produit n'existe pas on peut donc créer une ligne de commande et l'ajouter dans le panier");
				verifLigne=false;
				
			}
		}
		
		if(verifLigne == true){
			
			System.out.println("On paye initialement "+ligneAModifier.getPrix()+"pour"+ligneAModifier.getQuantite()+"unités");

			double nouveauPrix = ligneAModifier.getPrix()+produitDemande.getPrix();
			int nouvelleQuantite = ligneAModifier.getQuantite() + 1;
			if(produitDemande.getQuantite()<nouvelleQuantite){
				System.out.println("Trop de produit demandé");
				System.out.println("La ligne de commande pour ce produit ne sera pas modifiée");
			}else {
				ligneAModifier.setPrix(nouveauPrix);
				ligneAModifier.setQuantite(nouvelleQuantite);
				System.out.println("On paye désormais "+ligneAModifier.getPrix()+"pour"+ligneAModifier.getQuantite()+"unités");

			}

		}
		
		
		if(verifLigne==false){
			LigneCommande ligneCommande = new LigneCommande();
			ligneCommande.setProduit(produitDemande);
			ligneCommande.setQuantite(1);
			ligneCommande.setPrix(ligneCommande.getQuantite()*produitDemande.getPrix());
			listeLigneCommande.add(ligneCommande);
			panierSession.setListeLignesCommande(listeLigneCommande);
		}
		
		System.out.println(panierSession.getListeLignesCommande().size());
		 
		//Ajoute le panier à la session
		session.setAttribute("panier", panierSession);
		List<Produit> listeProduit = serviceProduit.listerProduits();
		
		return new ModelAndView("accueil","listeProduit",listeProduit);
	}
	
	@RequestMapping(value="/affichagePanier")
	public ModelAndView affichagePanier(HttpSession session){
		//Récupération du panier
		
		Panier panierSession = (Panier) session.getAttribute("panier");
		//panierSession.getListeLignesCommande().get(0).get
		return new ModelAndView("panier","panierAffiche",panierSession);
		
	}
	
}
