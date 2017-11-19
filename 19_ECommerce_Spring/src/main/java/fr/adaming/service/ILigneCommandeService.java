package fr.adaming.service;

import java.util.List;

import fr.adaming.modele.Commande;
import fr.adaming.modele.LigneCommande;

public interface ILigneCommandeService {

	public LigneCommande enregistrerLigneCommande(LigneCommande ligne);
	public List<LigneCommande> listeLigneCommandeParCommande(Commande commande);

}
