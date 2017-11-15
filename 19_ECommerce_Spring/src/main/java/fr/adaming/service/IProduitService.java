package fr.adaming.service;

import java.util.List;



import fr.adaming.modele.Produit;

public interface IProduitService {

	public List<Produit> listerProduits();

	public Produit rechercherProduitAvecId(Produit produit);
	
	
	public Produit ajouterProduit(Produit produit);
	
	public void supprimerProduit(Produit produit);
	
	public Produit modifierProduit(Produit produit);
	
	public int assoicierImageProduit(Produit produit);

}
