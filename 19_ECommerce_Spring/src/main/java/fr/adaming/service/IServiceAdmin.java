package fr.adaming.service;

import fr.adaming.dao.IAdminDao;
import fr.adaming.modele.Administrateur;

public interface IServiceAdmin {

	public Administrateur connexionAdmin(Administrateur administrateur);	
}
