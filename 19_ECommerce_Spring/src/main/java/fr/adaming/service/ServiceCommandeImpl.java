package fr.adaming.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.adaming.dao.ICommandeDao;
import fr.adaming.modele.Client;
import fr.adaming.modele.Commande;

@Service("commandeService")
@Transactional
public class ServiceCommandeImpl implements ICommandeService{

	@Autowired
	private ICommandeDao daoCommande;
	
	@Override
	public Commande enregistrementCommande(Commande commande) {
		return daoCommande.enregistrementCommande(commande);
	}

	@Override
	public Commande getCommandeById(Commande commande) {
		return daoCommande.getCommandeById(commande);
	}

	@Override
	public boolean deleteCommande(Commande commande) {
		return daoCommande.deleteCommande(commande);
	}

	@Override
	public List<Commande> getCommandesByClient(Client client) {
		return daoCommande.getCommandesByClient(client);
	}

	@Override
	public List<Commande> getAllCommandes() {
		return daoCommande.getAllCommandes();
	}

}
