package fr.adaming.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/admin/produit")
public class ProduitController {
	@RequestMapping(value="/recap", method=RequestMethod.GET)
	public String afficherRecap() {
		return "produit_recap";
	}
}
