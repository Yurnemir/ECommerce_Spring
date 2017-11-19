package fr.adaming.dao;

import java.util.List;

import fr.adaming.modele.Commande;
import fr.adaming.modele.LigneCommande;

public interface ILigneCommandeDao {

	public LigneCommande enregistrerLigneCommande(LigneCommande ligne);
	public List<LigneCommande> listeLigneCommandeParCommande(Commande commande);
	
}
