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

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
/**
 * 
 * Cette classe repr�sente une cat�gorie de produit c'est � dire un type de produit propos� au client.
 * Elle poss�de les attributs suivants :
 * <ul>
 * <li> idCategorie : Il s'agit de l'id de la cat�gorie dans la base de donn�es
 * <li>	nomCategorie : Le nom de la cat�gorie tel qu'affich� sur le site
 * <li> description : Une description plus ou moins d�taill�e de la cat�gorie
 * </ul>
 * Elle est aussi en association avec une liste de Produit repr�sentant l'ensemble des produits appartenant � cette cat�gorie.
 * @author inti0236
 *
 */
@Entity
@Table(name="categories")
public class Categorie {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_Categorie")
	private int idCategorie;
	private String nomCategorie;
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
