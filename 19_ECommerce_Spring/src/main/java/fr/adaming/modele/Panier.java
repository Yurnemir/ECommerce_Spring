package fr.adaming.modele;

import java.util.List;
/**
 * Classe repr�sentant l'ensemble des produits command�s par le client.
 * Elle n'a pas d'attribut propre mais est en association avec une liste de ligne de commande.
 * @see LigneCommande
 * @author inti0236
 *
 */
public class Panier {

	//Pas d'attributs propres
	//Association avec ligne de commande
	private List<LigneCommande> listeLignesCommande;

	// Constructeur vide par d�faut.
	
	//Getters/Setters

	public List<LigneCommande> getListeLignesCommande() {
		return listeLignesCommande;
	}

	public void setListeLignesCommande(List<LigneCommande> listeLignesCommande) {
		this.listeLignesCommande = listeLignesCommande;
	}
	
	
	
}
