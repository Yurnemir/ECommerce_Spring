package fr.adaming.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * Controleur des opérations liés au client
 * @author inti0236
 *
 */
import org.springframework.web.servlet.ModelAndView;

import fr.adaming.modele.Client;
import fr.adaming.modele.Commande;
import fr.adaming.modele.LigneCommande;
import fr.adaming.service.IClientService;
import fr.adaming.service.ICommandeService;
import fr.adaming.service.ILigneCommandeService;
@Controller
public class ClientControlleur {

	@Autowired
	private IClientService serviceClient;
	@Autowired
	private ICommandeService serviceCommande;
	@Autowired
	private ILigneCommandeService serviceLigneCommande;
	
	
	@RequestMapping(value="/afficheClient")
	public ModelAndView affichageFormCommandeClient(){
		return new ModelAndView("client","clientChercheCommande",new Client());
	}
	@RequestMapping(value="affichageCommande")
	private ModelAndView affichageCommande(@ModelAttribute("clientChercheCommande")Client client){
		//On reste toujours sur cette page.
		ModelAndView modeleVue = new ModelAndView("client","clientChercheCommande",new Client());
		
		System.out.println(client);
		Client clientRecup = serviceClient.getClientByInfo(client);
		System.out.println(clientRecup);
		if(clientRecup==null){
			System.out.println("Rien trouve");
			modeleVue.addObject("messageErreur","Aucun client ne correspond à ces informations");
			return modeleVue;
		}
		
		//Récupération des commandes.
		List<Commande> listeCommande = serviceCommande.getCommandesByClient(clientRecup);
		System.out.println(listeCommande);
		for(Commande commande: listeCommande){
			commande.setListeLigneCommande(serviceLigneCommande.listeLigneCommandeParCommande(commande));
		}
		for(Commande commande : listeCommande){
		}
		
		//On ajoute la liste des commandes au modele
		modeleVue.addObject("listeCommande",listeCommande);
		return modeleVue;
	}
	
}
