package fr.adaming.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import fr.adaming.modele.Administrateur;
import fr.adaming.modele.LigneCommande;
import fr.adaming.modele.Panier;
import fr.adaming.modele.Produit;
import fr.adaming.modele.Role;
import fr.adaming.service.IProduitService;
import fr.adaming.service.IRoleService;
import fr.adaming.service.IServiceAdmin;

@Controller
public class PortailController {
	@Autowired
	private IProduitService serviceProduit;
	@Autowired
	private IServiceAdmin serviceAdmin;
	@Autowired
	private IRoleService serviceRole;
	
	@RequestMapping(value="/accueil", method=RequestMethod.GET)
	public String afficherAccueil(Model modele, HttpSession session) {

		List<Produit> listeProduit = serviceProduit.listerProduits();
		modele.addAttribute("listeProduit",listeProduit);
		Panier panier = (Panier) session.getAttribute("panier");
		if (panier == null) {
			panier = new Panier();
			List<LigneCommande> lcs = new ArrayList<LigneCommande>();
			panier.setListeLignesCommande(lcs);
			session.setAttribute("panier", panier);
			System.out.println("panier==null");
		}
		return "accueil";
	}
	@RequestMapping(value="/admin/connexion", method=RequestMethod.GET)
	public String afficherPageAdmin(HttpSession session) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Administrateur admin = serviceAdmin.getAdminByName(auth.getName());
		Role role = serviceRole.getRoleByAdmin(admin);
		session.setAttribute("role", role);
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
