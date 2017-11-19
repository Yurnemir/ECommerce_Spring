package fr.adaming.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.adaming.dao.IClientDao;
import fr.adaming.modele.Categorie;
import fr.adaming.modele.Client;
import fr.adaming.modele.Commande;
import fr.adaming.modele.Panier;
import fr.adaming.modele.Produit;


@Service("cService")
@Transactional
public class ClientServiceImpl implements IClientService {

	@Autowired
	private IClientDao clientDao ;
	
	
	
	public void setClientDao(IClientDao clientDao) {
		this.clientDao = clientDao;
	}

	@Override
	public Client getClientByInfo(Client c) {
		return clientDao.getClientByInfo(c);
	}

	@Override
	public Client getClientById(Client c) {
		return clientDao.getClientById(c);
	}

	@Override
	public Client createClient(Client c) {
		return clientDao.createClient(c);
	}

	@Override
	public Client modifClient(Client c) {
		return clientDao.modifClient(c);
	}

	@Override
	public boolean deleteClient(Client c) {
		return clientDao.deleteClient(c);
	}

	@Override
	public List<Client> listerClient() {
		return clientDao.listerClient();
	}

	@Override
	public List<Client> rechercheClientMail(Client c) {
		return clientDao.rechercheClientMail(c);
	}



}
