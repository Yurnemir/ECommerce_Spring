package fr.adaming.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import fr.adaming.modele.Produit;
import fr.adaming.service.IProduitService;
/**
 * 
 * @author Benjamin Henry
 * @version 1
 * Cette classe est le contrôleur de la partie gestion de produit. Elle permet de générer les formulaires des différents actions
 * proposées.
 *
 */
@Controller
@RequestMapping(value="/admin/produit")
public class ProduitController {
	
	@Autowired
	private IProduitService serviceProduit;
	
	@RequestMapping(value="/recap", method=RequestMethod.GET)
	public String afficherRecap() {
		return "produit_recap";
	}
	
	//Méthodes du formulaire d'ajout
	//Affichage formulaire d'ajout de produit
	/**
	 * Permet d'afficher le formaulaire d'ajout d'un produit dans la base de données.
	 * 
	 * @return Le modèle de la page contenant le formulaire d'ajout de la méthode.
	 * Cette méthode permet d'afficher le formulaire d'ajout d'un produit.
	 * Les informations du formulaire seront stockés dans l'objet produitAjoute
	 * qui est une instance de la classe Produit.
	 */
	@RequestMapping(value="/ajout", method = RequestMethod.GET)
	public ModelAndView affichageFormulaireAjout(){
		return new ModelAndView("produit_ajout","ProduitAjoute",new Produit());
	}
	
	/**
	 * Permet d'ajouter un produit contenant les informations dans la base de données.
	 * Les informations sont fournis par l'utilisateur à travers le formulaire d'ajout.
	 * 
	 * @param modele : 
	 * @param produit : Un objet de type produit (il s'agit de produitAjoute du formulaire affiché par affichageFormulaireAjout) qui contient les infos nécessaire à l'ajout dans la base de données
	 * @return : La page où l'on 
	 */
	// Soumission du formulaire
	@RequestMapping(value="/ajouterProduit" ,method=RequestMethod.POST)
	public String soumettreFormulaireAjout(Model modele, @ModelAttribute("produitAjoute") Produit produit){
		Produit produitAjoute = serviceProduit.ajouterProduit(produit);
		//On actualise la liste des produits
		List<Produit> listeProduit = serviceProduit.listerProduits();
		System.out.println(listeProduit.toString());
		modele.addAttribute("listeProduit",listeProduit);
		return "admin";
	}
	/**
	 *  Permet d'afficher le formaulaire permettant de supprimer un produit de la base de données.
	 * 
	 * @return Le modèle de la page contenant le formulaire de suppression d'un produit de la base de données.
	 * Les informations sont stockées dans produitSuppression qui est une instance de la classe Produit.
	 */
	
	//Méthodes du formulaire de suppression
	@RequestMapping(value="/suppr", method = RequestMethod.GET)
	public ModelAndView affichageFormulaireSuppression(){
		return new ModelAndView("produit_suppr","produitSuppression",new Produit());
	}

	/**
	 * Permet d'ajouter un produit contenant les informations dans la base de données.
	 * Les informations sont fournis par l'utilisateur à travers le formulaire de suppression.
	 * 
	 * @param modele : 
	 * @param produit : Un objet de type produit (il s'agit de produitSuppression du formulaire affiché la méthode affichageFormulaireSuppression) qui contient les infos nécessaire à l'ajout dans la base de données
	 * @return : La page d'accueil à partir de laquelle les admins ont accès aux différentes fonctionnalités 
	 * @see affichageFormulaireSuppression
	 */
	
	//Suppression du produit
	@RequestMapping(value="/supprimerProduit",method=RequestMethod.POST)
	public String soumissionFormulaireSuppression(Model modele,@ModelAttribute("produitSuppression") Produit produit){
		serviceProduit.supprimerProduit(produit);
		//On actualise la liste des produits
		List<Produit> listeProduit = serviceProduit.listerProduits();
		System.out.println(listeProduit.toString());
		modele.addAttribute("listeProduit",listeProduit);
		return "admin";

	}
	@RequestMapping(value="/modif", method = RequestMethod.GET)
	public ModelAndView affichageFormulaireModification(){
		return new ModelAndView("produit_modif","produitModif",new Produit());
	}
	
	@RequestMapping(value="modifierProduit",method=RequestMethod.POST)
	public String soumissionFormulaireModification(Model modele,@ModelAttribute("produitModif") Produit produit){
		Produit produitModif = serviceProduit.modifierProduit(produit);
		
		List<Produit> listeProduit = serviceProduit.listerProduits();
		System.out.println(listeProduit.toString());
		modele.addAttribute("listeProduit",listeProduit);
		return "admin";
	}
}
