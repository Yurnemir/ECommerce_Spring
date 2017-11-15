package fr.adaming.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/admin/categorie")
public class CategorieController {
	@RequestMapping(value="/recap", method=RequestMethod.GET)
	public String afficherRecap() {
		return "categorie_recap";
	}
}
