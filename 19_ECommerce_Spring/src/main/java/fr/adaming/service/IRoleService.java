package fr.adaming.service;

import fr.adaming.modele.Administrateur;
import fr.adaming.modele.Role;

public interface IRoleService {
	public Role getRoleByAdmin(Administrateur admin);
}
