package fr.adaming.modele;

import java.util.List;

public class Panier {

	//Pas d'attributs propres
	//Association avec ligne de commande
	private List<LigneCommande> listeLignesCommande;

	// Constructeur vide par défaut.
	
	//Getters/Setters

	public List<LigneCommande> getListeLignesCommande() {
		return listeLignesCommande;
	}

	public void setListeLignesCommande(List<LigneCommande> listeLignesCommande) {
		this.listeLignesCommande = listeLignesCommande;
	}
	
	
	
}
