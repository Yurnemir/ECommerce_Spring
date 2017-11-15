package fr.adaming.service;

import java.util.List;

import fr.adaming.modele.Categorie;
import fr.adaming.modele.Client;
import fr.adaming.modele.Commande;
import fr.adaming.modele.Panier;
import fr.adaming.modele.Produit;

public interface IClientService {

	public Client getClientByInfo(Client c); 
	public Client getClientById(Client c);
	public Client createClient(Client c);
	public Client modifClient(Client c);
	public boolean deleteClient (Client c);
	
	
}
