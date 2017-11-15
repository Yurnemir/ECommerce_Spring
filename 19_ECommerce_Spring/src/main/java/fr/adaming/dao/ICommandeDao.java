package fr.adaming.dao;

import fr.adaming.modele.Commande;
import fr.adaming.modele.Panier;
import fr.adaming.modele.Produit;

public interface ICommandeDao {

	public Commande enregistrementCommande(Commande commande);
	public Produit addProduitPanier(Produit p, int quantite, Panier pan) ;
	public int deleteProduitPanier(Produit p, Panier pan) ; 
	
}
