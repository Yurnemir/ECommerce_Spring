package fr.adaming.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.adaming.dao.ILigneCommandeDao;
import fr.adaming.modele.Commande;
import fr.adaming.modele.LigneCommande;
@Service
@Transactional
public class ServiceLigneCommandeImpl implements ILigneCommandeService{

	@Autowired
	private ILigneCommandeDao daoLigneCommande;
	
	@Override
	public LigneCommande enregistrerLigneCommande(LigneCommande ligne) {

		return daoLigneCommande.enregistrerLigneCommande(ligne);
	}

	@Override
	public List<LigneCommande> listeLigneCommandeParCommande(Commande commande) {
		return daoLigneCommande.listeLigneCommandeParCommande(commande);
	}

}
