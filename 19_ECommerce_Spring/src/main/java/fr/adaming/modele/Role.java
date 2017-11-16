package fr.adaming.modele;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
/**
 * 
 * @author inti0236
 * Classe qui permet de d�crire les r�les de Spring Security.
 * Classe contenant kes attributs suivants : 
 * <ul>
 * 	<li> Un id qui n'est pas modifiable
 * 	<li> Le nom du role qui est utilis� par Spring Security. Non modifiable
 * </ul>
 * 
 * Elle est aussi en instanciation avec un admistrateur. Cela permet de d�terminer quel administrateur a quel role.
 */
@Entity
@Table(name="roles")
public class Role {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_role")
	private int id;
	private String nomRole;
	@ManyToOne
	@JoinColumn(name="id_admin")
	private Administrateur administrateur;
	
	public Role() {
		super();
	}
	public Role(String nomRole) {
		super();
		this.nomRole = nomRole;
	}
	public Role(int id, String nomRole) {
		super();
		this.id = id;
		this.nomRole = nomRole;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNomRole() {
		return nomRole;
	}
	public void setNomRole(String nomRole) {
		this.nomRole = nomRole;
	}
	public Administrateur getAdministrateur() {
		return administrateur;
	}
	public void setAdministrateur(Administrateur administrateur) {
		this.administrateur = administrateur;
	}
	
	@Override
	public String toString() {
		return "Role [id=" + id + ", nomRole=" + nomRole + "]";
	}
}
