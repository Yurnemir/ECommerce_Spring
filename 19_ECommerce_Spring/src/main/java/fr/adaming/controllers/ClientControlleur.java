package fr.adaming.controllers;
/**
 * Controleur pour la consultation des infos par le client
 */
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
	
	public void setServiceClient(IClientService serviceClient) {
		this.serviceClient = serviceClient;
	}
	public void setServiceCommande(ICommandeService serviceCommande) {
		this.serviceCommande = serviceCommande;
	}
	public void setServiceLigneCommande(ILigneCommandeService serviceLigneCommande) {
		this.serviceLigneCommande = serviceLigneCommande;
	}
	

	/**
	 * Affichage du formulaire pour que le client puisse voir toutes ses commandes
	 * @return Page du formulaire
	 */
	@RequestMapping(value="/afficheClient")
	public ModelAndView affichageFormCommandeClient(){
		return new ModelAndView("client", "clientChercheCommande", new Client());
	}
	
	/**
	 * Méthodes pour envoyer l'ensemble des commandes du client sur la page
	 * @param client Client qui cherche à connaitre ses commandes
	 * @return On reste sur la même page mais le modele contient maitenant l'ensemble des commandes du client
	 */
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
	
	/**
	 * On liste tous les clients enregistré
	 * @return
	 */
	@RequestMapping(value="/client/recap")
	private ModelAndView listeClientPourAdmin(){
		List<Client> listeClients = serviceClient.listerClient();
		return new ModelAndView("client_liste", "listeClient", listeClients);
	}
	
	/**
	 *  On liste tous les clients enregistré et on leur associe leurs commandes.
	 * @return 
	 */
	@RequestMapping(value="/admin/categorie/clients/recapCommande")
	private ModelAndView listeCommandesClients(){
		System.out.println("Liste des clients/Commandes");
		List<Client> listeClients = serviceClient.listerClient();
		
		for(Client client: listeClients){
			client.setCommandes(serviceCommande.getCommandesByClient(client));
			for (Commande commande :client.getCommandes()){
				commande.setListeLigneCommande(serviceLigneCommande.listeLigneCommandeParCommande(commande));
				//System.out.println(commande.getListeLigneCommande());
			}
			System.out.println(client.getCommandes());
		}
		return new ModelAndView("commande_client", "listeClient", listeClients);
	}
}
