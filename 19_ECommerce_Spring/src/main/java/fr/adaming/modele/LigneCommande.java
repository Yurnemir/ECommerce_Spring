package fr.adaming.modele;

import javax.persistence.Transient;

public class LigneCommande {
	
	// attributs
	private int quantite ; 
	private double prix ;
	
	//Association avec produits (Ligne commande n'est pas stocké dans la base de données)
	@Transient
	private Produit produit;
	//Association avec Commande (Ligne commande n'est pas stocké dans la base de données)	
	@Transient
	private Commande commande;
	
	
	// constructeurs
	public LigneCommande() {
		super();
	}


	public LigneCommande(int quantite, double prix) {
		super();
		this.quantite = quantite;
		this.prix = prix;
	}

	//getters-setters
	public int getQuantite() {
		return quantite;
	}


	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}


	public double getPrix() {
		return prix;
	}


	public void setPrix(double prix) {
		this.prix = prix;
	}

	
	
	public Produit getProduit() {
		return produit;
	}


	public void setProduit(Produit produit) {
		this.produit = produit;
	}


	public Commande getCommande() {
		return commande;
	}


	public void setCommande(Commande commande) {
		this.commande = commande;
	}


	//toString
	@Override
	public String toString() {
		return "LigneCommande [quantite=" + quantite + ", prix=" + prix + "]";
	} 
	
	
	
}
