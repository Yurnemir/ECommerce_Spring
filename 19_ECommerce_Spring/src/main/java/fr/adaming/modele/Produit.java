package fr.adaming.modele;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="produits")
public class Produit {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idProduit;
	private String designation;
	private String description;
	private double prix;
	private int quantite;
	private String image;
	@Column(columnDefinition="TINYINT(1)")
	private boolean selectionne;
	@Lob
	private byte[] imageFichier;
	
	// Association avec la catégorie
	@ManyToOne
	@JoinColumn(name="id_categorie",referencedColumnName="id_Categorie")
	private Categorie categorie;
	
	// Association avec ligne de commande
	//(Ligne commande n'est pas stocké dans la base de données il ne faut pas stocker l'attribut de l'association)
	@Transient
	private List<LigneCommande> listeLigneCommande;
	
	
	// Constructeur vides
	public Produit() {
		super();
	}

	

//	public Produit(String designation, String description, double prix, int quantite, String image,
//			boolean selectionne) {
//		super();
//		this.designation = designation;
//		this.description = description;
//		this.prix = prix;
//		this.quantite = quantite;
//		this.image = image;
//		this.selectionne = selectionne;
//	}
//
//	public Produit(int idProduit, String designation, String description, double prix, int quantite, String image,
//			boolean selectionne) {
//		super();
//		this.idProduit = idProduit;
//		this.designation = designation;
//		this.description = description;
//		this.prix = prix;
//		this.quantite = quantite;
//		this.image = image;
//		this.selectionne = selectionne;
//	}

	public Produit(String designation, String description, double prix, int quantite, String image, boolean selectionne,
			byte[] imageFichier) {
		super();
		this.designation = designation;
		this.description = description;
		this.prix = prix;
		this.quantite = quantite;
		this.image = image;
		this.selectionne = selectionne;
		this.imageFichier = imageFichier;
	}

	

	public Produit(int idProduit, String designation, String description, double prix, int quantite, String image,
		boolean selectionne, byte[] imageFichier) {
	super();
	this.idProduit = idProduit;
	this.designation = designation;
	this.description = description;
	this.prix = prix;
	this.quantite = quantite;
	this.image = image;
	this.selectionne = selectionne;
	this.imageFichier = imageFichier;
}



	// Getters/Setters
	public int getIdProduit() {
		return idProduit;
	}



	public void setIdProduit(int idProduit) {
		this.idProduit = idProduit;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getPrix() {
		return prix;
	}
	public void setPrix(double prix) {
		this.prix = prix;
	}
	public int getQuantite() {
		return quantite;
	}
	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}
	public boolean isSelectionne() {
		return selectionne;
	}
	public void setSelectionne(boolean selectionne) {
		this.selectionne = selectionne;
	}
	public Categorie getCategorie() {
		return categorie;
	}
	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}
	public List<LigneCommande> getListeLigneCommande() {
		return listeLigneCommande;
	}
	public void setListeLigneCommande(List<LigneCommande> listeLigneCommande) {
		this.listeLigneCommande = listeLigneCommande;
	}
	

	public String getImage() {
		return image;
	}
	
	public byte[] getImageFichier() {
		return imageFichier;
	}



	public void setImageFichier(byte[] imageFichier) {
		this.imageFichier = imageFichier;
	}
	
	// toString









	public void setImage(String image) {
		this.image = image;
	}



	@Override
	public String toString() {
		return "Produit [idProduit=" + idProduit + ", designation=" + designation + ", description=" + description
				+ ", prix=" + prix + ", quantite=" + quantite + ", selectionne=" + selectionne + "]";
	}
	
	
	
}
