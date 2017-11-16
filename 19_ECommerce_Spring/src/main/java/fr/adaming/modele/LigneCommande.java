package fr.adaming.modele;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
/**
 * Classe qui repr�sente la quantit� et le prix total command� par le client pour un produit donn�es.
 * Elle contient deux attributs : 
 * <ul>
 * <li> quantite : qui repr�sente le nombre d'objets command�
 * <li> prix : repr�sente le prix total demand� pour ces objets
 * <ul>
 * Elle est aussi en association avec un produit repr�sentant le produit achet�.
 * @author inti0236
 *
 */
@Entity
@Table(name="lignesCommande")
public class LigneCommande {
	
	// attributs
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idLigne;
	private int quantite ; 
	private double prix ;
	
	//Association avec produits (Ligne commande n'est pas stock� dans la base de donn�es)
	@ManyToOne
	@JoinColumn(name="produit_id", referencedColumnName="idProduit")
	private Produit produit;
	//Association avec Commande (Ligne commande n'est pas stock� dans la base de donn�es)	
	@ManyToOne
	@JoinColumn(name="commande_id", referencedColumnName="idCommande")
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
