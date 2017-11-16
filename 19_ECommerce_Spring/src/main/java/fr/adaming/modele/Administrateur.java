package fr.adaming.modele;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Classe représentant les informations d'un administrateur c'est à dire une personne chargée de la 
 * gestion du site.
 * Elle possède les attributs suivant : 
 * idAdmin : id de l'admin dans la base de données
 * identifiant : pseudo utilisé par l'utilisateur pour ce connecté
 * mdp : mot de passe utilisé pour se connecter
 * actif : permet de vérifier si l'utilisateur est considéré comme actif au sens de Spring Security.
 * @author inti0236
 *Elle possède aussi une liste de role permettant de savoir quel role (au sens de Spring Security) joue cet administrateur.
 */
@Entity
@Table(name="administrateurs")
public class Administrateur {
	// Attributs

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_a")
	private int idAdmin;
	private String identifiant;
	private String mdp;
	
	private boolean actif;
	
	@OneToMany(mappedBy="administrateur")
	@Column(name="rolename")
	private List<Role> listeRoles;

	
	// Constructeurs
	public Administrateur() {
		super();
	}
	public Administrateur(String identifiant, String mdp) {
		super();
		this.identifiant = identifiant;
		this.mdp = mdp;
	}
	public Administrateur(int idAdmin, String identifiant, String mdp) {
		super();
		this.idAdmin = idAdmin;
		this.identifiant = identifiant;
		this.mdp = mdp;
	}
	// Getters/Setters
	public int getIdAdmin() {
		return idAdmin;
	}
	public void setIdAdmin(int idAdmin) {
		this.idAdmin = idAdmin;
	}
	public String getIdentifiant() {
		return identifiant;
	}
	public void setIdentifiant(String identifiant) {
		this.identifiant = identifiant;
	}
	public String getMdp() {
		return mdp;
	}
	public void setMdp(String mdp) {
		this.mdp = mdp;
	}
	//toString
	@Override
	public String toString() {
		return "Administrateur [idAdmin=" + idAdmin + ", identifiant=" + identifiant + ", mdp=" + mdp + "]";
	}
	
	
}
