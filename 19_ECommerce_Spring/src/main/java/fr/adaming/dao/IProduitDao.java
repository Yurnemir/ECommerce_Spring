package fr.adaming.dao;

import java.util.List;

import fr.adaming.modele.Categorie;
import fr.adaming.modele.Produit;

public interface IProduitDao {

	public List<Produit> listerProduits();
	
	public Produit rechercherProduitAvecId(Produit produit);
	
	public Produit ajouterProduit(Produit produit);
	
	public void supprimerProduit(Produit produit);
	
	public Produit modifierProduit(Produit produit);
	
	public int assoicierImageProduit(Produit produit);

	List<Produit> getProduitsByCategorie(Categorie c);

	List<Produit> getSelectedProduit();

	List<Produit> getProduitsByMot(String mot);
	
}
