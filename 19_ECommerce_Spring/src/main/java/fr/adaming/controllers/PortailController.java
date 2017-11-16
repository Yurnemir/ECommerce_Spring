package fr.adaming.controllers;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import fr.adaming.modele.LigneCommande;
import fr.adaming.modele.Panier;
import fr.adaming.modele.Produit;
import fr.adaming.service.IProduitService;

@Controller
public class PortailController {
	@Autowired
	private IProduitService serviceProduit;
	
	@RequestMapping(value="/accueil", method=RequestMethod.GET)
	public String afficherAccueil(Model modele, HttpSession session) {
		List<Produit> listeProduit = serviceProduit.listerProduits();
		modele.addAttribute("listeProduit",listeProduit);
		Panier panier = (Panier) session.getAttribute("panier");
		if (panier == null) {
			panier = new Panier();
			List<LigneCommande> lcs = new ArrayList<LigneCommande>();
			lcs.add(new LigneCommande(15, 45.2));
			panier.setListeLignesCommande(lcs);
			session.setAttribute("panier", panier);
			System.out.println("panier==null");
		} else {
			System.out.println("panier!=null : " + panier.getListeLignesCommande().get(0));
		}
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
	@RequestMapping(value="/panier", method=RequestMethod.GET)
	public String afficherPageClient(ModelMap model, HttpSession session) {
		Panier panier = (Panier) session.getAttribute("panier");
		if (panier != null) {
			System.out.println("panier!=null : " + panier.getListeLignesCommande().get(0));
		}
		return "panier";
	}
	@RequestMapping(value="/logout")
	public String seDeconnecter(){
		return "accueil";
	}
}
