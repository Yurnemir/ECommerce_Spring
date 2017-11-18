package fr.adaming.service;

import java.util.List;

import fr.adaming.modele.Client;
import fr.adaming.modele.Commande;

public interface ICommandeService {


	public Commande enregistrementCommande(Commande commande);
	public Commande getCommandeById(Commande commande);
	public boolean deleteCommande(Commande commande);
	public List<Commande> getCommandesByClient(Client client);
	public List<Commande> getAllCommandes();

	
}
