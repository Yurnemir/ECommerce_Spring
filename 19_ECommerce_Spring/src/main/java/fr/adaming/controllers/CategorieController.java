package fr.adaming.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import fr.adaming.modele.Categorie;
import fr.adaming.service.IServiceCategorie;

@Controller
@RequestMapping(value="/admin/categorie")
public class CategorieController {
	@Autowired
	private IServiceCategorie categrieService;
	
	public void setCategrieService(IServiceCategorie categrieService) {
		this.categrieService = categrieService;
	}
	
	/* ========================== Affichage ========================== */
	@RequestMapping(value="/recap", method=RequestMethod.GET)
	public ModelAndView afficherRecap() {
		List<Categorie> categories = categrieService.listerCategorie();
		return new ModelAndView("categorie_recap", "categories", categories);
	}
	@RequestMapping(value="/ajout", method=RequestMethod.GET)
	public ModelAndView afficherFormAjout() {
		return new ModelAndView("categorie_ajout", "categorie", new Categorie());
	}
	@RequestMapping(value="/modif", method=RequestMethod.GET)
	public ModelAndView afficherFormModif() {
		return new ModelAndView("categorie_modif", "categorie", new Categorie());
	}

	/* ========================== Actions ========================== */
	@RequestMapping(value="/ajouterCategorie", method=RequestMethod.POST)
	public String soumettreFormAjout(Model model, @ModelAttribute("categorie") Categorie categorie) {
		Categorie cOut = categrieService.ajouterCategorie(categorie);
		if (cOut.getIdCategorie() != 0) {
			List<Categorie> categories = categrieService.listerCategorie();
			model.addAttribute("categories", categories);
			return "admin";
		} else {
			return "redirect:ajout";
		}
	}
	@RequestMapping(value="/modifierCategorie", method=RequestMethod.POST)
	public String soumettreFormModif(Model model, @ModelAttribute("categorie") Categorie categorie) {
		Categorie cOut = categrieService.modifierCategorie(categorie);
		if (cOut.getIdCategorie() != 0) {
			List<Categorie> categories = categrieService.listerCategorie();
			model.addAttribute("categories", categories);
			return "admin";
		} else {
			return "redirect:ajout";
		}
	}

	/* ========================== Actions via lien ========================== */
	@RequestMapping(value="/supprViaLien/{pId}", method=RequestMethod.GET)
	public String supprViaLink(Model model, @PathVariable("pId") int id) {
		Categorie cIn = new Categorie();
		cIn.setIdCategorie(id);
		cIn = categrieService.rechercherCategorieParId(cIn);
		categrieService.supprimerCategorie(cIn);
		List<Categorie> categories = categrieService.listerCategorie();
		model.addAttribute("categories", categories);
		return "categorie_recap";
	}
}
