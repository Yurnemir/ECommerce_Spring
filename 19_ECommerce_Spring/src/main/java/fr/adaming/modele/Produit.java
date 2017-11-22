package fr.adaming.modele;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotEmpty;
/**
 * 
 * @author inti0236
 * Classe reprï¿½sentant un produit proposï¿½ par le magasin.
 * Un produit est dï¿½fini  par les attributs suivants : 
 * <ul>
 * <li> Un id qui permet de l'identifier dans une base de donnï¿½es
 * <li> Une designation (son nom)
 * <li> Une description plus ou mois dï¿½taillï¿½.
 * <li> Un prix
 * <li> Une quantitï¿½ qui reprï¿½sente le stock disponible
 * <li>	Le nom de son image
 * <li> Une image reprï¿½sentï¿½ par un tableau de byte
 * </ul>
 *
 *Elle est associï¿½e avec une catï¿½gorie reprï¿½sentant la catï¿½gorie ï¿½ laquelle appartient ce produit et 
 *ï¿½ une ligne de commande reprï¿½sentant la commande de ce produit
 */
@Entity
@Table(name="produits")
public class Produit {


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	/**
	 * L'id du produit. Non modifiable par l'utilisateur
	 *
	 */
	@Min(value=1, message="l'id doit être supérieur à 1")
	private int idProduit;
	/**
	 * Le nom utilisï¿½ dans le magasin pour nommer le produit. Modifiable
	 */
	@NotEmpty(message="aucune designation n'a été saisie")
	private String designation;
	/**
	 * Une description plus ou mois dï¿½taillï¿½ du produit. Modifiable
	 */
	@NotEmpty(message="aucune description n'a été saisie")
	private String description;
	/**
	 * Le prix de vente du produit. Modifiable
	 */
	@Min(value=1, message="le prix minimal est de 1€")
	private double prix;
	/**
	 * Nombre d'unitï¿½ du produit en stock. Modifiable
	 */
	private int quantite;
	private String image;
	@Column(columnDefinition="TINYINT(1)")
	private boolean selectionne;
	@Lob
	/**
	 * Image reprï¿½sentant le produit vendu comme un tableau de byte. Modifiable
	 */
	private byte[] imageFichier;
	
	// Association avec la catï¿½gorie
	@ManyToOne
	@JoinColumn(name="id_categorie",referencedColumnName="id_Categorie")
	private Categorie categorie;
	
	// Association avec ligne de commande
	//(Ligne commande n'est pas stockï¿½ dans la base de donnï¿½es il ne faut pas stocker l'attribut de l'association)
	@OneToMany(mappedBy="produit", cascade=CascadeType.ALL)
	private List<LigneCommande> listeLigneCommande;
	
	
	// Constructeur vides
	public Produit() {
		super();
	}

	


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
