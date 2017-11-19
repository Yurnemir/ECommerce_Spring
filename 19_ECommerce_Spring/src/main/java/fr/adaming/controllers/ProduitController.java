package fr.adaming.controllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import fr.adaming.modele.Categorie;
import fr.adaming.modele.Produit;
import fr.adaming.service.IProduitService;
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
	
	@RequestMapping(value="/recap", method=RequestMethod.GET)
	public ModelAndView afficherRecap() {
		
		List<Produit> listeProduit = serviceProduit.listerProduits();
		System.out.println(listeProduit.toString());


		return new ModelAndView("produit_recap","listeProduit",listeProduit);
	}
	
	//M�thodes du formulaire d'ajout
	//Affichage formulaire d'ajout de produit
	/**
	 * Permet d'afficher le formulaire d'ajout d'un produit dans la base de donn�es.
	 * 
	 * @return Modele MVC de la page ajout de produit.
	 * Cette m�thode permet d'afficher le formulaire d'ajout d'un produit.
	 * Les informations du formulaire seront stock�s dans l'objet produitAjoute
	 * qui est une instance de la classe Produit.
	 */
	@RequestMapping(value="/ajout", method = RequestMethod.GET)
	public ModelAndView affichageFormulaireAjout(){
		return new ModelAndView("produit_ajout","produitAjoute",new Produit());
	}
	
	/**
	 * Permet d'ajouter un produit contenant les informations dans la base de donn�es.
	 * Les informations sont fournis par l'utilisateur � travers le formulaire d'ajout.
	 * 
	 * @param modele : Modele MVC de la page ajout de produit
	 * @param produit : Un objet de type produit (il s'agit de produitAjoute du formulaire affich� par affichageFormulaireAjout) qui contient les infos n�cessaire � l'ajout dans la base de donn�es
	 * @return : La page o� l'on 
	 */
	// Soumission du formulaire
	@RequestMapping(value="/ajouterProduit" ,method=RequestMethod.POST)
	public String soumettreFormulaireAjout(Model modele, @ModelAttribute("produitAjoute") Produit produit, @RequestParam CommonsMultipartFile file, HttpSession session) throws Exception {
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
		//On actualise la liste des produits
		List<Produit> listeProduit = serviceProduit.listerProduits();
		System.out.println(listeProduit.toString());
		modele.addAttribute("listeProduit",listeProduit);
		return "admin";
	}
	/**
	 *  Permet d'afficher le formaulaire permettant de supprimer un produit de la base de donn�es.
	 * 
	 * @return Le mod�le de la page contenant le formulaire de suppression d'un produit de la base de donn�es.
	 * Les informations sont stock�es dans produitSuppression qui est une instance de la classe Produit.
	 */
	
	//M�thodes du formulaire de suppression
	@RequestMapping(value="/suppr", method = RequestMethod.GET)
	public ModelAndView affichageFormulaireSuppression(){
		return new ModelAndView("produit_suppr","produitSuppression",new Produit());
	}

	/**
	 * Permet de supprimer un produit contenu dans la base de donn�es.
	 * Les informations sont fournis par l'utilisateur � travers le formulaire de suppression.
	 * 
	 * @param modele : Modele MVC de la page supression de produit
	 * @param produit : Un objet de type produit (il s'agit de produitSuppression du formulaire affich� la m�thode affichageFormulaireSuppression) qui contient les infos n�cessaire � l'ajout dans la base de donn�es
	 * @return : La page d'accueil � partir de laquelle les admins ont acc�s aux diff�rentes fonctionnalit�s 
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
		modele.addAttribute("listeProduit",listeProduit);
		return "produit_recap";
	}
	/**
	 * Permet d'afficher le formulaire pour modifier un produit
	 * @return Le mod�le de la page contenant le formulaire de modification de produit.
	 * Les informations sont stock�es dans produitModif qui est une instance de la classe Produit.
	 */
	@RequestMapping(value="/modif", method = RequestMethod.GET)
	public ModelAndView affichageFormulaireModification(){
		return new ModelAndView("produit_modif","produitModif",new Produit());
	}
	/**
	 * Permet de modifier un produit pr�sent dans la base de donn�es
	 * 
	 * @param modele Le mod�le MVC de la page contenant le formulaire de modification de produit.
	 * @param produit : Un objet de type produit contenant toutes les informations. Infos stock�es dans produitModif
	 * @return : La page d'accueil � partir de laquelle les admins ont acc�s aux diff�rentes fonctionnalit�s 
	 * @see affichageFormulaireModification
	 */
	@RequestMapping(value="modifierProduit",method=RequestMethod.POST)
	public String soumissionFormulaireModification(Model modele,@ModelAttribute("produitModif") Produit produit, @RequestParam CommonsMultipartFile file, HttpSession session) throws Exception {
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
		System.out.println("file.getOriginalFilename() = " + file.getOriginalFilename());
		//On actualise la liste des produits
		List<Produit> listeProduit = serviceProduit.listerProduits();
		System.out.println(listeProduit.toString());
		modele.addAttribute("listeProduit",listeProduit);
		return "admin";
	}
}
