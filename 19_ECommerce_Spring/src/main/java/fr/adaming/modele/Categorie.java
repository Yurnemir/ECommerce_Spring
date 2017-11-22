package fr.adaming.modele;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Min;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.NotEmpty;
/**
 * 
 * Cette classe reprï¿½sente une catï¿½gorie de produit c'est ï¿½ dire un type de produit proposï¿½ au client.
 * Elle possï¿½de les attributs suivants :
 * <ul>
 * <li> idCategorie : Il s'agit de l'id de la catï¿½gorie dans la base de donnï¿½es
 * <li>	nomCategorie : Le nom de la catï¿½gorie tel qu'affichï¿½ sur le site
 * <li> description : Une description plus ou moins dï¿½taillï¿½e de la catï¿½gorie
 * </ul>
 * Elle est aussi en association avec une liste de Produit reprï¿½sentant l'ensemble des produits appartenant ï¿½ cette catï¿½gorie.
 * @author inti0236
 *
 */
@Entity
@Table(name="categories")
public class Categorie {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_Categorie")
	@Min(value=1, message="l'id doit être supérieur à 1")
	private int idCategorie;
	@NotEmpty(message="aucun nom n'a été saisi")
	private String nomCategorie;
	@NotEmpty(message="aucune description n'a été saisie")
	private String description;
	
	//Association avec la liste de produits
	@OneToMany(mappedBy="categorie",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	//@OneToMany(mappedBy="categorie")
	private List<Produit> listeProduits;
	
	public Categorie() {
		super();
	}

	public Categorie(String nomCategorie, String description) {
		super();
		this.nomCategorie = nomCategorie;
		this.description = description;
	}

	public Categorie(int idCategorie, String nomCategorie, String description) {
		super();
		this.idCategorie = idCategorie;
		this.nomCategorie = nomCategorie;
		this.description = description;
	}
	
	// Getters/Setters

	public int getIdCategorie() {
		return idCategorie;
	}

	public void setIdCategorie(int idCategorie) {
		this.idCategorie = idCategorie;
	}

	public String getNomCategorie() {
		return nomCategorie;
	}

	public void setNomCategorie(String nomCategorie) {
		this.nomCategorie = nomCategorie;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	public List<Produit> getListeProduits() {
		return listeProduits;
	}

	public void setListeProduits(List<Produit> listeProduits) {
		this.listeProduits = listeProduits;
	}
	
	// toString

	@Override
	public String toString() {
		return "Categorie [idCategorie=" + idCategorie + ", nomCategorie=" + nomCategorie + ", description="
				+ description + "]";
	}
	
	

}
