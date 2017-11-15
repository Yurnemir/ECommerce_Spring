package fr.adaming.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PortailController {
	@RequestMapping(value="/accueil", method=RequestMethod.GET)
	public String afficherAccueil() {
		return "accueil";
	}
	@RequestMapping(value="/admin", method=RequestMethod.GET)
	public String afficherPageAdmin(ModelMap model) {
		model.addAttribute("message", "Bonjour Admin");
		return "admin";
	}
	@RequestMapping(value="/client", method=RequestMethod.GET)
	public String afficherPageClient(ModelMap model) {
		model.addAttribute("message", "Bonjour Client");
		return "client";
	}
}
