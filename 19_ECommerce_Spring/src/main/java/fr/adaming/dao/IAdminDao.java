package fr.adaming.dao;

import fr.adaming.modele.Administrateur;

public interface IAdminDao {
	/**
	 * verifie l'existence de l'administrateur dans la db
	 * @param administrateur administrateur à chercher dans la db
	 * @return administrateur présent dans la db
	 */
	public Administrateur connexionAdmin(Administrateur administrateur);
	
}
