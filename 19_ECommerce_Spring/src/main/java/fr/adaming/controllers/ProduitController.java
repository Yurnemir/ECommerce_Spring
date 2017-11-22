package fr.adaming.controllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import fr.adaming.modele.Produit;
import fr.adaming.service.IProduitService;
import fr.adaming.service.IServiceCategorie;
/**
 * 
 * @author Benjamin Henry
 * @version 1
 * Cette classe est le contr�leur de la partie gestion de produit. Elle permet de g�n�rer les formulaires des diff�rents actions
 * propos�es.
 *
 */

@Controller
@RequestMapping(value="/admin/produit")
public class ProduitController {
	@Autowired
	private IProduitService serviceProduit;
	@Autowired
	private IServiceCategorie serviceCategorie;
	
	public void setServiceProduit(IProduitService serviceProduit) {
		this.serviceProduit = serviceProduit;
	}
	public void setServiceCategorie(IServiceCategorie serviceCategorie) {
		this.serviceCategorie = serviceCategorie;
	}

	/* ========================== Affichage ========================== */
	@RequestMapping(value="/recap", method=RequestMethod.GET)
	public ModelAndView afficherRecap() {
		List<Produit> listeProduit = serviceProduit.listerProduits();
		return new ModelAndView("produit_recap", "listeProduit", listeProduit);
	}
	/**
	 * Permet d'afficher le formulaire d'ajout d'un produit dans la base de donn�es.
	 * 
	 * @return Modele MVC de la page ajout de produit.
	 * Cette m�thode permet d'afficher le formulaire d'ajout d'un produit.
	 * Les informations du formulaire seront stock�s dans l'objet produitAjoute
	 * qui est une instance de la classe Produit.
	 */
	@RequestMapping(value="/ajout", method = RequestMethod.GET)
	public String affichageFormulaireAjout(Model model){
		model.addAttribute("listeCategorie", serviceCategorie.listerCategorie());
		model.addAttribute("produitAjoute", new Produit());
		return "produit_ajout";
	}
	

	/* ========================== Actions ========================== */
	/**
	 * Permet d'ajouter un produit contenant les informations dans la base de donn�es.
	 * Les informations sont fournis par l'utilisateur � travers le formulaire d'ajout.
	 * 
	 * @param modele : Modele MVC de la page ajout de produit
	 * @param produit : Un objet de type produit (il s'agit de produitAjoute du formulaire affich� par affichageFormulaireAjout) qui contient les infos n�cessaire � l'ajout dans la base de donn�es
	 * @return : La page o� l'on 
	 */
	@RequestMapping(value="/ajouterProduit", method=RequestMethod.POST)
	public ModelAndView soumettreFormulaireAjout(Model modele, @Valid @ModelAttribute("produitAjoute") Produit produit, @RequestParam CommonsMultipartFile file, HttpSession session, BindingResult result) throws Exception {
		if(result.hasErrors()) {
			return new ModelAndView("produit_ajout", "listeProduit", serviceProduit.listerProduits());
		}
		Produit produitAjoute = serviceProduit.ajouterProduit(produit);
		String path = session.getServletContext().getRealPath("/images");
		File imagesDir = new File(path);
		if (!imagesDir.exists()) {
			imagesDir.mkdir();
		}
		byte[] bytes = file.getBytes();
		BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(
				new File(path + File.separator + "produit_" + produitAjoute.getIdProduit() + ".jpg")));
		stream.write(bytes);
		stream.flush();
		stream.close();
		return new ModelAndView("produit_recap", "listeProduit", serviceProduit.listerProduits());
	}
	/**
	 * Permet de modifier un produit pr�sent dans la base de donn�es
	 * 
	 * @param modele Le mod�le MVC de la page contenant le formulaire de modification de produit.
	 * @param produit : Un objet de type produit contenant toutes les informations. Infos stock�es dans produitModif
	 * @return : La page d'accueil � partir de laquelle les admins ont acc�s aux diff�rentes fonctionnalit�s 
	 * @see affichageFormulaireModification
	 */
	@RequestMapping(value="modifierProduit", method=RequestMethod.POST)
	public String soumissionFormulaireModification(Model modele, @Valid @ModelAttribute("produitModif") Produit produit, @RequestParam CommonsMultipartFile file, HttpSession session, BindingResult result) throws Exception {
		if(result.hasErrors()) {
			return "produit_modif";
		}
		Produit produitModif = serviceProduit.modifierProduit(produit);
		String path = session.getServletContext().getRealPath("/images");
		File imagesDir = new File(path);
		if (!imagesDir.exists()) {
			imagesDir.mkdir();
		}
		byte[] bytes = file.getBytes();
		BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(
				new File(path + File.separator + "produit_" + produitModif.getIdProduit() + ".jpg")));
		stream.write(bytes);
		stream.flush();
		stream.close();
		modele.addAttribute("listeProduit", serviceProduit.listerProduits());
		return "produit_recap";
	}
	
	
	/* ========================== Actions via lien ========================== */
	/**
	 * Permet de modifier un produit contenu dans la base de donn�es via un lien sur la page r�capitulative.
	 * 
	 * @param modele : Modele MVC de la page modification de produit
	 * @param id : id du produit � modifier
	 * @return : la page r�capitulative des produits apr�s suppression du produit
	 */
	@RequestMapping(value="/modifViaLien", method=RequestMethod.GET)
	public String affichageFormulaireModificationViaLien(Model model, @RequestParam("pId") int id) {
		Produit pIn = new Produit();
		pIn.setIdProduit(id);
		pIn = serviceProduit.rechercherProduitAvecId(pIn);
		model.addAttribute("listeCategorie", serviceCategorie.listerCategorie());
		model.addAttribute("produitModif", pIn);
		return "produit_modif";
	}
	/**
	 * Permet de supprimer un produit contenu dans la base de donn�es via un lien sur la page r�capitulative.
	 * 
	 * @param modele : Modele MVC de la page supression de produit
	 * @param id : id du produit � supprimer
	 * @return : la page r�capitulative des produits apr�s suppression du produit
	 */
	@RequestMapping(value="/supprViaLien/{pId}", method=RequestMethod.GET)
	public String supprViaLink(Model modele, @PathVariable("pId") int id) {
		Produit pIn = new Produit();
		pIn.setIdProduit(id);
		pIn = serviceProduit.rechercherProduitAvecId(pIn);
		serviceProduit.supprimerProduit(pIn);
		List<Produit> listeProduit = serviceProduit.listerProduits();
		modele.addAttribute("listeProduit", listeProduit);
		return "produit_recap";
	}
}
