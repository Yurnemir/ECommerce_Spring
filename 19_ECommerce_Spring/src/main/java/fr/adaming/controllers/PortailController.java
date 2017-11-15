package fr.adaming.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

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
	@RequestMapping(value="/admin", method=RequestMethod.GET)
	public String afficherLogin(ModelMap model) {
		return "admin";
	}
	@RequestMapping(value="/panier", method=RequestMethod.GET)
	public String afficherPanier(ModelMap model) {
		return "panier";
	}
}
