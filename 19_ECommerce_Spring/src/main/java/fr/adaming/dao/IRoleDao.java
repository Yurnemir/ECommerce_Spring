package fr.adaming.dao;

import fr.adaming.modele.Administrateur;
import fr.adaming.modele.Role;

public interface IRoleDao {
	public Role getRoleByAdmin(Administrateur admin);
}
