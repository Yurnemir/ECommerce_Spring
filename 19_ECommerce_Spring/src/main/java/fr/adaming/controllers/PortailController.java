package fr.adaming.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import fr.adaming.modele.Produit;
import fr.adaming.service.IProduitService;

@Controller
public class PortailController {
	@Autowired
	private IProduitService serviceProduit;
	
	@RequestMapping(value="/accueil", method=RequestMethod.GET)
	public String afficherAccueil(Model modele) {
		List<Produit> listeProduit = serviceProduit.listerProduits();
		modele.addAttribute("listeProduit",listeProduit);
		return "accueil";
	}
	@RequestMapping(value="/admin/connexion", method=RequestMethod.GET)
	public String afficherPageAdmin(ModelMap model) {
		return "admin";
	}
	@RequestMapping(value="/login")
	public String afficherFormulaireConnexion(){
		return "connexion";
	}
	@RequestMapping(value="/client", method=RequestMethod.GET)
	public String afficherPageClient(ModelMap model) {
		model.addAttribute("message", "Bonjour Client");
		return "client";
	}
	@RequestMapping(value="/logout")
	public String seDeconnecter(){
		return "accueil";
	}
}
