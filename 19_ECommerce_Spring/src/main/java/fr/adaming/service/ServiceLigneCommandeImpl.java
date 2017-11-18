package fr.adaming.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.adaming.dao.ILigneCommandeDao;
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

}
