package fr.adaming.service;

import java.util.List;

import fr.adaming.modele.Categorie;
import fr.adaming.modele.Client;
import fr.adaming.modele.Commande;
import fr.adaming.modele.Panier;
import fr.adaming.modele.Produit;

public interface IClientService {

	public List<Categorie> getAllCategories() ; 
	public List<Produit> getAllProduits() ;
	public List<Produit> getAllProduitByCategorie(Categorie c) ; 
	public List<Produit> getProduitsSelect(List<Produit> lp) ; 
	public List<Produit> getProduitsByMot(String mot) ; 
	public Produit addProduitPanier(Produit p, int quantite, Panier pan) ;
	public int deleteProduitPanier(Produit p, Panier pan) ; 
	public Client enregitrementClient(Client c) ; 
	public Commande enregistrementCommande(Commande commande);
	public Client recuperClient(Client c);
	
	
}
