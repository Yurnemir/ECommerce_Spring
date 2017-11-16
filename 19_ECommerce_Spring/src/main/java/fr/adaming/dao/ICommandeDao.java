package fr.adaming.dao;

import java.util.List;

import fr.adaming.modele.Client;
import fr.adaming.modele.Commande;
import fr.adaming.modele.Panier;
import fr.adaming.modele.Produit;

public interface ICommandeDao {

	public Commande enregistrementCommande(Commande commande);
	public Commande getCommandeById(Commande commande);
	public boolean deleteCommande(Commande commande);
	public List<Commande> getCommandesByClient(Client client);
	public List<Commande> getAllCommandes();
	
}
