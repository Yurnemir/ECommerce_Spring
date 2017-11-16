package fr.adaming.dao;

import fr.adaming.modele.Administrateur;

public interface IAdminDao {
	public Administrateur getAdminByName(String name);
	public Administrateur connexionAdmin(Administrateur administrateur);
}
