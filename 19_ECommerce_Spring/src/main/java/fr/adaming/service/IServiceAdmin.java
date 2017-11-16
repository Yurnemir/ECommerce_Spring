package fr.adaming.service;

import fr.adaming.modele.Administrateur;

public interface IServiceAdmin {
	public Administrateur getAdminByName(String name);
	public Administrateur connexionAdmin(Administrateur administrateur);
}
