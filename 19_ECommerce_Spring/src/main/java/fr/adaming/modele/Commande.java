package fr.adaming.modele;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Classe qui repr�sente une commande effectu�e par le client.
 * Elle poss�de deux attributs :
 * <ul>
 * <li> idCommande : id de la commande dans la base de donn�es
 * <li>	dateCommande : date � laquelle la commande a �t� effectu�e
 * <ul>
 * Elle est aussi en association avec une liste de ligne commande permettant de connaitre les d�tails des produits command�s.
 * @author inti0236
 *
 */

@Entity
@Table(name="commandes")
public class Commande{
	
	// Attributs
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private	int idCommande;
	private Date dateCommande ;
	
	// Association avec LigneCommande 
	//(Ligne commande n'est pas stock� dans la base de donn�es pas besoin de stocker l'attribut de l'association)
	@OneToMany(mappedBy="commande",cascade=CascadeType.REMOVE)
	private List<LigneCommande> listeLigneCommande;
	// Association avec un client
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_client",referencedColumnName="id_client")
	private Client client;
	
	// Constructeurs
	public Commande() {
		super();
	}
	public Commande(Date dateCommande) {
		super();
		this.dateCommande = dateCommande;
	}
	public Commande(int idCommande, Date dateCommande) {
		super();
		this.idCommande = idCommande;
		this.dateCommande = dateCommande;
	}
	// Getters/Setters
	public int getIdCommande() {
		return idCommande;
	}
	public void setIdCommande(int idCommande) {
		this.idCommande = idCommande;
	}
	public Date getDateCommande() {
		return dateCommande;
	}
	public void setDateCommande(Date dateCommande) {
		this.dateCommande = dateCommande;
	}
	
	public List<LigneCommande> getListeLigneCommande() {
		return listeLigneCommande;
	}
	public void setListeLigneCommande(List<LigneCommande> listeLigneCommande) {
		this.listeLigneCommande = listeLigneCommande;
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	//toString
	@Override
	public String toString() {
		return "Commande [idCommande=" + idCommande + ", dateCommande=" + dateCommande + "]";
	}
	
	public void addLigne(LigneCommande ligne){
		if (ligne != null){
			this.listeLigneCommande.add(ligne);
		}
	}
	
}
 